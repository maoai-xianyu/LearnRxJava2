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
import com.mao.cn.learnRxJava2.model.Translation;
import com.mao.cn.learnRxJava2.model.User;
import com.mao.cn.learnRxJava2.ui.commons.BasePresenterImp;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaLearnDetail;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaLearnDetailPresenter;
import com.mao.cn.learnRxJava2.utils.network.NetworkUtils;
import com.mao.cn.learnRxJava2.utils.tools.LogU;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
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

    private int i = 0;

    private CompositeDisposable compositeDisposable;

    public RxjavaLearnDetailPresenterImp(IRxjavaLearnDetail viewInterface,
        RxjavaLearnDetailInteractor rxjavaLearnDetailInteractor) {
        super();
        this.viewInterface = viewInterface;
        this.interactor = rxjavaLearnDetailInteractor;
        this.compositeDisposable = new CompositeDisposable();
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
                        .client(new OkHttpClient.Builder().addInterceptor(chain -> {
                            Response proceed = chain.proceed(chain.request());
                            LogU.d("request: " + proceed.code());
                            return proceed;
                        }).build())
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
                                if (compositeDisposable != null) {
                                    compositeDisposable.add(d);
                                }

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
                    if (compositeDisposable != null) {
                        compositeDisposable.add(d);
                    }

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

    @Override
    public void getIntraval() {
        if (!NetworkUtils.isConnected(context)) {
            viewInterface.onTip(context.getString(R.string.no_connect_net));
            return;
        }

        /*
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         **/
        Observable.interval(2, 1, TimeUnit.SECONDS)
            // 参数说明：
            // 参数1 = 第1次延迟时间；
            // 参数2 = 间隔时间数字；
            // 参数3 = 时间单位；
            // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

            /*
             * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
             * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
             **/
            .doOnNext(new Consumer<Long>() {
                @Override
                public void accept(Long integer) throws Exception {
                    LogU.d("第 " + integer + " 次轮询");

                    /*
                     * 步骤3：通过Retrofit发送网络请求
                     **/
                    // a. 创建Retrofit对象
                    Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                        .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                        .build();

                    // b. 创建 网络请求接口 的实例
                    HttpApi request = retrofit.create(HttpApi.class);

                    // c. 采用Observable<...>形式 对 网络请求 进行封装
                    Observable<Translation> observable = request.getCall();
                    // d. 通过线程切换发送网络请求
                    observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                        .subscribe(new Observer<Translation>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                if (compositeDisposable != null) {
                                    compositeDisposable.add(d);
                                }
                            }

                            @Override
                            public void onNext(Translation result) {
                                // e.接收服务器返回的数据
                                result.show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogU.e("请求失败");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                }
            }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (compositeDisposable != null) {
                    compositeDisposable.add(d);
                }

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {
                LogU.e("对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogU.d("对Complete事件作出响应");
            }
        });
    }

    @Override
    public void getT() {
        if (!NetworkUtils.isConnected(context)) {
            viewInterface.onTip(context.getString(R.string.no_connect_net));
            return;
        }

        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
            .build();

        // 步骤2：创建 网络请求接口 的实例
        HttpApi request = retrofit.create(HttpApi.class);

        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = request.getCall();

        // 步骤4：发送网络请求 & 通过repeatWhen（）进行轮询
        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                // 此处有2种情况：
                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                        if (i > 3) {
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                    }
                });

            }
        }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
            .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
            .subscribe(new Observer<Translation>() {
                @Override
                public void onSubscribe(Disposable d) {
                    if (compositeDisposable != null) {
                        compositeDisposable.add(d);
                    }
                }

                @Override
                public void onNext(Translation result) {
                    // e.接收服务器返回的数据
                    result.show();
                    i++;
                }

                @Override
                public void onError(Throwable e) {
                    // 获取轮询结束信息
                    LogU.e(e.toString());
                }

                @Override
                public void onComplete() {

                }
            });
    }

    @Override
    public void clear() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()){
            LogU.i("  compositeDisposable clear");
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
