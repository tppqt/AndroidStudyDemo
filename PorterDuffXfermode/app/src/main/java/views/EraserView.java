package views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.elvis.porterduffxfermode.R;

import utils.ScreenMeasureUtil;

/**
 * Created by ELVIS on 2015/11/13.
 */
public class EraserView extends View {
    //屏幕宽高
    private int[] screenSize;
    private int screenH;
    private int screenW;
    //PorterDuff Mode
    private static final PorterDuff.Mode PD_MODE = PorterDuff.Mode.DST_IN;//使用dst_out效果一样
    private static final int MIN_MOVE_DIS = 5;//最小的移动距离：如果我们手指在屏幕上的移动距离小于此值则不会绘制
    private float preTouchX, preTouchY;
    //绘图
    private Canvas mCanvas; //画布
    private Paint mPaint;//画笔
    private Path mPath;//路径
    //前后背景
    private Bitmap fgBitmap, bgBitmap;

    public EraserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        screenSize = ScreenMeasureUtil.getScreenHW((Activity) context);
        screenH = screenSize[0];
        screenW = screenSize[1];

        initViews(context);


    }

    private void initViews(Context context) {
        //实例化路径对象
        mPath = new Path();

        //实例化画笔开启抗锯齿和抗抖动
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        //画笔风格为描边
        mPaint.setStyle(Paint.Style.STROKE);
        //笔触类型为圆角
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        //设置描边
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置描边宽度
        mPaint.setStrokeWidth(20);
        //设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PD_MODE));
        //此处采用的是DST_IN模式必须要将画笔透明度设置为0
        mPaint.setARGB(128, 255, 0, 0);


        //前景图片的bitmap
        fgBitmap = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);
        //填充进画布
        mCanvas = new Canvas(fgBitmap);
        //绘制画布背景颜色为灰色
        mCanvas.drawColor(0xffacacac);
        //获取底面背景图片
        bgBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_splash);
        //缩放bitmap至屏幕大小
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenW, screenH, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        //绘制前景
        canvas.drawBitmap(fgBitmap, 0, 0, null);
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(x, y);
                preTouchX = x;
                preTouchY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - preTouchX);
                float dy = Math.abs(y - preTouchY);
                if (dx >= MIN_MOVE_DIS || dy >= MIN_MOVE_DIS) {
                    mPath.quadTo(preTouchX, preTouchY, (x + preTouchX) / 2, (y + preTouchY) / 2);
                    preTouchX = x;
                    preTouchY = y;
                }
                break;

        }
        //重绘视图
        invalidate();
        return true;

    }
}
