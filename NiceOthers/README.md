#1、RotateDrawableActivity
[http://www.jianshu.com/p/0e0de2cdd2bb](http://www.jianshu.com/p/0e0de2cdd2bb)

仿网易播放器，但是有点不像

使用RotateDrawable

使用到MediaPlayer

handelr+thread管理状态

分解状态处理复杂流程



#2、CircleProgressBar

[https://github.com/dinuscxj/CircleProgressBar/blob/master/README-ZH.md](https://github.com/dinuscxj/CircleProgressBar/blob/master/README-ZH.md)

继承了procressBar的加载动画，有点像华为计数器那种，但是缺少了边沿动画效果。

使用到注解

使用progressBar的getProgrss/getMax获取比例

体现更好的适配性
    `//体现更好的适配性
        mLineWidth = a.getDimensionPixelSize(R.styleable.CircleProgressBar_line_width, UnitUtils.dip2px(getContext(), DEFAULT_LINE_WIDTH));
    `
参考文献：

[http://www.flysnow.org/2015/08/13/android-tech-docs-support-annotations.html](http://www.flysnow.org/2015/08/13/android-tech-docs-support-annotations.html)

[http://blog.csdn.net/harvic880925/article/details/50423762](http://blog.csdn.net/harvic880925/article/details/50423762)

[http://blog.csdn.net/wangjinyu501/article/details/25963993](http://blog.csdn.net/wangjinyu501/article/details/25963993)