// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/09/2017 19:56 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.presenterimp;

import com.mao.cn.learnRxJava2.interactors.RxjavaLearnDetailInteractor;
import com.mao.cn.learnRxJava2.ui.commons.BasePresenterImp;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaLearnDetail;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaLearnDetailPresenter;

/**
* DESC   :
* AUTHOR : Xabad
*/
public class RxjavaLearnDetailPresenterImp extends BasePresenterImp implements RxjavaLearnDetailPresenter {
    RxjavaLearnDetailInteractor interactor;
    IRxjavaLearnDetail viewInterface;
    public RxjavaLearnDetailPresenterImp(IRxjavaLearnDetail viewInterface,RxjavaLearnDetailInteractor rxjavaLearnDetailInteractor) {
        super();
        this.viewInterface = viewInterface;
        this.interactor = rxjavaLearnDetailInteractor;
    }
}
