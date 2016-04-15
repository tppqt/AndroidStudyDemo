package xsf.animatestudy.activities;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import xsf.animatestudy.R;
import xsf.animatestudy.base.BaseActvity;

public class valueOfObjActivity extends BaseActvity {
    private Button btnStartanim, btnCancleanim;
    private TextView tvShowanim;
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_of_obj);
        initViews();
        createAnimator();
    }

    private void initViews() {
        btnStartanim = (Button) findViewById(R.id.btnStartanim);
        btnCancleanim = (Button) findViewById(R.id.btnCancelanim);
        btnStartanim.setOnClickListener(this);
        btnCancleanim.setOnClickListener(this);
        tvShowanim = (TextView) findViewById(R.id.tvShowanim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartanim:
                start();
                break;
            case R.id.btnCancelanim:
                cancel();
                break;
        }
    }

    public void createAnimator() {
        animator = ValueAnimator.ofObject(new CharEvaluator(), new Character('A'), new Character('Z'));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                char text = (char) animation.getAnimatedValue();
                tvShowanim.setText(String.valueOf(text));
            }
        });
        animator.setDuration(10000);
        animator.setInterpolator(new AccelerateInterpolator());
        // animator.start();
    }

    public void start() {
      //  animator.start();

        if (!animator.isRunning()) {
            animator.cancel();
            animator.start();
        }
    }

    public void cancel() {
        animator.cancel();

    }


    class CharEvaluator implements TypeEvaluator<Character> {

        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int starttInt = (int) startValue;
            int endInt = (int) endValue;
            int curInt = (int) (starttInt + fraction * (endInt - starttInt));
            char result = (char) curInt;
            return result;
        }
    }
}
