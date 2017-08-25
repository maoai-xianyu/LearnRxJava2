package com.mao.cn.learnRxJava2.component;

import com.mao.cn.learnRxJava2.LearnRxJava2Application;
import com.mao.cn.learnRxJava2.modules.AppModule;
import com.mao.cn.learnRxJava2.modules.DomainModule;
import com.mao.cn.learnRxJava2.utils.tools.PreferenceU;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        DomainModule.class
})
public interface AppComponent {
    void inject(LearnRxJava2Application instance);

    PreferenceU getPreferenceU();
}