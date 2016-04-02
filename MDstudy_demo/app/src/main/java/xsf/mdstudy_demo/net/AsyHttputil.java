package xsf.mdstudy_demo.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import xsf.mdstudy_demo.book.BookBean;

/**
 * @author xushangfei
 * @time Created at 2016/3/17.
 * @email xsf_uestc_ncl@163.com
 */
public class AsyHttputil {

    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
    private static final String BASE_URL = "https://api.douban.com/v2/";


    public interface ISearchResponse<T> {
        void onData(T data);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void search(String name, final ISearchResponse<List<BookBean>> response) {
        RequestParams params = new RequestParams();
        params.put("q", name);
        params.put("start", 0);
        params.put("end", 50);
        client.get(getAbsoluteUrl("book/search"), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Gson gson = new Gson();
                    JSONObject json = new JSONObject(new String(responseBody));
                    JSONArray jsonBooks = json.getJSONArray("books");
                    List<BookBean> books = gson.fromJson(jsonBooks.toString(), new TypeToken<List<BookBean>>() {
                    }.getType());

                   response.onData(books);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("xsf", "false");
            }
        });


    }


}
