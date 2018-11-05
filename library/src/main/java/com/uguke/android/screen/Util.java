package com.uguke.android.screen;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class Util {

    /**
     * 功能描述：隐藏真实的状态栏
     * @param activity 活动对象
     */
    static void hideRealStatusBar(Activity activity) {
        Window window = activity.getWindow();
        // 隐藏真实的状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        // 适配5.0版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams winParams = window.getAttributes();
            final int bits;
            bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                winParams.flags &= ~bits;
            } else {
                winParams.flags |= bits;
            }
            window.setAttributes(winParams);
        }
        // 设置标题栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 功能描述：隐藏真实的导航栏
     * @param activity 活动对象
     */
    static void hideRealNavigationBar(Activity activity) {
        Window window  = activity.getWindow();
        // 隐藏真实的导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        // 适配5.0版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams winParams = window.getAttributes();
            final int bits;
            bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                winParams.flags &= ~bits;
            } else {
                winParams.flags |= bits;
            }
            window.setAttributes(winParams);
        }
        // 设置导航栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 功能描述:设置状态栏为深色字体
     * @param activity 活动对象
     * @param darkFont 是否为深色字体
     */
    static void setStatusBarDarkFont(Activity activity, boolean darkFont) {
        // 先尝试设置MIUI手机的状态栏颜色
        // 不成功尝试设置魅族手机的状态栏颜色
        // 最后尝试设置通用手机的状态栏颜色
        if (setMIUIStatusBarFont(activity, darkFont)) {
            setDefaultStatusBarFont(activity, darkFont);
        } else if (setMeizuStatusBarFont(activity, darkFont)) {
            setDefaultStatusBarFont(activity, darkFont);
        } else {
            setDefaultStatusBarFont(activity, darkFont);
        }
    }

    /**
     * 功能描述:魅族手机设置状态栏为深色字体
     * @param activity 活动对象
     * @param darkFont 是否为深色字体
     */
    private static boolean setMeizuStatusBarFont(Activity activity, boolean darkFont) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkFont) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            return true;
        } catch (Exception ignored) {}
        return false;
    }

    /**
     * 功能描述:MIUI手机设置状态栏为深色字体
     * @param activity 活动对象
     * @param darkFont 是否为深色字体
     */
    private static boolean setMIUIStatusBarFont(Activity activity, boolean darkFont) {
        Window window = activity.getWindow();
        Class<?> clazz = window.getClass();
        try {
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (darkFont) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * 功能描述:常规手机设置状态栏为深色字体
     * @param activity 活动对象
     * @param darkFont 是否为深色字体
     */
    private static void setDefaultStatusBarFont(Activity activity, boolean darkFont) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            if (darkFont) {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() &
                        ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

}