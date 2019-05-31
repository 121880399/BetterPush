package org.zzy.libpush.impl.getui;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.igexin.sdk.PushManager;

import org.zzy.libpush.IBetterPushProcess;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/6
 */
public class BetterGetuiPush implements IBetterPushProcess {
    @Override
    public void pushInit(Application application) {
    }

    @Override
    public void pushConnect(Activity activity) {
        init(activity);
    }

    @Override
    public void pushDestroy(Application application) {

    }

    @Override
    public void getPushToken(Activity activity) {
        init(activity);
    }

    @Override
    public void delPushToken(Activity activity) {

    }

    @Override
    public void getPushStatus() {

    }

    private void init (Activity activity) {
        Log.i(TAG, "初始化个推推送");
        PushManager.getInstance().initialize(activity.getApplicationContext(), GeTuiPushService.class);
        PushManager.getInstance().registerPushIntentService(activity.getApplicationContext(), GetuiIntentService.class);
    }
}
