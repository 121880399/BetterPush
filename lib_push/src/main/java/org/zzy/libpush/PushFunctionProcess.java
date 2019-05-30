package org.zzy.libpush;

import android.os.Build;
import android.util.Log;

import org.zzy.libpush.impl.getui.BetterGetuiPush;
import org.zzy.libpush.impl.huawei.BetterHuaweiPush;
import org.zzy.libpush.impl.vivo.BetterVivoPush;
import org.zzy.libpush.impl.xiaomi.BetterMiPush;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/5
 */
public class PushFunctionProcess {
    /**
     * tag
     */
    private final String TAG = "PushFunctionProcess";

    /**
     * 单例
     */
    private static PushFunctionProcess INSTANCE = null;

    /**
     * 小米
     */
    public final static int PHONE_TYPE_XIAOMI =3;
    /**
     * 华为
     */
    public final static int PHONE_TYPE_HUAWEI =5;
    /**
     * 其他
     */
    public final static int PHONE_TYPE_OTHERS =6;
    /**
     * vivo
     */
    public final static int PHONE_TYPE_VIVO =7;
    /**
     * oppo
     */
    public final static int PHONE_TYPE_OPPO =8;

    /**
     * 标识类型
     */
    private int phoneTypeInt = PHONE_TYPE_OTHERS;

    private IBetterPushProcess mIBetterPushProcess;

    private PushFunctionProcess(){
        initCurrentPush();
    }

    public static PushFunctionProcess getInstance() {
        if (INSTANCE == null) {
            synchronized (PushFunctionProcess.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PushFunctionProcess();
                }
            }
        }
        return INSTANCE;
    }

    private void initCurrentPush(){
        String phoneType = (Build.MANUFACTURER+"-"+Build.BRAND).toLowerCase();
        Log.e(TAG, "phoneType="+phoneType);
        if(phoneType.contains("huawei")){
            phoneTypeInt = PHONE_TYPE_HUAWEI;
            mIBetterPushProcess = new BetterHuaweiPush();
        }else if(phoneType.contains("vivo")){
            phoneTypeInt = PHONE_TYPE_VIVO;
            mIBetterPushProcess = new BetterVivoPush();
        }else if(phoneType.contains("xiaomi")){
            //小米推送
            phoneTypeInt = PHONE_TYPE_XIAOMI;
            mIBetterPushProcess =  new BetterMiPush();
        }else {//个推
            phoneTypeInt = PHONE_TYPE_OTHERS;
            mIBetterPushProcess =  new BetterGetuiPush();
        }
    }

    public IBetterPushProcess getIBetterPushProcess () {
        if(mIBetterPushProcess == null){
            initCurrentPush();
        }
        return mIBetterPushProcess;
    }

    public int getPhoneTypeInt () {
        return phoneTypeInt;
    }
}
