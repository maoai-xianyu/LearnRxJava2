package com.mao.cn.learnRxJava2.ui.commons;

import android.content.Context;

import com.mao.cn.learnRxJava2.LearnRxJava2Application;
import com.mao.cn.learnRxJava2.utils.tools.PreferenceU;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


// +----------------------------------------------------------------------
// | CreateTime: 15/8/12 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.xueyong.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------
public abstract class BasePresenterImp implements BasePresenter {
    protected Context context;
    protected PreferenceU preferenceU;

    public BasePresenterImp() {
        this.context = LearnRxJava2Application.context();
        this.preferenceU = PreferenceU.getInstance(LearnRxJava2Application.context());

    }

    protected <T> ObservableTransformer<T, T> applyIoSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> ObservableTransformer<T, T> timer() {
        return observable -> observable.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
    }

}
