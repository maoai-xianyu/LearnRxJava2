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
import com.mao.cn.learnRxJava2.model.TranslationNew;
import com.mao.cn.learnRxJava2.model.User;
import com.mao.cn.learnRxJava2.ui.commons.BasePresenterImp;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaLearnDetail;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaLearnDetailPresenter;
import com.mao.cn.learnRxJava2.utils.network.NetworkUtils;
import com.mao.cn.learnRxJava2.utils.tools.LogU;

import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
    private Disposable mDisposable;
    private long count = 0;

    public RxjavaLearnDetailPresenterImp(IRxjavaLearnDetail viewInterface,
        RxjavaLearnDetailInteractor rxjavaLearnDetailInteractor) {
        super();
        this.viewInterface = viewInterface;
        this.interactor = rxjavaLearnDetailInteractor;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void requestDouble() {

        if (!NetworkUtils.isConnected(context)) {
            viewInterface.onTip(context.getString(R.string.no_connect_net));
            return;
        }

        interactor.getCall1().compose(applyIoSchedulers())
            .doOnNext(new Consumer<Translation>() {
                @Override
                public void accept(Translation translation) throws Exception {
                    LogU.d(" 第一网络请求回来");
                    translation.show();
                }
            }).observeOn(Schedulers.io())
            .flatMap(new Function<Translation, ObservableSource<TranslationNew>>() {
                @Override
                public ObservableSource<TranslationNew> apply(@NonNull Translation translation) throws Exception {
                    return interactor.getCall2();
                }
            }).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<TranslationNew>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    if (compositeDisposable != null) {
                        compositeDisposable.add(d);
                    }

                }

                @Override
                public void onNext(@NonNull TranslationNew translationNew) {
                    LogU.d(" 第二次网络请求回来，成功");
                    translationNew.show();

                }

                @Override
                public void onError(@NonNull Throwable e) {
                    LogU.e(" 第二次网络请求回来, 失败 " + e.toString());
                }

                @Override
                public void onComplete() {

                }
            });

    }

    @Override
    public void runIntraval(String name) {
        if (!NetworkUtils.isConnected(context)) {
            viewInterface.onTip(context.getString(R.string.no_connect_net));
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
            .build();

        // b. 创建 网络请求接口 的实例
        HttpApi request = retrofit.create(HttpApi.class);

        // c. 采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = request.getCall();

        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {

                return objectObservable.delay(5000, TimeUnit.MILLISECONDS);
            }
        }).compose(applyIoSchedulers()).subscribe(new Observer<Translation>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                if (compositeDisposable != null) {
                    compositeDisposable.add(d);
                }

            }

            @Override
            public void onNext(@NonNull Translation user) {
                user.show();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogU.e(e.toString());

            }

            @Override
            public void onComplete() {

            }
        });

        /*HttpApi httpApiHuihub = getHttpApiHuihub();

        Observable<User> user = httpApiHuihub.getUser(name);
        user.repeat()
            .flatMap(new Function<User, ObservableSource<User>>() {
                @Override
                public ObservableSource<User> apply(@NonNull User user) throws Exception {
                    return Observable.just(user).delay(2000, TimeUnit.MILLISECONDS);
                }
            })
            .compose(applyIoSchedulers())
            .subscribe(new Observer<User>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    if (compositeDisposable != null) {
                        compositeDisposable.add(d);
                    }

                }

                @Override
                public void onNext(@NonNull User user) {
                    LogU.d(" user " + user.getLogin());
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });*/
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

                    HttpApi httpApi = getHttpApiHuihub();
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
                    LogU.d(" 数据" + value);

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

    private HttpApi getHttpApiHuihub() {
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

        return retrofit.create(HttpApi.class);
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
    public void getRepeatWhen() {
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
    public void requestDataByNet() {
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
            .take(20)//设置截取前20次
            .doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(Disposable disposable) throws Exception {
                    mDisposable = disposable;
                }
            })
            .flatMap(new Function<Long, ObservableSource<User>>() {
                @Override
                public ObservableSource<User> apply(@NonNull Long aLong) throws Exception {
                    count = aLong;
                    HttpApi httpApi = getHttpApiHuihub();
                    Observable<User> observable = httpApi.getUser("maoai-xianyu");
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
                    return observable;
                }
            })
            .compose(applyIoSchedulers())
            .subscribe(new Observer<User>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    if (compositeDisposable != null) {
                        compositeDisposable.add(d);
                    }
                }

                @Override
                public void onNext(@NonNull User user) {
                    if (count == 13) {//模拟获取到该字段数据
                        mDisposable.dispose();
                        LogU.d(" 取消 count 不发射");
                    }
                    LogU.d(" onNext count " + count + " user" + user.getLogin());
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    LogU.e("eonError" + e.toString());

                }

                @Override
                public void onComplete() {
                    LogU.e("onComplete");
                }

            });

    }

    @Override
    public void zip() {

        // 创建第1个被观察者
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                LogU.d("被观察者1发送了事件1");
                emitter.onNext(1);
                // 为了方便展示效果，所以在发送事件后加入2s的延迟

                LogU.d("被观察者1发送了事件2");
                emitter.onNext(2);

                LogU.d("被观察者1发送了事件3");
                emitter.onNext(3);

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()); // 设置被观察者1在工作线程1中工作

        // 创建第2个被观察者
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LogU.d("被观察者2发送了事件A");
                emitter.onNext("A");

                LogU.d("被观察者2发送了事件B");
                emitter.onNext("B");

                LogU.d("被观察者2发送了事件C");
                emitter.onNext("C");

                LogU.d("被观察者2发送了事件D");
                emitter.onNext("D");

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());// 设置被观察者2在工作线程2中工作
        // 假设不作线程控制，则该两个被观察者会在同一个线程中工作，即发送事件存在先后顺序，而不是同时发送

        // 使用zip变换操作符进行事件合并
        // 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String string) throws Exception {
                return integer + string;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (compositeDisposable != null) {
                    compositeDisposable.add(d);
                }
                LogU.d("onSubscribe");
            }

            @Override
            public void onNext(String value) {
                LogU.d("最终接收到的事件 =  " + value);
            }

            @Override
            public void onError(Throwable e) {
                LogU.d("onError" + e.toString());
            }

            @Override
            public void onComplete() {
                LogU.d("onComplete");
            }
        });
    }

    @Override
    public void delayTimer() {

    }

    @Override
    public void doxx() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Throwable("发生错误了"));
            }
        })// 1. 当Observable每发送1次数据事件就会调用1次
            .doOnEach(new Consumer<Notification<Integer>>() {
                @Override
                public void accept(Notification<Integer> integerNotification) throws Exception {
                    LogU.d("doOnEach: " + integerNotification.getValue());
                }
            })
            // 2. 执行Next事件前调用
            .doOnNext(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) throws Exception {
                    LogU.d("doOnNext: " + integer);
                }
            })
            // 3. 执行Next事件后调用
            .doAfterNext(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) throws Exception {
                    LogU.d("doAfterNext: " + integer);
                }
            })
            // 4. Observable正常发送事件完毕后调用
            .doOnComplete(new Action() {
                @Override
                public void run() throws Exception {
                    LogU.d("doOnComplete: ");
                }
            })
            // 5. Observable发送错误事件时调用
            .doOnError(new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    LogU.e("doOnError: " + throwable.getMessage());
                }
            })
            // 6. 观察者订阅时调用
            .doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(@NonNull Disposable disposable) throws Exception {
                    LogU.d("doOnSubscribe: ");
                }
            })
            // 7. Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
            .doAfterTerminate(new Action() {
                @Override
                public void run() throws Exception {
                    LogU.e("doAfterTerminate: ");
                }
            })
            // 8. 最后执行
            .doFinally(new Action() {
                @Override
                public void run() throws Exception {
                    LogU.e("doFinally: ");
                }
            })
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer value) {
                    LogU.d("接收到了事件" + value);
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
    public void onErrorReturn() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(@NonNull Throwable throwable) throws Exception {
                // 捕捉错误异常
                LogU.d("在onErrorReturn处理了错误: " + throwable.toString());

                return 666;
                // 发生错误事件后，发送一个"666"事件，最终正常结束
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                LogU.d("接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                LogU.d("对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogU.d("对Complete事件作出响应");
            }
        });
    }

    @Override
    public void onErrorResumeNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                //e.onError(new Throwable("发生错误了"));
            }
        })/*.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(@NonNull Throwable throwable) {
                // 1. 捕捉错误异常
                LogU.e("在onErrorReturn处理了错误: " + throwable.toString());

                // 2. 发生错误事件后，发送一个新的被观察者 & 发送事件序列
                return Observable.just(11, 22);
            }
        })*/
            .onExceptionResumeNext(new Observable<Integer>() {
                @Override
                protected void subscribeActual(Observer<? super Integer> observer) {
                    LogU.e("发生异常后，发送其他值，onExceptionResumeNext 只能捕获Exception 异常，如果是Throwable,直接到观察者的onError");
                    observer.onNext(11);
                    observer.onNext(22);
                    observer.onComplete();
                }
            }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                LogU.d("接收到了事件" + value);
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
    public void retry() {
        //-- 1. retry（） -->
        // 作用：出现错误时，让被观察者重新发送数据
        // 注：若一直错误，则一直重新发送
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        }).retry() // 遇到错误时，让被观察者重新发射数据（若一直错误，则一直重新发送
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer value) {
                    LogU.d("接收到了事件" + value);
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

        //2. retry（long time） -->
        // 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
        // 参数 = 重试次数
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        }).retry(3) // 设置重试次数 = 3次
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer value) {
                    LogU.d("接收到了事件" + value);
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

        //3. retry（Predicate predicate）-- >
        // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
        // 参数 = 判断逻辑
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
            // 拦截错误后，判断是否需要重新发送请求
            .retry(new Predicate<Throwable>() {
                @Override
                public boolean test(@NonNull Throwable throwable) throws Exception {
                    // 捕获异常
                    LogU.e("retry错误: " + throwable.toString());

                    //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                    //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                    return false;
                }
            })
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer value) {
                    LogU.d("接收到了事件" + value);
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

        //4. retry（new BiPredicate<Integer, Throwable>）-- >
        // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
        // 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })// 拦截错误后，判断是否需要重新发送请求
            .retry(new BiPredicate<Integer, Throwable>() {
                @Override
                public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) throws Exception {
                    // 捕获异常
                    LogU.e("异常错误 =  " + throwable.toString());

                    // 获取当前重试次数
                    LogU.e("当前重试次数 =  " + integer);

                    //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                    //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                    return false;
                }
            })
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer value) {
                    LogU.d("接收到了事件" + value);
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

        //<--5. retry（long time, Predicate predicate）-- >
        // 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
        // 参数 = 设置重试次数 & 判断逻辑
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
            // 拦截错误后，判断是否需要重新发送请求
            .retry(3, new Predicate<Throwable>() {
                @Override
                public boolean test(@NonNull Throwable throwable) throws Exception {
                    // 捕获异常
                    LogU.e("retry错误: " + throwable.toString());

                    //返回false = 不重新重新发送数据 & 调用观察者的onError（）结束
                    //返回true = 重新发送请求（最多重新发送3次）
                    return true;
                }
            })
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer value) {
                    LogU.d("接收到了事件" + value);
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
    public void retryWhen() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
            // 遇到error事件才会回调
            .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {

                @Override
                public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                    // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                    // 返回Observable<?> = 新的被观察者 Observable（任意类型）
                    // 此处有两种情况：
                    // 1. 若 新的被观察者 Observable发送的事件 = Error事件，那么 原始Observable则不重新发送事件：
                    // 2. 若 新的被观察者 Observable发送的事件 = Next事件 ，那么原始的Observable则重新发送事件：
                    return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {

                            // 1. 若返回的Observable发送的事件 = Error事件，则原始的Observable不重新发送事件
                            // 该异常错误信息可在观察者中的onError（）中获得
                            return Observable.error(new Throwable("retryWhen终止啦"));

                            // 2. 若返回的Observable发送的事件 = Next事件，则原始的Observable重新发送事件（若持续遇到错误，则持续重试）
                            // return Observable.just(1);
                        }
                    });

                }
            })
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer value) {
                    LogU.d("接收到了事件" + value);
                }

                @Override
                public void onError(Throwable e) {
                    LogU.e("对Error事件作出响应" + e.toString());
                    // 获取异常错误信息
                }

                @Override
                public void onComplete() {
                    LogU.d("对Complete事件作出响应");
                }
            });
    }

    @Override
    public void repeatWhen() {
        Observable.just(1, 2, 4).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，这里我们使用的是flatMap操作符接收上游的数据
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable
                // 此处有2种情况：
                // 1. 若新被观察者（Observable）返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable
                // 2. 若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 情况1：若新被观察者（Observable）返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable
                        return Observable.empty();
                        // Observable.empty() = 发送Complete事件，但不会回调观察者的onComplete（）

                         //return Observable.error(new Throwable("不再重新订阅事件"));
                        // 返回Error事件 = 回调onError（）事件，并接收传过去的错误信息。

                        // 情况2：若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
                        // return Observable.just(1);
                        // 仅仅是作为1个触发重新订阅被观察者的通知，发送的是什么数据并不重要，只要不是Complete（） /  Error（）事件
                    }
                });

            }
        })
            .subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    LogU.d("开始采用subscribe连接");
                }

                @Override
                public void onNext(Integer value) {
                    LogU.d("接收到了事件" + value);
                }

                @Override
                public void onError(Throwable e) {
                    LogU.e("对Error事件作出响应：" + e.toString());
                }

                @Override
                public void onComplete() {
                    LogU.d("对Complete事件作出响应");
                }

            });
    }

    @Override
    public void clear() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            LogU.i("  compositeDisposable clear");
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
