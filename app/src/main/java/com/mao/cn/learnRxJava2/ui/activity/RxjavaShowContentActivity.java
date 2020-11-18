// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 08/04/2017 16:53 下午
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
import com.mao.cn.learnRxJava2.component.DaggerRxjavaShowContentComponent;
import com.mao.cn.learnRxJava2.contants.ValueMaps;
import com.mao.cn.learnRxJava2.model.MovieDetail;
import com.mao.cn.learnRxJava2.modules.RxjavaShowContentModule;
import com.mao.cn.learnRxJava2.ui.adapter.MovieTopAdapter;
import com.mao.cn.learnRxJava2.ui.commons.BaseActivity;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaShowContent;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaShowContentPresenter;
import com.mao.cn.learnRxJava2.utils.tools.ListU;
import com.mao.cn.learnRxJava2.utils.tools.LogU;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class RxjavaShowContentActivity extends BaseActivity implements IRxjavaShowContent {

    @Inject
    RxjavaShowContentPresenter presenter;

    @BindView(R.id.ib_header_back)
    ImageButton ibHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rvData)
    RecyclerView rvData;

    @Override
    public void getArgs(Bundle bundle) {

    }

    @Override
    public int setView() {
        return R.layout.aty_rxjava_show_content;
    }

    @Override
    public void initView() {
        ibHeaderBack.setVisibility(View.VISIBLE);
        presenter.getMovieTop(0, 10);
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
        DaggerRxjavaShowContentComponent.builder()
                .appComponent(appComponent)
                .rxjavaShowContentModule(new RxjavaShowContentModule(this))
                .build().inject(this);
    }

    @Override
    public void showTopMovie(List<MovieDetail> movieDetails, String title) {
        if (!checkActivityState()) return;
        tvHeaderTitle.setVisibility(View.VISIBLE);
        tvHeaderTitle.setText(title);
        if (ListU.notEmpty(movieDetails)) {
            LinearLayoutManager linearLayoutCourse = new LinearLayoutManager(context);
            linearLayoutCourse.setOrientation(LinearLayoutManager.VERTICAL);
            rvData.setLayoutManager(linearLayoutCourse);
            MovieTopAdapter movieTopAdapter = new MovieTopAdapter(this);
            movieTopAdapter.addMovieList(movieDetails);
            rvData.setAdapter(movieTopAdapter);
        }
    }

    @Override
    public void onDestroy() {
        presenter.onDestroySubscribe();
        super.onDestroy();
    }
}
