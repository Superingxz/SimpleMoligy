package com.softgarden.simplemoligy.utils;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;

import com.softgarden.baselibrary.utils.LogUtil;


/**
 * Created by liujinghui on 6/28/16.
 */
public class DimenUtil {

    public static int getPix(Context context, int id) {
        return context.getResources().getDimensionPixelSize(id);
    }

    public static int getWidthOffsetScreen(Activity context, int id) {
        return getScreenWidth(context) - context.getResources().getDimensionPixelSize(id);
    }

    public static int getHeightOffsetScreen(Activity context, int id) {
        return getScreenHeight(context) - context.getResources().getDimensionPixelSize(id);
    }

    public static int getScreenWidth(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static int getStatuBarHeight(Activity context) {

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        LogUtil.i("getStatuBarHeight=>" + result);
        return result;
    }

    public static int getTitleBarHeight(Activity context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        LogUtil.i("getTitleBarHeight=>" + actionBarHeight);
        return actionBarHeight;
    }

}
