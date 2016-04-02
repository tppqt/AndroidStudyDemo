package utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by ELVIS on 2015/11/13.
 * 屏幕测量类
 */
public class ScreenMeasureUtil {
    //同时获取宽高
    public static int[] getScreenHW(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int Height = metrics.heightPixels;
        int Width = metrics.widthPixels;
        int HW[] = new int[]{Height, Width};
        return HW;
    }

}
