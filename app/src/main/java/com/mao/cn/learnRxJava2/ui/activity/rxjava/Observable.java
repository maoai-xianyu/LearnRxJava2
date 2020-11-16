package com.mao.cn.learnRxJava2.ui.activity.rxjava;

/**
 * @author zhangkun
 * @time 2020/11/15 10:29 PM
 * @Description 被观察者  持有 ObservableSubscribe 通知 Action1 通知 Observer
 */
public class Observable<T> {

    private ObservableSubscribe<T> onSubscribe;

    public Observable(ObservableSubscribe<T> subscribe) {
        this.onSubscribe = subscribe;
    }

    public static <T> Observable<T> create(ObservableSubscribe<T> subscribe) {

        return new Observable<>(subscribe);
    }

    public void subscribe(Observer<T> observer){
        onSubscribe.subscribe(observer);
    }

}
