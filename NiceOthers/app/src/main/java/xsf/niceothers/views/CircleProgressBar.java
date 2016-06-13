package xsf.niceothers.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import xsf.niceothers.R;
import xsf.niceothers.util.UnitUtils;

public class CircleProgressBar extends ProgressBar {
    private static final int LINE = 0;
    private static final int SOLID = 1;

    private static final int DEFAULT_LINE_COUNT = 60;

    private static final float DEFAULT_LINE_WIDTH = 3.0f;
    private static final float DEFAULT_PROGRESS_TEXT_SIZE = 9.0f;
    private static final float DEFAULT_PROGRESS_STROKE_WIDTH = 1.0f;

    private static final String COLOR_FFF2A670 = "#fff2a670";
    private static final String COLOR_FFD3D3D5 = "#ffd3d3d5";
    private static final String DEFAULT_PATTERN = "%d%%";

    private final RectF mProgressRectF = new RectF();
    private final Rect mProgressTextRect = new Rect();

    private final Paint mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mProgressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mRadius;
    private float mCenterX;
    private float mCenterY;

    //Only work well in the Line Style, represents the line count of the rings included
    private int mLineCount;
    //Only work well in the Line Style, Height of the line of the progress bar
    private float mLineWidth;

    //Stroke width of the progress of the progress bar
    private float mProgressStrokeWidth;
    //Text size of the progress of the progress bar
    private float mProgressTextSize;

    //Color of the progress of the progress bar
    private int mProgressColor;
    //Color of the progress value of the progress bar
    private int mProgressTextColor;
    //Background color of the progress of the progress bar
    private int mProgressBackgroundColor;

    //If mDrawProgressText is true, will draw the progress text. otherwise, will not draw the progress text.
    private boolean mDrawProgressText;
    //Format the current progress value to the specified format
    private String mProgressTextFormatPattern;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LINE, SOLID})
    private @interface Style {
    }

    //The style of the progress color
    @Style
    private int mStyle;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        adjustIndeterminate();
        initFromAttributes(context, attrs);
        initPaint();
    }

    /**
     * Basic data initialization
     */
    @SuppressWarnings("ResourceType")
    private void initFromAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);

        mDrawProgressText = a.getBoolean(R.styleable.CircleProgressBar_draw_progress_text, true);

        mLineCount = a.getInt(R.styleable.CircleProgressBar_line_count, DEFAULT_LINE_COUNT);
        mProgressTextFormatPattern = a.hasValue(R.styleable.CircleProgressBar_progress_text_format_pattern) ?
                a.getString(R.styleable.CircleProgressBar_progress_text_format_pattern) : DEFAULT_PATTERN;

        mStyle = a.getInt(R.styleable.CircleProgressBar_style, LINE);
        //体现更好的适配性
        mLineWidth = a.getDimensionPixelSize(R.styleable.CircleProgressBar_line_width, UnitUtils.dip2px(getContext(), DEFAULT_LINE_WIDTH));
        mProgressTextSize = a.getDimensionPixelSize(R.styleable.CircleProgressBar_progress_text_size, UnitUtils.dip2px(getContext(), DEFAULT_PROGRESS_TEXT_SIZE));
        mProgressStrokeWidth = a.getDimensionPixelSize(R.styleable.CircleProgressBar_progress_stroke_width, UnitUtils.dip2px(getContext(), DEFAULT_PROGRESS_STROKE_WIDTH));

        mProgressColor = a.getColor(R.styleable.CircleProgressBar_progress_color, Color.parseColor(COLOR_FFF2A670));
        mProgressTextColor = a.getColor(R.styleable.CircleProgressBar_progress_text_color, Color.parseColor(COLOR_FFF2A670));
        mProgressBackgroundColor = a.getColor(R.styleable.CircleProgressBar_progress_background_color, Color.parseColor(COLOR_FFD3D3D5));

        a.recycle();
    }

    /**
     * Paint initialization
     */
    private void initPaint() {
        mProgressTextPaint.setTextAlign(Paint.Align.CENTER);
        mProgressTextPaint.setTextSize(mProgressTextSize);

        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressStrokeWidth);
    }

    /**
     * In order to work well, need to modify some of the following fields through reflection.
     * Another available way: write the following attributes to the xml
     * <p>
     * android:indeterminateOnly="false"
     * ndeterminate意思是“模糊的，不明确的”，而 android:indeterminateOnly这个属性如果设置为true，
     * 表示的是这个ProgressBar是模糊的，不明确的，也就是说，当前它并没有体现出具体的进度，
     * 只是一个小菊花在转。若设置为true
     * secondaryProgress将不显示的
     * android:indeterminate="false"
     * 不明确(false)就是滚动条的当前值自动在最小到最大值之间来回移动，形成这样一个动画效果，
     * 这个只是告诉别人“我正在工作”，
     * 但不能提示工作进度到哪个阶段。主要是在进行一些无法确定操作时间的任务时作为提示。
     * 而“明确”(true)就是根据你的进度可以设置现在的进度值。
     */
    private void adjustIndeterminate() {
        try {
            Field mOnlyIndeterminateField = ProgressBar.class.getDeclaredField("mOnlyIndeterminate");
            mOnlyIndeterminateField.setAccessible(true);//可以获取私有属性
            mOnlyIndeterminateField.set(this, false);//

            Field mIndeterminateField = ProgressBar.class.getDeclaredField("mIndeterminate");
            mIndeterminateField.setAccessible(true);
            mIndeterminateField.set(this, false);//

            Field mCurrentDrawableField = ProgressBar.class.getDeclaredField("mCurrentDrawable");
            mCurrentDrawableField.setAccessible(true);
            mCurrentDrawableField.set(this, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //======================================系统函数=============================
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        drawProgress(canvas);//画进度
        drawProgressText(canvas);//画文本
    }

    /**
     * When the size of CircleProgressBar changed, need to re-adjust the drawing area
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;

        mRadius = Math.min(mCenterX, mCenterY);
        mProgressRectF.top = mCenterY - mRadius;
        mProgressRectF.bottom = mCenterY + mRadius;
        mProgressRectF.left = mCenterX - mRadius;
        mProgressRectF.right = mCenterX + mRadius;

        //Prevent the progress from clipping,防止画笔过宽
        mProgressRectF.inset(mProgressStrokeWidth / 2, mProgressStrokeWidth / 2);
    }

    //==================================功能函数=================================

    //画文字
    private void drawProgressText(Canvas canvas) {
        if (!mDrawProgressText) {
            return;
        }

        String progressText = String.format(mProgressTextFormatPattern, getProgress());//get新的API

        mProgressTextPaint.setTextSize(mProgressTextSize);
        mProgressTextPaint.setColor(mProgressTextColor);
        mProgressTextPaint.getTextBounds(progressText, 0, progressText.length(), mProgressTextRect);//得到文字最小矩形
        canvas.drawText(progressText, mCenterX, mCenterY + mProgressTextRect.height() / 2, mProgressTextPaint);
    }


    private void drawProgress(Canvas canvas) {
        switch (mStyle) {
            case LINE:
                drawLineProgress(canvas);
                break;
            case SOLID:
            default:
                drawSolidProgress(canvas);
                break;
        }
    }

    /**
     * In the center of the drawing area as a reference point , rotate the canvas
     */
    private void drawLineProgress(Canvas canvas) {
        mProgressPaint.setColor(mProgressBackgroundColor);
        int rotateUnit = 360 / mLineCount;
        for (int i = 0; i < mLineCount; i++) {
            int saveCount = canvas.save();
            canvas.rotate(i * rotateUnit, mCenterX, mCenterY);
            canvas.drawLine(mProgressRectF.centerX(), mProgressRectF.top,
                    mProgressRectF.centerX(), mProgressRectF.top + mLineWidth, mProgressPaint);
            canvas.restoreToCount(saveCount);
        }
        //在前面基础上画有颜色的线段
        mProgressPaint.setColor(mProgressColor);
        int progressLineCount = (int) ((float) getProgress() / (float) getMax() * mLineCount);//getProgress/getMax
        for (int i = 0; i < progressLineCount; i++) {
            int saveCount = canvas.save();
            canvas.rotate(i * rotateUnit, mCenterX, mCenterX);
            canvas.drawLine(mProgressRectF.centerX(), mProgressRectF.top,
                    mProgressRectF.centerX(), mProgressRectF.top + mLineWidth, mProgressPaint);
            canvas.restoreToCount(saveCount);
        }
    }

    /**
     * Just draw arc
     */
    private void drawSolidProgress(Canvas canvas) {
        mProgressPaint.setColor(mProgressBackgroundColor);
        canvas.drawArc(mProgressRectF, 0.0f, 360.0f, false, mProgressPaint);

        mProgressPaint.setColor(mProgressColor);
        canvas.drawArc(mProgressRectF, -90.0f, 360.0f * getProgress() / getMax(), false, mProgressPaint);
    }


    public void setProgressTextFormatPattern(String progressTextformatPattern) {
        this.mProgressTextFormatPattern = progressTextformatPattern;
    }

    public String getProgressTextFormatPattern() {
        return mProgressTextFormatPattern;
    }

    public void setProgressStrokeWidth(float progressStrokeWidth) {
        this.mProgressStrokeWidth = progressStrokeWidth;
        mProgressRectF.inset(mProgressStrokeWidth / 2, mProgressStrokeWidth / 2);
        invalidate();
    }

    public float getProgressStrokeWidth() {
        return mProgressStrokeWidth;
    }

    public void setProgressTextSize(float progressTextSize) {
        this.mProgressTextSize = progressTextSize;
        invalidate();
    }

    public float getProgressTextSize() {
        return mProgressTextSize;
    }

    public void setProgressColor(int progressColor) {
        this.mProgressColor = progressColor;
        invalidate();
    }

    public int getProgressColor() {
        return mProgressColor;
    }

    public void setProgressTextColor(int progressTextColor) {
        this.mProgressTextColor = progressTextColor;
        invalidate();
    }

    public int getProgressTextColor() {
        return mProgressTextColor;
    }


    public void setProgressBackgroundColor(int progressBackgroundColor) {
        this.mProgressBackgroundColor = progressBackgroundColor;
        invalidate();
    }

    public int getProgressBackgroundColor() {
        return mProgressBackgroundColor;
    }

    public int getLineCount() {
        return mLineCount;
    }

    public void setLineCount(int lineCount) {
        this.mLineCount = lineCount;
    }

    public float getLineWidth() {
        return mLineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.mLineWidth = lineWidth;
    }

    public int getStyle() {
        return mStyle;
    }

    public void setStyle(@Style int style) {
        this.mStyle = style;
    }
}
