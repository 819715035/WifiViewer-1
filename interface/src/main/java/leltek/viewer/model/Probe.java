package leltek.viewer.model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Probe interface
 */
public interface Probe {

    /**
     * SystemListener
     */
    interface SystemListener {

        /**
         * 初始成功
         */
        void onInitialized();

        /**
         * 初始失敗
         */
        void onInitializationError(String message);

        /**
         * 系統錯誤
         */

        void onSystemError(String message);
    }

    /**
     * 設定InitializeListener
     * @param systemListener
     */
    void setSystemListener(SystemListener systemListener);


    /**
     * 執行初始化：讀assets下cfg目錄的設定檔，目連結device。
     * SystemListener.onInitialized()表示初始成功
     * SystemListener.onInitializationError表示初始失敗
     */
    void initialize();

    /**
     *
     * @return true 如果已經連上device
     */
    boolean isConnected();

    /**
     * BatteryListener
     */
    interface BatteryListener {

        /**
         * device battery level發生改變，可用來更新UI的battery level
         * @param newBatteryLevel
         */
        void onBatteryLevelChanged(int newBatteryLevel);

        /**
         * battery level太低，可用來提醒user
         * @param BatteryLevel
         */
        void onBatteryLevelTooLow(int BatteryLevel);
    }

    /**
     * 設定BatteryListener
     * @param batteryListener
     */
    void setBatteryListener(BatteryListener batteryListener);

    /**
     *
     * @return 0~100 (%), null代表尚未取得device的值
     */
    Integer getBatteryLevel();

    /**
     * TemperatureListener
     */
    interface TemperatureListener {

        /**
         * device溫度發生改變，可用來更新UI的temperature
         * @param newTemperature
         */
        void onTemperatureChanged(int newTemperature);

        /**
         * device溫度太高，可用來提醒user
         * @param temperature
         */
        void onTemperatureOverHeated(int temperature);
    }

    /**
     * 設定TemperatureListener
     * @param temperatureListener
     */
    void setTemperatureListener(TemperatureListener temperatureListener);

    /**
     *
     * @return 0~100 (°C), null代表尚未取得device的值
     */
    Integer getTemperature();

    /**
     * Frame class
     */
    class Frame {

        public String mode;
        public Date date;
        public int frameRate;

        public float freq;
        public int depth;
        public int gain;
        public int dr;
        public int grayMap;
        public int persistence;
        public int enhanceLevel;

        public Bitmap image;

        /*
            public int prf;
            public int threshold;
            public int wall;
            public int angle;
        */
        public Frame(Probe probe, Bitmap image) {
            this.mode = probe.getMode();
            this.date = new Date();
            this.frameRate = probe.getFrameRate();
            this.freq = probe.getFreq();
            this.depth = probe.getDepth();
            this.gain = probe.getGain();
            this.dr = probe.getDr();
            this.grayMap = probe.getGrayMap();
            this.persistence = probe.getPersistence();
            this.enhanceLevel = probe.getEnhanceLevel();
            this.image = image;
        }
    }

    /**
     * CineBufferListener
     */
    interface CineBufferListener {

        /**
         * Cine buffer的個數增加，可用來更新UI的buffer個數
         * @param newCineBufferCount
         */
        void onCineBufferCountIncreased(int newCineBufferCount);

        /**
         * Cine buffer的個數清除為0，可用來更新UI的buffer個數
         */
        void onCineBufferCleared();
    }

    /**
     * 設定CineBufferListener
     * @param cineBufferListener
     */
    void setCineBufferListener(CineBufferListener cineBufferListener);

    /**
     * 從CineBuffer取得frame
     * @param index 從0開始
     * @return 只有在非live時才有值，live時會回傳null
     */
    Frame getFrameFromCineBuffer(int index);

    /**
     * ScanListener
     */
    interface ScanListener {

        /**
         * scan mode切換成功
         * @param mode "B"表示切換到B mode, "C"表示切換到Color mode
         */
        void onModeSwitched(String mode);

        /**
         * scan mode切換失敗
         */
        void onModeSwitchingError();

        /**
         * 跟device之間的connection發生error
         */
        void onConnectionError();

        /**
         * device開始scan
         */
        void onScanStarted();

        /**
         * device停止scan
         */
        void onScanStopped();

        /**
         * scan進行中，傳回接收到的image
         * @param image
         */
        void onNewFrameReady(Bitmap image);

        /**
         * 要求設定depth
         * @param depth
         */
        /**
         * 設定depth成功
         * @param newDepth
         */
        void onDepthSet(int newDepth);

        /**
         * 設定depth失敗
         */
        void onDepthSetError();

        /**
         * 當發生此事件時，代表此硬體來不及做影像後處理，底層會將image丟掉
         */
        void onImageBufferOverflow();
    }

    /**
     * 設定ScanListener
     * @param scanListener
     */
    void setScanListener(ScanListener scanListener);

    /**
     * 試圖將scan mode切換為B mode
     * 由ScanListener.onModeSwitched(String mode), 且mode等於"B"表示切換成功
     * 由ScanListener.onModeSwitchingError()表示切換失敗
     */
    void swithToBMode();

    /**
     * 回傳目前的scan mode
     * @return "B"表示B mode
     */
    String getMode();

    /**
     * 要求開始scan
     * 由ScanListener.onScanStarted()表示開始scan成功
     */
    void startScan();

    /**
     * 要求停止scan
     * 由ScanListener.onScanStopped()表示停止scan成功
     */
    void stopScan();

    /**
     *
     * @return true 如果正在scan
     */
    boolean isLive();

    /**
     * 取得目前的frame rate, 單位為 Hz
     * @return
     */
    int getFrameRate();

    /**
     * 單位為 MHz
     * @return
     */
    float getFreq();

    /**
     * 取得目前的depth, 單位為 cm
     * @return
     */
    int getDepth();

    /**
     * 要求設定depth
     * 由ScanListener.onDepthSet()表示設定成功
     * 由ScanListener.onDepthSetError表示設定失敗
     *
     * @param depth 合理值 3 ~ 18 (cm)
     */
    void setDepth(int depth);

    /**
     * 取得目前的gain
     * @return
     */
    int getGain();

    /**
     * 設定gain
     * @param gain 合理值 0 ~ 100
     */
    void setGain(int gain);

    /**
     * 取得目前的dynamic range
     * @return
     */
    int getDr();

    /**
     * 設定dynamic range
     * @param dr 合理值 0 ~ 100
     */
    void setDr(int dr);

    /**
     * 取得目前的gray map編號
     * @return
     */
    int getGrayMap();

    /**
     * 設定gray map
     * @param grayMap 合理值 1 ~ 10
     */
    void setGrayMap(int grayMap);

    /**
     * 取得目前的persistence設定值
     * @return
     */
    int getPersistence();

    /**
     * 設定persistence
     * @param persistence 合理值 0 ~ 4
     */
    void setPersistence(int persistence);

    /**
     * 取得目前的image enhance level
     * @return
     */
    int getEnhanceLevel();

    /**
     * 設定image enhance level
     * @param enhanceLevel 合理值0 ~ 3
     */
    void setEnhanceLevel(int enhanceLevel);

    /**
     * 取得目前第一組TGC的值
     * @return
     */
    int getTgc1();

    /**
     * 設定第一組TGC
     * @param tgc1 合理值0 ~ 100
     */
    void setTgc1(int tgc1);

    /**
     * 取得目前第二組TGC的值
     * @return
     */
    int getTgc2();

    /**
     * 設定第二組TGC
     * @param tgc2 合理值0 ~ 100
     */
    void setTgc2(int tgc2);

    /**
     * 取得目前第三組TGC的值
     * @return
     */
    int getTgc3();

    /**
     * 設定第三組TGC
     * @param tgc3 合理值0 ~ 100
     */
    void setTgc3(int tgc3);

    /**
     * 取得目前第四組TGC的值
     * @return
     */
    int getTgc4();

    /**
     * 設定第四組TGC
     * @param tgc4 合理值0 ~ 100
     */
    void setTgc4(int tgc4);

    /**
     * 回傳底層image bitmap的寬度
     * @return
     */
    int getImageWidth();

    /**
     * 回傳底層image bitmap的高度
     * @return
     */
    int getImageHeight();

    class BModePreset {
        public int depth;
        public int gain;
        public int dr;
        public int grayMap;
        public int persistence;
        public int enhanceLevel;

        public BModePreset(int depth, int gain, int dr, int grayMap, int persistence, int enhanceLevel) {
            this.depth = depth;
            this.gain = gain;
            this.dr = dr;
            this.grayMap = grayMap;
            this.persistence = persistence;
            this.enhanceLevel = enhanceLevel;
        }
    }

    /**
     * 用BModePreset來設定系統的B mode參數
     * @param preset
     */
    void setByBModePreset(BModePreset preset);
}
