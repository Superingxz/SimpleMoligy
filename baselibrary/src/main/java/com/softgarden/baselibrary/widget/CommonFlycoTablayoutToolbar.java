package com.softgarden.baselibrary.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.softgarden.baselibrary.R;
import com.softgarden.baselibrary.databinding.LayoutFlycoTablayoutToolbarBinding;


import java.lang.reflect.Field;

/**
 * Created by admin on 2017/11/8.
 */

public class CommonFlycoTablayoutToolbar extends Toolbar {

    private boolean showSplitLine = true;//是否显示分割线

    private LayoutFlycoTablayoutToolbarBinding mbinding;

    public CommonFlycoTablayoutToolbar(Context context) {
        this(context, null);
    }

    public CommonFlycoTablayoutToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonFlycoTablayoutToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mbinding =  DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_flyco_tablayout_toolbar, this, true);
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 显示状态栏 ((此功能需配合沉浸式API>=19才会生效))
     * 默认为透明，保持和toolbar一样的颜色
     */
    public void showStatusBar() {
        showStatusBar(Color.TRANSPARENT);
    }


    /**
     * 显示状态栏 (此功能需配合沉浸式API>=19才会生效)
     *
     * @param colorId
     */
    public void showStatusBar(@ColorInt int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//android 19 4.4 以上才支持沉浸式
            if (getContext() instanceof Activity) {//设置 沉浸式 flag
                ((Activity) getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            mbinding.mStatusBar.setVisibility(VISIBLE);
            mbinding.mStatusBar.setBackgroundColor(colorId);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mbinding.mStatusBar.getLayoutParams();
            params.height = getStatusBarHeight();
        }
    }


    /**
     * 显示图形菜单按钮,右边
     */
    public void showImageLeft(@DrawableRes int resId, OnClickListener listener) {
        mbinding.imgToolbarMenuLeft.setVisibility(View.VISIBLE);
        mbinding.imgToolbarMenuLeft.setImageResource(resId);
        mbinding.imgToolbarMenuLeft.setOnClickListener(listener);
    }

    /**
     * 显示文本菜单按钮
     */
    public void showTextLeft(CharSequence content, OnClickListener listener) {
        mbinding.tvToolbarMenuLeft.setVisibility(View.VISIBLE);
        mbinding.tvToolbarMenuLeft.setText(content);
        mbinding.tvToolbarMenuLeft.setOnClickListener(listener);
    }

    public void showTextLeft(@StringRes int resId, OnClickListener listener) {
        showTextLeft(getContext().getText(resId), listener);
    }

    /**
     * 显示文本菜单按钮
     *
     * @param content
     * @param listener
     */
    public void showTextRight(CharSequence content, OnClickListener listener) {
        mbinding.tvToolbarMenuRight.setVisibility(View.VISIBLE);
        mbinding.tvToolbarMenuRight.setText(content);
        mbinding.tvToolbarMenuRight.setOnClickListener(listener);
    }

    public void showTextRight(@StringRes int content, OnClickListener listener) {
        showTextRight(getContext().getText(content), listener);
    }

    /**
     * 显示图形菜单按钮,右边
     */
    public void showImageRight(@DrawableRes int resId, OnClickListener listener) {
        mbinding.imgToolbarMenuRight.setVisibility(View.VISIBLE);
        mbinding.imgToolbarMenuRight.setImageResource(resId);
        mbinding.imgToolbarMenuRight.setOnClickListener(listener);
    }

    /**
     * 显示图形菜单按钮,右边
     */
    public void showImageRight(@DrawableRes int resId) {
        mbinding.imgToolbarMenuRight.setVisibility(View.VISIBLE);
        mbinding.imgToolbarMenuRight.setImageResource(resId);
    }

    //Tablayout
    public void setViewPager(ViewPager viewPager){
        mbinding.tablayout.setViewPager(viewPager);
    }

    public void setOnTabListener(OnTabSelectListener onTabListener){
        mbinding.tablayout.setOnTabSelectListener(onTabListener);
    }

    /**
     * 设置整个toolbar背景色
     *
     * @param colorResId
     */
    @SuppressLint("ResourceType")
    @Override
    public void setBackgroundColor(@ColorRes int colorResId) {
        mbinding.layoutToolbar.setBackgroundColor(ContextCompat.getColor(getContext(), colorResId));
    }

    public void setStatusBarPadding() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//android 19 4.4 以上才支持沉浸式
            int statusBarHeight = getStatusBarHeight();
            LayoutParams params = (LayoutParams) mbinding.layoutToolbar.getLayoutParams();
            params.height = params.height + statusBarHeight;
            mbinding.layoutToolbar.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 设置返回按钮,
     * image 将资源id设置 <= 0 的值就可以隐藏返回键
     */
    public void setBackButton(@DrawableRes int resId) {
        if (resId <= 0) {
            mbinding.imgToolbarMenuLeft.setVisibility(View.GONE);
            return;
        }
        showImageLeft(resId, v -> {
            if (getContext() instanceof Activity)
                ((Activity) getContext()).onBackPressed();//调用activity的返回键
        });
    }

    public TextView getRightTextView() {
        return mbinding.tvToolbarMenuRight;
    }

    public ImageView getRightImageView() {
        return mbinding.imgToolbarMenuRight;
    }

    public ImageView getLeftImageView() {
        return mbinding.imgToolbarMenuLeft;
    }

    public TextView getLeftTextView() {
        return mbinding.tvToolbarMenuRight;
    }

    public SlidingTabLayout getTabLayout(){
        return mbinding.tablayout;
    }

    /**
     * builder 模式
     */
    public static class Builder {
        private CharSequence leftStr, rightStr;
        private int backgroundColorResId, leftImgResId, leftStrResId, rightImgResId, rightStrResId;
        private OnClickListener leftOnClickListener, rightOnClickListener;
        private int statusBarColorId = Color.BLACK, backgroundColorId = Color.WHITE,
                allTextColorId = Color.BLACK, titleColorId = Color.BLACK
                , indicatorColorId = Color.BLACK, dividerColorId = Color.BLACK
                , tabSelectColorId = Color.BLACK, tabUnselectColorId = Color.GRAY;//均设置默认值

        private ViewPager mViewPager;
        private OnTabSelectListener listener;
        private int leftDip;
        private int rightDip;


        public Builder showImageLeft(@DrawableRes int leftImgResId, OnClickListener leftOnClickListener) {
            this.leftImgResId = leftImgResId;
            this.leftOnClickListener = leftOnClickListener;
            return this;
        }

        public Builder showImageRight(@DrawableRes int rightImgResId, OnClickListener rightOnClickListener) {
            this.rightImgResId = rightImgResId;
            this.rightOnClickListener = rightOnClickListener;
            return this;
        }


        public Builder showImageRight(@DrawableRes int rightImgResId) {
            this.rightImgResId = rightImgResId;
            return this;
        }


        public Builder setTabLayoutParams(ViewPager viewPager, OnTabSelectListener listener){
            this.mViewPager = viewPager;
            this.listener = listener;
            return this;
        }

        public Builder showTextLeft(CharSequence leftStr, OnClickListener leftOnClickListener) {
            this.leftStr = leftStr;
            this.leftOnClickListener = leftOnClickListener;
            return this;
        }

        public Builder showTextLeft(@StringRes int leftStrResId, OnClickListener leftOnClickListener) {
            this.leftStrResId = leftStrResId;
            this.leftOnClickListener = leftOnClickListener;
            return this;
        }

        public Builder showTextRight(CharSequence rightStr, OnClickListener rightOnClickListener) {
            this.rightStr = rightStr;
            this.rightOnClickListener = rightOnClickListener;
            return this;
        }

        public Builder showTextRight(@StringRes int rightStrResId, OnClickListener rightOnClickListener) {
            this.rightStrResId = rightStrResId;
            this.rightOnClickListener = rightOnClickListener;
            return this;
        }

        public Builder setViewPager(ViewPager viewPager){
            this.mViewPager = viewPager;
            return this;
        }

        public Builder setTabListener(OnTabSelectListener onTabSelectListener){
            this.listener = onTabSelectListener;
            return this;
        }

        /**
         * 优先级比 showImageLeft 低
         *
         * @param backResId
         * @return
         */
        public Builder setBackButton(@DrawableRes int backResId) {
            this.leftImgResId = backResId;
            return this;
        }

        public Builder setBackgroundColor(@ColorRes int backgroundColorResId) {
            this.backgroundColorResId = backgroundColorResId;
            return this;
        }

        public Builder showStatusBar(@ColorInt int statusBarColorId) {
            this.statusBarColorId = statusBarColorId;
            return this;
        }

        public Builder setIndicatorColor(@ColorInt int indicatorColorId){
            this.indicatorColorId = indicatorColorId;
            return this;
        }

        public Builder setDividerColor(@ColorInt int dividerColorId){
            this.dividerColorId = dividerColorId;
            return this;
        }

        public Builder setTabSelectColor(@ColorInt int tabSelectColorId){
            this.tabSelectColorId = tabSelectColorId;
            return this;
        }

        public Builder setTabUnSelectColor(@ColorInt int tabUnSelectColorId){
            this.tabUnselectColorId = tabUnSelectColorId;
            return this;
        }

        public CommonFlycoTablayoutToolbar build(Activity activity) {
            CommonFlycoTablayoutToolbar toolbar = new CommonFlycoTablayoutToolbar(activity);

            /*** setBackgroundColor */
            if (backgroundColorResId > 0) toolbar.setBackgroundColor(backgroundColorResId);

            //flycotablayout颜色
            if (indicatorColorId > 0) {
                toolbar.getTabLayout().setIndicatorColor(indicatorColorId);
            }

            if (dividerColorId > 0) {
                toolbar.getTabLayout().setDividerColor(indicatorColorId);
            }

            if (tabSelectColorId > 0) {
                toolbar.getTabLayout().setTextSelectColor(tabSelectColorId);
            }

            if (tabUnselectColorId > 0) {
                toolbar.getTabLayout().setTextUnselectColor(tabUnselectColorId);
            }

            /*** default backbuttton 这里默认设置一个返回按钮图标 */
            toolbar.setBackButton(leftImgResId);

            /*** leftMenu */
            if (TextUtils.isEmpty(leftStr)) {
                if (leftStrResId > 0) toolbar.showTextLeft(leftStrResId, leftOnClickListener);
            } else toolbar.showTextLeft(leftStr, leftOnClickListener);
            if (leftImgResId > 0) toolbar.showImageLeft(leftImgResId, leftOnClickListener);

            /*** rightMenu */
            if (TextUtils.isEmpty(rightStr)) {
                if (rightStrResId > 0)
                    toolbar.showTextRight(rightStrResId, rightOnClickListener);
            } else toolbar.showTextRight(rightStr, rightOnClickListener);
            if (rightImgResId > 0) toolbar.showImageRight(rightImgResId, rightOnClickListener);

            if (mViewPager != null && mViewPager.getAdapter() != null) {
                toolbar.setViewPager(mViewPager);
            }
            if (listener != null) {
                toolbar.setOnTabListener(listener);
            }

            toolbar.showStatusBar(statusBarColorId);

            System.out.println("CommonToolbar.Builder.build");
            return toolbar;
        }
    }
}
