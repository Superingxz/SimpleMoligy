package com.softgarden.baselibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softgarden.baselibrary.R;
import com.softgarden.baselibrary.databinding.LayoutTablayoutToolbarBinding;

import java.lang.reflect.Field;

/**
 * Created by admin on 2017/11/8.
 */

public class CommonTablayoutToolbar extends Toolbar {

    private boolean showSplitLine = true;//是否显示分割线

    private LayoutTablayoutToolbarBinding mbinding;

    public CommonTablayoutToolbar(Context context) {
        this(context, null);
    }

    public CommonTablayoutToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTablayoutToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mbinding =  DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_tablayout_toolbar, this, true);
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

    public void setViewPager(ViewPager viewPager, TabLayout.OnTabSelectedListener listener,int leftDip, int rightDip){
        mbinding.tablayout.setupWithViewPager(viewPager);
        mbinding.tablayout.addOnTabSelectedListener(listener);
        //setIndicator(mbinding.tablayout,leftDip,rightDip);
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 设置整个toolbar背景色
     *
     * @param colorResId
     */
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

    public TabLayout getTabLayout(){
        return mbinding.tablayout;
    }

    /**
     * builder 模式
     */
    public static class Builder {
        private CharSequence leftStr, rightStr;
        private int backgroundColorResId, leftImgResId, leftStrResId, rightImgResId, rightStrResId;
        private OnClickListener leftOnClickListener, rightOnClickListener;
        private ViewPager mViewPager;
        private TabLayout.OnTabSelectedListener listener;
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


        public Builder setTabLayoutParams(ViewPager viewPager, TabLayout.OnTabSelectedListener listener, int leftDip, int rightDip){
            this.mViewPager = viewPager;
            this.listener = listener;
            this.leftDip = leftDip;
            this.rightDip = rightDip;
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

        public CommonTablayoutToolbar build(Activity activity) {
            CommonTablayoutToolbar toolbar = new CommonTablayoutToolbar(activity);

            /*** setBackgroundColor */
            if (backgroundColorResId > 0) toolbar.setBackgroundColor(backgroundColorResId);

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

            if (mViewPager != null && listener != null && leftDip != 0 && rightDip != 0) {
                toolbar.setViewPager(mViewPager, listener, leftDip, rightDip);
            }

            System.out.println("CommonToolbar.Builder.build");
            return toolbar;
        }
    }
}
