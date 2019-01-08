# Screen
实现Android状态栏、导航栏沉浸式效果，提供了几个屏幕相关的静态方法。</br>
<li>状态栏和导航栏均支持设置颜色、渐变色、图片、透明度、内容入侵和状态栏深色字体；</br></li>
<li>兼容竖屏、横屏，当屏幕旋转时会自动适配；</br></li>
<li>Api19及以上可实现沉浸式效果。</br></li>

## 截图
<image src="./screenshot/barImmerse.gif" width="270">  <image src="./screenshot/darkFont.gif"  width="270">  <image src="./screenshot/barColor.gif"  width="270">

<li>图一：状态栏和导航栏用图片作为背景，同时展示内容入侵和恢复演示。</br></li>
<li>图二：内容侵入状态栏、导航栏演示。</br></li>
<li>图三：状态栏、导航栏颜色变更及透明度变更。</br></li>

## 下载
在Gradle中添加依赖：
```groovy
implementation 'com.github.uguker:screen:1.0.5'
```

## Api
使用方式如下，具体请参考Demo。  
```java

Screen.with(Activity)
    ...;
    
```

调用`with`方法后返回一个`Bar`接口，然后可以链式连续地调用下列所有方法。
```java

// 状态栏深色字体
Bar statusBarDarkFont();
// 状态栏浅色字体
Bar statusBarLightFont();
// 状态栏背景颜色
Bar statusBarColor(@ColorInt int color);
// 状态栏背景图片
Bar statusBarBackground(@DrawableRes int resId);
// 状态栏背景图片
Bar statusBarBackground(Drawable drawable);
// 状态栏透明度
Bar statusBarAlpha(@IntRange(from = 0, to = 255) int alpha);
// 导航栏背景颜色
Bar navigationBarColor(@ColorInt int color);
// 导航栏背景图片
Bar navigationBarBackground(@DrawableRes int resId);
// 导航栏背景图片
Bar navigationBarBackground(Drawable drawable);
// 导航栏透明度
Bar navigationBarAlpha(@IntRange(from = 0, to = 255) int alpha);
// 内容侵入状态栏
Bar immerseStatusBar();
// 内容侵入状态栏
Bar immerseStatusBar(boolean immerse);
// 内容侵入导航栏
Bar immerseNavigationBar();
// 内容侵入导航栏
Bar immerseNavigationBar(boolean immerse);
// 使控件适应状态栏
Bar fitStatusBar(@IdRes int viewId);
// 使控件适应状态栏
Bar fitStatusBar(View view);
// 使控件适应导航栏
Bar fitNavigationBar(@IdRes int viewId);
// 使控件适应导航栏
Bar fitNavigationBar(View view);

```
## 静态方法
```java

// 隐藏真实的状态栏
Screen.hideRealStatusBar(activity);
// 隐藏真实的导航栏
Screen.hideRealNavigationBar(activity);
// 设置标题栏字体颜色
Screen.setStatusBarDarkFont(activity, darkFont);
// 全屏
Screen.setFull(window);
// 全屏不包括导航栏
Screen.setFull(window, false);
// 屏幕像素密度
Screen.getDensity();
// 屏幕内容宽度
Screen.getWidth();
// 屏幕内容高度
Screen.getHeight();
// 状态栏高度
Screen.getStatusBarHeight();
// 屏幕真实宽度
Screen.getRealWidth(activity);
// 屏幕真实高度
Screen.getRealHeight(activity);
// 导航栏高度
Screen.getNavigationBarHeight(activity);
// 是否有虚拟导航栏
Screen.hasNavigationBar(activity);
// 是否显示导航栏
Screen.isNavigationBarShow(activity);
// 是否是横屏显示
Screen.isLandscape();

```
