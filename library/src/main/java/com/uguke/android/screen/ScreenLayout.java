package com.uguke.android.screen;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 功能描述：替换界面
 * @author LeiJue
 * @date 2018/11/2
 */
@SuppressLint("ViewConstructor")
class ScreenLayout extends RelativeLayout implements Bar {
    
    private static final int FLAG_IMMERSE_NONE = 0x0;
    private static final int FLAG_IMMERSE_STATUS = 0x1;
    private static final int FLAG_IMMERSE_NAVIGATION = 0x2;
    private static final int FLAG_IMMERSE_BOTH = 0x3;

    private Activity activity;
    /** 状态栏 **/
    private ImageView statusView;
    /** 导航栏部分 **/
    private ImageView navigationView;
    /** 内容部分 **/
    private FrameLayout contentView;

    private int immerseFlag = FLAG_IMMERSE_NONE;

    ScreenLayout(Activity activity) {
        super(activity);
        // 如果Api版本没有达到19则不做处理
        if (isLowVersion()) {
            return;
        }
        this.activity = activity;
        initStatusView();
        initContentView();
        initNavigationView();
        replaceContentView();

        Util.hideRealStatusBar(activity);
        Util.hideRealNavigationBar(activity);
    }

    private void initStatusView() {
        RelativeLayout.LayoutParams params;
        // 初始化状态栏
        statusView = new ImageView(getContext());
        statusView.setId(R.id.screen_status_view);
        statusView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, Screen.getStatusBarHeight());
        if (Screen.isLandscape()) {
            params.addRule(LEFT_OF, R.id.screen_navigation_view);
        }
        statusView.setLayoutParams(params);
        addView(statusView);
    }

    private void initContentView() {
        RelativeLayout.LayoutParams params;
        // 初始化内容
        contentView = new FrameLayout(getContext());
        contentView.setId(R.id.screen_content_view);
        params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.screen_status_view);
        params.addRule(Screen.isLandscape() ? LEFT_OF : ABOVE, R.id.screen_navigation_view);
        contentView.setLayoutParams(params);
        addView(contentView);
    }

    private void initNavigationView() {
        RelativeLayout.LayoutParams params;

        // 初始化导航栏
        navigationView = new ImageView(getContext());
        navigationView.setId(R.id.screen_navigation_view);
        navigationView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        navigationView.setImageDrawable(new ColorDrawable(Color.BLACK));
        // 如果是横屏
        if (Screen.isLandscape()) {
            params = new RelativeLayout.LayoutParams(Screen.getNavigationBarHeight(), LayoutParams.MATCH_PARENT);
            params.addRule(ALIGN_PARENT_RIGHT);
        } else {
            params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, Screen.getNavigationBarHeight());
            params.addRule(ALIGN_PARENT_BOTTOM);
        }
        navigationView.setLayoutParams(params);
        addView(navigationView);
    }

    private void replaceContentView() {

        Window window = activity.getWindow();
        ViewGroup contentLayout = window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
        if (contentLayout.getChildCount() > 0) {
            View contentView = contentLayout.getChildAt(0);
            contentLayout.removeView(contentView);
            ViewGroup.LayoutParams contentParams = contentView.getLayoutParams();
            this.contentView.addView(contentView, contentParams);
        }
        contentLayout.addView(this, -1, -1);
    }

    @Override
    public Bar statusBarDarkFont() {
        if (isLowVersion()) {
            return this;
        }
        Util.setStatusBarDarkFont(activity, true);
        return this;
    }

    @Override
    public Bar statusBarLightFont() {
        if (isLowVersion()) {
            return this;
        }
        Util.setStatusBarDarkFont(activity, false);
        return this;
    }

    @Override
    public Bar statusBarColor(int color) {
        if (isLowVersion()) {
            return this;
        }
        statusView.setImageDrawable(new ColorDrawable(color));
        return this;
    }

    @Override
    public Bar statusBarBackground(@DrawableRes int resId) {
        if (isLowVersion()) {
            return this;
        }
        statusView.setImageResource(resId);
        return this;
    }

    @Override
    public Bar statusBarBackground(Drawable drawable) {
        if (isLowVersion()) {
            return this;
        }
        statusView.setImageDrawable(drawable);
        return this;
    }

    @Override
    public Bar statusBarAlpha(int alpha) {
        if (isLowVersion()) {
            return this;
        }
        statusView.setImageAlpha(alpha);
        return this;
    }

    @Override
    public Bar navigationBarColor(int color) {
        if (isLowVersion()) {
            return this;
        }
        navigationView.setImageDrawable(new ColorDrawable(color));
        return this;
    }

    @Override
    public Bar navigationBarBackground(@DrawableRes int resId) {
        if (isLowVersion()) {
            return this;
        }
        navigationView.setImageResource(resId);
        return this;
    }

    @Override
    public Bar navigationBarBackground(Drawable drawable) {
        if (isLowVersion()) {
            return this;
        }
        navigationView.setImageDrawable(drawable);
        return this;
    }

    @Override
    public Bar navigationBarAlpha(int alpha) {
        if (isLowVersion()) {
            return this;
        }
        navigationView.setImageAlpha(alpha);
        return this;
    }

    @Override
    public Bar immerseStatusBar() {
        if (isLowVersion()) {
            return this;
        }
        immerseStatusBar(true);
        return this;
    }

    @Override
    public Bar immerseStatusBar(boolean immerse) {
        if (isLowVersion()) {
            return this;
        }
        if (immerse) {
            immerseFlag |= FLAG_IMMERSE_STATUS;
            layoutImmerse();
        } else if (immerseFlag == FLAG_IMMERSE_STATUS ||
                immerseFlag == FLAG_IMMERSE_BOTH) {
            immerseFlag ^= FLAG_IMMERSE_STATUS;
            layoutImmerse();
        }
        return this;
    }

    @Override
    public Bar immerseNavigationBar() {
        if (isLowVersion()) {
            return this;
        }
        immerseNavigationBar(true);
        return this;
    }

    @Override
    public Bar immerseNavigationBar(boolean immerse) {
        if (isLowVersion()) {
            return this;
        }
        if (isLowVersion()) {
            return this;
        }
        if (immerse) {
            immerseFlag |= FLAG_IMMERSE_NAVIGATION;
            layoutImmerse();
        } else if (immerseFlag == FLAG_IMMERSE_NAVIGATION ||
                immerseFlag == FLAG_IMMERSE_BOTH) {
            immerseFlag ^= FLAG_IMMERSE_NAVIGATION;
            layoutImmerse();
        }
        return this;
    }

    @Override
    public Bar fitStatusBar(@IdRes int viewId) {
        if (isLowVersion()) {
            return this;
        }
        fitStatusBar(findViewById(viewId));
        return this;
    }

    @Override
    public Bar fitStatusBar(View view) {
        if (isLowVersion()) {
            return this;
        }
        FrameLayout.MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        Integer topMargin = (Integer) view.getTag(R.id.screen_status_tag);
        if (topMargin == null) {
            view.setTag(R.id.screen_status_tag, params.topMargin);
            topMargin = params.topMargin;
        }
        params.topMargin = topMargin + Screen.getStatusBarHeight();
        return this;
    }

    @Override
    public Bar fitNavigationBar(@IdRes int viewId) {
        if (isLowVersion()) {
            return this;
        }
        fitNavigationBar(findViewById(viewId));
        return this;
    }

    @Override
    public Bar fitNavigationBar(View view) {
        if (isLowVersion()) {
            return this;
        }
        FrameLayout.MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        Integer bottomMargin = (Integer) view.getTag(R.id.screen_navigation_tag);
        if (bottomMargin == null) {
            view.setTag(R.id.screen_status_tag, params.bottomMargin);
            bottomMargin = params.bottomMargin;
        }
        params.bottomMargin = bottomMargin + Screen.getStatusBarHeight();
        return this;
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        // 如果Api版本没有达到19则不做处理
        if (isLowVersion()) {
            return;
        }
        if (Screen.isLandscape()) {

            LayoutParams navigationParams = (LayoutParams) navigationView.getLayoutParams();
            // 如果不需要变动
            if (navigationParams.width == Screen.getNavigationBarHeight()) {
                return;
            }
            // 修改导航栏大小及位置
            navigationParams.width = Screen.getNavigationBarHeight();
            navigationParams.height = LayoutParams.MATCH_PARENT;
            navigationParams.addRule(ALIGN_PARENT_RIGHT);
            navigationParams.removeRule(ALIGN_PARENT_BOTTOM);
            // 修改状态栏位置
            LayoutParams statusParams = (LayoutParams) statusView.getLayoutParams();
            statusParams.addRule(LEFT_OF, R.id.screen_navigation_view);
        } else {
            LayoutParams navigationParams = (LayoutParams) navigationView.getLayoutParams();
            // 如果不需要变动
            if (navigationParams.height == Screen.getNavigationBarHeight()) {
                return;
            }
            // 修改导航栏大小及位置
            navigationParams.addRule(ALIGN_PARENT_BOTTOM);
            navigationParams.removeRule(ALIGN_PARENT_RIGHT);
            navigationParams.width = LayoutParams.MATCH_PARENT;
            navigationParams.height = Screen.getNavigationBarHeight();
            // 修改状态栏位置
            LayoutParams statusParams = (LayoutParams) statusView.getLayoutParams();
            statusParams.removeRule(LEFT_OF);

        }
        layoutImmerse();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        activity = null;
        statusView = null;
        contentView = null;
        navigationView = null;
    }

    private void layoutImmerse() {
        // 重新定义
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        switch (immerseFlag) {
            case FLAG_IMMERSE_STATUS:
                params.addRule(Screen.isLandscape() ? LEFT_OF : ABOVE,
                        R.id.screen_navigation_view);
                bringChildToFront(statusView);
                break;
            case FLAG_IMMERSE_NAVIGATION:
                params.addRule(RelativeLayout.BELOW, R.id.screen_status_view);
                bringChildToFront(navigationView);
                break;
            case FLAG_IMMERSE_BOTH:
                bringChildToFront(statusView);
                bringChildToFront(navigationView);
                break;
            default:
                params.addRule(BELOW, R.id.screen_status_view);
                params.addRule(Screen.isLandscape() ? LEFT_OF : ABOVE, R.id.screen_navigation_view);
                break;
        }
        contentView.setLayoutParams(params);

    }

    private boolean isLowVersion() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT;
    }
}