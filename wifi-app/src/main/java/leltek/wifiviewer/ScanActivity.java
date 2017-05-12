package leltek.wifiviewer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import com.corelibs.utils.ToastMgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import leltek.viewer.model.Probe;
import leltek.viewer.model.WifiProbe;

public class ScanActivity extends AppCompatActivity
        implements Probe.ScanListener, Probe.CineBufferListener,
        Probe.BatteryListener, Probe.TemperatureListener {
    final static Logger logger = LoggerFactory.getLogger(ScanActivity.class);

    private Probe probe;
    private Button mToggleScan;
    private Button mBMode;
    private Button mCMode;
    private Button mFit;
    private UsImageView mImageView;
    private TextView mCineBufferCount;
    private Button mTestConnectionError;
    private Button mTestOverHeated;
    private Button mTestBatteryLow;
    private SeekBar mSeekBarGain;
    private SeekBar mSeekBarDr;
    private SeekBar mSeekBarTgc1;
    private SeekBar mSeekBarTgc2;
    private SeekBar mSeekBarTgc3;
    private SeekBar mSeekBarTgc4;
    private SeekBar mSeekBarPersistence;
    private SeekBar mSeekBarEnhancement;
    private Button mResetAllTgc;
    private NumberPicker mNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.debug("onCreate() called");
        setContentView(R.layout.activity_scan);

        probe = WifiProbe.getDefault();
        probe.setScanListener(this);
        probe.setCineBufferListener(this);
        probe.setBatteryListener(this);
        probe.setTemperatureListener(this);

        mToggleScan = (Button) findViewById(R.id.toogle_scan);
        mToggleScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (probe.isLive())
                    probe.stopScan();
                else {
                    probe.startScan();
                }
            }
        });

        mBMode = (Button) findViewById(R.id.bMode);
        mBMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                probe.swithToBMode();
            }
        });

        mCMode = (Button) findViewById(R.id.cMode);
        mCMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                probe.swithToCMode();
            }
        });

        mFit = (Button) findViewById(R.id.fit);
        mFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFit();
            }
        });

        mImageView = (UsImageView) findViewById(R.id.image_view);

        mCineBufferCount = (TextView) findViewById(R.id.cine_buffer_count);

        mTestConnectionError = (Button) findViewById(R.id.test_conn_error);
        mTestConnectionError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WifiProbe) probe).testConnectionClosed();
            }
        });

        mTestOverHeated = (Button) findViewById(R.id.test_over_heated);
        mTestOverHeated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WifiProbe) probe).testOverHeated();
            }
        });


        mTestBatteryLow = (Button) findViewById(R.id.test_battery_low);
        mTestBatteryLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WifiProbe) probe).testBatteryLevelTooLow();
            }
        });

        mSeekBarGain = (SeekBar) findViewById(R.id.seekBarGain);
        mSeekBarGain.setProgress(probe.getGain());
        mSeekBarGain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setGain(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarDr = (SeekBar) findViewById(R.id.seekBarDr);
        mSeekBarDr.setProgress(probe.getDr());
        mSeekBarDr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setDr(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mSeekBarTgc1 = (SeekBar) findViewById(R.id.seekBarTgc1);
        mSeekBarTgc1.setProgress(probe.getTgc1());
        mSeekBarTgc1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setTgc1(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarTgc2 = (SeekBar) findViewById(R.id.seekBarTgc2);
        mSeekBarTgc2.setProgress(probe.getTgc2());
        mSeekBarTgc2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setTgc2(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarTgc3 = (SeekBar) findViewById(R.id.seekBarTgc3);
        mSeekBarTgc3.setProgress(probe.getTgc3());
        mSeekBarTgc3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setTgc3(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarTgc4 = (SeekBar) findViewById(R.id.seekBarTgc4);
        mSeekBarTgc4.setProgress(probe.getTgc4());
        mSeekBarTgc4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setTgc4(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mResetAllTgc = (Button) findViewById(R.id.resetAllTgc);
        mResetAllTgc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                probe.resetAllTgc();
                mSeekBarTgc1.setProgress(probe.getTgc1());
                mSeekBarTgc2.setProgress(probe.getTgc2());
                mSeekBarTgc3.setProgress(probe.getTgc3());
                mSeekBarTgc4.setProgress(probe.getTgc4());
            }
        });

        mSeekBarPersistence = (SeekBar) findViewById(R.id.seekBarPersistence);
        mSeekBarPersistence.setProgress(probe.getPersistence());
        mSeekBarPersistence.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setPersistence(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarEnhancement = (SeekBar) findViewById(R.id.seekBarEnhancement);
        mSeekBarEnhancement.setProgress(probe.getEnhanceLevel());
        mSeekBarEnhancement.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                probe.setEnhanceLevel(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mNumberPicker = (NumberPicker) findViewById(R.id.np);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(probe.getGrayMapMaxValue());
        mNumberPicker.setWrapSelectorWheel(false);
        mNumberPicker.setValue(probe.getGrayMap());
        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                probe.setGrayMap(newVal);
            }
        });
    }

    private void switchFit() {
        mImageView.switchFit();
        if (mImageView.isFitWidth()) {
            mFit.setText(R.string.fit_height);
        } else {
            mFit.setText(R.string.fit_width);
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

        probe.stopScan();
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
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.debug("onResume() called");
        if (probe.getMode() == Probe.EnumMode.MODE_C) {
            probe.swithToBMode();
        } if (probe.getMode() == Probe.EnumMode.MODE_B && !probe.isLive()) {
            probe.startScan();
        }
    }

    @Override
    public void onModeSwitched(Probe.EnumMode mode) {
        if (mode == Probe.EnumMode.MODE_B) {
            ToastMgr.show("switched to B mode");
        }
        else if (mode == Probe.EnumMode.MODE_C) {
            ToastMgr.show("switched to Color mode");
        }
    }

    @Override
    public void onModeSwitchingError() {
        ToastMgr.show("Switch mode failed");
    }

    @Override
    public void onConnectionError() {
        ToastMgr.show("Connection Closed");
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onScanStarted() {
        mToggleScan.setText(R.string.freeze);

        ToastMgr.show("Scan Started");
    }

    @Override
    public void onScanStopped() {
        mToggleScan.setText(R.string.live);

        ToastMgr.show("Scan Stopped");
    }

    @Override
    public void onNewFrameReady(Probe.Frame frame, Bitmap bitmap) {
        if (frame.mode == probe.getMode()) {
            mImageView.setImageBitmap(bitmap);
        }

        Probe.CModeFrameData cModeFrameData = frame.cModeFrameData;
        if (cModeFrameData != null) {
            mImageView.setParams(cModeFrameData.originXPx, cModeFrameData.originYPx,
                    cModeFrameData.rPx);
        }
     }

    /**
     * 設定depth成功
     *
     * @param newDepth newDepth
     */
    @Override
    public void onDepthSet(Probe.EnumDepth newDepth) {

    }

    @Override
    public void onDepthSetError() {

    }

    @Override
    public void onImageBufferOverflow() {
        ToastMgr.show("This hardware is not capable of image processing.");
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, ScanActivity.class);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //probe.startScan();
    }

    @Override
    public void onCineBufferCountIncreased(int newCineBufferCount) {
        mCineBufferCount.setText(String.valueOf(newCineBufferCount));
    }

    @Override
    public void onCineBufferCleared() {
        mCineBufferCount.setText(String.valueOf(0));
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
}
