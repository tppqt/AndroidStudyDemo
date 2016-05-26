package xsf.study2D;

import android.view.View;
import android.widget.TextView;

import xsf.study2D.base.BaseActvity;

public class View1Activity extends BaseActvity {
    private TextView tvShow;


    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_view1;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setTitle("view1");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private void showWeatherInfo() {

    }
}
