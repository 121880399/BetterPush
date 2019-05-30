package org.zzy.libpush.impl.huawei;
/**
 * 应用需要创建一个子类继承com.huawei.hms.support.api.push.PushReceiver，
 * 实现onToken，onPushState ，onPushMsg，onEvent，这几个抽象方法，用来接收token返回，push连接状态，透传消息和通知栏点击事件处理。
 * onToken 调用getToken方法后，获取服务端返回的token结果，返回token以及belongId
 * onPushState 调用getPushState方法后，获取push连接状态的查询结果
 * onPushMsg 推送消息下来时会自动回调onPushMsg方法实现应用透传消息处理。本接口必须被实现。 在开发者网站上发送push消息分为通知和透传消息
 *           通知为直接在通知栏收到通知，通过点击可以打开网页，应用 或者富媒体，不会收到onPushMsg消息
 *           透传消息不会展示在通知栏，应用会收到onPushMsg
 * onEvent 该方法会在设置标签、点击打开通知栏消息、点击通知栏上的按钮之后被调用。由业务决定是否调用该函数。
 */

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.support.api.push.PushReceiver;

import org.json.JSONException;
import org.zzy.libpush.PushFunctionProcess;
import org.zzy.libpush.PushInfoCreateSync;
import org.zzy.libpush.entity.PushInfo;
import org.zzy.libpush.utils.JsonUtils;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/30
 */
public class HuaweiPushReceiver extends PushReceiver {

    public static String huaweiToken = "";

    private boolean isFirstGetPushToken = true;


    /**
     * onEvent会在后续版本中逐渐废弃
     * 实现通知栏点击事件回调。该方法会在点击打开通知栏消息、点击通知栏上的按钮之后被调用。开发者决定是否实现该方法，
     * 如果需要触发onEvent点击事件回调，开发者需要在下发的PUSH消息中携带customize参数自定义键值对。
     */
    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
        super.onEvent(context, event, extras);
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
        }
        String message = extras.getString(BOUND_KEY.pushMsgKey);

        try {
            String[] msgList = JsonUtils.parseStringArray(message);
            if(msgList!=null && msgList.length > 0){
                String msg = msgList[0];
                PushInfo pushInfo = JsonUtils.fromJson(msg, PushInfo.class);
                PushInfoCreateSync
                        .createPushInfoSync(context, pushInfo.getTitle(), pushInfo.getMessage(), pushInfo
                        .getMessageId(), pushInfo.getAction(), PushFunctionProcess.PHONE_TYPE_HUAWEI, "", true);
            }else {
                Log.e("", "exception");
            }
        } catch (JSONException e) {
            Log.e("", "exception"+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        super.onToken(context, token, extras);
    }

    @Override
    public boolean onPushMsg(Context context, byte[] msgBytes, Bundle extras) {
        return super.onPushMsg(context, msgBytes, extras);
    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        super.onPushState(context, pushState);
    }
}
