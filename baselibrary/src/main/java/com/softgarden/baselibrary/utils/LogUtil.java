package com.softgarden.baselibrary.utils;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.softgarden.baselibrary.BuildConfig;

/**
 * Created by Administrator on 2015/12/15.
 * 统一封装
 */
public class LogUtil {

    public static boolean isDebug = BuildConfig.DEBUG;//BuildConfig.DEBUG
    private static final String TAG = "com.softgarden.priestbeer";

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        LogUtil.e(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg) {
        LogUtil.w(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        LogUtil.d(TAG, msg);
    }

    /**
     * 信息类 不去隐藏log
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (true) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        LogUtil.i(TAG, msg);
    }

    /**
     * 以下为logger 工具
     *
     * @param tag
     * @param msg
     */
    public static void logger_i(String tag, String msg) {
        if (isDebug) {
            Logger.i(tag, msg);
        }
    }

    public static void logger_i(String msg) {
        LogUtil.i(TAG, msg);
    }

    public static void logger_json(String msg) {
        if (isDebug) {
            Logger.i(TAG, msg);
        }
    }

}
