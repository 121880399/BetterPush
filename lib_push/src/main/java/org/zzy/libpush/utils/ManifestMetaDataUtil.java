package org.zzy.libpush.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * 获取Mainfest文件中定义的元数据的值 工具类
 */
public class ManifestMetaDataUtil {
    private final static Map<String, Object> mCacheMetaData = new HashMap<>();
    private static int versionCode = -1;
    private static String versionName;
    private ManifestMetaDataUtil() {

    }

    private static Object readKey(@NonNull Context context, String keyName) {
        try {
            Object metaStr = mCacheMetaData.get(keyName);
            if(metaStr != null){
                return metaStr;
            }
            ApplicationInfo appi = context.getApplicationContext().getPackageManager()
                        .getApplicationInfo(context.getPackageName(),
                                PackageManager.GET_META_DATA);
            Bundle bundle = appi.metaData;
            metaStr = bundle.get(keyName);
            mCacheMetaData.put(keyName, metaStr);
            return metaStr;

        } catch (NameNotFoundException e) {
            return null;
        }

    }

    @Nullable
    public static String getString(@NonNull Context context, String keyName) {
        return String.valueOf(readKey(context, keyName));
    }

    @Nullable
    public static Boolean getBoolean(@NonNull Context context, String keyName) {
        Object o = readKey(context, keyName);
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        if (o instanceof String) {
            return Boolean.valueOf((String) o);
        }
        return false;
    }

    @Nullable
    public static Object get(@NonNull Context context, String keyName) {
        return readKey(context, keyName);
    }

    /**
     * 获取当前版本
     */
    public static int getVersionCode(@NonNull Context context) {
        try {
            if(versionCode != -1){
                return versionCode;
            }
            PackageInfo packageInfo = context.getApplicationContext().getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName(@NonNull Context context) {
        try {
            if(!TextUtils.isEmpty(versionName)){
                return versionName;
            }
            PackageInfo packageInfo = context.getApplicationContext().getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            return versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
