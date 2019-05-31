package org.zzy.libpush.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import org.zzy.libpush.R;
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

    public static void showNotification(Context context, PushInfo pushInfo) {
        if (pushInfo == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(ACTION_CLICK);
        intent.putExtra(OKPUSH_EXTRA_DATA, pushInfo);

        Bitmap pushBigIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.push_big_icon);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                convert2Int(pushInfo.getMessageId()),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.push_small_icon)
                .setLargeIcon(pushBigIcon)
                .setContentTitle(pushInfo.getTitle())
                .setContentText(pushInfo.getMessage())
                .setAutoCancel(true)
                .setTicker(pushInfo.getTitle())
                .setWhen(System.currentTimeMillis())
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(convert2Int(pushInfo.getMessageId()),
                notificationBuilder.build());
    }

    public static int convert2Int(@Nullable String d) {
        if (TextUtils.isEmpty(d)) {
            return -1;
        }
        try {
            return Integer.parseInt(d);
        } catch (Exception p) {
            p.printStackTrace();
        }
        return -1;
    }
}
