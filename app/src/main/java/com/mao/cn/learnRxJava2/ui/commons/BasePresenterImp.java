package com.mao.cn.learnRxJava2.ui.commons;

import android.content.Context;

import com.mao.cn.learnRxJava2.LearnRxJava2Application;
import com.mao.cn.learnRxJava2.utils.tools.PreferenceU;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


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

    protected <T> Observable.Transformer<T, T> applyIoSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
