// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 08/04/2017 16:53 下午
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
public interface RxjavaShowContentPresenter {

    void getMovieTop(int start, int count);

    void onDestroySubscribe();

}
