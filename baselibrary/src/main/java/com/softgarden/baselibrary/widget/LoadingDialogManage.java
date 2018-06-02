package com.softgarden.baselibrary.widget;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2016/3/15.
 */
public class LoadingDialogManage {
    private static int mCount = 0;
    private static LoadingDialog mLoadingDialog;

    private LoadingDialogManage() {
    }

    public static void showLoading(Context context) {
       showLoading(context,null,true);
    }

    public static void showLoading(Context context, String msg) {
        showLoading(context,msg,true);
    }



    public static void showLoading(Context context, String msg, boolean cancelable) {
        if (mCount == 0) {
            mLoadingDialog = new LoadingDialog(context,msg,cancelable);
            mLoadingDialog.setOnCancelListener(dialogInterface -> mCount = 0);
            mLoadingDialog.show();
        }
        mCount++;
    }

    public static void dismissLoading() {
        if (mCount == 0) {
            return;
        }

        mCount--;
        if (mCount == 0) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    public static void dismissLoadingAll(){
        mCount = 0;
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    public static boolean isDismissLoading(){
        if (mCount == 0 && mLoadingDialog != null) {
            return !mLoadingDialog.isShowing();
        }
        return false;
    }
}
