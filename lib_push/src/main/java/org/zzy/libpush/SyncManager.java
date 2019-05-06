package org.zzy.libpush;

import android.content.Context;

import org.zzy.libpush.interceptor.HttpLoggingInterceptor;
import org.zzy.libpush.utils.Debug;
import org.zzy.libpush.utils.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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
}
