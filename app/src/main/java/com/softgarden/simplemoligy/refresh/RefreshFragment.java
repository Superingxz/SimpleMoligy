package com.softgarden.simplemoligy.refresh;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.softgarden.baselibrary.base.BaseLazyFragment;
import com.softgarden.baselibrary.base.IBaseViewModel;
import com.softgarden.baselibrary.widget.RefreshDelegateLayout;
import com.softgarden.simplemoligy.config.Constants;
import com.softgarden.simplemoligy.R;

import java.util.List;

/**
 * @author by DELL
 * @date on 2017/9/27
 * @describe 通用的列表刷新Fragment
 */
public abstract class RefreshFragment<VM extends IBaseViewModel, B extends ViewDataBinding>
        extends BaseLazyFragment<VM, B> implements BaseQuickAdapter.RequestLoadMoreListener {

    RefreshDelegateLayout mRefreshLayout;
    protected int mPage = 1;

    protected int mPageNum = 1;

    BaseQuickAdapter mAdapter;

    /**
     * initialize 已被实现  需要调用super()
     */
    @Override
    protected void initialize() {
        mRefreshLayout = (RefreshDelegateLayout) mView.findViewById(R.id.mRefreshLayout);
        if (mRefreshLayout != null) {
            mRefreshLayout.setEnableLoadmore(false);
            mRefreshLayout.setEnableAutoLoadmore(false);
            mRefreshLayout.setOnRefreshDelegateListener(new RefreshDelegateLayout.OnRefreshDelegateListener() {
                @Override
                public void onRefresh() {
                    RefreshFragment.this.onRefresh();
                }
            });
        }
        super.initialize();
    }

    public abstract void onRefresh();

    public void finishRefresh() {
        if (mRefreshLayout != null) mRefreshLayout.finishRefresh(0);
    }

    public void autoRefresh(){
        autoRefresh(0);
    }

    public void autoRefresh(int delay){
        if (mRefreshLayout != null) {
            mRefreshLayout.autoRefresh(delay);
        }
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

    public void setLoadMore(RecyclerView recyclerView, BaseQuickAdapter adapter, List<?> list) {
        finishRefresh();
        if (list != null) {
            if (mPage == 1) adapter.setNewData(list);
            else adapter.addData(list);
        }

        if (list == null && mPage == 1) {
            adapter.setNewData(list);
        }

        if (list == null || list.size() < Constants.PAGE_COUNT) {
            adapter.loadMoreEnd();
        } else {
            adapter.setOnLoadMoreListener(this,recyclerView);
            adapter.loadMoreComplete();
        }
    }

    public void setLoadMore(RecyclerView recyclerView, BaseQuickAdapter adapter, List<?> list,int pageSize) {
        finishRefresh();
        if (list != null) {
            if (mPageNum == 1) adapter.setNewData(list);
            else adapter.addData(list);
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
