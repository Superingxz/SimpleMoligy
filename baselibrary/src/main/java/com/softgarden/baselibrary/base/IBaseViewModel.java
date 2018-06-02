package com.softgarden.baselibrary.base;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public interface IBaseViewModel<V extends IBaseDisplay> {
    void attachView(V view);

    void detachView();
}
