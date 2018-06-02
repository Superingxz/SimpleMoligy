package com.softgarden.simplemoligy.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softgarden.baselibrary.base.databinding.DataBindingActivity;
import com.softgarden.baselibrary.utils.EmptyUtil;
import com.softgarden.tuike.Constants;
import com.softgarden.tuike.R;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc
 * Author moligy
 * Date   2017/11/08.
 */

public class ImageUtil {

    /**
     * 检查url 是否拼接
     *
     * @param url
     * @return
     */
    public static String checkUrl(String url) {
        //有完整路径的url不用拼接
        if (url != null && (url.startsWith("http"))) {
        } else
            url = Constants.getImageURL(url);
        return url;
    }

    /**
     * 检查url集合 是否拼接
     *
     * @param urlList
     * @return
     */
    public static List<String> checkUrl(List<String> urlList) {
        List<String> imgList = new ArrayList<>();
        if (EmptyUtil.isEmpty(urlList))
            return imgList;
        for (String path : urlList) {
            imgList.add(checkUrl(path));
        }
        return imgList;
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void loadImageSquare(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


    /**
     * 加载图片
     * 默认图是背景图
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"img_load"})
    public static void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载图片正方形图片
     * 默认图是正方形
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"img_square"})
    public static void loadIcon(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .placeholder(R.mipmap.loading_square)
                .error(R.mipmap.loading_square)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载图片正方形图片
     * 默认图是正方形
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"header_icon"})
    public static void loadHeaderIcon(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .placeholder(R.mipmap.header_icon)
                .error(R.mipmap.header_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


    /***
     * 加载矩形图片
     * 默认图是矩形图片
     * @param imageView
     * @param url
     */
    @BindingAdapter({"img_load_rec"})
    public static void loadRec(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .placeholder(R.mipmap.loading_rec)
                .error(R.mipmap.loading_rec)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


    /**
     * 加载本地图片
     *
     * @param imageView
     * @param url
     */
    public static void loadLocalSrc(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.mipmap.loading_square)
                .error(R.mipmap.loading_square)
                //  .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"img_load_camara_image"})
    public static void loadCamaraImageSrc(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.mipmap.upload_image)
                .error(R.mipmap.upload_image)
                //  .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void loadSquare(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.mipmap.loading_square)
                .error(R.mipmap.loading_square)
                //  .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 欢迎页
     *
     * @param imageView
     * @param url
     */
   /* public static void loadStartImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.mipmap.splash)
                .error(R.mipmap.splash)
                .into(imageView);
    }*/

    /**
     * 加载图片长方形图片
     * 默认图是长方形
     *
     * @param imageView
     * @param url
     */
    /*@BindingAdapter({"img_load_Rectangle"})
    public static void loadRectangle(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .placeholder(R.mipmap.shop_style_demo)
                .error(R.mipmap.shop_style_demo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }*/


    /**
     * 加载头像
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"img_loadHeader"})
    public static void loadHeader(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    /**
     * 加载头像
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"img_loadHeaderBg"})
    public static void loadHeaderBg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    /**
     * 加载大头像
     * 只是默认图不同
     *
     * @param imageView
     * @param url
     */
   /* @BindingAdapter({"img_load_big_Header"})
    public static void loadBigHeader(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.default_header)
                .error(R.mipmap.default_header)
                .into(imageView);
    }*/

   /* @BindingAdapter({"img_load_group_Header"})
    public static void loadGroupHeader(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.default_header)
                .error(R.mipmap.default_header)
                .into(imageView);
    }*/

    /*public static void loadWithDefault(ImageView imageView, String url, int defaultImg) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(defaultImg)
                .error(R.mipmap.default_header)
                .into(imageView);
    }*/

    /**
     * 在Databinding中  不能直接使用android:src=“@{resId}”
     * 要BindingAdapter
     *
     * @param imageView
     * @param resId
     */
    @BindingAdapter({"android:src"})
    public static void setSrc(ImageView imageView, int resId) {
        imageView.setImageResource(resId);
    }

   /* *//**
     * 保存图片到sd卡
     *
     * @param
     * @param
     * @param
     *//*
    public static void loadImagesToSDCard(Activity context, List<String> imgList, boolean isSilentLoad) {
        if (imgList == null || imgList.isEmpty())
            return;
        //申请权限
        RxPermissionsUtil.requestStorage(context).subscribe(aBoolean -> {
            if (aBoolean) {
                LoadingDialog dialog = new LoadingDialog(context);
                if (!isSilentLoad) {
                    dialog.show();
                }
                Observable.create((ObservableOnSubscribe<List<String>>) e -> {
                    List<String> count = new ArrayList<String>();
                    for (String path : imgList) {
                        ImageLoader.save2SDCard(context, ImageUtil.checkUrl(path))
                                .subscribe(bitmap -> {
                                    ImageCacheUtil.saveBitmap2File(context, bitmap, ImageCacheUtil.getRootDir(), path.hashCode() + ".jpeg");
                                    count.add(path);
                                    if (!e.isDisposed())
                                        e.onNext(count);
                                }, throwable -> {
                                    if (!isSilentLoad) ToastUtil.s(R.string.load_img_failed);
                                });
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(count -> {
                            if (count.size() == imgList.size()) {
                                if (!isSilentLoad) {
                                    dialog.dismiss();
                                    ToastUtil.l(String.format(BaseApplication.getInstance().getString(R.string.s_save_to_sdcard_success), ImageCacheUtil.getRootDir()));
                                }

                            }
                        });
            } else {//被拒绝 要去再次申请
                RxPermissionsUtil.shouldShowRequestPermissionRationale(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(aBoolean1 -> {
                            if (aBoolean1) {// 查询是否 应该提醒
                                RxPermissionsUtil.showPermissionDialog(context,
                                        context.getString(R.string.permission_apply), "此功能需要读写存储权限，请授权",
                                        (dialog, which) -> loadImagesToSDCard(context, imgList, isSilentLoad));
                            } else {//选中了禁止以后提醒
                                RxPermissionsUtil.showLackPermissionDialog(context);
                            }
                        });
            }
        });
    }*/

  /*  public static void loadImagesToSDCard(Activity activity, List<String> imageList) {
        loadImagesToSDCard(activity, imageList, false);
    }*/

    /**
     * 高斯模糊
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"img_loadBlur"})
    public static void loadBlur(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                .placeholder(R.drawable.ic_launcher_background)
                // .bitmapTransform(new BlurTransformation(imageView.getContext(), 1, 3))
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadBlur(ImageView imageView, String url, int radius, int sampling) {
        // radius "23":模糊度；sampling "4":图片缩放4倍后再进行模糊a
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .dontAnimate()
                // .placeholder(R.mipmap.icon)
                // .bitmapTransform(new BlurTransformation(imageView.getContext(), radius, sampling))
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


    public static void loadAsGif(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(checkUrl(url))
                .asGif()
                .thumbnail(0.5f)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    public static Observable<Boolean> clearCache(DataBindingActivity context) {
        return Observable.create((ObservableOnSubscribe<Boolean>) e -> {
            Glide.get(context).clearDiskCache();
            e.onNext(true);
            e.onComplete();

        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> context.showProgressDialog())
                .doOnTerminate(context::hideProgressDialog)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY));

    }

    /**
     * View背景
     * @param tv
     * @param resId
     */
    @BindingAdapter({"bg_load"})
    public static void loadBg(TextView tv, int resId) {
        tv.setBackgroundResource(resId);
    }

}
