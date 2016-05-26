package xsf.study2D;

import xsf.study2D.base.BaseActvity;

public class View2Activity extends BaseActvity {


    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_view2;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setTitle("view2");

    }
}
