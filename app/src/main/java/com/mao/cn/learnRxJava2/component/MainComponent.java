// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 06/09/2017 11:16 上午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.component;

import com.mao.cn.learnRxJava2.component.commons.ActivityScope;
import com.mao.cn.learnRxJava2.interactors.MainInteractor;
import com.mao.cn.learnRxJava2.modules.MainModule;
import com.mao.cn.learnRxJava2.ui.activity.MainActivity;
import com.mao.cn.learnRxJava2.ui.features.IMain;
import com.mao.cn.learnRxJava2.ui.presenter.MainPresenter;

import dagger.Component;

@ActivityScope

@Component(
    dependencies = AppComponent.class,
    modules = MainModule.class
)
public interface MainComponent {
    void inject(MainActivity instance);

    IMain getViewInterface();

    MainPresenter getPresenter();

    MainInteractor getMainInteractor();

}
