package org.zzy.libpush;

import android.app.Application;
import android.os.Debug;
import android.support.annotation.NonNull;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/5
 */
public class BetterPush {

    private static boolean isInit = false;

    private static Application app;
    /**
     * BetterPush框架初始化方法
     */
    public static void initialize(Application application,String appCode){

    }

    /**
     * BetterPush框架初始化方法
     *
     * @param application Application上下文
     * @param isSyncData 是否同步数据
     */
    public static void initialize(@NonNull Application application, final boolean isSyncData, final String appCode,
            final String appChannel,String url) {
        initialize(application, url, isSyncData, appCode, appChannel);
    }

    public static void initialize(@NonNull Application application, @NonNull String url,
             final boolean isSyncData, final String appCode, final String appChannel) {
        isInit = true;
        app = application;
        PushConst.init(app);
        PushConst.setHost(url);
        PushSDKInitializeManager manager = PushSDKInitializeManager.getInstance(app);
        manager.initialize();
    }

    /**
     * 设置应用在前台取消通知(只适用个推)
     */
    public static void setInAppNotNorify(boolean inAppNotNorify){
        PushConst.setInAppNotNotify(inAppNotNorify);
    }
}
