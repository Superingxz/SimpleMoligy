package com.softgarden.simplemoligy.base;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.barlibrary.ImmersionBar;
import com.softgarden.baselibrary.base.IBaseViewModel;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.softgarden.simplemoligy.config.RouterPath;
import com.softgarden.simplemoligy.bean.UserBean;
import com.softgarden.simplemoligy.network.ApiException;
import com.softgarden.simplemoligy.refresh.RefreshActivity;
import com.softgarden.simplemoligy.utils.PushUtil;


/**
 * Created by moligy on 2017/12/11.
 */

public abstract class AppBaseRefreshActivity<VM extends IBaseViewModel, B extends ViewDataBinding> extends RefreshActivity<VM, B> {
    protected boolean isUserImmersionBarUtil = true;//是否使用ImmersionBar
    protected ImmersionBar mImmersionBar;
    protected boolean keyboardEnable =false;
    protected int keyboardMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        if (isUserImmersionBarUtil) {
            mImmersionBar = ImmersionBar.with(this)
                    .keyboardEnable(keyboardEnable)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                    .keyboardMode(keyboardMode)  //单独指定软键盘模式
                    .statusBarDarkFont(true);
            mImmersionBar.init();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2

    }

    @Override
    public void showError(Throwable e) {
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            if (apiException.getStatus() == -1) {//token过期 这里不用管前面已处理
                UserBean.cleanLogin(); //清除数据
                ARouter.getInstance().build(RouterPath.LOGIN)
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation(); //跳转登录页面
                PushUtil.stopPush();
                finish();
            } else {
                String message = apiException.getMessage();
                if (!TextUtils.isEmpty(message)
                        && !"".equals(message)) {
                    ToastUtil.s(message);
                }
            }
        } else {
            super.showError(e);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null && isUserImmersionBarUtil) {
            mImmersionBar.destroy();
        }
    }
}