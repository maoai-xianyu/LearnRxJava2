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

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.component.AppComponent;
import com.mao.cn.learnRxJava2.component.DaggerOkhttpShowContentComponent;
import com.mao.cn.learnRxJava2.contants.ValueMaps;
import com.mao.cn.learnRxJava2.model.MovieDetail;
import com.mao.cn.learnRxJava2.modules.OkhttpShowContentModule;
import com.mao.cn.learnRxJava2.ui.adapter.MovieTopAdapter;
import com.mao.cn.learnRxJava2.ui.commons.BaseActivity;
import com.mao.cn.learnRxJava2.ui.features.IOkhttpShowContent;
import com.mao.cn.learnRxJava2.ui.presenter.OkhttpShowContentPresenter;
import com.mao.cn.learnRxJava2.utils.tools.ListU;
import com.mao.cn.learnRxJava2.utils.tools.LogU;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class OkhttpShowContentActivity extends BaseActivity implements IOkhttpShowContent {

    @Inject
    OkhttpShowContentPresenter presenter;

    @BindView(R.id.ib_header_back)
    ImageButton ibHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rvData)
    RecyclerView rvData;
    private MovieTopAdapter movieTopAdapter;


    @Override
    public void getArgs(Bundle bundle) {

    }

    @Override
    public int setView() {
        return R.layout.aty_okhttp_show_content;
    }

    @Override
    public void initView() {
        ibHeaderBack.setVisibility(View.VISIBLE);
        movieTopAdapter = new MovieTopAdapter(this);
//        presenter.getMovieTopMyOkHttp(0, 10);
        presenter.getMovieTop(0, 10);

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
        DaggerOkhttpShowContentComponent.builder()
                .appComponent(appComponent)
                .okhttpShowContentModule(new OkhttpShowContentModule(this))
                .build().inject(this);
    }

    @Override
    public void showTopMovie(List<MovieDetail> subjects, String title) {
        if (!checkActivityState()) return;
        runOnUiThread(() -> {
            tvHeaderTitle.setVisibility(View.VISIBLE);
            tvHeaderTitle.setText(title);
            if (ListU.notEmpty(subjects)) {
                LinearLayoutManager linearLayoutCourse = new LinearLayoutManager(context);
                linearLayoutCourse.setOrientation(LinearLayoutManager.VERTICAL);
                rvData.setLayoutManager(linearLayoutCourse);
                movieTopAdapter.addMovieList(subjects);
                rvData.setAdapter(movieTopAdapter);
            }
        });

    }
}
