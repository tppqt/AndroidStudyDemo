package xsf.okhttp.net;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import xsf.okhttp.util.JsonUtils;

/**
 * @author xushangfei
 * @time Created at 2016/4/11.
 * @email xsf_uestc_ncl@163.com
 */
public class OkHttpUtils {
    public static final String TAG = "OKHttpUtils";
    private static OkHttpUtils okInstance;
    private OkHttpClient okHttpClient;
    private Handler HdDelivery;

    private OkHttpUtils() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        //add cookie
        okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        HdDelivery = new Handler(Looper.getMainLooper());
    }

    private synchronized static OkHttpUtils getOkInstance() {
        if (okInstance == null) {
            okInstance = new OkHttpUtils();
        }
        return okInstance;
    }

    /**
     * get方式请求
     *
     * @param url
     * @param callBack
     */
    private void getRequest(String url, final ResultCallBack callBack) {
        final Request request = new Request.Builder().url(url).build();
        deliveryResult(callBack, request);

    }

    private void postRequest(String url, final ResultCallBack callBack, List<Param> params) {
        final Request request = buildPostRequest(url, params);
        deliveryResult(callBack, request);

    }

    private Request buildPostRequest(String url, List<Param> params) {

        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }


    /**
     * 统一处理分发处理请求事件
     *
     * @param callBack
     * @param request
     */
    private void deliveryResult(final ResultCallBack callBack, Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailCallBack(callBack, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String str = response.body().string();
                    if (callBack.mtype == String.class) {
                        sendSucessCallBack(callBack, str);
                    } else {
                        Object object = JsonUtils.deserialize(str, callBack.mtype);
                        sendSucessCallBack(callBack, object);
                    }
                } catch (final Exception e) {
                    // LogUtils.e(TAG, "convert json failure", e);
                    sendSucessCallBack(callBack, e);
                }


            }
        });

    }

    private void sendSucessCallBack(final ResultCallBack callBack, final Object obj) {
        HdDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onSucess(obj);
                }
            }
        });
    }

    /**
     * 统一到UI线程中处理失败的callback
     *
     * @param callBack
     * @param e
     */
    private void sendFailCallBack(final ResultCallBack callBack, final Exception e) {
        HdDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onFailure(e);
                }
            }
        });

    }


    /**
     * http请求回调类,回调方法在UI线程中执行
     * Gson获取泛型类型
     *
     * @param <T>
     */
    public static abstract class ResultCallBack<T> {
        Type mtype;

        public ResultCallBack() {
            mtype = getSuperclassTypeParameter(getClass());
        }

        //得到泛型T的实际类型
        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superClass = subclass.getGenericSuperclass();//获取带泛型的父类
            if (superClass instanceof Class) {
                throw new RuntimeException("Missing type paramters");
            }
            ParameterizedType parameterizedType = (ParameterizedType) superClass;//表示一种参数化的泛型
            return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        }

        /**
         * 请求成功回调
         *
         * @param response
         */
        public abstract void onSucess(T response);

        /**
         * 请求失败回调
         *
         * @param response
         */
        public abstract void onFailure(Exception response);


    }

    /**
     * post请求参数类,键值对
     */
    public static class Param {

        String key;
        String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

    }

    /*********************************************对外接口****************************/
    /**
     * get请求
     *
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(String url, ResultCallBack callback) {
        getOkInstance().getRequest(url, callback);
    }

    /**
     * post请求
     *
     * @param url      请求url
     * @param callback 请求回调
     * @param params   请求参数
     */
    public static void post(String url, final ResultCallBack callback, List<Param> params) {
        getOkInstance().postRequest(url, callback, params);
    }


}
