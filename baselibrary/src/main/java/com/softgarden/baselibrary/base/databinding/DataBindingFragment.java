package com.softgarden.baselibrary.base.databinding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.ParseException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.softgarden.baselibrary.BuildConfig;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.utils.LogUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.softgarden.baselibrary.widget.LoadingDialogManage;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava2.HttpException;

import static android.app.Activity.RESULT_OK;
import static com.softgarden.baselibrary.base.databinding.DataBindingActivity.RESULT_LOGIN;

/**
 * Created by Administrator on 2016/9/10 0010.
 * DataBinding fragment基类
 */
public abstract class DataBindingFragment<B extends ViewDataBinding> extends RxFragment implements IBaseDisplay {

    protected B binding;

    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private boolean isInited = false;//

    private int position;
    private String title;
    protected String errorMsg = "";

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e(getClass().getSimpleName() + "onCreateView");
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mView = binding.getRoot();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        isInited = true;
        initialize();
    }

    /**
     * 初始化Presenter
     */
    protected void initViewModel() {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isInited && !hidden) {
            isInited = true;
            initialize();
        }
    }

    @Override
    public void useNightMode(boolean isNight) {

    }

    //    public void launchActivity(Class<? extends Activity> cls) {
    //        this.startActivity(new Intent(getActivity(), cls));
    //    }


    public Postcard getRouter(String url) {
        return ARouter.getInstance().build(url);
    }

    public void openActivity(String url) {
        ARouter.getInstance().build(url).navigation();
    }

    public void openActivityForResult(String url, int requestCode) {
        ARouter.getInstance().build(url).navigation(getActivity(), requestCode);
    }

    public void openActivityForResult(String url, int requestCode, NavCallback navCallback) {
        ARouter.getInstance().build(url).navigation(getActivity(), requestCode, navCallback);
    }

    //    private LoadingDialog mLoadingDialog;
    //    private int showLoadingDialog = 0;
    //
    //    @Override
    //    public synchronized void showProgressDialog() {
    //        if (showLoadingDialog == 0) {
    //            if (mLoadingDialog == null)
    //                mLoadingDialog = new LoadingDialog(getActivity());
    //            if (!getActivity().isFinishing()) {
    //                mLoadingDialog.show();
    //            }
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
        if (!getActivity().isFinishing()) {
            LoadingDialogManage.showLoading(getActivity());
        }
    }

    public void showProgressDialog(String message) {
        if (!getActivity().isFinishing()) {
            LoadingDialogManage.showLoading(getActivity(),message);
        }
    }

    @Override
    public void hideProgressDialog() {
        LoadingDialogManage.dismissLoading();
    }

    @Override
    public void showError(Throwable throwable) {
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
            if (!"获取成功".equals(msg) && !"没有数据".equals(msg)) {
                ToastUtil.s(msg);
            }
            this.errorMsg = msg;
            noData();
            ErrorInfo(this.errorMsg);
        }
        if (BuildConfig.DEBUG) throwable.printStackTrace();
    }

    protected String ErrorInfo(String errorMsg){
        return errorMsg;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    protected abstract void initialize();


}
