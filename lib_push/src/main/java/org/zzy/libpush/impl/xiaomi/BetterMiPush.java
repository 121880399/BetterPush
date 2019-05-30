package org.zzy.libpush.impl.xiaomi;

import android.app.Activity;
import android.app.Application;

import com.xiaomi.mipush.sdk.MiPushClient;

import org.zzy.libpush.IBetterPushProcess;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/6
 */
public class BetterMiPush implements IBetterPushProcess {

    private String pushAppId;

    private String pushAppKey;

    @Override
    public void pushInit(Application application) {
        MiPushClient.registerPush(application, pushAppId.trim(), pushAppKey.trim());
    }

    @Override
    public void pushConnect(Activity activity) {

    }

    @Override
    public void pushDestroy(Application application) {

    }

    @Override
    public void getPushToken(Activity activity) {

    }

    @Override
    public void delPushToken(Activity activity) {

    }

    @Override
    public void getPushStatus() {

    }
}
