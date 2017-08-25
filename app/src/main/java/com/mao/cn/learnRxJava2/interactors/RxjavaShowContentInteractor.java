// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 08/04/2017 16:53 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.interactors;

import com.mao.cn.learnRxJava2.http.HttpApi;
import com.mao.cn.learnRxJava2.http.RestApiAdapter;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class RxjavaShowContentInteractor {

    @Inject
    public RxjavaShowContentInteractor() {

    }

    public Observable<String> getMovieTop(int start, int count) {
        HttpApi httpApi = RestApiAdapter.getStringInstance().create(HttpApi.class);
        return httpApi.getTodayMovie(start, count);
    }


}
