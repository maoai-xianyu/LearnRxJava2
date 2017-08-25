// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/16/2017 15:27 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.component;

import com.mao.cn.learnRxJava2.component.commons.ActivityScope;
import com.mao.cn.learnRxJava2.interactors.RxjavaLearnRxBingdingInteractor;
import com.mao.cn.learnRxJava2.modules.RxjavaLearnRxBingdingModule;
import com.mao.cn.learnRxJava2.ui.activity.RxjavaLearnRxBingdingActivity;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaLearnRxBingding;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaLearnRxBingdingPresenter;

import dagger.Component;

@ActivityScope

@Component(
    dependencies = AppComponent.class,
    modules = RxjavaLearnRxBingdingModule.class
)
public interface RxjavaLearnRxBingdingComponent {
    void inject(RxjavaLearnRxBingdingActivity instance);

    IRxjavaLearnRxBingding getViewInterface();

    RxjavaLearnRxBingdingPresenter getPresenter();

    RxjavaLearnRxBingdingInteractor getRxjavaLearnRxBingdingInteractor();

}
