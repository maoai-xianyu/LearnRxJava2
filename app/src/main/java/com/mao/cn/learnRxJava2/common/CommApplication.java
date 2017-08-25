package com.mao.cn.learnRxJava2.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhangkun on 2017/6/8.
 */
public abstract class CommApplication extends Application {
    private static CommApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.beforeCreate();
        super.onCreate();
        this.initApplication();
        this.afterOnCreate();
    }

    protected abstract void initApplication();

    protected abstract void beforeCreate();

    protected abstract void afterOnCreate();

    public static Context context() {
        return instance.getApplicationContext();
    }
}
