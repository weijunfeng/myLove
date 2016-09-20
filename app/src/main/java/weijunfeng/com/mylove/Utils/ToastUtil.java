package weijunfeng.com.mylove.Utils;

import android.widget.Toast;

import weijunfeng.com.mylove.Acti.MyApplication;

/**
 * Created by hexin on 2016/9/19.
 */
public class ToastUtil {

    private final Toast toast;

    public ToastUtil() {
        toast = Toast.makeText(MyApplication.application, "", Toast.LENGTH_LONG);
    }

    public static ToastUtil getInstance() {
        return ToastHolder.TOAST_UTILS;
    }

    public void showMsg(String msg) {
        toast.setText(msg);
        toast.show();
    }

    private static class ToastHolder {
        public static final ToastUtil TOAST_UTILS = new ToastUtil();
    }
}
