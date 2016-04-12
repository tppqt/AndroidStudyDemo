package xsf.okhttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import xsf.okhttp.base.BaseActvity;
import xsf.okhttp.bean.weather.Weather;
import xsf.okhttp.net.OkHttpUtils;
import xsf.okhttp.util.JsonUtil;

public class MainActivity extends BaseActvity {

    private TextView tvShow, tvShow2;
    private Weather weather;
    private ImageView ivShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Stetho.initializeWithDefaults(this);
        inintViews();
    }

    private void inintViews() {
        findViewById(R.id.btn_hettpget).setOnClickListener(this);
        findViewById(R.id.btn_http2).setOnClickListener(this);
        findViewById(R.id.btn_http3).setOnClickListener(this);
        tvShow = (TextView) findViewById(R.id.tvShow);
        tvShow2 = (TextView) findViewById(R.id.tvSho2);
        tvShow.setMovementMethod(new ScrollingMovementMethod());
        tvShow2.setMovementMethod(new ScrollingMovementMethod());
        ivShow = (ImageView) findViewById(R.id.ivShow);


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //  tvShow.setText(msg.toString());
                    tvShow2.setText(msg.toString());

                    break;
            }
        }
    };


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_hettpget:
                //  launch(TestActivity1.class);
                httpGet();
                break;
            case R.id.btn_http2:
                //  launch(TestActivity2.class);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpget2();
                    }
                }).start();
                break;
            case R.id.btn_http3:

               /* new Thread(new Runnable() {
                    @Override
                    public void run() {
                        testOkutil();
                    }
                }).start();*/

                testOkutil();
                break;
        }
    }

    private void testOkutil() {
        // String url = "http://blog.csdn.net/xsf50717";
        startProgressDialog();
        String url = "http://api.map.baidu.com/telematics/v3/weather?location=" + "成都" + "&output=json&ak=UMs5TPhtIKtzxG6RQz2QcSPs";


        OkHttpUtils.get(url, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSucess(String response) {
                //tvShow.setText(response.toString());
                weather = (Weather) JsonUtil.jsonToObject(response, Weather.class);

                tvShow.setText("weather.status: " + weather.status + "\n"
                        + "city: " + weather.results.get(0).currentCity + "\n"
                        + "天气描述 :" + weather.results.get(0).index.get(3).des + "\n"
                        + "温度：" + weather.results.get(0).weather_data.get(2).temperature + "\n"
                );

            }

            @Override
            public void onFailure(Exception response) {
                tvShow.setText("网络出错");
                tvShow2.setText(response.toString());
            }

        });
        stopProgressDialog();

    }

    private void httpget2() {
        //创建一个Request
        final OkHttpClient okClient = new OkHttpClient();
        //创建一个request
        final Request request = new Request.Builder()
                .url("http://blog.csdn.net/xsf50717")
                .build();
        try {
            Response response = okClient.newCall(request).execute();
            Logger.d(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void httpGet() {

        //创建一个Request
        OkHttpClient okClient = new OkHttpClient();
        //创建一个request
        final Request request = new Request.Builder()
                .url("http://blog.csdn.net/xsf50717")
                .build();
        //创建一个call
        Call call = okClient.newCall(request);
        //加入请求队列,异步执行.NOT UI Thread
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvShow.setText("请求失败");
                    }
                });
            }


            @Override
            public void onResponse(final Response response) throws IOException {
                // 由于是在异步线程中因此会出错
                // tvShow.setText(response.toString());
                //下面是采用Handler来处理
                Message msg = handler.obtainMessage(0, response.body().string());
                handler.sendMessage(msg);

                //下面是用runOnUIthread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvShow.setText(response.toString());
                        /*try {
                            tvShow2.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                });

                Logger.d(response.toString());
            }
        });

    }
}
