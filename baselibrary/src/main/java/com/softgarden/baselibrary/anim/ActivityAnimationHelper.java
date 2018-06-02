package com.softgarden.baselibrary.anim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.softgarden.baselibrary.base.databinding.DataTranBindingActivity;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.ScreenUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by suxq on 2017/11/14.
 */

public class ActivityAnimationHelper {
    private static final String TAG = ActivityAnimationHelper.class.getSimpleName();
    private static Interpolator sScaleUpInterpolator = PathInterpolatorCompat.create(0.52f, 0.14f, 0.36f, 0.72f);
    private static Interpolator sScaleDownInterpolator = PathInterpolatorCompat.create(0.44f, 0.2f, 0.56f, 0.62f);
    private static final int ANIM_STATE_PREPARE = 1;
    private static final int ANIM_STATE_START = 2;
    private static final int ANIM_STATE_END = 3;

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, View view) {
        if (!AnimationConstants.ENABLE_ANIMATION) {
            activity.startActivityForResult(intent, requestCode);
            return;
        }
        activity.startActivityForResult(intentWrapper(activity, intent, view), requestCode);
        activity.overridePendingTransition(0, 0);
    }

    public static void startActivity(Activity activity, Intent intent, View view) {
        if (!AnimationConstants.ENABLE_ANIMATION) {
            activity.startActivity(intent);
            return;
        }
        activity.startActivity(intentWrapper(activity, intent, view));
        activity.overridePendingTransition(0, 0);
    }

    public static void startActivity(Context context, Intent intent, View view) {
        if (!(context instanceof Activity)) {
            context.startActivity(intent);
            return;
        }
        startActivity((Activity) context, intent, view);
    }

    private static Intent intentWrapper(Activity activity, Intent intent, View view) {
        int[] location = new int[2];
        calculatePivotXY(view, location, activity);
        intent.putExtra(AnimationConstants.VIEW_WIDTH, view.getWidth());
        intent.putExtra(AnimationConstants.VIEW_HEICHT, view.getHeight());
        intent.putExtra(AnimationConstants.ACTIVITY_ANIMATION_ENABLE, true);
        intent.putExtra(AnimationConstants.ACTIVITY_ANIMATION_PIVOTX, location[0]);
        intent.putExtra(AnimationConstants.ACTIVITY_ANIMATION_PIVOTY, location[1]);
        return intent;
    }

    private static void calculatePivotXY(View view, int[] location, Context context) {
        int screenCenterX = ScreenUtil.getScreenWidth(context) / 2;
        int screenCenterY = ScreenUtil.getScreenHeight(context) / 2;
        if (view != null) {
            view.getLocationOnScreen(location);
            location[0] = location[0] + view.getWidth() / 2;
            location[1] = location[1] + view.getHeight() / 2;
        } else {
            location[0] = screenCenterX;
            location[1] = screenCenterY;
        }
    }

    public static void animScaleUp(DataTranBindingActivity activity, Intent intent) {
        //普通页面默认在style里将activity设置成透明的，所以动画开始前不需要再手动设置，手动设置比较耗时
        animScaleUp(activity, intent, null, false, false);
    }

    public static void animScaleUp(DataTranBindingActivity activity,OnAnimationListener listener, Intent intent) {
        //普通页面默认在style里将activity设置成透明的，所以动画开始前不需要再手动设置，手动设置比较耗时
        animScaleUp(activity, intent, listener, false, false);
    }

    /**
     * @param activity
     * @param intent
     * @param listener
     * @param doTranslucentBegin true:动画结束时将Activity设置成透明状态
     * @param doOpaqueEnd        true：动画结束时将Activity设置成非透明状态, 最后这两个接口预留，目前的场景并没有使用到
     */
    public static void animScaleUp(final DataTranBindingActivity activity, Intent intent,
                                   final OnAnimationListener listener,
                                   final boolean doTranslucentBegin,
                                   final boolean doOpaqueEnd) {
        if (!canAnimation(activity)) {
            //ActivityTranslucentHelper.convertActivityFromTranslucent(activity);
            notifyAnimState(listener, ANIM_STATE_END);
            return;
        }

        final View view = activity.findViewById(activity.getWindow().ID_ANDROID_CONTENT);
        if (view == null) {
            notifyAnimState(listener, ANIM_STATE_END);
            return;
        }
        notifyAnimState(listener, ANIM_STATE_PREPARE);
        final ViewGroup viewGroup = (ViewGroup) view;
       /* if (doTranslucentBegin) {
            ActivityTranslucentHelper.converActivityToTranslucent(activity, null);
        }*/
        int screenCenterX = ScreenUtil.getScreenWidth(activity) / 2;
        int screenCenterY = ScreenUtil.getScreenHeight(activity) / 2;
        final int x = intent.getIntExtra(AnimationConstants.ACTIVITY_ANIMATION_PIVOTX, screenCenterX);
        final int y = intent.getIntExtra(AnimationConstants.ACTIVITY_ANIMATION_PIVOTY, screenCenterY);

        int view_width = intent.getIntExtra(AnimationConstants.VIEW_WIDTH, 0);
        int view_height = intent.getIntExtra(AnimationConstants.VIEW_HEICHT, 0);

        float startScaleX = (float) view_width / (float) (ScreenUtil.getScreenWidth(activity));
        float startScaleY = (float) view_height / (float) (ScreenUtil.getScreenHeight(activity));

        final AnimationSet animation = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(startScaleX, 1.0f, startScaleY, 1.0f, x, y);
        TranslateAnimation translateAnimation = new TranslateAnimation(screenCenterX - x, 0, screenCenterY - y, 0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setInterpolator(sScaleUpInterpolator);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                L.d(TAG, "animScaleUp(), onAnimationStart");
                suppressLayout(true, viewGroup);
                notifyAnimState(listener, ANIM_STATE_START);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                suppressLayout(false, viewGroup);
                if (doOpaqueEnd) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        view.postDelayed(() -> {
                            //延迟执行是因为执行这个会导致界面有点卡顿，所以动画看着就不怎么连贯
                            ActivityTranslucentHelper.convertActivityFromTranslucent(activity);
                        }, 100);
                    } else {
                        //todo api<19
                    }
                }
                view.post(() -> notifyAnimState(listener, ANIM_STATE_END));
                L.d(TAG, "animScaleUp(), onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.addAnimation(scaleAnimation);
        //animation.addAnimation(translateAnimation);
        //animation.addAnimation(alphaAnimation);
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                if (view.getAnimation() == animation && !animation.hasEnded()) {
                    return false;
                }
                view.startAnimation(animation);
                return true;
            }
        });
    }

    public static void animScaleDown(final DataTranBindingActivity activity, final OnAnimationListener listener) {
        if (!canAnimation(activity)) {
            notifyAnimState(listener, ANIM_STATE_END);
            return;
        }

        final View view = activity.findViewById(activity.getWindow().ID_ANDROID_CONTENT);
        if (view == null) {
            notifyAnimState(listener, ANIM_STATE_END);
            return;
        }

        notifyAnimState(listener, ANIM_STATE_PREPARE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityTranslucentHelper.converActivityToTranslucent(activity, null);
        } else {
            //api < 19
        }

        final ViewGroup viewGroup = (ViewGroup) view;
        int screenCenterX = ScreenUtil.getScreenWidth(activity) / 2;
        int screenCenterY = ScreenUtil.getScreenHeight(activity) / 2;
        final int x = activity.getIntent().getIntExtra(AnimationConstants.ACTIVITY_ANIMATION_PIVOTX, screenCenterX);
        final int y = activity.getIntent().getIntExtra(AnimationConstants.ACTIVITY_ANIMATION_PIVOTY, screenCenterY);

        int view_width = activity.getIntent().getIntExtra(AnimationConstants.VIEW_WIDTH, 0);
        int view_height = activity.getIntent().getIntExtra(AnimationConstants.VIEW_HEICHT, 0);

        float endScaleX = (float) view_width / (float) (ScreenUtil.getScreenWidth(activity));
        float endScaleY = (float) view_height / (float) (ScreenUtil.getScreenHeight(activity));

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, endScaleX, 1.0f, endScaleY, x, y);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, x - screenCenterX, 0, y - screenCenterY);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        AnimationSet animation = new AnimationSet(true);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setInterpolator(sScaleDownInterpolator);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                L.d(TAG, "animScaleDown(), onAnimationStart");
                activity.stopDispatchKeyEvent(true);
                notifyAnimState(listener, ANIM_STATE_START);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                L.d(TAG, "animScaleDown(), onAnimationEnd");
                notifyAnimState(listener, ANIM_STATE_END);
                activity.overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.addAnimation(scaleAnimation);
        //animation.addAnimation(translateAnimation);
        //animation.addAnimation(alphaAnimation);
        suppressLayout(true, viewGroup);
        view.startAnimation(animation);

    }

    public static void animScaleDown(final DataTranBindingActivity activity,float fromScaleX,float fromScaleY,final OnAnimationListener listener) {
        if (!canAnimation(activity)) {
            notifyAnimState(listener, ANIM_STATE_END);
            return;
        }

        final View view = activity.findViewById(activity.getWindow().ID_ANDROID_CONTENT);
        if (view == null) {
            notifyAnimState(listener, ANIM_STATE_END);
            return;
        }

        notifyAnimState(listener, ANIM_STATE_PREPARE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityTranslucentHelper.converActivityToTranslucent(activity, null);
        } else {
            //api < 19
        }

        final ViewGroup viewGroup = (ViewGroup) view;
        int screenCenterX = ScreenUtil.getScreenWidth(activity) / 2;
        int screenCenterY = ScreenUtil.getScreenHeight(activity) / 2;
        final int x = activity.getIntent().getIntExtra(AnimationConstants.ACTIVITY_ANIMATION_PIVOTX, screenCenterX);
        final int y = activity.getIntent().getIntExtra(AnimationConstants.ACTIVITY_ANIMATION_PIVOTY, screenCenterY);

        int view_width = activity.getIntent().getIntExtra(AnimationConstants.VIEW_WIDTH, 0);
        int view_height = activity.getIntent().getIntExtra(AnimationConstants.VIEW_HEICHT, 0);

        float endScaleX = (float) view_width / (float) (ScreenUtil.getScreenWidth(activity));
        float endScaleY = (float) view_height / (float) (ScreenUtil.getScreenHeight(activity));

        ScaleAnimation scaleAnimation = new ScaleAnimation(fromScaleX, endScaleX, fromScaleY, endScaleY, x, y);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, x - screenCenterX, 0, y - screenCenterY);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        AnimationSet animation = new AnimationSet(true);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setInterpolator(sScaleDownInterpolator);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                L.d(TAG, "animScaleDown(), onAnimationStart");
                activity.stopDispatchKeyEvent(true);
                notifyAnimState(listener, ANIM_STATE_START);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                L.d(TAG, "animScaleDown(), onAnimationEnd");
                notifyAnimState(listener, ANIM_STATE_END);
                activity.overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.addAnimation(scaleAnimation);
        //animation.addAnimation(translateAnimation);
        //animation.addAnimation(alphaAnimation);
        suppressLayout(true, viewGroup);
        view.startAnimation(animation);

    }

    private static boolean canAnimation(Activity activity) {
        if (!AnimationConstants.ENABLE_ANIMATION) {//动画总开关
            return false;
        }
        if (activity.getIntent() == null ||
                !activity.getIntent().getBooleanExtra(AnimationConstants.ACTIVITY_ANIMATION_ENABLE, false)) {//每个Activity自己的动画开关
            return false;
        }
        return true;
    }

    private static void notifyAnimState(OnAnimationListener listener, int animState) {
        if (listener != null) {
            switch (animState) {
                case ANIM_STATE_PREPARE:
                    listener.onAnimationPrepare();
                    break;
                case ANIM_STATE_START:
                    listener.onAnimationStart();
                    break;
                case ANIM_STATE_END:
                    listener.onAnimationEnd();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 放大过程中的动画建议不用使用该方法优化，否则很多界面发起的延迟焦点请求操作可能会失效，导致界面焦点没达到预期效果
     *
     * @param suppress  true:暂停ViewGroup layout操作
     * @param viewGroup
     */
    private static void suppressLayout(boolean suppress, ViewGroup viewGroup) {
        try {
            Method suppressLayout = ViewGroup.class.getDeclaredMethod("suppressLayout", boolean.class);
            suppressLayout.setAccessible(true);
            suppressLayout.invoke(viewGroup, suppress);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
