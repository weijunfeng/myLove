package weijunfeng.com.mylove.Acti;

import android.app.Application;
import android.os.Handler;

/**
 * Created by hexin on 2016/9/19.
 */

public class MyApplication extends Application {
    public volatile static MyApplication application;
    public volatile static Handler mainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mainHandler = new Handler();
    }
}
