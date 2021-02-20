package com.mao.cn.learnRxJava2.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author zhangkun
 * @time 2021/2/20 6:47 PM
 * @Description
 */

class RxBus1 {

    private final Subject<Object> mBus;

    private RxBus1() {
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus1 get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {

        private static final RxBus1 BUS = new RxBus1();
    }

}
