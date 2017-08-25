// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/16/2017 15:27 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.presenterimp;

import com.mao.cn.learnRxJava2.interactors.RxjavaLearnRxBingdingInteractor;
import com.mao.cn.learnRxJava2.ui.commons.BasePresenterImp;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaLearnRxBingding;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaLearnRxBingdingPresenter;

/**
* DESC   :
* AUTHOR : Xabad
*/
public class RxjavaLearnRxBingdingPresenterImp extends BasePresenterImp implements RxjavaLearnRxBingdingPresenter {
    RxjavaLearnRxBingdingInteractor interactor;
    IRxjavaLearnRxBingding viewInterface;
    public RxjavaLearnRxBingdingPresenterImp(IRxjavaLearnRxBingding viewInterface,RxjavaLearnRxBingdingInteractor rxjavaLearnRxBingdingInteractor) {
        super();
        this.viewInterface = viewInterface;
        this.interactor = rxjavaLearnRxBingdingInteractor;
    }
}
