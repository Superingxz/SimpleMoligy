package com.softgarden.baselibrary.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/9.
 */
public class BaseViewpagerAdapter extends PagerAdapter {


    private List<BasePager> list = new ArrayList<BasePager>();
    private String[] titles;

    public BaseViewpagerAdapter(List<BasePager> list, String[] titles) {
        this.list = list;
        this.titles = titles;
    }

    public void addDatas(List<BasePager> datas) {
        list.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(BasePager data) {
        list.add(data);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position).getRootView());
        return list.get(position).getRootView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    // 返回选项卡显示的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
