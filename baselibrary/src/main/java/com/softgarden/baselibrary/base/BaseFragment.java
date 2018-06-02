package com.softgarden.baselibrary.base;

import android.databinding.ViewDataBinding;

import com.softgarden.baselibrary.base.databinding.DataBindingFragment;
import com.softgarden.baselibrary.utils.InstanceUtil;

import java.lang.reflect.ParameterizedType;

/**
 * Activity的基类
 * Created by Administrator on 2015/6/2.
 */

public abstract class BaseFragment<VM extends IBaseViewModel, B extends ViewDataBinding> extends DataBindingFragment<B> {
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mViewModel != null) mViewModel.detachView();
    }

}
