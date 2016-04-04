package xsf.evnetbustudy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xsf.evnetbustudy.base.BaseActvity;
import xsf.evnetbustudy.event.TestEvent;
import xsf.evnetbustudy.event.TestEvent2;
import xsf.evnetbustudy.event.TestEvent3;

public class MainActivity extends BaseActvity {
    private TextView tv_msg, tv_step2, tv_step3;


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
        tv_step2 = (TextView) findViewById(R.id.tv_step2);
        tv_step3 = (TextView) findViewById(R.id.tv_step3);
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

    {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doMain(TestEvent event) {
        String msg = "onEventMainThread get the msg: \n" + event.getMsg();
        tv_msg.setText(msg);
        Log.d("thread main", Thread.currentThread().getName());
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void doBack(TestEvent event) {
        Log.d("thread back", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void doAsync(TestEvent event) {
        Log.d("thread async", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void dopost(TestEvent event) {
        Log.d("thread post", Thread.currentThread().getName());
    }


    @Subscribe
    public void doReceive(TestEvent2 event2) {
        String msg = " receive the msg: \n" + event2.getMsg();
        tv_msg.setText(msg);
        EventBus.getDefault().post(new TestEvent3(" Net Datas"));
        tv_step2.setText("request Net Data ing…… ");
    }

    @Subscribe
    public void showNetData(TestEvent3 event3) {
        String msg = "show data:\n" + event3.getMsg();
        tv_step3.setText(msg);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
