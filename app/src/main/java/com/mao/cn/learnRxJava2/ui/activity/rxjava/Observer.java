package com.mao.cn.learnRxJava2.ui.activity.rxjava;

/**
 * @author zhangkun
 * @time 2020/11/15 10:26 PM
 * @Description  观察者  T 为接收到的消息
 */
public interface Observer<T> {

    void onNext(T t);

    void onComplete();

    void onError(Throwable e);

}
