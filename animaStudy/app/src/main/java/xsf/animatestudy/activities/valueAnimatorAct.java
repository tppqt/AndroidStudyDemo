package xsf.animatestudy.activities;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import xsf.animatestudy.R;
import xsf.animatestudy.base.BaseActvity;

public class valueAnimatorAct extends BaseActvity {
    private TextView tvShow;
    private EditText etInput;
    private Button btnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animator);
        initView();
    }

    private void initView() {
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        tvShow = (TextView) findViewById(R.id.tvShow);
        etInput = (EditText) findViewById(R.id.etInput);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                NumValueAnimator();
                break;
        }

    }

    private void NumValueAnimator() {
        int end = etInput.getText().toString().equals("") ? 0 : Integer.valueOf(etInput.getText().toString());
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, end);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) valueAnimator.getAnimatedValue();
                tvShow.setText(String.valueOf(curValue));
            }
        });

        //增加监听器
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                Log.d("xsf", "animation start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("xsf", "animation end");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("xsf", "animation cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("xsf", "animation repeat");
            }
        });

        //增加指定的监听器
       /* valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("xsf", "animation end");
            }
        });*/
        //valueAnimator.setRepeatCount(2);
        //valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.start();


    }

    class myInInterpolator implements TimeInterpolator {

        @Override
        public float getInterpolation(float input) {
            return 1 - input;
        }
    }
}
