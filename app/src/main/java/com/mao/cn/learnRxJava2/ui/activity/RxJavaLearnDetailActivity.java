// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/09/2017 19:56 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.component.AppComponent;
import com.mao.cn.learnRxJava2.component.DaggerRxjavaLearnDetailComponent;
import com.mao.cn.learnRxJava2.contants.ValueMaps;
import com.mao.cn.learnRxJava2.http.HttpApi;
import com.mao.cn.learnRxJava2.http.RestApiAdapter;
import com.mao.cn.learnRxJava2.model.User;
import com.mao.cn.learnRxJava2.modules.RxjavaLearnDetailModule;
import com.mao.cn.learnRxJava2.ui.commons.BaseActivity;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaLearnDetail;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaLearnDetailPresenter;
import com.mao.cn.learnRxJava2.utils.tools.LogU;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class RxJavaLearnDetailActivity extends BaseActivity implements IRxjavaLearnDetail {

    @Inject
    RxjavaLearnDetailPresenter presenter;

    @BindView(R.id.ib_header_back)
    ImageButton ibHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.ib_header_right)
    ImageButton ibHeaderRight;

    @Override
    public void getArgs(Bundle bundle) {

    }

    @Override
    public int setView() {
        return R.layout.aty_rxjava_learn_detail;
    }

    @Override
    public void initView() {
        ibHeaderBack.setVisibility(View.VISIBLE);
        //presenter.getUser("maoai-xianyu");
        //presenter.runIntraval("maoai-xianyu");
        //presenter.requestDouble();
        //presenter.getRepeatWhen();
        //presenter.getT();
        //presenter.getIntraval();
        //presenter.requestDataByNet();
        //test();
        //presenter.zip();
        //presenter.doxx();
        //presenter.onErrorReturn();
        //presenter.onErrorResumeNext();
        //presenter.retry();
        //presenter.retryWhen();
        //presenter.repeatWhen();
        //presenter.groupBy();
        presenter.threadChange();
    }

    private void test() {
        HttpApi httpApi = RestApiAdapter.getStringInstance().create(HttpApi.class);

        Observable<User> observable = httpApi.getUser("maoai-xianyu");
        observable.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
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

    @SuppressLint("CheckResult")
    @Override
    public void setListener() {

        RxView.clicks(ibHeaderBack).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
            .MILLISECONDS).subscribe(aVoid -> {
            finish();
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });

    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerRxjavaLearnDetailComponent.builder()
            .appComponent(appComponent)
            .rxjavaLearnDetailModule(new RxjavaLearnDetailModule(this))
            .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.clear();
    }
}
