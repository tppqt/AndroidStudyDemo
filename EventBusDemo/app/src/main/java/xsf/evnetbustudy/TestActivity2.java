package xsf.evnetbustudy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import xsf.evnetbustudy.base.BaseActvity;
import xsf.evnetbustudy.event.TestEvent;

public class TestActivity2 extends BaseActvity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        inintViews();


    }


    private void inintViews() {
        Button btn_post = (Button) findViewById(R.id.btn_msgpost2);
        btn_post.setOnClickListener(this);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_msgpost2:
                EventBus.getDefault().post(new TestEvent("testEvent2 msg send byTestAvtivity2"));
                break;
        }

    }
}
