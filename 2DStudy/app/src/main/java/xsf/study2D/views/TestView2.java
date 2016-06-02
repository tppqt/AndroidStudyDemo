package xsf.study2D.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: xsf
 * Time: created at 2016/6/2.
 * Email: xsf_uestc_ncl@163.com
 */
public class TestView2 extends View {
    private Paint paint_black, paint_white;
    private Path path1, path2, path3, path4;


    private int mWidth, mHeight;

    public TestView2(Context context) {
        this(context, null);
    }

    public TestView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //构造两个画笔，一个红色，一个绿色
        paint_black = generatePaint(Color.BLACK, Paint.Style.FILL, 3);
        paint_white = generatePaint(Color.WHITE, Paint.Style.FILL, 3);
        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        Path path4 = new Path();
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);

        path1.addCircle(0, 0, 200, Path.Direction.CW);
        path2.addRect(0, -200, 200, 200, Path.Direction.CW);
        path3.addCircle(0, -100, 100, Path.Direction.CW);
        path4.addCircle(0, 100, 100, Path.Direction.CCW);


        path1.op(path2, Path.Op.DIFFERENCE);
        path1.op(path3, Path.Op.UNION);
        path1.op(path4, Path.Op.DIFFERENCE);
        canvas.drawPath(path1, paint_black);

    }
}
