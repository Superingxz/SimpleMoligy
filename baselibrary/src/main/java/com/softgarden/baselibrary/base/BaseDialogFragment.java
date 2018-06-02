package com.softgarden.baselibrary.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.trello.rxlifecycle2.components.support.RxAppCompatDialogFragment;

/**
 * Created by Lightwave on 2015/12/3.
 */
public abstract class BaseDialogFragment<B extends ViewDataBinding> extends RxAppCompatDialogFragment {
    public final String TAG = getClass().getSimpleName();
    protected B binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initContentView();
        initialize();
    }

    protected void initContentView() {
        if (!isAdded()) return;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable());
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }
//    @NonNull
//    public <T extends Display> T $(@IdRes int resId) {
//        return (T) getView().findViewById(resId);
//    }
//
//    public <T extends Display> T inflate(@LayoutRes int resId) {
//        return (T) Display.inflate(getActivity(), resId, null);
//    }

    public abstract int getLayoutId();

    public abstract void initialize();

}
