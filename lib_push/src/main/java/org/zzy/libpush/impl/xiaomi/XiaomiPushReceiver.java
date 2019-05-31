package org.zzy.libpush.impl.xiaomi;

import android.content.Context;
import android.text.TextUtils;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.zzy.libpush.PushFunctionProcess;
import org.zzy.libpush.PushInfoCreateSync;
import org.zzy.libpush.SyncManager;
import org.zzy.libpush.entity.PushInfo;
import org.zzy.libpush.utils.PushNotifyUtils;

import java.util.List;
import java.util.Map;

/**
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 XiaomiPushReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.XiaomiPushReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、XiaomiPushReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、XiaomiPushReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、XiaomiPushReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、XiaomiPushReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、XiaomiPushReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 */
/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/30
 */
public class XiaomiPushReceiver extends PushMessageReceiver{
    private boolean isFirstGetPushToken = true;

    /**
     * 用来接收服务器向客户端发送的透传消息
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onReceivePassThroughMessage (Context context, MiPushMessage miPushMessage) {
        super.onReceivePassThroughMessage(context, miPushMessage);
        Map<String, String> map = miPushMessage.getExtra();
        if (map != null) {
            PushInfoCreateSync.createPushInfoSync(context, miPushMessage.getTitle(), miPushMessage.getDescription()
                    , map.get("messageId"), map.get("action"), PushFunctionProcess.PHONE_TYPE_XIAOMI, map.get
                            ("extraMsg"), map.get("isIntoPsnCenter"));
        }
    }

    /**
     * 用来接收服务器向客户端发送的通知消息，这个回调方法会在用户手动点击通知后触发。
     *
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onNotificationMessageClicked (Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageClicked(context, miPushMessage);

        Map<String, String> map = miPushMessage.getExtra();
        if (map != null) {
            PushInfo pushInfo = new PushInfo();
            pushInfo.setMessage(miPushMessage.getDescription());
            pushInfo.setTitle(miPushMessage.getTitle());
            pushInfo.setMessageId(map.get("messageId"));
            pushInfo.setAction(map.get("action"));
            pushInfo.setReceiveMills(System.currentTimeMillis());
            pushInfo.setChannel(PushFunctionProcess.PHONE_TYPE_XIAOMI);
            pushInfo.setIsIntoMC(Integer.valueOf(map.get("isIntoPsnCenter")));
            pushInfo.setExtraMsg(map.get("extraMsg"));
            PushNotifyUtils.notifyClickBroadcast(context, pushInfo);
        }
    }

    /**
     * 用来接收服务器向客户端发送的通知消息，
     * 这个回调方法是在通知消息到达客户端时触发。
     * 另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数
     * @param context
     * @param miPushMessage
     */

    @Override
    public void onNotificationMessageArrived (Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageArrived(context, miPushMessage);
        Map<String, String> map = miPushMessage.getExtra();
        if (map != null) {
            PushInfoCreateSync.createPushInfoSync(context, miPushMessage.getTitle(), miPushMessage.getDescription()
                    , map.get("messageId"), map.get("action"), PushFunctionProcess.PHONE_TYPE_XIAOMI, map.get
                            ("extraMsg"), map.get("isIntoPsnCenter"));
        }
    }

    /**
     * 方法用来接收客户端向服务器发送注册命令后的响应结果
     *
     * @param context
     * @param message
     */
    @Override
    public void onReceiveRegisterResult (Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                List<String> arguments = message.getCommandArguments();
                String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
                if (isFirstGetPushToken && !TextUtils.isEmpty(cmdArg1)) {
                    isFirstGetPushToken = false;
                    SyncManager.getInstance(context).syncPushDeviceInfo(cmdArg1,PushFunctionProcess
                            .PHONE_TYPE_XIAOMI);
                }
            }
        }
    }
}
