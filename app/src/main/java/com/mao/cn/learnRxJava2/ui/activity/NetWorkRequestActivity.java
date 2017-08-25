package com.mao.cn.learnRxJava2.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.component.AppComponent;
import com.mao.cn.learnRxJava2.contants.ValueMaps;
import com.mao.cn.learnRxJava2.ui.commons.BaseActivity;
import com.mao.cn.learnRxJava2.utils.tools.LogU;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * author:  zhangkun .
 * date:    on 2017/8/8.
 */

public class NetWorkRequestActivity extends BaseActivity {


    @BindView(R.id.ib_header_back)
    ImageButton ibHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.btn_okhttp)
    Button btnDescOkhttp;
    @BindView(R.id.btn_rxjava)
    Button btnDescRxjava;
    @BindView(R.id.btn_retrofit)
    Button btnDescRetrofit;


    @Override
    public void getArgs(Bundle bundle) {

    }

    @Override
    public int setView() {
        return R.layout.aty_net_work_request;
    }

    @Override
    public void initView() {
        ibHeaderBack.setVisibility(View.VISIBLE);
        tvHeaderTitle.setVisibility(View.VISIBLE);
        tvHeaderTitle.setText(getString(R.string.header));
    }


    @Override
    public void setListener() {

        RxView.clicks(ibHeaderBack).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
                .MILLISECONDS).subscribe(aVoid -> {
            finish();
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });

        RxView.clicks(btnDescOkhttp).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
                .MILLISECONDS).subscribe(aVoid -> {
            startActivity(OkhttpShowContentActivity.class);
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });

        RxView.clicks(btnDescRxjava).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
                .MILLISECONDS).subscribe(aVoid -> {
            startActivity(RxjavaShowContentActivity.class);

        }, throwable -> {
            LogU.e(throwable.getMessage());
        });

        RxView.clicks(btnDescRetrofit).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
                .MILLISECONDS).subscribe(aVoid -> {
            startActivity(RetrofitShowContentActivity.class);
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });

    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }
}
