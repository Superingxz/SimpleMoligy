package com.softgarden.baselibrary.base;


/**
 * 引入 IBaseDisplay mView  控制RxJava生命周期
 */
public class RxViewModel<V extends IBaseDisplay> implements IBaseViewModel<V> {
    protected V mView;

    @Override
    public void attachView(V display) {
        this.mView = display;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
