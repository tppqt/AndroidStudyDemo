package xsf.reboundstudy.popView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author xushangfei
 * @time Created at 2016/3/30.
 * @email xsf_uestc_ncl@163.com
 */
public class PopSubView extends LinearLayout {
    private static final float factor = 1.5f;
    private ImageView iv_icon;
    private TextView tv_des;


    public PopSubView(Context context) {
        this(context, null);
    }

    public PopSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        iv_icon = new ImageView(context);
        iv_icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        LayoutParams lp_iv = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(iv_icon, lp_iv);

        tv_des = new TextView(context);
        LayoutParams lp_tv = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp_tv.topMargin = 10;
        addView(tv_des, lp_tv);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        scalViewAnimation(PopSubView.this, factor);
                        break;
                    case MotionEvent.ACTION_UP:
                        //恢复原样
                        scalViewAnimation(PopSubView.this, 1);
                        break;
                }
                return false;
            }
        });


    }

    public void setPopMenuItem(PopMenuItem popMenuItem) {
        if (popMenuItem == null) {
            return;
        }
        iv_icon.setImageDrawable(popMenuItem.getDrawable());
        tv_des.setText(popMenuItem.getTitle());
    }

    /**
     * 缩放动画
     *
     * @param popSubView
     * @param factor
     */
    private void scalViewAnimation(PopSubView popSubView, float factor) {
        popSubView.animate().scaleX(factor).scaleY(factor).setDuration(100).start();

    }
}
