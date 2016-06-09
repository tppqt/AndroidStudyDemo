package xsf.weather;

import xsf.MvpStudy.R;
import xsf.weather.base.BaseActvity;

public class MVPActivity extends BaseActvity {


    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setTitle("MVP");

    }
}
