// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/08/2017 18:35 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.modules;


import com.mao.cn.learnRxJava2.interactors.RxJavaLearnInteractor;
import com.mao.cn.learnRxJava2.ui.features.IRxJavaLearn;
import com.mao.cn.learnRxJava2.ui.presenter.RxJavaLearnPresenter;
import com.mao.cn.learnRxJava2.ui.presenterimp.RxJavaLearnPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class RxJavaLearnModule {

    private IRxJavaLearn viewInterface;

    public RxJavaLearnModule(IRxJavaLearn viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Provides
    public IRxJavaLearn getViewInterface() {
        return viewInterface;
    }
    @Provides
    public RxJavaLearnPresenter getPresenter(IRxJavaLearn viewInterface,RxJavaLearnInteractor rxJavaLearnInteractor){
        return new RxJavaLearnPresenterImp(viewInterface,rxJavaLearnInteractor);
    }
}