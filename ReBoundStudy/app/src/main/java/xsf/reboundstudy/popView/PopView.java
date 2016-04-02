package xsf.reboundstudy.popView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import java.util.ArrayList;
import java.util.List;

import xsf.reboundstudy.R;

/**
 * @author xushangfei
 * @time Created at 2016/3/29.
 * @email xsf_uestc_ncl@163.com
 */
public class PopView {

    /**
     * 默认的列数为3个
     */
    private static final int DEFAULT_COLUMN_COUNT = 3;

    /**
     * 动画时间
     */
    private static final int DEFAULT_DURATION = 300;

    /**
     * 拉力系数
     */
    private static final int DEFAULT_TENSION = 50;
    /**
     * 摩擦力系数
     */
    private static final int DEFAULT_FRICTION = 3;

    /**
     * item水平之间的间距
     */
    private static final int DEFAULT_HORIZONTAL_PADDING = 40;
    /**
     * item竖直之间的间距
     */
    private static final int DEFAULT_VERTICAL_PADDING = 15;

    private Activity activity;
    private int columCount;
    private List<PopMenuItem> menuItems = new ArrayList<>();
    private FrameLayout fl_animateLayout;
    private GridLayout gv_layout;
    private ImageView iv_close;
    private int duration;
    private double tension;
    private double frication;
    private int horizontalPadding;
    private int verticalPadding;
    private int screenWidth;
    private int screenHeight;

    private PopMenuItemListener popListener;

    private boolean isShowing = false;

    private SpringSystem springSystem;

    {
        springSystem = SpringSystem.create();
    }

    private PopView(Builder builder) {
        this.activity = builder.activity;
        this.menuItems.clear();
        this.menuItems.addAll(builder.itemList);

        this.columCount = builder.columnCount;
        this.duration = builder.duration;
        this.tension = builder.tension;
        this.frication = builder.friction;
        this.horizontalPadding = builder.horizontalPadding;
        this.verticalPadding = builder.verticalPadding;
        this.popListener = builder.popMenuItemListener;

        screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
        screenHeight = activity.getResources().getDisplayMetrics().heightPixels;

    }

    /**
     * 显示
     */
    public void show() {

        buildAnimateGridLayout();

        if (fl_animateLayout.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) fl_animateLayout.getParent();
            viewGroup.removeView(fl_animateLayout);
        }

        ViewGroup decorview = (ViewGroup) activity.getWindow().getDecorView();
        decorview.addView(fl_animateLayout);
        //执行显示动画
        showSubMenu(gv_layout);
        isShowing = true;
    }

    /**
     * 构建动画布局
     */
    private void buildAnimateGridLayout() {
        fl_animateLayout = new FrameLayout(activity);
        gv_layout = new GridLayout(activity);
        gv_layout.setColumnCount(columCount);
        gv_layout.setBackgroundColor(Color.parseColor("#f0ffffff"));

        int hPadding = dp2px(activity, horizontalPadding);
        int vPadding = dp2px(activity, verticalPadding);
        int itemWidth = (screenWidth - (columCount + 1) * hPadding) / columCount;
        int rowCount = menuItems.size() % columCount == 0 ? menuItems.size() / columCount : menuItems.size() / columCount + 1;

        int topMargin = (screenHeight - (itemWidth + vPadding) * rowCount + vPadding) / 2;

        for (int i = 0; i < menuItems.size(); i++) {
            final int position = i;
            PopSubView subView = new PopSubView(activity);
            PopMenuItem menuItem = menuItems.get(i);
            subView.setPopMenuItem(menuItem);
            subView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popListener != null) {
                        popListener.onPopItemClick(PopView.this, position);
                    }
                    hide();
                }
            });

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = itemWidth;
            lp.leftMargin = hPadding;
            if (i / columCount == 0) {
                //第一行的图标
                lp.topMargin = topMargin;
            } else {
                lp.topMargin = vPadding;
            }

            Log.d("xsf", "第i个 " + i + " lp.topMargin= " + lp.topMargin + "    lp.width =" + lp.width + "   lp.leftMargin=" + lp.leftMargin);
            gv_layout.addView(subView, lp);

        }

        fl_animateLayout.addView(gv_layout);

        iv_close = new ImageView(activity);
        iv_close.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv_close.setImageResource(R.mipmap.tabbar_compose_background_icon_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        layoutParams.bottomMargin = dp2px(activity, 25);
        fl_animateLayout.addView(iv_close, layoutParams);

    }


    /**
     * 隐藏
     */
    public void hide() {
        //执行消失的动画
        if (isShowing && gv_layout != null) {
            hideSubMenu(gv_layout, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                    decorView.removeView(fl_animateLayout);
                }
            });
            isShowing = false;
        }

    }

    private void hideSubMenu(ViewGroup viewGroup, final AnimatorListenerAdapter listener) {
        if (viewGroup == null) return;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate().translationY(screenHeight).setDuration(duration).setListener(listener).start();
        }

    }

    private void showSubMenu(ViewGroup viewGroup) {
        if (viewGroup == null) return;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            animateDirection(view, screenHeight, 0, tension, frication);
        }
    }

    /**
     * 弹簧动画
     *
     * @param view
     * @param from
     * @param to
     * @param tension
     * @param frication
     */
    private void animateDirection(final View view, float from, float to, double tension, double frication) {
        Spring spring = springSystem.createSpring();
        spring.setCurrentValue(from);
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, frication));

        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                view.setTranslationY((float) spring.getCurrentValue());
            }
        });

        spring.setEndValue(to);

    }


    public static class Builder {
        private Activity activity;
        private int columnCount = DEFAULT_COLUMN_COUNT;
        private List<PopMenuItem> itemList = new ArrayList<>();
        private int duration = DEFAULT_DURATION;
        private double tension = DEFAULT_TENSION;
        private double friction = DEFAULT_FRICTION;
        private int horizontalPadding = DEFAULT_HORIZONTAL_PADDING;
        private int verticalPadding = DEFAULT_VERTICAL_PADDING;
        private PopMenuItemListener popMenuItemListener;

        public Builder attachToactvity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder columnCount(int count) {
            this.columnCount = count;
            return this;
        }

        public Builder addMenuItem(PopMenuItem menuItem) {
            this.itemList.add(menuItem);
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder tension(double tension) {
            this.tension = tension;
            return this;
        }

        public Builder friction(double friction) {
            this.friction = friction;
            return this;
        }

        public Builder horizontalPadding(int padding) {
            this.horizontalPadding = padding;
            return this;
        }

        public Builder verticalPadding(int padding) {
            this.verticalPadding = padding;
            return this;
        }

        public Builder setOnItemClickListener(PopMenuItemListener listener) {
            this.popMenuItemListener = listener;
            return this;
        }

        public PopView build() {
            final PopView popMenu = new PopView(this);
            return popMenu;
        }


    }

/*    *//**
     * 每个接口的点击监听
     *//*
    public interface PopMenuItemListener {
        public void onPopItemClick(PopView popView, int position);
    }*/

    /**
     * px转换类
     *
     * @param context
     * @param dpVal
     * @return
     */
    protected int dp2px(Context context, int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public boolean isShowing() {
        return isShowing;
    }

}
