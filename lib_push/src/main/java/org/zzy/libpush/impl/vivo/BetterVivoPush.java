package org.zzy.libpush.impl.vivo;

import android.app.Activity;
import android.app.Application;

import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;

import org.zzy.libpush.IBetterPushProcess;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/6
 */
public class BetterVivoPush implements IBetterPushProcess {
    @Override
    public void pushInit(Application application) {
        PushClient.getInstance(application).initialize();
    }

    @Override
    public void pushConnect(Activity activity) {

    }

    @Override
    public void pushDestroy(Application application) {

    }

    @Override
    public void getPushToken(Activity activity) {
        PushClient.getInstance(activity.getApplicationContext()).turnOnPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int state) {
                // TODO: 开关状态处理
            }
        });
    }

    @Override
    public void delPushToken(Activity activity) {

    }

    @Override
    public void getPushStatus() {

    }
}
