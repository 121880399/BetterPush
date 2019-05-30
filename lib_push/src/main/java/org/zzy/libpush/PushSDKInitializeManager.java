package org.zzy.libpush;

import android.app.Application;

import org.zzy.libpush.utils.ManifestMetaDataUtil;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/5
 */
public class PushSDKInitializeManager {

    private static PushSDKInitializeManager INSTANCE = null;

    private Application mApplication;

    private IBetterPushProcess mIBetterPushProcess;

    private PushSDKInitializeManager(Application application) {
        mApplication = application;
        mIBetterPushProcess = PushFunctionProcess.getInstance().getIBetterPushProcess();
    }

    public static PushSDKInitializeManager getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (PushSDKInitializeManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PushSDKInitializeManager(application);
                }
            }
        }
        return INSTANCE;
    }

    public void initialize() {
        String pushAppCode = ManifestMetaDataUtil.getString(mApplication, "PUSH_APP_CODE");
        PushConst.setPushAppCode(pushAppCode);
        if(mIBetterPushProcess == null){
            mIBetterPushProcess = PushFunctionProcess.getInstance().getIBetterPushProcess();
        }
        mIBetterPushProcess.pushInit(mApplication);
        SyncManager.getInstance(mApplication).syncPushDeviceInfo(PushFunctionProcess
                .getInstance()
                .getPhoneTypeInt());
    }
}
