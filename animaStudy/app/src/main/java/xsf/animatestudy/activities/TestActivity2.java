package xsf.animatestudy.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import xsf.animatestudy.R;
import xsf.animatestudy.base.BaseActvity;

public class TestActivity2 extends BaseActvity {
    private Button btnObjAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        initView();
    }

    private void initView() {
        btnObjAnimator = (Button) findViewById(R.id.btnValueObj);
        btnObjAnimator.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnValueObj:
                launch(objanimatorTest.class);
                break;

        }
    }
}
