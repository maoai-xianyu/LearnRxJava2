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

import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.http.HttpApi;
import com.mao.cn.learnRxJava2.interactors.RxjavaLearnDetailInteractor;
import com.mao.cn.learnRxJava2.model.User;
import com.mao.cn.learnRxJava2.ui.commons.BasePresenterImp;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaLearnDetail;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaLearnDetailPresenter;
import com.mao.cn.learnRxJava2.utils.network.NetworkUtils;
import com.mao.cn.learnRxJava2.utils.tools.LogU;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class RxjavaLearnDetailPresenterImp extends BasePresenterImp implements RxjavaLearnDetailPresenter {

    RxjavaLearnDetailInteractor interactor;
    IRxjavaLearnDetail viewInterface;

    public RxjavaLearnDetailPresenterImp(IRxjavaLearnDetail viewInterface,
        RxjavaLearnDetailInteractor rxjavaLearnDetailInteractor) {
        super();
        this.viewInterface = viewInterface;
        this.interactor = rxjavaLearnDetailInteractor;
    }

    @Override
    public void getUser(String name) {
        if (!NetworkUtils.isConnected(context)) {
            viewInterface.onTip(context.getString(R.string.no_connect_net));
            return;
        }

        Observable.intervalRange(1, 10, 2, 1, TimeUnit.SECONDS)
            .doOnNext(new Consumer<Long>() {
                @Override
                public void accept(Long integer) throws Exception {
                    LogU.d("第 " + integer + " 次轮询");

                    Retrofit retrofit = new Retrofit.Builder()
                        /*.client(new OkHttpClient.Builder().addInterceptor(chain -> {
                            Response proceed = chain.proceed(chain.request());
                            LogU.d("request: " + proceed.code());
                            return proceed;
                        }).build())*/
                        .baseUrl("https://api.github.com/") // 设置 网络请求 Url
                        .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                        .build();

                    HttpApi httpApi = retrofit.create(HttpApi.class);
                    Observable<User> observable = httpApi.getUser(name);
                    observable.compose(applyIoSchedulers())
                        .subscribe(new Observer<User>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull User user) {
                                String username = user.getLogin();
                                LogU.d("username" + username);

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                }
            })
            .subscribe(new Observer<Long>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Long value) {
                   // LogU.d(" 数据" + value);

                }

                @Override
                public void onError(@NonNull Throwable e) {
                    LogU.d("对Error事件作出响应" + e.toString());
                }

                @Override
                public void onComplete() {
                    LogU.d("对Complete事件作出响应");
                }
            });

    }
}
