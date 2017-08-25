package com.mao.cn.learnRxJava2.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Window;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by zhangkun on 2017/6/9.
 */

public abstract class CommActivity extends RxAppCompatActivity {

    public Context context;
    public Activity activity;

    public CommActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getArgs(this.getIntent().getExtras());
        this.setContentView(this.setView());
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        this.activity = this;
        this.context = this;
        OttoManager.register(this);
        this.setting();
        this.initView();
        this.setListener();
    }

    public abstract void getArgs(Bundle var1);

    public abstract int setView();

    public abstract void initView();

    public abstract void setting();

    public abstract void setListener();

    public void onBackPressed() {
        this.finish();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        System.gc();
        super.onDestroy();
        OttoManager.unregister(this);
    }

    protected void startActivity(Class cls) {
        this.startActivity(cls, (Bundle)null, false);
    }

    protected void startActivity(Class cls, boolean isfinish) {
        this.startActivity(cls, (Bundle)null, isfinish);
    }

    protected void startActivity(Class cls, Bundle bundle) {
        this.startActivity(cls, bundle, false);
    }

    protected void startActivity(Class cls, Bundle bundle, boolean isfinish) {
        if(cls != null && this.activity != null && !this.activity.isFinishing()) {
            Intent intent = new Intent();
            if(bundle != null) {
                intent.putExtras(bundle);
            }

            intent.setClass(this.activity, cls);
            this.startActivity(intent);
            if(isfinish) {
                this.finish();
            }

        }
    }

    protected void setStyle(boolean status, boolean navigation) {
        if(VERSION.SDK_INT >= 19) {
            Window window = this.getWindow();
            if(status) {
                window.setFlags(67108864, 67108864);
            }

            if(navigation) {
                window.setFlags(134217728, 134217728);
            }
        }

    }
}
