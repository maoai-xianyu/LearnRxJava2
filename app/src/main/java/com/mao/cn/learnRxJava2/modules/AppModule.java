package com.mao.cn.learnRxJava2.modules;

import android.app.Application;

import com.mao.cn.learnRxJava2.LearnRxJava2Application;
import com.mao.cn.learnRxJava2.utils.tools.PreferenceU;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final LearnRxJava2Application app;

    public AppModule(LearnRxJava2Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return LearnRxJava2Application.getInstance();
    }

    @Provides
    @Singleton
    PreferenceU providePreferenceU() {
        return PreferenceU.getInstance(LearnRxJava2Application.context());
    }

}