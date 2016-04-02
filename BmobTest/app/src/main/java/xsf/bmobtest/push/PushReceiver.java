package xsf.bmobtest.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.push.PushConstants;
import xsf.bmobtest.R;

/**
 * @author xushangfei
 * @time Created at 2016/3/27.
 * @email xsf_uestc_ncl@163.com
 */
public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = "";
        //获取提送的广播
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            //获取推送内容
            String str = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            JSONTokener jsonTokenr = new JSONTokener(str);
            try {
                JSONObject object = (JSONObject) jsonTokenr.nextValue();
                msg = object.getString("alert");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            NotificationManager notifimanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification(R.mipmap.ic_launcher,"testBmob",System.currentTimeMillis());

        }

    }
}
