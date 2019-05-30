package org.zzy.libpush.impl.huawei;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;

import org.zzy.libpush.IBetterPushProcess;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/6
 */
public class BetterHuaweiPush implements IBetterPushProcess {
    @Override
    public void pushInit(Application application) {
        HMSAgent.init(application);
    }

    @Override
    public void pushConnect(Activity activity) {
        // 首个界面需要调用connect进行连接
        HMSAgent.connect(activity, new ConnectHandler() {
            @Override
            public void onConnect(int rst) {
                Log.e(TAG, "HMS connect end:" + rst);
            }
        });
    }

    @Override
    public void pushDestroy(Application application) {
        HMSAgent.destroy();
    }

    @Override
    public void getPushToken(Activity activity) {
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rst) {
                Log.e(TAG, "get token: end, rtnCode=" + rst);
            }
        });
    }

    @Override
    public void delPushToken(Activity activity) {
        HMSAgent.Push.deleteToken(HuaweiPushReceiver.huaweiToken, null);
    }

    @Override
    public void getPushStatus() {
        HMSAgent.Push.getPushState(null);
    }

    /**
     * 设置是否接收普通透传消息
     *
     * @param enable 是否开启
     */
    public void setReceiveNormalMsg (boolean enable) {
        HMSAgent.Push.enableReceiveNormalMsg(enable, null);
    }

    /**
     * 设置接收通知消息
     *
     * @param enable 是否开启
     */
    public void setReceiveNotifyMsg (boolean enable) {
        HMSAgent.Push.enableReceiveNotifyMsg(enable, null);
    }

    /**
     * 显示push协议
     */
    public void showAgreement () {
        HMSAgent.Push.queryAgreement(null);
    }
}
