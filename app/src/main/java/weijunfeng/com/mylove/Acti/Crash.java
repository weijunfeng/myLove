package weijunfeng.com.mylove.Acti;

import android.content.Context;

/**
 * Created by hexin on 2016/9/20.
 */
public class Crash implements Thread.UncaughtExceptionHandler {
    private Context context;

    private Crash() {
    }

    public static Crash getInstance() {
        return CrashHolder.holder;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

    }

    public void init(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private static class CrashHolder {
        static final Crash holder = new Crash();
    }
}
