package com.softgarden.baselibrary.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2015/12/5.
 */
public class FragmentBasePagerAdapter extends FragmentStatePagerAdapter {
    protected BaseFragment[] fragments;
    protected CharSequence[] titles;

    public FragmentBasePagerAdapter(FragmentManager fm, BaseFragment... fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public FragmentBasePagerAdapter(FragmentManager fm, Class<? extends BaseFragment> cls, CharSequence[] titles) {
        super(fm);
        if (titles == null || titles.length == 0) return;
        this.titles = titles;
        this.fragments = new BaseFragment[titles.length];
        for (int i = 0; i < titles.length; i++) {
            try {
                fragments[i] = cls.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public FragmentBasePagerAdapter(FragmentManager fm, Class<? extends BaseFragment> cls, CharSequence[] titles, List<Bundle> bundles) {
        super(fm);
        if (titles == null || titles.length == 0) return;
        this.titles = titles;
        this.fragments = new BaseFragment[titles.length];
        for (int i = 0; i < titles.length; i++) {
            try {
                fragments[i] = cls.newInstance();
                fragments[i].setArguments(bundles.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = fragments[position];
        fragment.setPosition(position);
        CharSequence pageTitle = getPageTitle(position);
        if (!TextUtils.isEmpty(pageTitle)) fragment.setTitle(pageTitle.toString());
        return fragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void setPrimaryItem(View container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && position < titles.length) {
            return titles[position];
        }
        return super.getPageTitle(position);
    }

    public void setTitle(String[] orderMenu) {
        this.titles = orderMenu;
    }
}