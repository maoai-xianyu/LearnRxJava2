// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/08/2017 18:35 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding2.view.RxView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.component.AppComponent;
import com.mao.cn.learnRxJava2.component.DaggerRxJavaLearnComponent;
import com.mao.cn.learnRxJava2.contants.ValueMaps;
import com.mao.cn.learnRxJava2.model.Student;
import com.mao.cn.learnRxJava2.model.StudentCourse;
import com.mao.cn.learnRxJava2.modules.RxJavaLearnModule;
import com.mao.cn.learnRxJava2.ui.adapter.RxJavaLearnAdapter;
import com.mao.cn.learnRxJava2.ui.commons.BaseActivity;
import com.mao.cn.learnRxJava2.ui.features.IRxJavaLearn;
import com.mao.cn.learnRxJava2.ui.funcitonMethod.InitDataMethodFunc;
import com.mao.cn.learnRxJava2.ui.funcitonMethod.RxJavaMethodFunc;
import com.mao.cn.learnRxJava2.ui.presenter.RxJavaLearnPresenter;
import com.mao.cn.learnRxJava2.utils.tools.LogU;
import com.mao.cn.learnRxJava2.utils.tools.ResourceU;
import com.mao.cn.learnRxJava2.utils.tools.StringU;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class RxJavaLearnActivity extends BaseActivity implements IRxJavaLearn {

    @Inject
    RxJavaLearnPresenter presenter;

    @BindView(R.id.ib_header_back)
    ImageButton ibHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rvData)
    RecyclerView rvData;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.sv_image)
    SimpleDraweeView svImage;
    @BindView(R.id.iv_show)
    ImageView ivShow;

    private RxJavaLearnAdapter adapter;
    private List<String> strings;

    private List<Student> students;

    @Override
    public void getArgs(Bundle bundle) {

    }

    @Override
    public int setView() {
        return R.layout.aty_rx_java_learn;
    }

    @Override
    public void initView() {
        ibHeaderBack.setVisibility(View.VISIBLE);
        tvHeaderTitle.setVisibility(View.VISIBLE);
        tvHeaderTitle.setText(getString(R.string.header_rxjava));

        strings = new ArrayList<>();

        strings.add("rxjava_start");
        strings.add("rxjava_create");
        strings.add("------------------");
        strings.add("rxjava_thread");
        strings.add("rxjava_threadM");
        strings.add("------------------");
        strings.add("rxjava_map");
        strings.add("rxjava_flatmap");
        strings.add("rxjava_flatmapNew");
        strings.add("rxjava_concatMap");
        strings.add("rxjava_FlatMapIterable");
        strings.add("rxjava_SwitchMap_one");
        strings.add("rxjava_SwitchMap_new");
        strings.add("rxjava_Scan_reduce");
        strings.add("rxjava_GroupBy");
        strings.add("------------------");
        strings.add("rxjava_define_BackPressure");
        strings.add("rxjava_clear_clearDisposable");
        strings.add("rxjava_clear_flowable");
        strings.add("------------------");
        strings.add("rxjava_rxBinding");
        strings.add("------------------");
        strings.add("rxjava_filter");
        strings.add("rxjava_take");
        strings.add("rxjava_skip");
        strings.add("rxjava_elementAt");
        strings.add("rxjava_Debounce");
        strings.add("rxjava_throttleWithTimeout");
        strings.add("rxjava_distinct");
        strings.add("rxjava_distinctUntilChanged");
        strings.add("rxjava_first");
        strings.add("rxjava_last");
        strings.add("------------------");
        strings.add("rxjava_merge");
        strings.add("rxjava_startwith");
        strings.add("rxjava_concat");
        strings.add("rxjava_zip");
        strings.add("rxjava_combineLatest");
        strings.add("rxjava_join");
        strings.add("------------------");
        strings.add("rxjava_buffer");
        strings.add("rxjava_doOnNext");
        strings.add("rxjava_single_and_debounce");
        strings.add("rxjava_defer");
        strings.add("rxjava_windowFun");

        LinearLayoutManager linearLayoutCourse = new LinearLayoutManager(context);
        linearLayoutCourse.setOrientation(LinearLayoutManager.VERTICAL);
        rvData.setLayoutManager(linearLayoutCourse);
        adapter = new RxJavaLearnAdapter(this);
        adapter.addStringList(strings);
        rvData.setAdapter(adapter);
        initDataStudent();
    }

    private void initDataStudent() {
        students = InitDataMethodFunc.initStudentData();
    }


    @Override
    public void setListener() {
        RxView.clicks(ibHeaderBack).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
                .MILLISECONDS).subscribe(aVoid -> {
            finish();
        }, throwable -> LogU.e(throwable.getMessage()));

        adapter.addListener(str -> {
            switch (str) {
                case "rxjava_start":
                    rxjava_startFun();
                    break;
                case "rxjava_create":
                    rxjava_createFun();
                    break;
                case "rxjava_map":
                    rxjava_mapFun();
                    break;
                case "rxjava_flatmap":
                    rxjava_flatmapFun();
                    break;
                case "rxjava_flatmapNew":
                    rxjava_flatmapFunNew();
                    break;
                case "rxjava_concatMap":
                    rxjava_concatMapFun();
                    break;
                case "rxjava_FlatMapIterable":
                    rxjava_FlatMapIterableFun();
                    break;
                case "rxjava_SwitchMap_one":
                    rxjava_SwitchMapFun();
                    break;
                case "rxjava_SwitchMap_new":
                    rxjava_SwitchMapFunNew();
                    break;
                case "rxjava_Scan_reduce":
                    rxjava_Scan_reduceFun();
                    break;
                case "rxjava_GroupBy":
                    rxjava_GroupByFun();
                    break;
                case "rxjava_thread":
                    rxjava_threadFun();
                    break;
                case "rxjava_threadM":
                    rxjava_threadMFun();
                    break;
                case "rxjava_rxBinding":
                    rxjava_rxBindingFun();
                    break;
                case "rxjava_filter":
                    rxjava_filterFun();
                    break;
                case "rxjava_take":
                    rxjava_takeFun();
                    break;
                case "rxjava_skip":
                    rxjava_skipFun();
                    break;
                case "rxjava_elementAt":
                    rxjava_elemnetFun();
                    break;
                case "rxjava_Debounce":
                    rxjava_DebounceFun();
                    break;
                case "rxjava_throttleWithTimeout":
                    rxjava_throttleWithTimeoutFun();
                    break;
                case "rxjava_distinct":
                    rxjava_distinctFun();
                    break;
                case "rxjava_distinctUntilChanged":
                    rxjava_distinctUntilChangedFun();
                    break;
                case "rxjava_first":
                    rxjava_firstFun();
                    break;
                case "rxjava_last":
                    rxjava_lastFun();
                    break;
                case "rxjava_merge":
                    rxjava_mergeFun();
                    break;
                case "rxjava_startwith":
                    rxjava_startwithFun();
                    break;
                case "rxjava_concat":
                    rxjava_concatFun();
                    break;
                case "rxjava_zip":
                    rxjava_zipFun();
                    break;
                case "rxjava_combineLatest":
                    rxjava_combineLatestFun();
                    break;
                case "rxjava_join":
                    rxjava_joinFun();
                    break;
                case "rxjava_define_BackPressure":
                    rxjava_define_BackPressureFun();
                    break;
                case "rxjava_clear_clearDisposable":
                    rxjava_clear_clearDisposableFun();
                    break;
                case "rxjava_clear_flowable":
                    rxjava_clear_flowableFun();
                    break;
                case "rxjava_buffer":
                    rxjava_bufferFun();
                    break;
                case "rxjava_doOnNext":
                    rxjava_doOnNextFun();
                    break;
                case "rxjava_single_and_debounce":
                    rxjava_single_and_debounceFun();
                    break;
                case "rxjava_defer":
                    rxjava_deferFun();
                    break;
                case "rxjava_windowFun":
                    rxjava_windowFun();
                    break;
                default:
                    break;
            }
        });
    }

    private void rxjava_windowFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_window log"));
        RxJavaMethodFunc.rxjava_window();

    }

    private void rxjava_deferFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_defer log"));
        RxJavaMethodFunc.rxjava_defer();

    }

    private void rxjava_single_and_debounceFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_single_and_debounce log"));
        RxJavaMethodFunc.rxjava_single_and_debounce();

    }

    private void rxjava_doOnNextFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_doOnNextFun log"));
        RxJavaMethodFunc.rxjava_doOnNext();
    }

    private void rxjava_bufferFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_bufferFun log"));
        RxJavaMethodFunc.rxjava_buffer();
    }

    private void rxjava_clear_flowableFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_clear_flowable log"));
        RxJavaMethodFunc.rxjava_clear_flowable();
    }

    private void rxjava_clear_clearDisposableFun() {
        tvShow.setText(String.valueOf("as 查看 clearDisposable log"));
        RxJavaMethodFunc.clearDisposable();
    }

    private void rxjava_define_BackPressureFun() {
        RxJavaMethodFunc.getInstance();
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_BackPressure  解释 log"));
        RxJavaMethodFunc.rxjava_define_BackPressure();
    }

    private void rxjava_createFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_createFun  disposable log"));
        RxJavaMethodFunc.rxjava_create();
    }

    private void rxjava_joinFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_joinFun log"));
        RxJavaMethodFunc.rxjava_join();
    }

    private void rxjava_combineLatestFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_combineLatest log"));
        RxJavaMethodFunc.rxjava_combineLatest();
    }

    private void rxjava_zipFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_zip log"));
        RxJavaMethodFunc.rxjava_zip();
    }

    private void rxjava_concatFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_concat log"));
        RxJavaMethodFunc.rxjava_concat();

    }

    private void rxjava_startwithFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_merge log"));
        RxJavaMethodFunc.rxjava_startwith();
    }

    private void rxjava_mergeFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_merge log"));
        RxJavaMethodFunc.rxjava_merge();
    }

    private void rxjava_lastFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_last log"));
        RxJavaMethodFunc.rxjava_last();
    }

    private void rxjava_firstFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_first log"));
        RxJavaMethodFunc.rxjava_first();
    }

    private void rxjava_distinctUntilChangedFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_distinctUntilChanged log"));
        RxJavaMethodFunc.rxjava_distinctUntilChanged();

    }

    private void rxjava_distinctFun() {

        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 rxjava_distinct log"));
        RxJavaMethodFunc.rxjava_distinct();
    }

    private void rxjava_throttleWithTimeoutFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 throttleWithTimeout log"));
        RxJavaMethodFunc.rxjava_throttleWithTimeout();
    }

    private void rxjava_DebounceFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 Debounce log"));
        RxJavaMethodFunc.rxjava_Debounce();
    }

    private void rxjava_elemnetFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 lementAt log"));
        RxJavaMethodFunc.rxjava_elemnet();
    }

    private void rxjava_rxBindingFun() {
        startActivity(RxjavaLearnRxBingdingActivity.class);
    }

    private void rxjava_skipFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 skip log"));
        RxJavaMethodFunc.rxjava_skip();
    }

    private void rxjava_takeFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 take log"));
        RxJavaMethodFunc.rxjava_take();
    }

    private void rxjava_filterFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 filter log"));
        RxJavaMethodFunc.rxjava_filter();
    }

    private void rxjava_threadMFun() {
        tvShow.setText(String.valueOf("rxjava 主线程 doOnSubscribe"));
        RxJavaMethodFunc.changeThreadMain(svImage);
    }

    private void rxjava_threadFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("rxjava 多次切换 线程"));
        RxJavaMethodFunc.changeThreadMore();
    }


    private void rxjava_GroupByFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 GroupBy log"));
        RxJavaMethodFunc.rxjava_GroupBy();
    }

    private void rxjava_Scan_reduceFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 Scan log"));
        RxJavaMethodFunc.rxjava_scan_reduce();
    }

    private void rxjava_SwitchMapFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 SwitchMap 同一个数据链 log"));
        RxJavaMethodFunc.rxjava_switchMap();
    }

    private void rxjava_SwitchMapFunNew() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 concatMap new Thread log"));
        RxJavaMethodFunc.rxjava_switchMapNew();
    }

    private void rxjava_FlatMapIterableFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 FlatMapIterable  log"));
        RxJavaMethodFunc.rxjava_FlatMapIterableMap();
    }

    private void rxjava_concatMapFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 concatMap 依次 log"));
        RxJavaMethodFunc.rxjava_concatMap();
    }

    private void rxjava_flatmapFun() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 flatmap log"));
        RxJavaMethodFunc.rxjava_flatmap();
    }

    private void rxjava_flatmapFunNew() {
        svImage.setVisibility(View.VISIBLE);
        tvShow.setText(String.valueOf("as 查看 flatmap 数据交叉 log"));
        RxJavaMethodFunc.rxjava_flatmapNew();
    }

    private void rxjava_mapFun() {
        svImage.setVisibility(View.VISIBLE);
        RxJavaMethodFunc.rxjava_map(ivShow);
    }

    private void rxjava_startFun() {
        String[] list = ResourceU.getAssetsFileNames("images_cover");
        List<File> files = new ArrayList<>();
        if (list != null) {
            for (String aList : list) {
                files.add(new File(aList));
                LogU.i("测试名：  " + aList);
            }
        }

       /* new Thread() {
            @Override
            public void run() {
                super.run();
                for (File file : files) {
                    if (file.getName().endsWith(".jpg")) {
                        final Bitmap bitmap = ResourceU.getBitmap("images_cover/"+file.getName());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivShow.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }
        }.start();*/

        Observable.fromIterable(files).filter(file -> StringU.endsWith(file.getName(), ".jpg"))
                .map(file -> ResourceU.getBitmap("images_cover/" + file.getName()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(bitmap -> ivShow.setImageBitmap(bitmap));

        RxJavaMethodFunc.rxjavaSchedule();


        // 过滤
        Observable.fromIterable(students)
                .flatMap(new Function<Student, ObservableSource<StudentCourse>>() {
                    @Override
                    public ObservableSource<StudentCourse> apply(@NonNull Student student) throws Exception {
                        return Observable.fromIterable(student.getStudentCourses());
                    }
                }).filter(studentCourse -> studentCourse.getCourse_price() > 3000)
                .compose(RxJavaMethodFunc.newThreadSchedulers())
                .subscribe(studentCourse -> LogU.i(studentCourse.toString()));

        LogU.i("-------------------------------------------------");
        // create  创建一个上游的 observable
        Observable<Integer> observable = Observable.create(observableEmitter -> {
            observableEmitter.onNext(1);
            observableEmitter.onNext(2);
            observableEmitter.onNext(3);
            observableEmitter.onNext(4);
            observableEmitter.onComplete();
            observableEmitter.onNext(5);
        });

        // 创建一个下游的 Observer
        /**
         * nComplete和onError必须唯一并且互斥, 即不能发多个onComplete, 也不能发多个onError,
         * 也不能先发一个onComplete, 然后再发一个onError, 反之亦然
         */
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                LogU.i("  disposable  onSubscribe " + disposable);

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogU.i("  onNext " + integer);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                LogU.i("  throwable ");
            }

            @Override
            public void onComplete() {
                LogU.i("  onComplete ");

            }
        };
        observable.subscribe(observer);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerRxJavaLearnComponent.builder()
                .appComponent(appComponent)
                .rxJavaLearnModule(new RxJavaLearnModule(this))
                .build().inject(this);
    }

    private void function() {
        Single<String> single = Single.just("1");
//        single.onErrorResumeNext();
//        single.onErrorReturn();
//        single.doOnError()
        single.subscribeOn(Schedulers.io());
        single.subscribeOn(Schedulers.newThread());
        single.observeOn(AndroidSchedulers.mainThread());
        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });


        Single<Integer> single1 = Single.just(1);

        single1.map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return String.valueOf(integer);
            }
        }).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });


        Observable.interval(5, TimeUnit.SECONDS).compose(timer()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                LogU.i("  aLong  " + aLong);
                startActivity(MainActivity.class, true);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxJavaMethodFunc.clearDisposable();
    }
}
