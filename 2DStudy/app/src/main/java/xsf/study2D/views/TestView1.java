package xsf.study2D.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: xsf
 * Time: created at 2016/5/26.
 * Email: xsf_uestc_ncl@163.com
 */
public class TestView1 extends View {
    private Paint paint_green, paint_red;
    Path star;

    private int mWidth, mHeight;

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
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(100,400);
        path.lineTo(300,100);
        path.lineTo(600,600);
        /*//canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(100,400);
        path.lineTo(300,100);
        path.lineTo(600,600);

        paint.setColor(Color.BLUE);
        float intervals[]={2,15,10,5};
        //paint.setPathEffect(new DashPathEffect(intervals,0));

        paint.setPathEffect(new DiscretePathEffect(3,10));
        canvas.drawPath(path,paint);*/

        /*Paint paint = new Paint();
        paint.setStrokeWidth(50);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        Path path  = new Path();
        path.moveTo(200,100);
        path.lineTo(450,100);
        path.lineTo(200,300);
        paint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(path,paint);

        path.moveTo(200,400);
        path.lineTo(450,400);
        path.lineTo(200,600);
        //paint.setStrokeJoin(Paint.Join.BEVEL);
        paint.setStrokeJoin(Paint.Join.ROUND);

        canvas.drawPath(path,paint);

        path.moveTo(200,700);
        path.lineTo(450,700);
        path.lineTo(200,900);
        paint.setStrokeJoin(Paint.Join.BEVEL);
        //paint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path,paint);
*/
        /*Paint paint = new Paint();
        paint.setStrokeWidth(80);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);


        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(100,200,400,200,paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(100,400,400,400,paint);

        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(100,600,400,600,paint);

//垂直画出x=100这条线
        paint.reset();
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        canvas.drawLine(100,50,100,750,paint);*/

       /* int baseLineY = 200;
        int baseLineX = 0 ;
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        //写文字
        paint.setColor(Color.BLACK);
        paint.setTextSize(80); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("cAnVasgfFぬも┰┠", baseLineX, baseLineY, paint);

        //计算各线在位置
        Paint.FontMetrics fm = paint.getFontMetrics();
        float ascent = baseLineY + fm.ascent;
        float descent = baseLineY + fm.descent;
        float top = baseLineY + fm.top;
        float bottom = baseLineY + fm.bottom;

        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //画top
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, top, 3000, top, paint);

        //画ascent
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, ascent, 3000, ascent, paint);

        //画descent
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, descent, 3000, descent, paint);

        //画bottom
        paint.setColor(Color.BLACK);
        canvas.drawLine(baseLineX, bottom, 3000, bottom, paint);*/
       /* String string = "测试文字偏移的参数";

        Path circlePath = new Path();
        circlePath.addCircle(220, 200, 100, Path.Direction.CCW);
        canvas.drawPath(circlePath, paint_red);//绘制出路径原形

        Path circlePath2 = new Path();
        circlePath2.addCircle(550, 200, 100, Path.Direction.CCW);
        canvas.drawPath(circlePath2, paint_red);//绘制出路径原形

        paint_green.setTextSize(30);

         //hoffset、voffset参数值全部设为0，看原始状态是怎样的
        canvas.drawTextOnPath(string, circlePath, 0, 0, paint_green);
        //第二个路径，改变hoffset、voffset参数值
        canvas.drawTextOnPath(string, circlePath2, 80, 30, paint_green);*/

       /* String str = "12345";
    paint_green.setTextSize(20);
        canvas.drawPosText(str,new float[]{
                100,100,    // 第一个字符位置
                200,200,    // 第二个字符位置
                300,300,    // ...
                400,400,
                500,500
        },paint_green);*/

        /*//构造一个矩形
        Rect rect1 = new Rect(100, 100,200, 200);
        canvas.drawRect(rect1, paint_red);

        // x 方向上倾斜45 度
        canvas.skew(1, 0);
        canvas.drawRect(rect1, paint_green);*/
       /* //skew 扭曲
        Paint paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 5);
        Paint paint_red   = generatePaint(Color.RED, Paint.Style.STROKE, 5);

        Rect rect1 = new Rect(10,10,200,100);

        canvas.drawRect(rect1, paint_green);
        canvas.skew(1,0);//X轴倾斜45度，Y轴不变
        canvas.drawRect(rect1, paint_red);*/

      /*  canvas.translate(50, 50);
        //在平移画布前用绿色画下边框
        canvas.drawRect(rect1, paint_green);

        //平移画布后,再用红色边框重新画下这个矩形
        canvas.translate(50, 50);
        canvas.drawRect(rect1, paint_red);*/
        //  canvas.drawRoundRect(rectF,30,30,paint_red);

        // 第二种 需要api21
        // canvas.drawRoundRect(100,100,800,400,30,30,paint_red);

        // 第一种
        /*RectF rectF = new RectF(100, 100, 400, 400);
        RectF rectF1 = new RectF(200, 200, 500, 500);
        canvas.drawArc(rectF, 0, 40, true, paint_green);
        canvas.drawArc(rectF1, 0, 40, false, paint_green);*/

       /* Path path = new Path();
        path.moveTo(50,50);
        path.quadTo(30,220,320,450);

        canvas.drawPath(path,paint_green);*/

       /* canvas.clipRect(10, 10, 110, 110);        //第一个
        canvas.clipRect(50, 50, 150, 150, Region.Op.DIFFERENCE); //第二个
        canvas.drawRect(0, 0, 200, 200, paint_green);*/


        //canvas.translate(400,400);
        /*canvas.translate(mWidth/2,mHeight/2);

        RectF rectf = new RectF(-300,-300,300,300);
        for (int i=0;i<15;i++){
            canvas.scale(0.8f,0.8f);
            canvas.drawRect(rectf,paint_red);
        }*/

        star(10);
        PathEffect cpe1 = new PathDashPathEffect(star, 20, 0, PathDashPathEffect.Style.MORPH);
        paint.setPathEffect(cpe1);
        canvas.drawPath(path,paint);


    }

    private void star(float length) {
        star = new Path();
        float dis1 = (float) ((length / 2) / Math.tan((54f / 180) * Math.PI));
        float dis2 = (float) (length * Math.sin((72f / 180) * Math.PI));
        float dis3 = (float) (length * Math.cos((72f / 180) * Math.PI));
        star.moveTo(length / 2, 0);
        star.lineTo(length / 2 - dis3, dis2);
        star.lineTo(length, dis1);
        star.lineTo(0, dis1);
        star.lineTo(length / 2 + dis3, dis2);
        star.lineTo(length / 2, 0);
    }
}
