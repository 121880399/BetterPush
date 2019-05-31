package org.zzy.libpush.impl.getui;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import org.zzy.libpush.PushConst;
import org.zzy.libpush.PushFunctionProcess;
import org.zzy.libpush.PushInfoCreateSync;
import org.zzy.libpush.SyncManager;
import org.zzy.libpush.entity.PushInfo;
import org.zzy.libpush.utils.JsonUtils;
import org.zzy.libpush.utils.PushNotifyUtils;
/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/31
 */
public class GetuiIntentService extends GTIntentService {
    private static final String TAG = "GeTuiIntentService";
    private boolean isFirstGetPushToken = true;
    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Log.e(TAG, "onReceiveServicePid -> " + pid);
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        if (isFirstGetPushToken && !TextUtils.isEmpty(clientid)) {
            isFirstGetPushToken = false;
            SyncManager.getInstance(context).syncPushDeviceInfo(clientid, PushFunctionProcess.PHONE_TYPE_OTHERS);
        }
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();

        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
        Log.e(TAG, "call sendFeedbackMessage = " + (result ? "success" : "failed"));
        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            Log.e(TAG, "receiver payload = "+data);
            try {
                PushInfo pushInfo = JsonUtils.fromJson(data, PushInfo.class);
                PushNotifyUtils.notifyBroadcast(context, pushInfo);
                if(!PushConst.getInAppNotNotify()){
                    PushNotifyUtils.showNotification(context, pushInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        Log.e(TAG, "onReceiveOnlineState -> " + (online ? "online" : "offline"));
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        Log.e(TAG, "onReceiveCommandResult -> " + gtCmdMessage);
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }
}
