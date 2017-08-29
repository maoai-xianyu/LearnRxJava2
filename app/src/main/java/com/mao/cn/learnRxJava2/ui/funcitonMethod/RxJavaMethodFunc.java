package com.mao.cn.learnRxJava2.ui.funcitonMethod;

import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mao.cn.learnRxJava2.model.Locations;
import com.mao.cn.learnRxJava2.model.Student;
import com.mao.cn.learnRxJava2.model.StudentCourse;
import com.mao.cn.learnRxJava2.utils.tools.LogU;
import com.mao.cn.learnRxJava2.utils.tools.ResourceU;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:  zhangkun .
 * date:    on 2017/8/11.
 */

public class RxJavaMethodFunc {


    private static CompositeDisposable compositeDisposable;


    public static void getInstance() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    public static void clearDisposable() {
        LogU.i("  clearDisposable ");
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
    //-----------------------------------------------------------------------------线程控制：Scheduler

    /**
     * subscribeOn() 指定的是上游发送事件的线程, observeOn() 指定的是下游接收事件的线程.
     * <p>
     * 多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
     * <p>
     * 多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
     */


    // 总是启用新线程，并在新线程执行操作
    public static <T> ObservableTransformer<T, T> newThreadSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    // 处理I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
    // 行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，
    // 因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
    public static <T> ObservableTransformer<T, T> applyIoSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。
    // 这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。
    // 不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
    public static <T> ObservableTransformer<T, T> applyIoSchedulersComputation() {
        return tObservable -> tObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // FlowableTransformer 背压
    public static <T> FlowableTransformer<T, T> toMain() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * RxJava 多次切换线程
     * observeOn() 指定的是它之后的操作所在的线程。
     * 因此如果有多次切换线程的需求，只要在每个想要切换线程的位置调用一次 observeOn() 即可
     */
    public static void changeThreadMore() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(integer -> LogU.i("  integer " + integer + " after observeOn(main), current thraed is  " + Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                .doOnNext(integer -> LogU.i("  integer " + integer + " after observeOn(io), current thraed is  " + Thread.currentThread().getName()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> LogU.i(s + "  thread  " + Thread.currentThread().getName()));
    }


    /**
     * doOnSubscribe()
     * 而与 Subscriber.onStart() 相对应的，有一个方法 Observable.doOnSubscribe() 。
     * 它和 Subscriber.onStart() 同样是在 subscribe() 调用后而且在事件发送前执行，但区别在于它可以指定线程。
     * 默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；
     * 而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
     */
    public static void changeThreadMain(SimpleDraweeView svImage) {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> svImage.setVisibility(View.GONE))    // 主线程
                .subscribeOn(AndroidSchedulers.mainThread())                // 指定主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> LogU.i(" num " + s));
    }


    /**
     * 线程的转换
     */
    public static void rxjavaSchedule() {
        // --------线程的转换  create
        // 观察者
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                LogU.i("onCompleted");

            }

            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(String string) {
                LogU.i(string);
            }
        };


        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                LogU.i("onCompleted");
            }

            @Override
            public void onSubscribe(Subscription subscription) {

            }

            @Override
            public void onNext(String s) {
                LogU.i(s);
            }
        };

        //------------------- just
        Observable.just("just hello", "just world")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(LogU::i);

        //------------------- from
        List<String> strings = new ArrayList<>();
        strings.add("from hello");
        strings.add("from world");
        Observable.fromIterable(strings)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(LogU::i);

        String[] stringsArray = {"from array hello", "from array world"};
        Observable.fromArray(stringsArray)
                .compose(newThreadSchedulers())
                .subscribe(LogU::i);
    }

    //--------------------------------------------------------------------------------------转换

    /**
     * map 是一对一的输出
     *
     * @param imageView
     */
    public static void rxjava_map(ImageView imageView) {
        //map(): 事件对象的直接变换
        Observable.just("images_cover/image_name_sign.png")
                .map(ResourceU::getBitmap)
                .compose(RxJavaMethodFunc.applyIoSchedulers())
                .subscribe(imageView::setImageBitmap);


        Observable.fromArray(new String[]{"This", "is", "RxJava"})
                .map(s -> {
                    LogU.i("Transform Data toUpperCase: " + s);
                    return s.toUpperCase();
                })
                //转成List
                .toList()
                .map(stringList -> {
                    LogU.i("Transform Data Reverse List: " + stringList.toString());
                    Collections.reverse(stringList);
                    return stringList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> LogU.i("Consume Data " + s.toString()));

    }

    /**
     * flatmap 处理一对多，数据准换
     * <p>
     * 1、将传入的事件对象装换成一个Observable对象；
     * 2、这是不会直接发送这个Observable, 而是将这个Observable激活让它自己开始发送事件；
     * 3、每一个创建出来的Observable发送的事件，都被汇入同一个Observable，这个Observable负责将这些事件统一交给Subscriber的回调方法。
     * <p>
     * 数据可能会交叉
     */
    public static void rxjava_flatmap() {
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .flatMap(new Function<Student, ObservableSource<StudentCourse>>() {
                    @Override
                    public ObservableSource<StudentCourse> apply(@NonNull Student student) throws Exception {
                        return Observable.fromIterable(student.getStudentCourses());
                    }
                }).compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(studentCourse -> LogU.i(studentCourse.getCourse_name()));
    }

    /**
     * 网络请求中，如果数据有顺序问题，那么就不能使用flatMap
     */
    public static void rxjava_flatmapNew() {
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .flatMap(new Function<Student, ObservableSource<StudentCourse>>() {
                    @Override
                    public ObservableSource<StudentCourse> apply(@NonNull Student student) throws Exception {
                        return Observable.fromIterable(student.getStudentCourses()).subscribeOn(Schedulers.io());
                    }
                }).compose(RxJavaMethodFunc.applyIoSchedulers())
                .subscribe(studentCourse -> LogU.i(studentCourse.getCourse_name()));
    }

    /**
     * concatMap()解决了flatMap()的交叉问题，它能够把发射的值连续在一起
     */
    public static void rxjava_concatMap() {
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .concatMap(new Function<Student, ObservableSource<StudentCourse>>() {
                    @Override
                    public ObservableSource<StudentCourse> apply(@NonNull Student student) throws Exception {
                        return Observable.fromIterable(student.getStudentCourses());
                    }
                }).compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(studentCourse -> LogU.i(studentCourse.getCourse_name()));
    }

    /**
     * flatMapIterable()它转化的多个Observable是使用Iterable作为源数据的。
     */
    public static void rxjava_FlatMapIterableMap() {
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .flatMapIterable(new Function<Student, Iterable<StudentCourse>>() {
                    @Override
                    public Iterable<StudentCourse> apply(@NonNull Student student) throws Exception {
                        return student.getStudentCourses();
                    }
                }).compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(studentCourse -> LogU.i(studentCourse.getCourse_name()));
    }

    /**
     * 每当源Observable发射一个新的数据项（Observable）时，它将取消订阅并停止监视之前那个数据项产生的Observable，并开始监视当前发射的这一个
     * <p>
     * 在同一线程产生数据，依次打出
     */
    public static void rxjava_switchMap() {
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .switchMap(new Function<Student, ObservableSource<StudentCourse>>() {
                    @Override
                    public ObservableSource<StudentCourse> apply(@NonNull Student student) throws Exception {
                        return Observable.fromIterable(student.getStudentCourses());
                    }
                }).compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(studentCourse -> LogU.i(studentCourse.getCourse_name()));
    }

    /**
     * 每当源Observable发射一个新的数据项（Observable）时，它将取消订阅并停止监视之前那个数据项产生的Observable，并开始监视当前发射的这一个
     * <p>
     * 不同的线程，结果就是最后的数据
     */
    public static void rxjava_switchMapNew() {
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .switchMap(new Function<Student, ObservableSource<StudentCourse>>() {
                    @Override
                    public ObservableSource<StudentCourse> apply(@NonNull Student student) throws Exception {
                        return Observable.fromIterable(student.getStudentCourses()).subscribeOn(Schedulers.newThread());
                    }
                }).compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(studentCourse -> LogU.i(studentCourse.getCourse_name()));
    }


    /**
     * scan()对一个序列的数据应用一个函数，并将这个函数的结果发射出去作为下个数据应用合格函数时的第一个参数使用。
     */
    public static void rxjava_scan() {
        Observable.just(1, 2, 3, 4, 5)
                .scan((integer, integer2) -> integer + integer2)
                .compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(integer -> LogU.i(" scan " + integer));
    }

    /**
     * GroupBy()将原始Observable发射的数据按照key来拆分成一些小的Observable，然后这些小Observable分别发射其所包含的的数据，和SQL中的groupBy类似。
     */
    public static void rxjava_GroupBy() {

        // GroupedObservable是一个特殊的Observable，它基于一个分组的key，在这个例子中的key就是 StudentCourse  名字
        Observable<GroupedObservable<String, StudentCourse>> groupedObservableObservable = Observable.fromIterable(InitDataMethodFunc.initStudentCourseData()).groupBy(StudentCourse::getCourse_name);

        //concat操作符肯定也是有序的，而concat操作符是接收若干个Observables，发射数据是有序的，不会交叉。
        Observable.concat(groupedObservableObservable).subscribe(studentCourse -> LogU.i(" StudentCourse 名字 " + studentCourse.getCourse_name() + "  班级描述： " + studentCourse.getCourse_desc()));

    }

    //--------------------------------------------------------------------------------------过滤

    /**
     * filter(Func1)用来过滤观测序列中我们不想要的值，只返回满足条件的值
     */
    public static void rxjava_filter() {
        // 过滤
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .flatMap(new Function<Student, ObservableSource<StudentCourse>>() {
                    @Override
                    public ObservableSource<StudentCourse> apply(@NonNull Student student) throws Exception {
                        return Observable.fromIterable(student.getStudentCourses());

                    }
                }).filter(studentCourse -> studentCourse.getCourse_price() > 3000)
                .compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(studentCourse -> LogU.i(studentCourse.toString()));
    }


    /**
     * take(int)用一个整数n作为一个参数，从原始的序列中发射前n个元素.
     * <p>
     * takeLast(int)同样用一个整数n作为参数，只不过它发射的是观测序列中后n个元素。
     * <p>
     * takeUntil(Observable)订阅并开始发射原始Observable，同时监视我们提供的第二个Observable。
     * <p>
     * 如果第二个Observable发射了一项数据或者发射了一个终止通知，takeUntil()返回的Observable会停止发射原始Observable并终止
     * takeUntil(Func1)通过Func1中的call方法来判断是否需要终止发射数据。
     */
    public static void rxjava_take() {
        // take
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .take(2)
                .compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(student -> LogU.i("  take " + student.getStu_name()));

        // takeLast
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .takeLast(1)
                .compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(student -> LogU.i("  take " + student.getStu_name()));

        // takeUntil
        Observable<Long> observableA = Observable.interval(300, TimeUnit.MILLISECONDS);
        Observable<Long> observableB = Observable.interval(800, TimeUnit.MILLISECONDS);

        observableA.takeUntil(observableB).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                LogU.i(" takeUntil  " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {
                LogU.i(" onCompleted  ");

            }
        });

        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .takeUntil(integer -> integer > 5)
                .subscribe(integer -> LogU.i(" takeUntil( func )  " + integer));

    }


    /**
     * skip(int)让我们可以忽略Observable发射的前n项数据。
     * skipLast(int)忽略Observable发射的后n项数据。
     */
    public static void rxjava_skip() {
        // skip
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .skip(2)
                .compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(student -> LogU.i("  skip " + student.getStu_name()));

        // skipLast
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .skipLast(1)
                .compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(student -> LogU.i("  skipLast " + student.getStu_name()));
    }


    /**
     * elementAt(int)用来获取元素Observable发射的事件序列中的第n项数据，并当做唯一的数据发射出去。
     */
    public static void rxjava_elemnet() {
        Observable.fromIterable(InitDataMethodFunc.initStudentData())
                .elementAt(2)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(student -> LogU.i("  elementAt " + student.getStu_name()));

    }

    /**
     * debounce(long, TimeUnit)过滤掉了由Observable发射的速率过快的数据；
     * 如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。
     * <p>
     * 需要结合实际场景进行操作，比如防止edit  binding的例子有
     */
    public static void rxjava_Debounce() {

        /*Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).debounce(integer -> Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
                //如果%2==0，则发射数据并调用了onCompleted结束，则不会被丢弃
                if (integer % 2 == 0 && !observableEmitter.isDisposed()) {
                    observableEmitter.onNext(integer);
                    observableEmitter.onComplete();
                }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogU.i("  integer " + integer);
            }
        });*/


    }

    /**
     * throttleWithTimeout 通过时间来限流，源Observable每次发射出来一个数据后就会进行计时
     * 如果在设定好的时间结束前源Observable有新的数据发射出来，这个数据就会被丢弃，同时重新开始计时。
     * 如果每次都是在计时结束前发射数据，那么这个限流就会走向极端：只会发射最后一个数据;
     * 默认在computation调度器上执行
     */
    public static void rxjava_throttleWithTimeout() {

        /*Observable.create((Observable.OnSubscribe<Integer>) subscriber -> {
            for (int i = 0; i < 10; i++) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(i);
                }

                int sleep = 100;

                if (i % 3 == 0) {
                    sleep = 300;
                }

                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.computation())
                .throttleWithTimeout(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> LogU.i(" integer  " + integer));*/
    }


    /**
     * distinct()的过滤规则是只允许还没有发射过的数据通过，所有重复的数据项都只会发射一次。
     */
    public static void rxjava_distinct() {
        Observable.just(2, 1, 2, 2, 3, 4, 3, 4, 5, 5)
                .distinct()
                .subscribe(i -> LogU.i(i + " 数据 distinct()"));

        // 没有什么卵用
        Observable.fromIterable(InitDataMethodFunc.initStudentCourseDataSame())
                .distinct()
                .subscribe(studentCourse -> {
                    LogU.i("  studentCourse distinct() name " + studentCourse.getCourse_name() + " desc " + studentCourse.getCourse_desc());

                });

        Observable.fromIterable(InitDataMethodFunc.initStudentCourseDataSame())
                .distinct(StudentCourse::getCourse_name)
                .subscribe(studentCourse -> {
                    LogU.i("  studentCourse distinct(fun) name " + studentCourse.getCourse_name() + " desc " + studentCourse.getCourse_desc());
                });
    }


    /**
     * distinctUntilChanged() 只不过它判定的是Observable发射的当前数据项和前一个数据项是否相同。
     */
    public static void rxjava_distinctUntilChanged() {
        Observable.just(2, 1, 2, 2, 3, 4, 3, 4, 5, 5)
                .distinctUntilChanged()
                .subscribe(i -> LogU.i(i + " 数据  distinctUntilChanged() "));

        Observable.fromIterable(InitDataMethodFunc.initStudentCourseDataSame())
                .distinctUntilChanged(StudentCourse::getCourse_name)
                .subscribe(studentCourse -> {
                    LogU.i("  studentCourse distinctUntilChanged(func) name " + studentCourse.getCourse_name() + " desc " + studentCourse.getCourse_desc());
                });


    }

    /**
     * first() Observable只发送观测序列中的第一个数据项。
     */
    public static void rxjava_first() {

        Observable.fromIterable(InitDataMethodFunc.initStudentCourseDataSame())
                .first(new StudentCourse())
                .subscribe(studentCourse -> {
                    LogU.i("  studentCourse  first() name " + studentCourse.getCourse_name() + " desc " + studentCourse.getCourse_desc());
                });
    }


    /**
     * last()只发射观测序列中的最后一个数据项。
     */
    public static void rxjava_last() {

        Observable.fromIterable(InitDataMethodFunc.initStudentCourseDataSame())
                .last(new StudentCourse())
                .subscribe(studentCourse -> {
                    LogU.i("  studentCourse last()  name " + studentCourse.getCourse_name() + " desc " + studentCourse.getCourse_desc());
                });

    }


    //--------------------------------------------------------------------------------------组合

    /**
     * merge(Observable, Observable)将两个Observable发射的事件序列组合并成一个事件序列，就像是一个Observable发射的一样。
     * 你可以简单的将它理解为两个Obsrvable合并成了一个Observable，合并后的数据是无序的。
     */
    public static void rxjava_merge() {

        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> letterSequence = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(position -> letters[position.intValue()]).take(letters.length);

        Observable<Long> numberSequence = Observable.interval(500, TimeUnit.MILLISECONDS).take(5);

        Observable.merge(letterSequence, numberSequence)
                .subscribe(new Observer<Serializable>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull Serializable serializable) {
                        LogU.i(" onNext  " + serializable.toString());
/*
                        if (serializable instanceof String) {
                            LogU.i("字符串  " + serializable);
                        } else if (serializable instanceof Long) {
                            LogU.i("数字  " + serializable);
                        }*/
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        LogU.e(" onError  " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogU.i(" onCompleted  ");
                    }
                });

    }


    /**
     * startWith(T)用于在源Observable发射的数据前插入数据。使用startWith(Iterable<T>)我们还可以在源Observable发射的数据前插入Iterable。
     * 数据需要相同的类型
     */
    public static void rxjava_startwith() {

        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> letterSequence = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(position -> letters[position.intValue()]).take(letters.length);

        Observable<String> numberSequence = Observable.interval(500, TimeUnit.MILLISECONDS)
                .map(aLong -> aLong + "").take(5);

        numberSequence.startWith(letterSequence).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull String s) {
                LogU.i(" onNext  " + s);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                LogU.e(" onError  " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                LogU.i(" onCompleted  ");
            }
        });

    }


    /**
     * concat(Observable<? extends T>, Observable<? extends T>) concat(Observable<？ extends Observable<T>>)用于将多个obserbavle发射的的数据进行合并发射，
     * concat严格按照顺序发射数据，前一个Observable没发射玩是不会发射后一个Observable的数据的。它和merge、startWitch和相似，不同之处在于
     * 1、merge:合并后发射的数据是无序的；
     * 2、startWitch:只能在源Observable发射的数据前插入数据
     */
    public static void rxjava_concat() {

        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> letterSequence = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(position -> letters[position.intValue()]).take(letters.length);

        Observable<String> numberSequence = Observable.interval(500, TimeUnit.MILLISECONDS)
                .map(aLong -> aLong + "").take(5);

        Observable.concat(letterSequence, numberSequence)
                .subscribe(s -> {
                    LogU.i(" s   == " + s);
                    System.out.println(" sys  onNext  " + s);
                });


    }


    /**
     * zip(Observable, Observable, Func2)用来合并两个Observable发射的数据项，根据Func2函数生成一个新的值并发射出去。
     * 当其中一个Observable发送数据结束或者出现异常后，另一个Observable也将停在发射数据。
     * <p>
     * 如果是集合或者是数组，则需要数据一一对应，否则将缺失数据；一般用于封装成为同一个对一下的属性
     */
    public static void rxjava_zip() {

        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> letterSequence = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(position -> letters[position.intValue()]).take(letters.length);

        Observable<String> numberSequence = Observable.interval(500, TimeUnit.MILLISECONDS)
                .map(aLong -> aLong + "").take(5);

        Observable.zip(letterSequence, numberSequence, (s, s2) -> s + s2).subscribe(new Observer<Serializable>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull Serializable serializable) {
                LogU.i(" onNext  " + serializable.toString());
                System.out.println(" sys  onNext  " + serializable.toString());
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                LogU.e(" onError  " + throwable.getMessage());

            }

            @Override
            public void onComplete() {
                LogU.i(" onCompleted  ");
            }
        });


    }


    /**
     * combineLatest(Observable, Observable, Func2)用于将两个Observale最近发射的数据已经Func2函数的规则进展组合
     * <p>
     * 应用场景：表单验证
     */
    public static void rxjava_combineLatest() {

        List<String> className = InitDataMethodFunc.initClassName();
        List<Locations> locationses = InitDataMethodFunc.initLocations();

        Observable<String> classNameObservable = Observable.interval(1, TimeUnit.SECONDS)
                .map(aLong -> className.get(aLong.intValue())).take(className.size());

        Observable<Locations> locationsObservable = Observable.interval(1, TimeUnit.SECONDS)
                .map(aLong -> locationses.get(aLong.intValue())).take(locationses.size());

        Observable.combineLatest(classNameObservable, locationsObservable, (s, locations) -> "班级名： " + s + " 经纬度 " + locations.toString()).subscribe(s -> LogU.i("  S  " + s));

    }

    /**
     * Join
     * join(Observable, Func1, Func1, Func2)我们先介绍下join操作符的4个参数：
     * Observable：源Observable需要组合的Observable,这里我们姑且称之为目标Observable；
     * Func1：接收从源Observable发射来的数据，并返回一个Observable，这个Observable的声明周期决定了源Obsrvable发射出来的数据的有效期；
     * Func1：接收目标Observable发射来的数据，并返回一个Observable，这个Observable的声明周期决定了目标Obsrvable发射出来的数据的有效期；
     * Func2：接收从源Observable和目标Observable发射出来的数据，并将这两个数据组合后返回。
     */
    public static void rxjava_join() {

        List<Locations> locationses = InitDataMethodFunc.initLocations();
        //模拟的房源数据，用于测试

        //用来每秒从houses总取出一套房源并发射出去
        Observable<Locations> houseSequence =
                Observable.interval(1, TimeUnit.SECONDS)
                        .map(position -> locationses.get(position.intValue())).take(locationses.size());
        //这里的take是为了防止houses.get(position.intValue())数组越界

        //用来实现每秒发送一个新的Long型数据
        Observable<Long> tictoc = Observable.interval(1, TimeUnit.SECONDS);

        houseSequence.join(tictoc,
                house -> Observable.timer(2, TimeUnit.SECONDS),
                aLong -> Observable.timer(0, TimeUnit.SECONDS),
                (house, aLong) -> aLong + "-->" + house.getLatitude()
        ).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull String s) {
                LogU.i(" s == " + s);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                LogU.i("Error:" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                LogU.i("onCompleted");

            }
        });

    }


    /**
     * onErrorResumeNext()  服务器返回特定的错误码之后，重新创建请求时。。。。。比如 taken 过期，重新请求taken
     *
     * retryWhen()  重试机制
     *
     * switchIfEmpty()  当本地不存在，请求网络的数据
     *
     *
     * onTerminateDetach  操作符要和subscription.unsubscribe() 结合使用，因为不执行subscription.unsubscribe()的话，onTerminateDetach就不会被触发。
     */


    /**
     * 比较
     * timer()
     * interval()
     * delay()
     */
    public static void rxjava_time() {


    }


    /**
     * 注意: 调用dispose()并不会导致上游不再继续发送事件, 上游会继续发送剩余的事件.
     * <p>
     * Disposable的用处不止这些,线程的调度, 我们会发现它的重要性.
     * <p>
     * <p>
     * RxJava中已经内置了一个容器 CompositeDisposable, 每当我们得到一个Disposable时就调用CompositeDisposable.add()将它添加到容器中,
     * 在退出的时候, 调用CompositeDisposable.clear() 即可切断所有的水管.
     */
    public static void rxjava_create() {

        Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
            observableEmitter.onNext(1);
            LogU.i(" Emitter " + 1);
            observableEmitter.onNext(2);
            LogU.i(" Emitter " + 2);
            observableEmitter.onNext(3);
            LogU.i(" Emitter " + 3);
            observableEmitter.onComplete();
            LogU.i(" Emitter  onComplete");
            observableEmitter.onNext(4);
            LogU.i(" Emitter " + 4);
            observableEmitter.onNext(5);
            LogU.i(" Emitter " + 5);
        }).subscribe(new Observer<Integer>() {

            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                LogU.i(" onSubscribe ");
                mDisposable = disposable;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogU.i(" onNext " + integer);
                i++;
                if (i == 2) {
                    LogU.i(" mDisposable " + mDisposable);
                    mDisposable.dispose();
                    LogU.i(" mDisposable " + mDisposable.isDisposed());
                }

            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                LogU.i(" throwable " + throwable);

            }

            @Override
            public void onComplete() {
                LogU.i(" onComplete ");
            }
        });

    }

    /**
     * 一是从数量上进行治理, 减少发送进水缸里的事件
     * 二是从速度上进行治理, 减缓事件发送进水缸的速度
     */
    public static void rxjava_define_BackPressure() {

        // oom
        /*Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
            for (int i = 0; ; i++) {
                observableEmitter.onNext(i);
            }
        }).subscribe(integer -> {
            LogU.i("  主线程睡2秒  值比较平缓  "+integer);
        });*/

        //emitter.onNext(i)其实就相当于直接调用了Consumer中的:
        /*Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
            for (int i = 0; ; i++) {
                observableEmitter.onNext(i);
            }
        }).subscribe(integer -> {
            Thread.sleep(2000);
            LogU.i("  主线程睡2秒  值比较平缓  "+integer);
        });*/

        // 切换线程之后 oom
        /*Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
            for (int i = 0; ; i++) {
                observableEmitter.onNext(i);
            }
        }).compose(applyIoSchedulers()).subscribe(integer -> {
            Thread.sleep(2000);
            LogU.i("  主线程睡2秒  值比较平缓  "+integer);
        });*/

        // 方法一：上游速度太快，（filter 或者 sample 取样）减少数量，但是会丢失大量的事件,依旧会oom
        /*Observable<Integer> integerObservable = Observable.create(observableEmitter -> {
            for (int i = 0; ; i++) {
                observableEmitter.onNext(i);
            }
        });
        Disposable subscribe = integerObservable.subscribeOn(Schedulers.io())
                .filter(integer -> integer % 100 == 0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    LogU.i("  值比较平缓  filter  " + integer);
                });*/

        // 取样
        /*Disposable subscribe = Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
            for (int i = 0; ; i++) {
                observableEmitter.onNext(i);
                LogU.i("  subscribe 发送  " + i);
            }
        }).subscribeOn(Schedulers.io())
                .sample(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    LogU.i("  值比较平缓 sample " + integer);
                });
        compositeDisposable.add(subscribe);*/


        // 方法二：减慢上游的发射速度
        /*Disposable subscribe = Observable.create((ObservableOnSubscribe<Integer>) observableEmitter -> {
            for (int i = 0; ; i++) {
                observableEmitter.onNext(i);
                Thread.sleep(2000);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    LogU.i("  值比较平缓 sample " + integer);
                });*/


        // 无线循环的发射，及时下游不接收，也会占有内存
        Observable<Integer> sample = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                for (int i = 0; ; i++) {
                    observableEmitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io()).sample(2, TimeUnit.SECONDS);


        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("A");
                observableEmitter.onNext("B");
            }
        }).subscribeOn(Schedulers.io());


        Observable.zip(sample, stringObservable, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                return integer + "  " + s;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> LogU.i(" accept " + s), throwable -> LogU.e(" accept " + throwable));
    }

    /**
     * 背压Flowable  默认也是有个 128个事件，如果发送了多余的事件，且没有处理，就会有异常 MissingBackpressureException
     * <p>
     * BackpressureStrategy.BUFFER  扩充缓冲池
     * <p>
     * BackpressureStrategy.ERROR
     * <p>
     * BackpressureStrategy.DROP  Drop就是直接把存不下的事件丢弃
     * <p>
     * BackpressureStrategy.LATEST Latest就是只保留最新的事件
     */
    public static void rxjava_clear_flowable() {
        Flowable<Integer> integerFlowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> flowableEmitter) throws Exception {

                flowableEmitter.onNext(1);
                flowableEmitter.onNext(2);
                flowableEmitter.onNext(3);
                flowableEmitter.onNext(4);
                flowableEmitter.onComplete();
            }
        }, BackpressureStrategy.LATEST);

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {

                LogU.i(" onSubscribe ");
                subscription.request(Long.MAX_VALUE);

            }

            @Override
            public void onNext(Integer integer) {
                LogU.i(" onNext " + integer);

            }

            @Override
            public void onError(Throwable throwable) {
                LogU.i(" onError " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                LogU.i(" onNonCompleteext ");
            }
        };

        integerFlowable.subscribe(subscriber);


        Flowable.interval(1, TimeUnit.SECONDS)
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        LogU.i("  onSubscribe  ");

                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {

                        LogU.i("  onNext  " + aLong);
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        LogU.i("  onError  " + throwable.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        LogU.i("  onComplete  ");
                    }
                });


    }

    /**
     * buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，
     * 然后生成一个  Observable
     */
    public static void rxjava_buffer() {

        Observable.just(1, 2, 3, 4, 5)
                .buffer(3)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        LogU.i(" integers.size() " + integers.size());

                        for (Integer i : integers) {
                            LogU.i(" 遍历 " + i);
                        }
                        LogU.i("");
                    }
                });


    }

    /**
     * doOnNext\让订阅者在接收到数据之前干点有意思的事情
     */
    public static void rxjava_doOnNext() {

        Observable.just(1, 2, 3, 4, 5)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogU.i("  保存  "+integer);
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogU.i(" 处理  "+integer);
            }
        });


    }
}
