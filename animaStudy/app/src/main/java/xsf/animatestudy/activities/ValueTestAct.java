package xsf.animatestudy.activities;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import xsf.animatestudy.R;
import xsf.animatestudy.base.BaseActvity;

public class ValueTestAct extends BaseActvity {
    private EditText etName, etPswd;
    private Button btnLogin;
    private TextView tvTips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_test);
        initViews();
    }

    private void initViews() {
        etName = (EditText) findViewById(R.id.etName);
        etPswd = (EditText) findViewById(R.id.etPswd);
        tvTips = (TextView) findViewById(R.id.tvTips);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                checkLogin();
                break;
        }

    }


    private void checkLogin() {

        boolean isName = etName.getText().toString().equals("xsf");
        boolean isPswd = etPswd.getText().toString().equals("123");
        if (isName && isPswd) {

            tvTips.setText("输入正确");
        } else {
            tvTips.setText("输入错误");
            ObjectAnimator.ofFloat(tvTips,"translationX",0,60,-60,20,-20,5,-5,0).setDuration(600).start();
        }

    }
}


/*
lockHintView.setVisibility(View.VISIBLE);
        lockHintView.setText("密码错误,还可以再输入"+lockInfo.getTryTime()+"次");

        ObjectAnimator.ofFloat(lockHintView,"translationX",0,60,-60,20,-20,5,-5,0).setDuration(600).start();*/
