package com.softgarden.simplemoligy.utils;

/**
 * Created by admin on 2018/4/17.
 */

public class PushUtil {
    private static OnPushListener mOnPushListener;

    private PushUtil(){}

    public static void setOnPushListener(OnPushListener listener) {
        mOnPushListener = listener;
    }

    public static void setAlias(String alias) {
        if (mOnPushListener != null) {
            mOnPushListener.onSetAlias(alias);
        }
    }

    public static void stopPush() {
        if (mOnPushListener != null) {
            mOnPushListener.onStop();
        }
    }

    public interface OnPushListener {
        void onSetAlias(String alias);

        void onStop();
    }
}
