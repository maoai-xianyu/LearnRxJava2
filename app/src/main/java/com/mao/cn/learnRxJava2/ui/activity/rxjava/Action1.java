package com.mao.cn.learnRxJava2.ui.activity.rxjava;

/**
 * @author zhangkun
 * @time 2020/11/15 10:31 PM
 *
 *
 * @Description 发射器，被观察者发射消息给观察者,
 * 如何管联 Action1 和 observer , 不要互相持有，要解耦使用 ObservableSubscribe
 */
public interface Action1<T> {

    void subscribe(T t);

}
