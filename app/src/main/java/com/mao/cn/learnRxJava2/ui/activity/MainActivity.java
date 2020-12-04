// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 06/09/2017 11:17 上午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.component.AppComponent;
import com.mao.cn.learnRxJava2.component.DaggerMainComponent;
import com.mao.cn.learnRxJava2.contants.ValueMaps;
import com.mao.cn.learnRxJava2.modules.MainModule;
import com.mao.cn.learnRxJava2.roundcornerprogressbar.RoundCornerProgressBar;
import com.mao.cn.learnRxJava2.ui.activity.rxjava.Observable;
import com.mao.cn.learnRxJava2.ui.activity.rxjava.ObservableSubscribe;
import com.mao.cn.learnRxJava2.ui.activity.rxjava.Observer;
import com.mao.cn.learnRxJava2.ui.commons.BaseActivity;
import com.mao.cn.learnRxJava2.ui.features.IMain;
import com.mao.cn.learnRxJava2.ui.presenter.MainPresenter;
import com.mao.cn.learnRxJava2.utils.tools.LogU;
import com.mao.cn.learnRxJava2.utils.tools.StringU;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class MainActivity extends BaseActivity implements IMain {

    @Inject
    MainPresenter presenter;

    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.btn_desc_net)
    Button btnDescNet;
    @BindView(R.id.btn_desc_rxjava)
    Button btnDescRxjava;
    @BindView(R.id.btn_desc_rxjava_1)
    Button btnDescRxjava1;
    @BindView(R.id.btn_desc_image)
    Button btnDescImage;
    @BindView(R.id.rcpb)
    RoundCornerProgressBar rcpb;


    @Override
    public void getArgs(Bundle bundle) {

    }

    @Override
    public int setView() {
        return R.layout.aty_main;
    }

    @Override
    public void initView() {
        tvHeaderTitle.setText(getString(R.string.header));
        tvHeaderTitle.setVisibility(View.VISIBLE);
        requestPermission();


        rcpb.setMax(100);
        //rcpb.setBackgroundColor(ContextCompat.getColor(this, R.color.mine_vip_progress_default));
        rcpb.enableAnimation();
        rcpb.setRadius((int) dp2px(15));
        rcpb.setProgressColors(getResources().getIntArray(R.array.movie_progress_gradient));


        initRx();
    }


    private void initRx() {

        // onSubscribe  = ObservableSubscribe
        Observable.create(new ObservableSubscribe<String>() {
            @Override
            public void subscribe(Observer<String> stringObserver) {

                // stringObserver  ==  Observer 值
                stringObserver.onNext("1111");
                stringObserver.onNext("222");
                stringObserver.onNext("3333");
                stringObserver.onNext("444");
                stringObserver.onComplete();
            }
        }).subscribe(new com.mao.cn.learnRxJava2.ui.activity.rxjava.Observer<String>() {
            @Override
            public void onNext(String s) {

                LogU.d("onNext  " + s);

            }

            @Override
            public void onComplete() {
                LogU.d("onComplete  ");
            }

            @Override
            public void onError(Throwable e) {
                LogU.d("onError  " + e.toString());
            }
        });
    }

    float dp2px(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    @SuppressLint("CheckResult")
    @Override
    public void setListener() {

        RxView.clicks(btnDescNet).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
            .MILLISECONDS).subscribe(aVoid -> {
            startActivity(NetWorkRequestActivity.class);
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });

        RxView.clicks(btnDescRxjava).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
            .MILLISECONDS).subscribe(aVoid -> {
            startActivity(RxJavaLearnActivity.class);
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });

        RxView.clicks(btnDescRxjava1).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
            .MILLISECONDS).subscribe(aVoid -> {
            startActivity(RxJavaLearnDetailActivity.class);
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });

        RxView.clicks(btnDescImage).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
            .MILLISECONDS).subscribe(aVoid -> {
            startActivity(RetrofitShowContentActivity.class);
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });
    }


    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
            .appComponent(appComponent)
            .mainModule(new MainModule(this))
            .build().inject(this);
    }

    /**
     * 获取需要操作日历的权限
     */
    @SuppressLint("CheckResult")
    private void requestPermission() {

        new RxPermissions(MainActivity.this)
            .requestEach(Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.RECORD_AUDIO)
            .subscribe(permission -> {
                if (permission.granted) {

                } else {
                    if (StringU.equals(permission.name, Manifest.permission.RECORD_AUDIO)) {
                        onTip("未授予录音权限,将会影响语音朗读");
                    }
                }

            }, throwable -> LogU.e("异常"));
    }
}
