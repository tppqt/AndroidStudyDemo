package xsf.animatestudy;

import android.os.Bundle;
import android.view.View;

import xsf.animatestudy.base.BaseActvity;

public class MainActivity extends BaseActvity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inintViews();
    }

    private void inintViews() {
        findViewById(R.id.btn_normal).setOnClickListener(this);
        findViewById(R.id.btn_multitype).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_normal:
                launch(TestActivity1.class);
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
                break;
            case R.id.btn_multitype:
                launch(TestActivity2.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
