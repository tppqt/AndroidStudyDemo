package xsf.niceothers;

import android.animation.ValueAnimator;

import xsf.niceothers.base.BaseActvity;
import xsf.niceothers.views.CircleProgressBar;

public class CirclePressActivity extends BaseActvity {
    private CircleProgressBar mLineProgressBar;
    private CircleProgressBar mSolidProgressBar;
    private CircleProgressBar mCustomProgressBar1;
    private CircleProgressBar mCustomProgressBar2;
    private CircleProgressBar mCustomProgressBar3;
    private CircleProgressBar mCustomProgressBar4;
    private CircleProgressBar mCustomProgressBar5;
    private CircleProgressBar mCustomProgressBar6;


    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setTitle("CirclePress");

        mLineProgressBar = IfindViewById(R.id.line_progress);
        mSolidProgressBar = IfindViewById(R.id.solid_progress);
        mCustomProgressBar1 = IfindViewById(R.id.custom_progress1);
        mCustomProgressBar2 = IfindViewById(R.id.custom_progress2);
        mCustomProgressBar3 = IfindViewById(R.id.custom_progress3);
        mCustomProgressBar4 = IfindViewById(R.id.custom_progress4);
        mCustomProgressBar5 = IfindViewById(R.id.custom_progress5);
        mCustomProgressBar6 = IfindViewById(R.id.custom_progress6);
    }

    @Override
    protected void onResume() {
        super.onResume();
        simulateProgress();
    }
    private void simulateProgress() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                mLineProgressBar.setProgress(progress);
                mSolidProgressBar.setProgress(progress);
                mCustomProgressBar1.setProgress(progress);
                mCustomProgressBar2.setProgress(progress);
                mCustomProgressBar3.setProgress(progress);
                mCustomProgressBar4.setProgress(progress);
                mCustomProgressBar5.setProgress(progress);
                mCustomProgressBar6.setProgress(progress);
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(4000);
        animator.start();
    }
}
