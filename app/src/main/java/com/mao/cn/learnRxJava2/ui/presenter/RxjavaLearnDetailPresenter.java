// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/09/2017 19:56 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.presenter;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public interface RxjavaLearnDetailPresenter {

    void runIntraval(String name);

    void getUser(String name);

    void getRepeatWhen();

    void getIntraval();

    void requestDataByNet();

    void requestDouble();

    void zip();

    void delayTimer();

    void doxx();

    void onErrorReturn();

    void onErrorResumeNext();

    void retry();

    void retryWhen();

    void repeatWhen();

    void groupBy();

    void threadChange();

    void throttle();

    void takeUtil();

    void clear();
}
