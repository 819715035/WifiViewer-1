package leltek.wifiviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.corelibs.utils.ToastMgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import leltek.viewer.model.Probe;
import leltek.viewer.model.WifiProbe;

public class MainActivity extends AppCompatActivity
        implements Probe.SystemListener, Probe.InfoListener {
    final static Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private Button mConnectButton;
    private Button mBModeButton;
    private Probe probe;
    private static String cfgRoot = "cfg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.debug("onCreate() called");
        setContentView(R.layout.activity_main);

        ToastMgr.init(getApplicationContext());

        probe = WifiProbe.init(cfgRoot, getAssets(), this);
        probe.setSystemListener(this);
        probe.setInfoListener(this);

        mConnectButton = (Button) findViewById(R.id.connect_button);
        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (probe.isConnected()) {
                    ToastMgr.show("already connected");
                    return;
                }

                if (!probe.isRequesting()) {
                    ToastMgr.show("connecting to probe");
                    probe.initialize();
                }
            }
        });
        mBModeButton = (Button) findViewById(R.id.scan_button);
        mBModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (probe.isRequesting()) {
                    ToastMgr.show("Processing previous request.");
                    return;
                } else if (!probe.isConnected()) {
                    ToastMgr.show("Not Connected");
                    return;
                }

                Intent intent = ScanActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        if (probe.isRequesting()) {
            ToastMgr.show("Processing previous request.");
            return;
        } else if (!probe.isConnected()) {
            ToastMgr.show("connecting to probe");
            probe.initialize();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        logger.debug("onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logger.debug("onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logger.debug("onDestroy() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logger.debug("onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.debug("onResume() called");
    }

    @Override
    public void onInitialized() {
        ToastMgr.show("connected");
    }

    @Override
    public void onInitializationError(String message) {
        ToastMgr.show("connect failed: " + message);
    }

    @Override
    public void onInitialingLowVoltageError(String message) {
        ToastMgr.show(message);
    }

    @Override
    public void onSystemError(String message) {
        ToastMgr.show("System Error: " + message);
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public void onBatteryLevelChanged(int newBatteryLevel) {
        // update battey level displayed on UI
    }

    @Override
    public void onBatteryLevelTooLow(int BatteryLevel) {
        ToastMgr.show("Battery level too low, now is " + BatteryLevel + "%");
    }

    @Override
    public void onTemperatureChanged(int newTemperature) {
        // update temperature displayed on UI
    }

    @Override
    public void onTemperatureOverHeated(int temperature) {
        ToastMgr.show("Temperature over heated, now is " + temperature + " °");
    }
    
    @Override
    public void onButtonPressed(int button) {
        ToastMgr.show("Button pressed.");
    }

    @Override
    public void onButtonReleased(int button) {
        ToastMgr.show("Button released.");
    }

}
