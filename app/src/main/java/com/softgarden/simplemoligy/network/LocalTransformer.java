package com.softgarden.simplemoligy.network;


import android.app.Activity;

import com.softgarden.baselibrary.base.databinding.DataBindingActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Lightwave on 2016/6/28.
 */
public class LocalTransformer<T> implements ObservableTransformer<T, T> {
    private DataBindingActivity activity;

    public LocalTransformer(Activity activity) {
        if (activity instanceof DataBindingActivity)
            this.activity = (DataBindingActivity) activity;
        else {
            throw Exceptions.propagate(new RuntimeException("activity is not instanceof DataBindingActivity"));
        }
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> activity.showProgressDialog())
                .doOnTerminate(() -> activity.hideProgressDialog())
                .doFinally(() -> activity.hideProgressDialog())
                .compose(activity.bindToLifecycle());

    }

}
