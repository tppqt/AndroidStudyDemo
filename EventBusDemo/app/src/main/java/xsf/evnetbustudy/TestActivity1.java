package xsf.evnetbustudy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import xsf.evnetbustudy.base.BaseActvity;
import xsf.evnetbustudy.event.TestEvent;

public class TestActivity1 extends BaseActvity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);


        inintViews();


    }


    private void inintViews() {
        Button btn_post = (Button) findViewById(R.id.btn_msgpost);
        btn_post.setOnClickListener(this);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_msgpost:
                Log.d("xsf", "send msg");

                EventBus.getDefault().post(new TestEvent("msg send byTestAvtivity1"));
                break;
        }

    }

}
