package org.zzy.libpush.impl.oppo;

import android.content.Context;
import android.util.Log;

import com.coloros.mcssdk.PushService;
import com.coloros.mcssdk.mode.AppMessage;
import com.coloros.mcssdk.mode.SptDataMessage;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/30
 */
public class OppoPushReceiver extends PushService {

    /**
     * 普通应用消息，视情况看是否需要重写
     */
    @Override
    public void processMessage(Context context, AppMessage appMessage) {
        super.processMessage(context, appMessage);
        String content = appMessage.getContent();
        Log.e("ProcessMessage", "AppMessage title = " + appMessage.getTitle() +
                " content = " + appMessage.getContent() + " rule = " + appMessage.getRule() + " timeRanges" + appMessage
                .getTimeRanges() + " appPackage = " + appMessage.getAppPackage() + " taskId = " + appMessage.getTaskID
                () + " balanceTime = " + appMessage
                .getBalanceTime() + " DistinctBycontent = " + appMessage.getDistinctBycontent() + " type = "
                + appMessage
                .getType());
    }

    /**
     * 透传消息处理，应用可以打开页面或者执行命令,如果应用不需要处理透传消息，则不需要重写此方法
     */
    @Override
    public void processMessage(Context context, SptDataMessage sptDataMessage) {
        super.processMessage(context.getApplicationContext(), sptDataMessage);
        Log.e("IOkPushProcess", "SptDataMessage appid = " + sptDataMessage
                .getAppID() + " content = " + sptDataMessage.getContent() + " description = " + sptDataMessage
                .getDescription
                        () + " globalid = " + sptDataMessage.getGlobalID());
    }
}
