package xsf.niceothers;

import android.view.View;
import android.widget.TextView;

import xsf.niceothers.base.BaseActvity;

public class RotateDrawableActivity extends BaseActvity {
    private TextView tvShow;


    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_mvc;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setTitle("MVC");
        tvShow = IfindViewById(R.id.tvShowInfo);
        IfindViewById(R.id.btn_getInfo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getInfo:
                showWeatherInfo();
                break;
        }
    }

    private void showWeatherInfo() {

    }
}
