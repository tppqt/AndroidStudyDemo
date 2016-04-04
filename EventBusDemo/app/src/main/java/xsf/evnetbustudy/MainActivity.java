package xsf.evnetbustudy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import xsf.evnetbustudy.base.BaseActvity;
import xsf.evnetbustudy.event.TestEvent;
import xsf.evnetbustudy.event.TestEvent2;

public class MainActivity extends BaseActvity {
    private TextView tv_msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        inintViews();
    }

    private void inintViews() {
        findViewById(R.id.btn_act1).setOnClickListener(this);
        findViewById(R.id.btn_act2).setOnClickListener(this);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
    }


    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_act1:
                launch(TestActivity1.class);
                break;
            case R.id.btn_act2:
                launch(TestActivity2.class);
                break;

        }
    }

    @Subscribe
    public void mytest(TestEvent event) {
        String msg = "onEventMainThread get the msg: \n" + event.getMsg();
        tv_msg.setText(msg);
        Log.d("xsf", msg);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void mytest(TestEvent2 event2) {
        String msg = "onEventMainThread get the msg: \n" + event2.getMsg();
        tv_msg.setText(msg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
