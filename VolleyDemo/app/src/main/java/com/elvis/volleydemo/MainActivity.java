package com.elvis.volleydemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import base.BaseActvity;
import bean.WeatherNew;
import customRequest.XMLRequest;
import utils.BitmapCache;
import utils.VolleyStrRequest;
import utils.VolleyStringReqItf;

public class MainActivity extends BaseActvity {

    private TextView tvshow;
    private ImageView ivShow;
    private NetworkImageView netImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Volley_StringGet();
        //Volley_JsonGet();
        //Volley_StringPost();
        //Volley_JsonPost();
        inintViews();


    }

    private void inintViews() {
        tvshow = (TextView) findViewById(R.id.tvShow);
        ivShow = (ImageView) findViewById(R.id.ivShow);
        netImg = (NetworkImageView) findViewById(R.id.netImg);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                VolleyStringRequest();
                break;
            case R.id.btn_2:
                VolleyJsonRequest();
                break;
            case R.id.btn_3:
                VolleyImgRequest();
                break;
            case R.id.btn_4:
                VolleyImgLoder();
                break;
            case R.id.btn_5:
                VolleyNetImg();
                break;
            case R.id.btn_6:
                xmlReq();
                break;
            default:
                break;

        }
    }


    private void xmlReq() {
        String url = " http://flash.weather.com.cn/wmaps/xml/china.xml";
        final XMLRequest xmlReq = new XMLRequest(url, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                try {
                    int eventType = response.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                String nodeName = response.getName();
                                if ("city".equals(nodeName)) {
                                    String pName = response.getAttributeValue(0);
                                    Log.d("TAG", "pName is " + pName);
                                }
                                break;
                        }
                        eventType = response.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                tvshow.setText("sucess,check it on Ternal");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tvshow.setText(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(xmlReq);
    }

    private void VolleyNetImg() {
        String imgurl = "https://avatars2.githubusercontent.com/u/8703500?v=3&s=460";
        RequestQueue queues = MyApplication.getHttpQueues();
        ImageLoader imageLoader = new ImageLoader(queues, new BitmapCache());
        netImg.setDefaultImageResId(R.mipmap.ic_launcher);
        netImg.setErrorImageResId(R.mipmap.ivempty);
        netImg.setImageUrl(imgurl, imageLoader);

    }

    private void VolleyImgLoder() {
        String imgurl = "https://avatars2.githubusercontent.com/u/8703500?v=3&s=460";
        RequestQueue queues = MyApplication.getHttpQueues();
        ImageLoader imageLoader = new ImageLoader(queues, new BitmapCache());
        ImageLoader.ImageListener listener = imageLoader.getImageListener(ivShow, R.mipmap.ic_launcher, R.mipmap.ivempty);
        imageLoader.get(imgurl, listener);
        //imageLoader.get(imgurl, listener,200,200);

    }

    private void VolleyImgRequest() {
        String imgurl = "https://avatars2.githubusercontent.com/u/8703500?v=3&s=460";
        final ImageRequest imgReq = new ImageRequest(imgurl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        ivShow.setImageBitmap(bitmap);
                    }
                }, 0, 0, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        tvshow.setText(volleyError.toString());
                        ivShow.setImageResource(R.mipmap.ic_launcher);
                    }
                }
        );
        MyApplication.getHttpQueues().add(imgReq);

    }

    private void VolleyJsonRequest() {
        final JsonObjectRequest jsonObj = new JsonObjectRequest("http://wthrcdn.etouch.cn/weather_mini?city=成都", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                WeatherNew weatherInfo = gson.fromJson(jsonObject.toString(), WeatherNew.class);
                try{
                    tvshow.setText("城市：" + weatherInfo.data.city + "\n"
                                    + "staus is: " + weatherInfo.status + "\n"
                                    + "温度 :" + weatherInfo.data.wendu + "\n"
                                    + "感冒指数 :" + weatherInfo.data.ganmao + "\n"
                                    + "昨日 最高气温：" + weatherInfo.data.yesterday.high + "\n"
                                    + "预报 后天风力" + weatherInfo.data.forecast.get(3).fengli
                                //    + "预报 后天风力" + weatherInfo.data.forecast.get(5).fengli
                    );
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }


                System.out.println(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tvshow.setText(volleyError.toString());
                Toast.makeText(MainActivity.this, "jsonObjRequest Error", Toast.LENGTH_SHORT).show();
            }
        });
        MyApplication.getHttpQueues().add(jsonObj);
    }

    private void VolleyStringRequest() {
        // RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest strrq = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                tvshow.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "String request error", Toast.LENGTH_SHORT).show();
            }
        });
        // mQueue.add(strrq);
        MyApplication.getHttpQueues().add(strrq);

    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueues().cancelAll("yourTag");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case 0:
                Volley_StringGet();
                break;
            case 1:
                Volley_JsonGet();
                break;
            case 2:
                Volley_StringPost();
                break;
            case 3:
                Volley_JsonPost();
                break;
            case 4:
                MyVolley_StringPost();
                break;
            case 5:
                /*Intent it = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(it);*/
                launch(ImageActivity.class);
                break;

            default:
                Toast.makeText(this, "方法还没定义", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 0, "StringGet");
        menu.add(Menu.NONE, 1, 1, "JsonGet");
        menu.add(Menu.NONE, 2, 2, "StringPost");
        menu.add(Menu.NONE, 3, 3, "JsonPost");
        menu.add(Menu.NONE, 4, 4, "MyVolleyStringPost");
        menu.add(Menu.NONE, 5, 5, "ImageRequest");
        return super.onCreateOptionsMenu(menu);
    }

    private void Volley_StringGet() {
        /*这里使用StringRequets*/
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=125.71.229.221";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();

            }
        });
        /*请求对象设置Tag标签,并加入全局队列*/
        request.setTag("StringGet");
        MyApplication.getHttpQueues().add(request);

    }

    private void Volley_JsonGet() {
        /*这里使用JsonRequets*/
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=125.71.229.221";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject js) {
                Toast.makeText(MainActivity.this, js.toString(), Toast.LENGTH_LONG).show();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        /*请求对象设置Tag标签,并加入全局队列*/
        request.setTag("JsonGet");
        MyApplication.getHttpQueues().add(request);

    }

    private void Volley_StringPost() {
        /*这里使用StringRequets*/
        String url = "http://ip.taobao.com/service/getIpInfo.php?";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //这里需要设置post的参数
                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("ip", "125.71.229.221");
                return hashMap;
            }
        };
        //post请求需要单独实现
        /*请求对象设置Tag标签,并加入全局队列*/
        request.setTag("StringPost");
        MyApplication.getHttpQueues().add(request);
    }

    private void Volley_JsonPost() {
        /*这里使用JsonRequets*/
        String url = "http://ip.taobao.com/service/getIpInfo.php?";

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("ip", "125.71.229.222");
        JSONObject jsonParams = new JSONObject(hashMap);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        /*请求对象设置Tag标签,并加入全局队列*/
        request.setTag("JsonPost");
        MyApplication.getHttpQueues().add(request);
    }

    private void MyVolley_StringPost() {
        String url = "http://ip.taobao.com/service/getIpInfo.php?";
        HashMap<String, String> hashmap = new HashMap<String, String>();
        hashmap.put("ip", "125.71.229.222");
        VolleyStrRequest.ReqestStrPost(this, url, "StringPost", hashmap,
                new VolleyStringReqItf(this, VolleyStringReqItf.listener, VolleyStringReqItf.errorListener) {
                    @Override
                    public void onMySuccess(String result) {
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }


}
