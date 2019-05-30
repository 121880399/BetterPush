package org.zzy.libpush.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.TelephonyManager;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/30
 */
public class PhoneInfoUtil {
    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getSysVer() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取设备的deviceID，一般情况下为设备的IMEI号
     */
    public static String getRawDeviceId(@NonNull Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 1代表开启了通知，0代表关闭了通知
     * @return
     */
    public static String isOpenNotification(Context context){
        if(context != null) {
            NotificationManagerCompat manager = NotificationManagerCompat.from(context.getApplicationContext());
            boolean isOpened = manager.areNotificationsEnabled();
            return isOpened ? "1" : "0";
        }
        return "1";
    }
}
