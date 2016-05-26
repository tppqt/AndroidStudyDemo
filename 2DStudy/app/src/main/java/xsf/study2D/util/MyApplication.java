package xsf.study2D.util;

import android.app.Application;

/**
 * Author: xsf
 * Time: created at 2016/4/22.
 * Email: xsf_uestc_ncl@163.com
 */
public class MyApplication extends Application {
    public static MyApplication AnthenaApp;

    public static MyApplication getApplication() {
        return AnthenaApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AnthenaApp = this;
        /*//初始化logger系统
        Logger
                .init("Athena")                 // default PRETTYLOGGER or use just init()
                .methodCount(2)                 // default 2
                .hideThreadInfo()               // default shown
                .methodOffset(2);                // default 0*/

    }
}
