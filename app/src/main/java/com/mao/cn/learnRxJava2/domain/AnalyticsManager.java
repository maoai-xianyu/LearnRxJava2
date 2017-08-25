package com.mao.cn.learnRxJava2.domain;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;


public class AnalyticsManager {

    private Application app;

    public AnalyticsManager(Application app) {
        this.app = app;
    }

    public void registerAppEnter() {
        initImageLoader(app.getApplicationContext());
    }

    public void initImageLoader(Context context) {
        Fresco.initialize(context);
    }
}
