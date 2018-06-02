package com.softgarden.baselibrary.base;


import android.app.Activity;
import android.view.View;

/**
 * 模块的页面基类
 */
public abstract class BasePager {
    public Activity context;//上下文
    private View rootView;//页面的根节点视图对象

    public BasePager(Activity context) {
        this.context = context;
        rootView = initView();
    }

    public View getRootView() {
        return rootView;
    }

    //初始化视图,父类不能决定子类的界面，所有要抽象出去
    public abstract View initView();

    //初始化数据,父类不能决定子类要加载的数据，所有要抽象出去
    public abstract void initData();
}
