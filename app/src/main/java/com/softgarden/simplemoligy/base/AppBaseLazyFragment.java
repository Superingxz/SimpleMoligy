package com.softgarden.simplemoligy.base;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.softgarden.baselibrary.base.BaseLazyFragment;
import com.softgarden.baselibrary.base.IBaseViewModel;
import com.softgarden.baselibrary.base.rxbase.RxManager;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.softgarden.simplemoligy.config.RouterPath;
import com.softgarden.simplemoligy.bean.UserBean;
import com.softgarden.simplemoligy.network.ApiException;
import com.softgarden.simplemoligy.utils.PushUtil;


/**
 * Fragment基类 懒加载
 */
public abstract class AppBaseLazyFragment<VM extends IBaseViewModel, B extends ViewDataBinding> extends BaseLazyFragment<VM, B> {
    private RxManager rxManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rxManager = new RxManager();
        return super.onCreateView(inflater, container, savedInstanceState);
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
                getActivity().finish();
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
}