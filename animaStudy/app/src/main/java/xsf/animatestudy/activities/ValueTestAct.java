package xsf.animatestudy.activities;

import android.os.Bundle;

import xsf.animatestudy.R;
import xsf.animatestudy.base.BaseActvity;

public class ValueTestAct extends BaseActvity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_test);
    }
}


/*
lockHintView.setVisibility(View.VISIBLE);
        lockHintView.setText("密码错误,还可以再输入"+lockInfo.getTryTime()+"次");

        ObjectAnimator.ofFloat(lockHintView,"translationX",0,60,-60,20,-20,5,-5,0).setDuration(600).start();*/
