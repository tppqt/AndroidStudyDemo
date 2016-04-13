package xsf.animatestudy;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import xsf.animatestudy.base.BaseActvity;

public class TestActivity1 extends BaseActvity {
    private Button btnDrableAmimation, btnSetAnimation;
    private ImageView ivShow;
    private Animation setAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);


        inintViews();


    }


    private void inintViews() {
        btnDrableAmimation = (Button) findViewById(R.id.btnDrableAnimation);
        btnSetAnimation = (Button) findViewById(R.id.btnSetAnimation);
        btnDrableAmimation.setOnClickListener(this);
        btnSetAnimation.setOnClickListener(this);
        ivShow = (ImageView) findViewById(R.id.ivShow);
        ivShow.setOnClickListener(this);
        setAnimation = AnimationUtils.loadAnimation(this, R.anim.setanimtion);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDrableAnimation:
                startProgressDialog();
                // stopProgressDialog();
                break;
            case R.id.btnSetAnimation:
                startSetnimation();
                break;
            case R.id.ivShow:
                Toast.makeText(TestActivity1.this, "点击了ivShow控件", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startSetnimation() {
        ivShow.startAnimation(setAnimation);

    }
}
