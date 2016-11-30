package utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.ParcelUuid;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by linmiao on 16/11/29.
 */
public class Config {
    private static final String SCREENSHOT_DIR="monkeyScreeenshots";
    private static final String MONKEY_LOG_DIR="monkeyLogs";
    public static final String TARGET_PACKAGE="com.letv.monkeytest1127_1830";
    public static final String LAUNCHER_ACTIVITY="MainActivity";
    public static final String LOG_TAG="NEWMONKEY";
    public static final String SCREENSHOT_TYPE=".png";
    public static File ScreenshotFolder = new File(Environment.getExternalStorageDirectory() +File.separator+ Config.SCREENSHOT_DIR);
    public static File LogFolder = new File(Environment.getExternalStorageDirectory() +File.separator+ Config.MONKEY_LOG_DIR);


    /**
     * 获取手机品牌
     * @return
     */
    public static String getBrandName() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     * @return
     */
    public  static String getDeviceName() {
        return android.os.Build.MODEL;
    }

    public static String getDeviceID(){
        return android.os.Build.SERIAL;
    }
    /**
     * 获取Mac地址
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macAddress=info.getMacAddress();
        if(macAddress != null && !macAddress.equals("")){
            return macAddress;
        }
        return "";
    }

    /**
     * 获取手机imsi号
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getSubscriberId();
        if(imsi != null && !imsi.equals("")){
            return imsi;
        }
        return "";
    }

    /**
     *  获取手机imei号
     * @param context
     * @return
     */
    public static String getIMEI(Context context){
        TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei=telephonyManager.getDeviceId();
        if(imei != null && !imei.equals("")){
            return imei;
        }
        return "";
    }


    /**
     * 获取乐视视频客户端版本名
     */
    public static String getClientVersionName(Context context) {
        try {
            PackageInfo packInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return  packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
