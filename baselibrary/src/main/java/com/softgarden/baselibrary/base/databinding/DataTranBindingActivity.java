package com.softgarden.baselibrary.base.databinding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.softgarden.baselibrary.BuildConfig;
import com.softgarden.baselibrary.R;
import com.softgarden.baselibrary.anim.AbsAnimationListener;
import com.softgarden.baselibrary.anim.ActivityAnimationHelper;
import com.softgarden.baselibrary.anim.OnAnimationListener;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.BaseSP;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.softgarden.baselibrary.widget.LoadingDialogManage;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava2.HttpException;


/**
 * Created by MirkoWu on 2017/3/22 0022.
 */

public abstract class DataTranBindingActivity<B extends ViewDataBinding> extends RxAppCompatActivity implements IBaseDisplay {

    public final String TAG = getClass().getSimpleName();
    public static final int RESULT_LOGIN = 0x00001234;

    private boolean mNightMode;

    protected B binding;
    private Toolbar toolBar;

    protected Context mContext;

    private boolean isInterceptKeyEventHandleSelf = false;
    private boolean isInterceptKeyEvent = false;
    private boolean isStopDispatchKeyEvent = false;
    private long mCreateTime = 0;
    private String errorMsg = "";

    protected float fromScaleX = 1.0f;
    protected float fromScaleY = 1.0f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (isFullScreen()) {
            //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                view.setSystemUiVisibility(View.INVISIBLE);
            }*/
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        initPreData();
        //显示当前的Activity路径
        if (BuildConfig.DEBUG) {
            L.e("当前打开的Activity:  " + getClass().getName());
        }

//        ARouter.getInstance().inject(this);//传递参数所需注解
        initViewModel();
        initContentView();

        if (isEnableActivityAnimation()) {
            ActivityAnimationHelper.animScaleUp(this, new OnAnimationListener() {
                @Override
                public void onAnimationStart() {

                }

                @Override
                public void onAnimationEnd() {
                    onActivityAnimationScaleUpEnd();
                }

                @Override
                public void onAnimationPrepare() {

                }
            },getIntent());
        }

        initialize();
        //        mNightMode = (boolean) SPUtils.get(MODE_NIGHT, false);
        //        if (mNightMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    /**
     * 启动动画结束
     */
    protected void onActivityAnimationScaleUpEnd(){

    }

    /**
     * 退出动画结束
     */
    protected void onActivityAnimationScaleDownEnd(){

    }

    /**
     * 是否全屏
     * @return
     */
    protected boolean isFullScreen(){
        return false;
    }

    /**
     *是否启动Acitivity缩放切换动画
     * @return
     */
    protected boolean isEnableActivityAnimation(){
        return false;
    }

    protected  void initPreData(){

    }

    protected void initViewModel() {
    }

    public void initContentView() {
        LinearLayout view = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        view.setBackgroundColor(Color.TRANSPARENT);
        view.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.toolbar_line));
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), view, false);
        toolBar = setToolbar();
        if (toolBar != null) {
            //添加标题栏
            view.addView(toolBar);
            view.addView(binding.getRoot());
            setContentView(view);
            setSupportActionBar(toolBar);//这里才是真的将toolbar设置为actionbar
        }  else {
            view.addView(binding.getRoot());
            setContentView(view);
        }
    }

    public Toolbar getToolbar() {
        return toolBar;
    }

    @Override
    public void useNightMode(boolean isNight) {
        //       boolean night = (boolean) SPUtils.get(MODE_NIGHT, false);
        //        if (night) {
        //            SPUtils.put(MODE_NIGHT, false);
        //            AppCompatDelegate.setDefaultNightMode(
        //                    AppCompatDelegate.MODE_NIGHT_NO);
        //        } else {
        //            SPUtils.put(MODE_NIGHT, true);
        //            AppCompatDelegate.setDefaultNightMode(
        //                    AppCompatDelegate.MODE_NIGHT_YES);
        //        }
        //        // recreate();
        //        reload();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        setPortrait();
        super.onResume();
        //        //检查日夜模式
        //        boolean night = (boolean) SPUtils.get(MODE_NIGHT, false);
        //        if (night != mNightMode) {
        //            if (night) {
        //                AppCompatDelegate.setDefaultNightMode(
        //                        AppCompatDelegate.MODE_NIGHT_YES);
        //            } else {
        //                AppCompatDelegate.setDefaultNightMode(
        //                        AppCompatDelegate.MODE_NIGHT_NO);
        //            }
        //            recreate();
        //            // reload();
        //        }
    }

    /**
     * 限制Activity横竖屏显示
     */
    protected void setPortrait() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void launchActivity(Class<? extends Activity> cls) {
        this.startActivity(new Intent(this, cls));
    }

    public Postcard getRouter(String url) {
        return ARouter.getInstance().build(url);
    }

    public void openActivity(String url) {
        ARouter.getInstance().build(url).navigation();
    }


    /**
     * 打开Activity 后finish自己，
     * 直接调用finish会改变转场动画，所以要在NavCallback中finish
     *
     * @param url
     */
    public void openActivityFinishSelf(String url) {
        ARouter.getInstance().build(url)
                .navigation(this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        finish();
                    }
                });
    }

    public void openActivityForResult(String url, int requestCode) {
        ARouter.getInstance().build(url).navigation(this, requestCode);
    }

    public void openActivityForResult(String url, int requestCode, NavCallback navCallback) {
        ARouter.getInstance().build(url).navigation(this, requestCode, navCallback);
    }

    public Activity getActivity() {
        return this;
    }

//    private LoadingDialog mLoadingDialog;
//    private int showLoadingDialog = 0;
//
//    @Override
//    public synchronized void showProgressDialog() {
//        if (showLoadingDialog == 0) {
//            if (mLoadingDialog == null)
//                mLoadingDialog = new LoadingDialog(getActivity());
//            if (!this.isFinishing())
//                mLoadingDialog.show();
//        }
//        showLoadingDialog++;
//    }
//
//    @Override
//    public synchronized void hideProgressDialog() {
//        showLoadingDialog--;
//        if (mLoadingDialog != null && showLoadingDialog == 0) {
//            mLoadingDialog.dismiss();
//        }
//    }


    @Override
    public void showProgressDialog() {
        if(!this.isFinishing()) {
            LoadingDialogManage.showLoading(getActivity());
        }
    }

    @Override
    public void hideProgressDialog() {
        LoadingDialogManage.dismissLoading();
    }

    @Override
    public void showError(Throwable throwable) {
        if (!TextUtils.isEmpty(BaseSP.getInfo())) {
            ToastUtil.s(BaseSP.getInfo());
        }
        //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
        String msg = throwable.getMessage();
        if (throwable instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (throwable instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            msg = convertStatusCode(httpException);
        } else if (throwable instanceof JsonParseException
                || throwable instanceof ParseException
                || throwable instanceof JSONException
                || throwable instanceof JsonIOException) {
        }
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.s(msg);
            this.errorMsg = msg;
        }
        if (BuildConfig.DEBUG) throwable.printStackTrace();
    }

    @Override
    public void noData(){

    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOGIN) {
            int eventId = 0;
            if (data != null)
                eventId = data.getIntExtra("eventId", 0);
            backFromLogin(eventId);//从登陆界面返回  登录成功
        }
    }

    protected void backFromLogin(int eventId) {

    }


    protected abstract int getLayoutId();

    protected abstract Toolbar setToolbar();

    protected abstract void initialize();


    @Override
    public void finish() {
        if (isEnableActivityAnimation()) {
            ActivityAnimationHelper.animScaleDown(this, fromScaleX, fromScaleY, new AbsAnimationListener() {
                @Override
                public void onAnimationPrepare() {
                    super.onAnimationPrepare();
                }

                @Override
                public void onAnimationStart() {
                    super.onAnimationStart();
                }

                @Override
                public void onAnimationEnd() {
                    onActivityAnimationScaleDownEnd();
                    if (toolBar != null) {
                        toolBar.setVisibility(View.GONE);
                    }
                    DataTranBindingActivity.super.finish();
                }
            });
        } else {
            super.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.v(TAG, "onKeyDown(), event: " + event);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        L.v(TAG, "onKeyUp(), event: " + event);
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        L.v(TAG, "onKeyLongPress(), event: " + event);
        return super.onKeyLongPress(keyCode, event);
    }

    protected final void interceptKeyEvent() {
        interceptKeyEvent(true);
    }

    protected final void interceptKeyEvent(boolean handleSelf) {
        isInterceptKeyEventHandleSelf = handleSelf;
        isInterceptKeyEvent = true;
        L.v(TAG, "interceptKeyEvent(), handle self:" + handleSelf);
    }

    protected void onDispatchKeyEvent(KeyEvent event) {}

    public void stopDispatchKeyEvent(boolean stop) {
        isStopDispatchKeyEvent = stop;
    }

    /**
     * 增加KeyEvent的拦截：一次性拦截所有后续KeyEvent or 只拦截一次KeyEvent
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        L.v(TAG, "dispatchKeyEvent(), event: " + event);
        if (isStopDispatchKeyEvent) {
            return true;
        }
        onDispatchKeyEvent(event);
        int keyAction = event.getAction();
        if (keyAction == KeyEvent.ACTION_DOWN) {
            if (isInterceptKeyEventHandleSelf) {
                return onKeyDown(event.getKeyCode(), event);
            } else if (isInterceptKeyEvent) {
                return true;
            }
        } else if (keyAction == KeyEvent.ACTION_UP) {
            boolean isIntercept = false;
            if (isInterceptKeyEventHandleSelf) {
                isIntercept = onKeyUp(event.getKeyCode(), event);
            } else if (isInterceptKeyEvent) {
                isIntercept = true;
            }
            isInterceptKeyEvent = false;
            isInterceptKeyEventHandleSelf = false;
            if (isIntercept) {
                return isIntercept;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
