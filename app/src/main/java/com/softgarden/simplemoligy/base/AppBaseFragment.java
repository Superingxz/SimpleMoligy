package com.softgarden.simplemoligy.base;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.softgarden.baselibrary.base.BaseFragment;
import com.softgarden.baselibrary.base.IBaseViewModel;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.softgarden.simplemoligy.config.RouterPath;
import com.softgarden.simplemoligy.bean.UserBean;
import com.softgarden.simplemoligy.network.ApiException;
import com.softgarden.simplemoligy.utils.PushUtil;


/**
 * Fragment基类 懒加载
 */
public abstract class AppBaseFragment<VM extends IBaseViewModel, B extends ViewDataBinding> extends BaseFragment<VM, B> {
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
                if (!TextUtils.isEmpty(message)) {
                    ToastUtil.s(message);
                }
            }
        } else {
            super.showError(e);
        }
    }
}