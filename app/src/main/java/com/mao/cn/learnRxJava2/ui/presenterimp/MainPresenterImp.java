// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 06/09/2017 11:17 上午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.presenterimp;

import com.mao.cn.learnRxJava2.interactors.MainInteractor;
import com.mao.cn.learnRxJava2.ui.commons.BasePresenterImp;
import com.mao.cn.learnRxJava2.ui.features.IMain;
import com.mao.cn.learnRxJava2.ui.presenter.MainPresenter;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class MainPresenterImp extends BasePresenterImp implements MainPresenter {
    MainInteractor interactor;
    IMain viewInterface;

    public MainPresenterImp(IMain viewInterface, MainInteractor mainInteractor) {
        super();
        this.viewInterface = viewInterface;
        this.interactor = mainInteractor;
    }
}
