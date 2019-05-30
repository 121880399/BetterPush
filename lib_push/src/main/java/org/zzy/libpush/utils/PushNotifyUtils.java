package org.zzy.libpush.utils;

import android.content.Context;
import android.content.Intent;

import org.zzy.libpush.entity.PushInfo;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/30
 */
public class PushNotifyUtils {
    /**
     * 点击BetterPush的Action
     */
    public static final String ACTION_CLICK = "com.betterpush.android.ACTION_CLICK";
    /**
     * 收到Push点击后,取到对应的值
     */
    public static final String OKPUSH_EXTRA_DATA = "com.betterpush.android.EXTRA_DATA";
    /**
     * 消息到达OkPush的Action
     */
    public static final String ACTION_RECEIVE = "com.betterpush.android.ACTION_RECEIVE";

    public static void notifyClickBroadcast(Context context, PushInfo pushInfo) {
        Intent intent = new Intent();
        intent.setAction(ACTION_CLICK);
        intent.setPackage(context.getPackageName());
        intent.putExtra(OKPUSH_EXTRA_DATA, pushInfo);
        context.sendBroadcast(intent);
    }

    public static void notifyBroadcast(Context context, PushInfo pushInfo) {
        Intent intent = new Intent();
        intent.setAction(ACTION_RECEIVE);
        intent.setPackage(context.getPackageName());
        intent.putExtra(OKPUSH_EXTRA_DATA, pushInfo);
        context.sendBroadcast(intent);
    }
}
