package xsf.evnetbustudy.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

import xsf.evnetbustudy.event.TestEvent;

/**
 * @author xushangfei
 * @time Created at 2016/4/2.
 * @email xsf_uestc_ncl@163.com
 */
public class BaseActvity extends AppCompatActivity implements View.OnClickListener {
    public static final String SER_KEY = "object";


    public void launch(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void launch(Class<?> clazz, Object object) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(SER_KEY, (Serializable) object);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doMain2(TestEvent event) {
        String msg = "onEventMainThread get the msg: \n" + event.getMsg();
        // tv_msg.setText(msg);
        Log.d("BaseActvity thread main", Thread.currentThread().getName());
        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {

    }
}
