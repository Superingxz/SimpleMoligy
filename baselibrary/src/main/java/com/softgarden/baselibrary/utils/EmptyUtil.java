package com.softgarden.baselibrary.utils;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by DELL on 2017/8/19.
 */

public class EmptyUtil {
    private EmptyUtil() {
        throw new UnsupportedOperationException("u can`t fuck me...");
    }

    /**
     * 列表是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(@Nullable CharSequence str) {
        return !isEmpty(str);
    }

    public static String nullIfEmpty(@Nullable String str) {
        return isEmpty(str) ? null : str;
    }

}
