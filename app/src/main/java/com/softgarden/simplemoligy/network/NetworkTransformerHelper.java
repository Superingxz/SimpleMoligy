package com.softgarden.simplemoligy.network;


import android.text.TextUtils;

import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.utils.ToastUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Lightwave on 2016/6/28.
 */
public class NetworkTransformerHelper<T> implements ObservableTransformer<BaseBean<T>, T> {
    private IBaseDisplay view;

    public NetworkTransformerHelper(IBaseDisplay view) {
        this.view = view;
    }

    @Override
    public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showProgressDialog())
                .doOnTerminate(() -> view.hideProgressDialog())
                .map(baseBean -> {
                    if (baseBean.status == 1) {
                        if (baseBean.data == null) {
                            view.hideProgressDialog();
                            view.noData();
                            //TODO   ToastUtil.s(baseBean.errorMsg); 还是放到activity 和fragment 显示吧
                            throw Exceptions.propagate(new ApiException(baseBean.status, baseBean.info));
                        } else {
                            if (!TextUtils.isEmpty(baseBean.info)) {
                                if (!"".equals(baseBean.info)) {
                                    ToastUtil.s(baseBean.info);
                                }
                            }
                            return baseBean;
                        }
                    } else if (baseBean.status == -1) {
                        //LoginBean.cleanLogin(); //清除数据
                        //ARouter.getInstance().build(RouterPath.USERCENTER_LOGIN_LOGINPAGE)
                        //        .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        //        .navigation(); //跳转登录页面
                        //PushUtil.stopPush();  //取消极光推送
                        view.hideProgressDialog();
                        //TODO   ToastUtil.s(baseBean.errorMsg); 还是放到activity 和fragment 显示吧
                        throw Exceptions.propagate(new ApiException(baseBean.status, baseBean.info));
                    } else {
                        view.hideProgressDialog();
                        //TODO   ToastUtil.s(baseBean.errorMsg); 还是放到activity 和fragment 显示吧
                        throw Exceptions.propagate(new ApiException(baseBean.status, baseBean.info));
                    }
                })
                .map(baseBean -> {
                    if (baseBean.data == null||baseBean.data.equals("[]")) {
                        baseBean.data = (T) "";
                    }
                    return baseBean;
                })
                .map(baseBean -> baseBean.data)
                .compose(view.bindToLifecycle());

    }
}
