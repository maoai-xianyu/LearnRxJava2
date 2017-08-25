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

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.component.AppComponent;
import com.mao.cn.learnRxJava2.component.DaggerRxjavaLearnDetailComponent;
import com.mao.cn.learnRxJava2.contants.ValueMaps;
import com.mao.cn.learnRxJava2.modules.RxjavaLearnDetailModule;
import com.mao.cn.learnRxJava2.ui.commons.BaseActivity;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaLearnDetail;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaLearnDetailPresenter;
import com.mao.cn.learnRxJava2.utils.tools.LogU;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;

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

    }

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
}
