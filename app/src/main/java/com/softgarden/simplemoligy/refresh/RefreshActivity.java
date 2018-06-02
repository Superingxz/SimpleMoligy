package com.softgarden.simplemoligy.refresh;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.softgarden.baselibrary.base.BaseActivity;
import com.softgarden.baselibrary.base.IBaseViewModel;
import com.softgarden.baselibrary.utils.EmptyUtil;
import com.softgarden.baselibrary.widget.RefreshDelegateLayout;
import com.softgarden.simplemoligy.config.Constants;

import java.util.List;

/**
 * Created by DELL on 2017/7/28.
 */

/**
 * @author by DELL
 * @date on 2017/9/27
 * @describe 通用的列表刷新Activity
 */
public abstract class RefreshActivity<VM extends IBaseViewModel, B extends ViewDataBinding>
        extends BaseActivity<VM, B> implements BaseQuickAdapter.RequestLoadMoreListener {

    RefreshDelegateLayout mRefreshLayout;
    protected int mPage = 1;

    BaseQuickAdapter mAdapter;
    /**
     * initialize 已被实现  需要调用super()
     */
    @Override
    protected void initialize() {
        mRefreshLayout = (RefreshDelegateLayout) findViewById(com.softgarden.baselibrary.R.id.mRefreshLayout);
        if (mRefreshLayout != null)
            mRefreshLayout.setOnRefreshDelegateListener(new RefreshDelegateLayout.OnRefreshDelegateListener() {
                @Override
                public void onRefresh() {
                    RefreshActivity.this.onRefresh();
                }
            });
    }

    public abstract void onRefresh();

    public void autoRefresh(){
        autoRefresh(0);
    }

    public void autoRefresh(int delay){
        if (mRefreshLayout != null) {
            mRefreshLayout.autoRefresh(delay);
        }
    }

    public void enableLoadMore(boolean enableLoadMore){
        mAdapter.setEnableLoadMore(enableLoadMore);
    }

    /**
     * 结束刷新
     */
    public void finishRefresh() {
        if (mRefreshLayout != null) mRefreshLayout.finishRefresh(0);
    }

    @Override
    public void showError(Throwable throwable) {
        super.showError(throwable);
        finishRefresh();
    }

    public void enableRefresh() {
        if (mRefreshLayout != null) mRefreshLayout.setEnableRefresh(true);
    }

    public void disableRefresh() {
        if (mRefreshLayout != null) mRefreshLayout.setEnableRefresh(false);
    }

    /**
     * 结束刷新
     * 上拉更多
     *
     * @param adapter
     * @param list
     */
    public void setLoadMore(RecyclerView recyclerView, BaseQuickAdapter adapter, List<?> list) {
        finishRefresh();
        if (!EmptyUtil.isEmpty(list)) {
            if (mPage == 1) {
                adapter.setNewData(list);
            } else {
                adapter.addData(list);
            }
        }

        if (list == null && mPage == 1) {
            adapter.getData().removeAll(adapter.getData());
            adapter.notifyDataSetChanged();
        }

        if (list != null && list.size() == 0) {
            adapter.loadMoreComplete();
        }

        if (list == null || list.size() < Constants.PAGE_COUNT) {
            adapter.loadMoreEnd();
        } else {
            adapter.setOnLoadMoreListener(this,recyclerView);

            adapter.loadMoreComplete();
        }
    }

    public void setAdapter(BaseQuickAdapter adapter){
        this.mAdapter = adapter;
    }

    @Override
    public void noData() {
        super.noData();
        if (mAdapter != null) {
            mAdapter.loadMoreEnd();
            finishRefresh();
        }
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
