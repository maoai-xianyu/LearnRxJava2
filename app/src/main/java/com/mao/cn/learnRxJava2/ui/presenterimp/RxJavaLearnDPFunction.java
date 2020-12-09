package com.mao.cn.learnRxJava2.ui.presenterimp;

import com.mao.cn.learnRxJava2.utils.tools.LogU;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhangkun
 * @time 2020/12/8 7:27 PM
 * @Description
 */
public class RxJavaLearnDPFunction {

    private RxJavaLearnDPFunction() {

    }

    private static volatile RxJavaLearnDPFunction instance = null;

    public static RxJavaLearnDPFunction getInstance() {
        if (instance == null) {
            synchronized (RxJavaLearnDPFunction.class) {
                if (instance == null) {
                    instance = new RxJavaLearnDPFunction();
                }
            }
        }
        return instance;
    }



    public void throttleFirstOrst() {
        // 不推荐在Rxjava中使用  Thread.sleep(500); 会有异常，一般这个用于防止重复点击
        //- 在某段时间内，只发送该段时间内第1次事件 ->>
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 隔段事件发送时间
                e.onNext(1);
                Thread.sleep(500);

                e.onNext(2);
                Thread.sleep(400);

                e.onNext(3);
                Thread.sleep(300);

                e.onNext(4);
                Thread.sleep(300);

                e.onNext(5);
                Thread.sleep(300);

                e.onNext(6);
                Thread.sleep(400);

                e.onNext(7);
                Thread.sleep(300);
                e.onNext(8);

                Thread.sleep(300);
                e.onNext(9);

                Thread.sleep(300);
                e.onComplete();
            }
        }).throttleFirst(1, TimeUnit.SECONDS)//每1秒中采用数据
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
                    LogU.d("对Error事件作出响应");
                }

                @Override
                public void onComplete() {
                    LogU.d("对Complete事件作出响应");
                }
            });

        //- 在某段时间内，只发送该段时间内最后1次事件 ->>
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 隔段事件发送时间
                e.onNext(1);
                Thread.sleep(500);

                e.onNext(2);
                Thread.sleep(400);

                e.onNext(3);
                Thread.sleep(300);

                e.onNext(4);
                Thread.sleep(300);

                e.onNext(5);
                Thread.sleep(300);

                e.onNext(6);
                Thread.sleep(400);

                e.onNext(7);
                Thread.sleep(300);
                e.onNext(8);

                Thread.sleep(300);
                e.onNext(9);

                Thread.sleep(300);
                e.onComplete();
            }
        }).throttleLast(1, TimeUnit.SECONDS)//每1秒中采用数据
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
                    LogU.d("对Error事件作出响应");
                }

                @Override
                public void onComplete() {
                    LogU.d("对Complete事件作出响应");
                }
            });


    }


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

    public void groupBy() {
        Observable<GroupedObservable<Integer, Integer>> observable = Observable.concat(
            Observable.range(1, 4), Observable.range(1, 6)).groupBy(integer -> integer);
        Observable.concat(observable).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogU.d("integer" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //Observable.range(2, 5).scan((i1, i2) -> i1 * i2).subscribe(i -> LogU.d((i + " ")));
        Observable.range(2, 5).scan(3, (integer, integer2) -> {
            LogU.d("integer " + integer);
            LogU.d("integer2 " + integer2);
            return integer * integer2;
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogU.d(integer + " ");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.range(1, 10).buffer(3)
            .subscribe(new Observer<List<Integer>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull List<Integer> integers) {
                    LogU.d("buffer" + Arrays.toString(integers.toArray()));
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });

        Observable.range(1, 10).window(3)
            .subscribe(new Observer<Observable<Integer>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Observable<Integer> integerObservable) {
                    integerObservable.subscribe(new Observer<Integer>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Integer integer) {
                            LogU.d("window  " + integerObservable.hashCode() + " integer " + integer);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
    }


    public void threadChange() {
        // 步骤1：创建被观察者 Observable & 发送事件
        // 在主线程创建被观察者 Observable 对象
        // 所以生产事件的线程是：主线程
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                LogU.d(" 被观察者 Observable的工作线程是: " + Thread.currentThread().getName());
                // 打印验证
                emitter.onNext(1);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .doOnNext(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) throws Exception {
                    LogU.d(" 观察者第一次 Observable的工作线程是: " + Thread.currentThread().getName());
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Integer>() {

                @Override
                public void onSubscribe(Disposable d) {
                    LogU.d("开始采用subscribe连接");
                    // 打印验证
                }

                @Override
                public void onNext(Integer value) {
                    LogU.d("对Next事件" + value + "作出响应");
                    LogU.d(" 最后的观察者 Observer的工作线程是: " + Thread.currentThread().getName());
                }

                @Override
                public void onError(Throwable e) {
                    LogU.d("对Error事件作出响应");
                }

                @Override
                public void onComplete() {
                    LogU.d("对Complete事件作出响应 执行的线程" + Thread.currentThread().getName());
                }
            });


    }


}
