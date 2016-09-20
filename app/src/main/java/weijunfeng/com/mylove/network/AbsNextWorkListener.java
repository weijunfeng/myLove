package weijunfeng.com.mylove.network;

import java.lang.reflect.ParameterizedType;

import weijunfeng.com.mylove.Acti.MyApplication;

/**
 * Created by hexin on 2016/9/19.
 */

public abstract class AbsNextWorkListener<T> implements NetworkListener<T> {

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(final T t) {
        MyApplication.mainHandler.post(new Runnable() {
            @Override
            public void run() {
                onHandleSuccess(t);
            }
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public Class<T> getActuClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract void onHandleSuccess(T t);
}
