package xsf.niceothers;

import android.view.View;
import android.widget.Button;

import xsf.niceothers.base.BaseActvity;

public class MainActivity extends BaseActvity {
    private Button btn_mvc;
    private Button btn_mvp;


    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btn_mvc = IfindViewById(R.id.btn_rotate);
        btn_mvc.setOnClickListener(this);
        btn_mvp = IfindViewById(R.id.btn_circleProcessBar);
        btn_mvp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rotate:
                launchActvity(RotateDrawableActivity.class);
                break;
            case R.id.btn_circleProcessBar:
                launchActvity(CirclePressActivity.class);
        }
    }
}
