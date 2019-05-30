package org.zzy.libpush;

import android.content.Context;

import org.zzy.libpush.entity.PushInfo;
import org.zzy.libpush.utils.PushNotifyUtils;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/30
 */
public class PushInfoCreateSync {


    public static void createPushInfoSync (Context context, String title, String msg, String msgId, String action,
            int channel, String extraMsg, boolean isIntoPsnCenter) {
        PushInfo pushInfo = createPushInfo(title, msg, msgId, action, channel, extraMsg, isIntoPsnCenter);
        PushNotifyUtils.notifyBroadcast(context, pushInfo);

    }

    private static PushInfo createPushInfo (String title, String msg, String msgId, String action, int
            channel, String extraMsg, boolean isIntoPsnCenter) {
        PushInfo pushInfo = new PushInfo();
        pushInfo.setMessage(msg);
        pushInfo.setTitle(title);
        pushInfo.setMessageId(msgId);
        pushInfo.setAction(action);
        pushInfo.setReceiveMills(System.currentTimeMillis());
        pushInfo.setChannel(channel);
        pushInfo.setIntoMC(isIntoPsnCenter);
        pushInfo.setExtraMsg(extraMsg);
        return pushInfo;
    }
}
