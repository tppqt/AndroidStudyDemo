package xsf.animatestudy.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import xsf.animatestudy.R;
import xsf.animatestudy.base.BaseActvity;

public class TestActivity2 extends BaseActvity {
    private Button btnObjAnimator, btnValueAnimator, btnValueObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        initView();
    }

    private void initView() {
        btnObjAnimator = (Button) findViewById(R.id.btnObjAnimator);
        btnValueAnimator = (Button) findViewById(R.id.btnValueAnimator);
        btnValueObj = (Button) findViewById(R.id.btnValueObj);
        btnObjAnimator.setOnClickListener(this);
        btnValueAnimator.setOnClickListener(this);
        btnValueObj.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnObjAnimator:
                launch(objanimatorTest.class);
                break;
            case R.id.btnValueAnimator:
                launch(valueAnimatorAct.class);
                break;
            case R.id.btnValueObj:
                launch(valueOfObjActivity.class);
                break;


        }
    }


}
