package xsf.study2D;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import xsf.study2D.base.BaseActvity;

public class MainActivity extends BaseActvity {
    private Button btn_mvc;
    private Button btn_mvp;


    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btn_mvc = IfindViewById(R.id.btn_view1);
        btn_mvc.setOnClickListener(this);
        btn_mvp = IfindViewById(R.id.btn_view2);
        btn_mvp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_view1:
                launchActvity(View1Activity.class);
                break;
            case R.id.btn_view2:
                launchActvity(View2Activity.class);
        }
    }

    class LooperThread extends Thread {
        public Handler mHandler;

        public void run() {
            Looper.prepare();   //【见 2.1】

            mHandler = new Handler() {  //【见 3.1】
                public void handleMessage(Message msg) {
                    //TODO    定义消息处理逻辑. 【见 3.2】
                }
            };

            Looper.loop();  //【见 2.2】
        }
    }
}
