package xsf.weather.util;

import com.google.gson.Gson;

import java.util.List;


/*
 * 解析json字符串
 */
public class GsonUtil {


	private static Gson gson = new Gson();

	public static Gson getGson() {
		return gson;
	}

	public static String objectToJson(Object obj) {
		return getGson().toJson(obj);

	}

	public static String listToJson(List list) {
		return getGson().toJson(list);

	}

	public static Object jsonToObject(String json, Class clazz) {
		return getGson().fromJson(json, clazz);

	}

}
