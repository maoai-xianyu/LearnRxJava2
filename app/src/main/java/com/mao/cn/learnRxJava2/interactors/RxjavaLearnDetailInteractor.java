// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/09/2017 19:56 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.interactors;

import com.mao.cn.learnRxJava2.http.HttpApi;
import com.mao.cn.learnRxJava2.http.RestApiAdapter;
import com.mao.cn.learnRxJava2.model.Translation;
import com.mao.cn.learnRxJava2.model.TranslationNew;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
* DESC   :
* AUTHOR : Xabad
*/
public class RxjavaLearnDetailInteractor {

    @Inject
    public RxjavaLearnDetailInteractor(){

    }


    public Observable<Translation> getCall1(){
        HttpApi httpApi = RestApiAdapter.getHttpsRxFyInstance().create(HttpApi.class);
        return httpApi.getCall1();
    }

    public Observable<TranslationNew> getCall2(){
        HttpApi httpApi = RestApiAdapter.getHttpsRxFyInstance().create(HttpApi.class);
        return httpApi.getCall2();
    }
}
