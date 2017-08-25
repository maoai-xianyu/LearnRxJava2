package com.mao.cn.learnRxJava2.modules;

import android.app.Application;

import com.mao.cn.learnRxJava2.domain.AnalyticsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {
    @Provides
    @Singleton
    public AnalyticsManager provideAnalyticsManager(Application app) {
        return new AnalyticsManager(app);
    }
}