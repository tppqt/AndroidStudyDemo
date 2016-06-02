package xsf.study2D.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: xsf
 * Time: created at 2016/6/2.
 * Email: xsf_uestc_ncl@163.com
 */
public class TestView2 extends View{
    private Paint paint_green, paint_red;

    private int mWidth, mHeight;

    public TestView2(Context context) {
        this(context,null);
    }

    public TestView2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //构造两个画笔，一个红色，一个绿色
        paint_green = generatePaint(Color.BLUE, Paint.Style.FILL, 3);
        paint_red = generatePaint(Color.RED, Paint.Style.STROKE, 10);
    }

    private Paint generatePaint(int color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        Path path4 = new Path();
        path1.addCircle(0, 0, 200, Path.Direction.CW);
        canvas.drawPath(path1, paint_green);

    }
}
