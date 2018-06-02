package com.softgarden.baselibrary.base;

import android.databinding.ViewDataBinding;

import com.softgarden.baselibrary.base.databinding.DataBindingActivity;
import com.softgarden.baselibrary.base.databinding.DataTranBindingActivity;
import com.softgarden.baselibrary.utils.InstanceUtil;

import java.lang.reflect.ParameterizedType;

/**
 * Activity的基类
 * Created by Administrator on 2015/6/2.
 */

public abstract class BaseActivity<VM extends IBaseViewModel, B extends ViewDataBinding> extends DataBindingActivity<B> {
    protected VM mViewModel;

    @Override
    protected void initViewModel() {
        if (this instanceof IBaseDisplay
                && this.getClass().getGenericSuperclass() instanceof ParameterizedType
                && ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class mViewModelClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass()))
                    .getActualTypeArguments()[0];//获取Presenter的class
            mViewModel = InstanceUtil.getInstance(mViewModelClass);
            if (mViewModel != null) mViewModel.attachView(this);
        }
    }

    @Override
    protected void initPreData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) mViewModel.detachView();
    }

}
