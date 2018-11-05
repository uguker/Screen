package com.uguke.android.screen;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.view.View;

/**
 * 功能描述：状态栏及导航栏
 * @author LeiJue
 * @date 2018/11/4
 */
public interface Bar {

    /**
     * 功能描述:状态栏深色字体
     * @return 自身
     */
    Bar statusBarDarkFont();

    /**
     * 功能描述:状态栏浅色字体
     * @return 自身
     */
    Bar statusBarLightFont();

    /**
     * 功能描述:状态栏背景颜色
     * @param color 颜色
     * @return 自身
     */
    Bar statusBarColor(@ColorInt int color);

    /**
     * 功能描述:状态栏背景图片
     * @param resId 资源ID
     * @return 自身
     */
    Bar statusBarBackground(@DrawableRes int resId);

    /**
     * 功能描述:状态栏背景图片
     * @param drawable 图片
     * @return 自身
     */
    Bar statusBarBackground(Drawable drawable);

    /**
     * 功能描述:状态栏透明度
     * @param alpha 透明度
     * @return 自身
     */
    Bar statusBarAlpha(@IntRange(from = 0, to = 255) int alpha);

    /**
     * 功能描述:导航栏背景颜色
     * @param color 颜色
     * @return 自身
     */
    Bar navigationBarColor(@ColorInt int color);

    /**
     * 功能描述:导航栏背景图片
     * @param resId 资源ID
     * @return 自身
     */
    Bar navigationBarBackground(@DrawableRes int resId);

    /**
     * 功能描述:导航栏背景图片
     * @param drawable 图片
     * @return 自身
     */
    Bar navigationBarBackground(Drawable drawable);

    /**
     * 功能描述:导航栏透明度
     * @param alpha 透明度
     * @return 自身
     */
    Bar navigationBarAlpha(@IntRange(from = 0, to = 255) int alpha);

    /**
     * 功能描述：内容侵入状态栏
     * @return 自身
     */
    Bar immerseStatusBar();

    /**
     * 功能描述：内容侵入状态栏
     * @param immerse 是否侵入
     * @return 自身
     */
    Bar immerseStatusBar(boolean immerse);

    /**
     * 功能描述：内容侵入导航栏
     * @return 自身
     */
    Bar immerseNavigationBar();

    /**
     * 功能描述：内容侵入导航栏
     * @param immerse 是否侵入
     * @return 自身
     */
    Bar immerseNavigationBar(boolean immerse);

    /**
     * 功能描述：使控件适应状态栏
     * @param viewId 控件ID
     */
    Bar fitStatusBar(@IdRes int viewId);

    /**
     * 功能描述：使控件适应状态栏
     * @param view 控件
     */
    Bar fitStatusBar(View view);

    /**
     * 功能描述：使控件适应导航栏
     * @param viewId 控件ID
     */
    Bar fitNavigationBar(@IdRes int viewId);

    /**
     * 功能描述：使控件适应导航栏
     * @param view 控件
     */
    Bar fitNavigationBar(View view);
}