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
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softgarden.baselibrary.R;
import com.softgarden.baselibrary.databinding.LayoutSearchToolbarBinding;

import java.lang.reflect.Field;

/**
 * Created by admin on 2017/11/22.
 */

public class CommonSearchToolbar extends Toolbar {
    private boolean showSplitLine = true;//是否显示分割线

    private LayoutSearchToolbarBinding mbinding;

    public CommonSearchToolbar(Context context) {
        this(context, null);
    }

    public CommonSearchToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonSearchToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mbinding =  DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_search_toolbar, this, true);
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
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mbinding.mStatusBar.getLayoutParams();
            params.height = getStatusBarHeight();
        }
    }

    /**
     * 隐藏 statusBar
     */
    public void hideStatusBar() {
        mbinding.mStatusBar.setVisibility(GONE);
    }

    /**
     * toolbar 和布局的分割线
     *
     * @param colorId
     * @param height
     */
    public void showSplitLine(@ColorInt int colorId, int height) {
        mbinding.mSplitLine.setVisibility(VISIBLE);
        mbinding.mSplitLine.setBackgroundColor(colorId);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mbinding.mSplitLine.getLayoutParams();
        params.height = height;
    }

    /**
     * 隐藏 分割线
     */
    public void hideSplitLine() {
        mbinding.mSplitLine.setVisibility(GONE);
    }

    /**
     * 显示图形菜单按钮,右边
     */
    public void showImageLeft(@DrawableRes int resId, OnClickListener listener) {
        mbinding.leftRl.setVisibility(View.VISIBLE);
        mbinding.imgToolbarMenuLeft.setVisibility(View.VISIBLE);
        mbinding.imgToolbarMenuLeft.setImageResource(resId);
        mbinding.imgToolbarMenuLeft.setOnClickListener(listener);
    }

    /**
     * 显示文本菜单按钮
     */
    /**
     * 显示文本菜单按钮
     */
    public void showTextLeft(CharSequence content) {
        mbinding.leftRl.setVisibility(View.VISIBLE);
        mbinding.tvToolbarMenuLeft.setVisibility(View.VISIBLE);
        mbinding.tvToolbarMenuLeft.setText(content);
    }

    public void showTextLeft(CharSequence content, OnClickListener listener) {
        mbinding.leftRl.setVisibility(View.VISIBLE);
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
        mbinding.rightRl.setVisibility(View.VISIBLE);
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
        mbinding.rightRl.setVisibility(View.VISIBLE);
        mbinding.imgToolbarMenuRight.setVisibility(View.VISIBLE);
        mbinding.imgToolbarMenuRight.setImageResource(resId);
        mbinding.imgToolbarMenuRight.setOnClickListener(listener);
    }

    public void showSearchMenuLeftImage(@DrawableRes int resId, OnClickListener listener){
        mbinding.innerMenuLeft.setVisibility(View.VISIBLE);
        mbinding.innerMenuLeft.setImageResource(resId);
        mbinding.innerMenuLeft.setOnClickListener(listener);
    }

    public void showSearchMenuRightImage(@DrawableRes int resId, OnClickListener listener){
        mbinding.innerMenuRight.setVisibility(View.VISIBLE);
        mbinding.innerMenuRight.setImageResource(resId);
        mbinding.innerMenuRight.setOnClickListener(listener);
    }

    public void showSearchTv(CharSequence hintText, OnClickListener listener) {
        mbinding.searchTv.setVisibility(View.VISIBLE);
        mbinding.searchTv.setText(hintText);
        mbinding.searchTv.setOnClickListener(listener);
    }

    public void showSearchEt(CharSequence hintText, OnClickListener listener,TextView.OnEditorActionListener actionListener) {
        mbinding.searchEt.setVisibility(View.VISIBLE);
        mbinding.searchEt.setHint(hintText);
        mbinding.searchEt.setOnClickListener(listener);
        mbinding.searchEt.setOnEditorActionListener(actionListener);
    }

    /**
     * 统一设置文字的颜色
     *
     * @param colorId
     */
    public void setAllTextColor(@ColorInt int colorId) {
        mbinding.tvToolbarMenuLeft.setTextColor(colorId);
        mbinding.tvToolbarMenuRight.setTextColor(colorId);
    }

    /**
     * 设置整个toolbar背景色
     *
     * @param colorResId
     */
    @Override
    public void setBackgroundColor(@ColorRes int colorResId) {
        mbinding.layoutToolbar.setBackgroundColor(colorResId);
    }

    public void setStatusBarPadding() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//android 19 4.4 以上才支持沉浸式
            int statusBarHeight = getStatusBarHeight();
            LayoutParams params = (LayoutParams) mbinding.layoutToolbar.getLayoutParams();
            params.height = params.height + statusBarHeight;
            mbinding.layoutToolbar.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    public void clearSearchContent(){
        getSearchEditText().setText("");
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

    public TextView getSearchTextView(){
        return mbinding.searchTv;
    }

    public EditText getSearchEditText(){
        return mbinding.searchEt;
    }

    /**
     * builder 模式
     */
    public static class Builder {
        private int statusBarColorId = Color.BLACK, backgroundColorId = Color.WHITE,
                allTextColorId = Color.BLACK, titleColorId = Color.BLACK;//均设置默认值
        private CharSequence leftStr, rightStr,tv_hintText,et_hintText;
        private int backgroundColorResId, leftImgResId, leftStrResId, rightImgResId, rightStrResId,
                leftSearchImgResId,rightSearchImgResId;
        private OnClickListener leftOnClickListener, rightOnClickListener,leftSearchOnClickListener,rightSearchOnClickListener,
                tvSearchOnClickListener,etSearchOnClickListener;

        private TextView.OnEditorActionListener actionListener;

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

        public Builder showSearchMenuLeftImage(@DrawableRes int resId, OnClickListener leftSearchOnClickListener){
            this.leftSearchImgResId = resId;
            this.leftSearchOnClickListener = leftSearchOnClickListener;
            return this;
        }

        public Builder showSearchMenuRightImage(@DrawableRes int rightSearchImgResId, OnClickListener rightSearchOnClickListener){
            this.rightSearchImgResId = rightSearchImgResId;
            this.rightSearchOnClickListener = rightSearchOnClickListener;
            return this;
        }

        public Builder showSearchTv(String tv_hintText, OnClickListener tvSearchOnClickListener) {
            this.tv_hintText = tv_hintText;
            this.tvSearchOnClickListener = tvSearchOnClickListener;
            return this;
        }

        public Builder showSearchEt(String et_hintText, OnClickListener etSearchOnClickListener,TextView.OnEditorActionListener actionListener) {
            this.et_hintText = et_hintText;
            this.etSearchOnClickListener = etSearchOnClickListener;
            this.actionListener = actionListener;
            return this;
        }

        public Builder setAllTextColor(@ColorInt int allTextColorId) {
            this.allTextColorId = allTextColorId;
            this.titleColorId = allTextColorId;
            return this;
        }

        public Builder setBackgroundColor(@ColorInt int backgroundColorId) {
            this.backgroundColorId = backgroundColorId;
            return this;
        }

        public Builder showStatusBar(@ColorInt int statusBarColorId) {
            this.statusBarColorId = statusBarColorId;
            return this;
        }

        public CommonSearchToolbar build(Activity activity) {
            CommonSearchToolbar toolbar = new CommonSearchToolbar(activity);

            /*** setBackgroundColor */
            if (backgroundColorResId > 0) toolbar.setBackgroundColor(backgroundColorResId);

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

            if (leftSearchImgResId > 0 ) {
                toolbar.showSearchMenuLeftImage(leftSearchImgResId, leftSearchOnClickListener);
            }

            if (rightSearchImgResId > 0) {
                toolbar.showSearchMenuRightImage(rightSearchImgResId, rightSearchOnClickListener);
            }

            if (!TextUtils.isEmpty(tv_hintText)) {
                toolbar.showSearchTv(tv_hintText,tvSearchOnClickListener);
            }

            if (!TextUtils.isEmpty(et_hintText)) {
                toolbar.showSearchEt(et_hintText, etSearchOnClickListener, actionListener);
            }

            toolbar.showStatusBar(statusBarColorId);

            toolbar.setBackgroundColor(backgroundColorId);

            toolbar.setAllTextColor(allTextColorId);

            System.out.println("CommonToolbar.Builder.build");
            return toolbar;
        }
    }
}
