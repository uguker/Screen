package com.uguke.android.screen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * 功能描述：屏幕功能类
 * @author LeiJue
 * @date 2018/10/29
 */
public class Screen {

    public static Bar with(Activity act) {
        Window window = act.getWindow();
        ViewGroup contentLayout = window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
        if (contentLayout.getChildCount() > 0) {
            View contentView = contentLayout.getChildAt(0);
            if (contentView instanceof Bar) {
                return (Bar) contentView;
            }
        }
        return new ScreenLayout(act);
    }

    public static void setStatusBarDarkFont(Activity activity, boolean darkFont) {
        Util.setStatusBarDarkFont(activity, darkFont);
    }

    public static void setFull(Window window) {
        setFull(window, true);
    }

    /**
     * 功能描述：设置全屏
     * @param window Window窗体
     * @param hideNavigation 是否隐藏虚拟导航栏
     */
    public static void setFull(Window window, final boolean hideNavigation) {
        final View decorView = window.getDecorView();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (hideNavigation) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int flags = hideNavigation ? View.SYSTEM_UI_FLAG_HIDE_NAVIGATION : 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            //布局位于状态栏下方
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            //全屏
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            //隐藏导航栏
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    flags |= View.SYSTEM_UI_FLAG_IMMERSIVE;
                } else {
                    flags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                }
                decorView.setSystemUiVisibility(flags);
            }
        });
    }

    public static float getDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static int getWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getRealWidth() {
        return isLandscape() ? (getWidth() + getNavigationBarHeight()) : getWidth();
    }

    public static int getRealHeight() {
        return isLandscape() ? getHeight() : (getHeight() + getNavigationBarHeight());
    }

    public static int getNavigationBarHeight() {
        if (!hasNavigationBar()) {
            return 0;
        }
        int navBarSize = 0;
        int resourceId = Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            navBarSize = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return navBarSize;
    }

    public static int getStatusBarHeight() {
        // 获取状态栏高度
        int statusBarSize = -1;
        //获取status_bar_height资源的ID
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarSize = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return statusBarSize;
    }

    public static boolean hasNavigationBar() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // 是否重写导航栏
            String navBarOverride = getNavBarOverride();
            if ("1".equals(navBarOverride)) {
                hasNav = false;
            } else if ("0".equals(navBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else {
            boolean hasMenuKey = KeyCharacterMap
                    .deviceHasKey(KeyEvent.KEYCODE_MENU);
            boolean hasBackKey = KeyCharacterMap
                    .deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !hasMenuKey && !hasBackKey;
        }
    }

    public static boolean isLandscape() {
        return Resources.getSystem().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    @SuppressWarnings("unchecked")
    private static String getNavBarOverride() {
        String navBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                @SuppressLint("PrivateApi")
                Class clazz = Class.forName("android.os.SystemProperties");
                Method m = clazz.getDeclaredMethod("gets", String.class);
                m.setAccessible(true);
                navBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return navBarOverride;
    }
}
