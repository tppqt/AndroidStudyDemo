package customRequest;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;

/**
 * @author xushangfei
 * @time Created at 2016/4/7.
 * @email xsf_uestc_ncl@163.com
 */
public class GsonRequest<T> extends Request<T> {
    private final Response.Listener<T> mListener;
    //private Gson gson;
    private GsonBuilder gson;
    private Class<T> clazz;

    public GsonRequest(int method, String url, Class<T> clzz, Response.Listener<T> listener, Response.ErrorListener errlistener) {
        super(method, url, errlistener);
        gson = new GsonBuilder();
        clazz = clzz;
        mListener = listener;
    }
    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.create().fromJson(jsonString, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T t) {
        mListener.onResponse(t);

    }
}
