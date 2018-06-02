package com.softgarden.baselibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.softgarden.baselibrary.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

/**
 * Created by MirkoWu on 2017/4/5 0005.
 */

public class RxPermissionsUtil {

    /**
     * 检查是否有该权限
     *
     * @param mContext
     * @param permission
     * @return
     */
    public static boolean checkPermission(Context mContext, String permission) {
        return ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkStorage(Context mContext) {
        return checkPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 检查是否精确定位
     * @param mContext
     * @return
     */
    public static boolean checkLocationFine(Context mContext) {
        return checkPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static boolean checkOverlaysPermission(Context mContext) {
        return checkPermission(mContext, Manifest.permission.SYSTEM_ALERT_WINDOW);
    }


    public static Observable<Boolean> requestPermisson(Activity activity, String... permissions) {
        return new RxPermissions(activity).request(permissions);
    }

    public static Observable<Boolean> shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        return new RxPermissions(activity).shouldShowRequestPermissionRationale(activity, permissions);
    }


    /**
     * 提示缺少什么权限的对话框
     */
    public static void showPermissionDialog(Context context, String title, String message,
                                     DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .setPositiveButton(R.string.ok, onClickListener).show();
    }

    /**
     * 提示缺少必要权限对话框
     */
    public static void showLackPermissionDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.tips_message)
                .setMessage(R.string.permission_lack)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .setPositiveButton(R.string.ok, (dialog, which) -> startAppSettings(context)).show();
    }

    /**
     * 启动当前应用设置页面
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /***
     * 常用的几个权限申请
     */

    public static boolean checkCamera(Context mContext) {
        return checkPermission(mContext, Manifest.permission.CAMERA);
    }

    /**
     * 申请相机权限
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestCamera(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.CAMERA);
    }

    /**
     * 申请存储
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestStorage(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 申请定位 粗略
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestLocationCoarse(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    /**
     * 申请定位 精确
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestLocationFine(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    /**
     * 申请悬浮窗 精确
     *
     * @param activity
     * @return
     */
    public static Observable<Boolean> requestOverlays(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.SYSTEM_ALERT_WINDOW);
    }

    /**
     * 悬浮窗权限检查
     * @param activity
     * @return
     */
    public static boolean checkOverlays(Activity activity){
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(activity)) {
                //有悬浮窗权限开启服务绑定 绑定权限
                return true;
            } else {
                //没有悬浮窗权限m,去开启悬浮窗权限
                new RxPermissions(activity).request(Manifest.permission.SYSTEM_ALERT_WINDOW);
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return true;
    }

    public static void requestMainPermision(Activity activity, int requestCode,Context appContext) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkLocation = ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_COARSE_LOCATION);
            int checkCall = ContextCompat.checkSelfPermission(appContext, Manifest.permission.CALL_PHONE);
            int checkCamera = ContextCompat.checkSelfPermission(appContext, Manifest.permission.CAMERA);
            int checkWriteSD = ContextCompat.checkSelfPermission(appContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int checkMic = ContextCompat.checkSelfPermission(appContext, Manifest.permission.RECORD_AUDIO);
            if (PackageManager.PERMISSION_GRANTED != checkLocation
                    || PackageManager.PERMISSION_GRANTED != checkCall
                    || PackageManager.PERMISSION_GRANTED != checkCamera
                    || PackageManager.PERMISSION_GRANTED != checkWriteSD
                    || PackageManager.PERMISSION_GRANTED != checkMic
                    ) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, requestCode);
                return;
            }
        }
    }

    public static boolean checkPermition(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
            int checkCall = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
            int checkCamera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
            int checkWriteSD = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int checkMic = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
            if (PackageManager.PERMISSION_GRANTED != checkLocation
                    || PackageManager.PERMISSION_GRANTED != checkCall
                    || PackageManager.PERMISSION_GRANTED != checkCamera
                    || PackageManager.PERMISSION_GRANTED != checkWriteSD
                    || PackageManager.PERMISSION_GRANTED != checkMic
                    ) {
                return false;
            }
        }
        return true;
    }

}
