package xsf.study2D.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: xsf
 * Time: created at 2016/5/26.
 * Email: xsf_uestc_ncl@163.com
 */
public class TestView1 extends View {
    private Paint paint_green, paint_red;

    public TestView1(Context context) {
        this(context, null);
    }

    public TestView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //构造两个画笔，一个红色，一个绿色
        paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 3);
        paint_red = generatePaint(Color.RED, Paint.Style.STROKE, 3);

    }

    private Paint generatePaint(int color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //构造一个矩形
        Rect rect1 = new Rect(0, 0, 400, 220);
        //在平移画布前用绿色画下边框
        canvas.drawRect(rect1, paint_green);

        //平移画布后,再用红色边框重新画下这个矩形
        canvas.translate(100, 100);
        canvas.drawRect(rect1, paint_red);
    }
}
