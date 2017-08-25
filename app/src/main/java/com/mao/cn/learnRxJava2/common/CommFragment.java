package com.mao.cn.learnRxJava2.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class CommFragment extends RxFragment implements CommViewInterface {
    public Context context;
    public Activity activity;
    protected View rootView;
    private Unbinder unbinder;

    public CommFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.getArgs(this.getArguments());
        this.rootView = inflater.inflate(this.setView(), (ViewGroup)null);
        this.unbinder = ButterKnife.bind(this, this.rootView);
        this.activity = this.getActivity();
        this.context = this.activity.getApplicationContext();
        OttoManager.register(this);
        this.setting();
        this.initView();
        this.setListener();
        return this.rootView;
    }

    protected abstract void getArgs(Bundle var1);

    protected abstract int setView();

    public abstract void initView();

    public abstract void setting();

    public abstract void setListener();

    protected void startActivity(Class cls) {
        this.startActivity(cls, (Bundle)null);
    }

    protected void startActivity(Class cls, Bundle bundle) {
        if(cls != null && this.activity != null && !this.activity.isFinishing()) {
            Intent intent = new Intent();
            if(bundle != null) {
                intent.putExtras(bundle);
            }

            intent.setClass(this.activity, cls);
            this.startActivity(intent);
        }
    }

    public void onDestroyView() {
        super.onDestroy();
        if(this.unbinder != null) {
            this.unbinder.unbind();
        }

        OttoManager.unregister(this);
    }
}