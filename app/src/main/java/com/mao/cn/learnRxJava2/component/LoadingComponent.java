// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 06/09/2017 11:36 上午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.component;

import com.mao.cn.learnRxJava2.component.commons.ActivityScope;
import com.mao.cn.learnRxJava2.interactors.LoadingInteractor;
import com.mao.cn.learnRxJava2.modules.LoadingModule;
import com.mao.cn.learnRxJava2.ui.activity.LoadingActivity;
import com.mao.cn.learnRxJava2.ui.features.ILoading;
import com.mao.cn.learnRxJava2.ui.presenter.LoadingPresenter;

import dagger.Component;

@ActivityScope

@Component(
    dependencies = AppComponent.class,
    modules = LoadingModule.class
)
public interface LoadingComponent {
    void inject(LoadingActivity instance);

    ILoading getViewInterface();

    LoadingPresenter getPresenter();

    LoadingInteractor getLoadingInteractor();

}
