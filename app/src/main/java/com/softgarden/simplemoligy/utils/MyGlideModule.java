package com.softgarden.simplemoligy.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.GlideModule;
import com.softgarden.baselibrary.BaseApplication;
import com.softgarden.tuike.HostUrl;

import java.io.File;


public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(() -> {
            //设置glide的缓存目录,与缓存文件夹的大小限制
            File cacheLocation = new File(BaseApplication.getInstance().getExternalCacheDir(), HostUrl.IMAG_HOST_URL);
            cacheLocation.mkdirs();
            return DiskLruCacheWrapper.get(cacheLocation, 150 * 1024 * 1024);
        });

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}