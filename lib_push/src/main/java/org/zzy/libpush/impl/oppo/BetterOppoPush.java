package org.zzy.libpush.impl.oppo;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.coloros.mcssdk.PushManager;
import com.coloros.mcssdk.callback.PushAdapter;

import org.zzy.libpush.IBetterPushProcess;
import org.zzy.libpush.PushFunctionProcess;
import org.zzy.libpush.SyncManager;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/6
 */
public class BetterOppoPush implements IBetterPushProcess {

    /**
     * oppo de app key
     */
    public static final String APP_KEY = "";
    /**
     *
     */
    public static final String APP_SECRET = "";
    /**
     * 是否可以注册，如果返回false使用后续功能会报异常
     */
    private boolean isSuppor;

    /**
     *
     * 如果是第一次就上报
     */
    private boolean isFirstGetPushToken;

    @Override
    public void pushInit(Application application) {

    }

    @Override
    public void pushConnect(Activity activity) {

    }

    @Override
    public void pushDestroy(Application application) {

    }

    @Override
    public void getPushToken(final Activity activity) {
        isSuppor = PushManager.isSupportPush(activity.getApplicationContext());
        if(isSuppor){
            PushManager.getInstance().register(activity.getApplicationContext(), APP_KEY, APP_SECRET, new PushAdapter() {
                @Override
                public void onRegister(int code, String s) {
                    if (code == 0) {
                        if(isFirstGetPushToken && !TextUtils.isEmpty(s)) {
                            isFirstGetPushToken = false;
                            SyncManager.getInstance(activity.getApplicationContext()).syncPushDeviceInfo
                                    (s,PushFunctionProcess.PHONE_TYPE_OPPO);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void delPushToken(Activity activity) {

    }

    @Override
    public void getPushStatus() {

    }
}
