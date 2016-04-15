package xsf.animatestudy.activities;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import xsf.animatestudy.R;
import xsf.animatestudy.base.BaseActvity;

public class objanimatorTest extends BaseActvity {
    private EditText etName, etPswd;
    private Button btnLogin, btnObjAnim;
    private TextView tvTips, tvShowObj;


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
        btnObjAnim = (Button) findViewById(R.id.btnObjAnim);
        btnObjAnim.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvShowObj = (TextView) findViewById(R.id.tvShowObj);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                checkLogin();
                break;
            case R.id.btnObjAnim:
                TestObjAnimtor();
                break;
        }

    }

    /*    //1、透明度：alpha
        public void setAlpha(float alpha);

        //2、旋转度数：rotation、rotationX、rotationY
        public void setRotation(float rotation);
        public void setRotationX(float rotationX);
        public void setRotationY(float rotationY);

        //3、平移：translationX、translationY
        public void setTranslationX(float translationX);
        public void setTranslationY(float translationY);

        //缩放：scaleX、scaleY
        public void setScaleX(float scaleX);
        public void setScaleY(float scaleY);*/
    private void TestObjAnimtor() {
        tvShowObj.setText("测试语言");
        ObjectAnimator.ofInt(tvShowObj, "alpha", 1, 0, 1).setDuration(600).start();

    }


    private void checkLogin() {

        boolean isName = etName.getText().toString().equals("xsf");
        boolean isPswd = etPswd.getText().toString().equals("123");
        if (isName && isPswd) {

            tvTips.setText("输入正确");
        } else {
            tvTips.setText("输入错误");
            ObjectAnimator.ofFloat(tvTips, "translationX", 0, 60, -60, 20, -20, 5, -5, 0).setDuration(600).start();
        }

    }
}


