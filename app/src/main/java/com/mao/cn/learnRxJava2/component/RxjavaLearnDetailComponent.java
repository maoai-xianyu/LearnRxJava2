// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/09/2017 19:56 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.component;

import com.mao.cn.learnRxJava2.component.commons.ActivityScope;
import com.mao.cn.learnRxJava2.interactors.RxjavaLearnDetailInteractor;
import com.mao.cn.learnRxJava2.modules.RxjavaLearnDetailModule;
import com.mao.cn.learnRxJava2.ui.activity.RxJavaLearnDetailActivity;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaLearnDetail;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaLearnDetailPresenter;

import dagger.Component;

@ActivityScope

@Component(
    dependencies = AppComponent.class,
    modules = RxjavaLearnDetailModule.class
)
public interface RxjavaLearnDetailComponent {
    void inject(RxJavaLearnDetailActivity instance);

    IRxjavaLearnDetail getViewInterface();

    RxjavaLearnDetailPresenter getPresenter();

    RxjavaLearnDetailInteractor getRxjavaLearnDetailInteractor();

}
