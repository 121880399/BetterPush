package org.zzy.libpush;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.util.Log;

import org.zzy.libpush.interceptor.HttpLoggingInterceptor;
import org.zzy.libpush.utils.Debug;
import org.zzy.libpush.utils.HttpsUtils;
import org.zzy.libpush.utils.ManifestMetaDataUtil;
import org.zzy.libpush.utils.PhoneInfoUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/6
 */
public class SyncManager {

    private static SyncManager INSTANCE = null;

    private Context mContext;

    private OkHttpClient mOkHttpClient = null;

    public static SyncManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SyncManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SyncManager(context);
                }
            }
        }
        return INSTANCE;
    }

    private SyncManager(Context context) {
        mContext = context;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (Debug.isDebug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("PaxOK");
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
            loggingInterceptor.setColorLevel(java.util.logging.Level.INFO);
            builder.addInterceptor(loggingInterceptor);
        }
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);

        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        mOkHttpClient = builder.build();
    }

    /**
     * 设备信息上报到服务器
     * @param channel 推送渠道
     * @param deviceToken 推送服务商返回的token
     */
    public void syncPushDeviceInfo(String deviceToken,int channel){
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        //设备型号
        bodyBuilder.add("deviceType", PhoneInfoUtil.getBrand() + PhoneInfoUtil.getModel());
        //手机号,可以为空
        bodyBuilder.add("phone", PushConst.getPhone());
        //设备系统版本
        bodyBuilder.add("osVersion", PhoneInfoUtil.getSysVer());
        //设备系统类型  1：Android 2：ios
        bodyBuilder.add("osType", "1");
        //推送渠道 3：XIAOMI 4：APNS 5 ：华为  6 表示个推，7：vivo 8：oppo
        bodyBuilder.add("channel", channel+"");
        //应用标识 用于多个客户端的情况，标识与后台定
        bodyBuilder.add("appCode", "1");
        //应用版本
        bodyBuilder.add("appVersion", ManifestMetaDataUtil.getVersionName(mContext));
        //设备号 苹果：idfa ； 安卓：imei
        bodyBuilder.add("devicecode", PhoneInfoUtil.getRawDeviceId(mContext));
        //推送服务商返回的token,后台要推送消息时，会把这个token发送给推送服务商，服务商根据这个token找到要推送的用户
        bodyBuilder.add("deviceToken", deviceToken);
        //表示用户打开了通知，0表示用户关闭了通知
        bodyBuilder.add("isAcceptPush", PhoneInfoUtil.isOpenNotification(mContext));
        //得到接口地址
        String host = PushConst.getHost();
        if (host.endsWith("/")) {
            host += "/devices/report.json";
        } else {
            host += "/devices/report.json";
        }
        Request request = new Request.Builder()
                .url(host)
                .post(bodyBuilder.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    String stringJSON = response.body().string();
                    try {
                        JsonObject jsonObject = new JsonParser().parse(stringJSON).getAsJsonObject();
                        int status = jsonObject.get("status").getAsInt();
                        if (status == 1) {//成功
                            Log.i("SyncManager", "设备信息同步成功");
                            //do nothing
                        } else {//失败
                            String errorMsg = jsonObject.get("errorMessage").getAsString();
                            Log.e("SyncManager", "设备信息同步失败:" + errorMsg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("SyncManager", "设备信息同步失败,业务异常:" + e.getMessage());
                    }
                } else {
                    if (response != null) {
                        Log.e("SyncManager", "设备信息同步失败,接口异常,http state code" + response.code());
                    } else {
                        Log.e("SyncManager", "设备信息同步失败,接口异常,response null");
                    }
                }
            }
        });
    }
}
