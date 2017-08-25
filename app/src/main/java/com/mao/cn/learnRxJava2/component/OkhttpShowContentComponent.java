// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 08/04/2017 16:53 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.component;

import com.mao.cn.learnRxJava2.component.commons.ActivityScope;
import com.mao.cn.learnRxJava2.interactors.OkhttpShowContentInteractor;
import com.mao.cn.learnRxJava2.modules.OkhttpShowContentModule;
import com.mao.cn.learnRxJava2.ui.activity.OkhttpShowContentActivity;
import com.mao.cn.learnRxJava2.ui.features.IOkhttpShowContent;
import com.mao.cn.learnRxJava2.ui.presenter.OkhttpShowContentPresenter;

import dagger.Component;

@ActivityScope

@Component(
    dependencies = AppComponent.class,
    modules = OkhttpShowContentModule.class
)
public interface OkhttpShowContentComponent {
    void inject(OkhttpShowContentActivity instance);

    IOkhttpShowContent getViewInterface();

    OkhttpShowContentPresenter getPresenter();

    OkhttpShowContentInteractor getOkhttpShowContentInteractor();

}
