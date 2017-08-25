// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/08/2017 16:39 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.component;

import com.mao.cn.learnRxJava2.component.commons.ActivityScope;
import com.mao.cn.learnRxJava2.interactors.RetrofitShowContentInteractor;
import com.mao.cn.learnRxJava2.modules.RetrofitShowContentModule;
import com.mao.cn.learnRxJava2.ui.activity.RetrofitShowContentActivity;
import com.mao.cn.learnRxJava2.ui.features.IRetrofitShowContent;
import com.mao.cn.learnRxJava2.ui.presenter.RetrofitShowContentPresenter;

import dagger.Component;

@ActivityScope

@Component(
    dependencies = AppComponent.class,
    modules = RetrofitShowContentModule.class
)
public interface RetrofitShowContentComponent {
    void inject(RetrofitShowContentActivity instance);

    IRetrofitShowContent getViewInterface();

    RetrofitShowContentPresenter getPresenter();

    RetrofitShowContentInteractor getRetrofitShowContentInteractor();

}
