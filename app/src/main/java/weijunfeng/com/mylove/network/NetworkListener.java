package weijunfeng.com.mylove.network;

/**
 * Created by hexin on 2016/9/19.
 */

public interface NetworkListener<T> {
    void onStart();

    void onSuccess(T t);

    void onError(Throwable throwable);

    Class<T> getActuClass();
}
