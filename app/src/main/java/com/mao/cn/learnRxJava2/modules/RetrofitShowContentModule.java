// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/08/2017 16:39 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.modules;


import com.mao.cn.learnRxJava2.interactors.RetrofitShowContentInteractor;
import com.mao.cn.learnRxJava2.ui.features.IRetrofitShowContent;
import com.mao.cn.learnRxJava2.ui.presenter.RetrofitShowContentPresenter;
import com.mao.cn.learnRxJava2.ui.presenterimp.RetrofitShowContentPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class RetrofitShowContentModule {

    private IRetrofitShowContent viewInterface;

    public RetrofitShowContentModule(IRetrofitShowContent viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Provides
    public IRetrofitShowContent getViewInterface() {
        return viewInterface;
    }
    @Provides
    public RetrofitShowContentPresenter getPresenter(IRetrofitShowContent viewInterface,RetrofitShowContentInteractor retrofitShowContentInteractor){
        return new RetrofitShowContentPresenterImp(viewInterface,retrofitShowContentInteractor);
    }
}