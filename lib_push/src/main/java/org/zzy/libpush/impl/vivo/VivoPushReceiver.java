package org.zzy.libpush.impl.vivo;

import android.content.Context;
import android.text.TextUtils;

import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.BasePushMessageReceiver;

import org.zzy.libpush.PushFunctionProcess;
import org.zzy.libpush.PushInfoCreateSync;
import org.zzy.libpush.SyncManager;
import org.zzy.libpush.entity.PushInfo;
import org.zzy.libpush.utils.PushNotifyUtils;

import java.util.Map;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/30
 */
public class VivoPushReceiver  extends BasePushMessageReceiver {
    private boolean isFirstGetPushToken = true;

    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {
        Map<String, String> map = upsNotificationMessage.getParams();
        if (map != null) {
            PushInfo pushInfo = new PushInfo();
            pushInfo.setMessage(upsNotificationMessage.getContent());
            pushInfo.setTitle(upsNotificationMessage.getTitle());
            pushInfo.setMessageId(map.get("messageId"));
            pushInfo.setAction(map.get("action"));
            pushInfo.setReceiveMills(System.currentTimeMillis());
            pushInfo.setChannel(PushFunctionProcess.PHONE_TYPE_VIVO);
            pushInfo.setIsIntoMC(Integer.valueOf(map.get("isIntoPsnCenter")));
            pushInfo.setExtraMsg(map.get("extraMsg"));

            PushNotifyUtils.notifyClickBroadcast(context, pushInfo);
        }
    }

    @Override
    public boolean onNotificationMessageArrived(Context context, UPSNotificationMessage upsNotificationMessage) {
        Map<String, String> map = upsNotificationMessage.getParams();
        if (map != null) {
            PushInfoCreateSync.createPushInfoSync(context, upsNotificationMessage.getTitle(),
                    upsNotificationMessage.getContent()
                    , map.get("messageId"), map.get("action"), PushFunctionProcess.PHONE_TYPE_VIVO, map.get
                            ("extraMsg"),map.get("isIntoPsnCenter"));
        }
        return false;
    }

    @Override
    public void onReceiveRegId(Context context, String s) {
        if(isFirstGetPushToken && !TextUtils.isEmpty(s)) {
            isFirstGetPushToken = false;
            SyncManager.getInstance(context).syncPushDeviceInfo(s,PushFunctionProcess.PHONE_TYPE_VIVO);
        }
    }
}
