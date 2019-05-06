package org.zzy.libpush;

import android.app.Activity;
import android.app.Application;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/6
 */
public interface IBetterPushProcess {

    String TAG = "IBetterPushProcess";
    void pushInit(Application application);
    void pushConnect(Activity activity);
    void pushDestroy(Application application);
    void getPushToken(Activity activity);
    void delPushToken(Activity activity);
    void getPushStatus();
}
