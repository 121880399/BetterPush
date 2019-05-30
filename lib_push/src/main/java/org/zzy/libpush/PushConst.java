package org.zzy.libpush;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/5
 */
public class PushConst {

    private static final String PUSH_SP_FILE_NAME = "push_info";

    private static final String PUSH_HOST = "host";

    private static final String PUSH_APP_CODE = "push_app_code";

    private static final String PUSH_PHONE_INFO = "phone_info";

    private static Context context;

    private static SharedPreferences sp;



    public static void init(Context c) {
        context = c;
        sp = context.getSharedPreferences(PushConst.PUSH_SP_FILE_NAME, 0);
    }

    public static void setHost(String host) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PUSH_HOST, host);
        editor.apply();
    }

    public static void setPushAppCode(String appCode) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PUSH_APP_CODE, appCode);
        editor.apply();
    }

    public static void setPhone(String phone) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PUSH_PHONE_INFO, phone);
        editor.apply();
    }

    public static String getPhone() {
        return sp.getString(PUSH_PHONE_INFO, "");
    }

    public static String getHost() {
        return sp.getString(PUSH_HOST, "");
    }

}
