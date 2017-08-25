// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 06/09/2017 11:36 上午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.component.AppComponent;
import com.mao.cn.learnRxJava2.component.DaggerLoadingComponent;
import com.mao.cn.learnRxJava2.modules.LoadingModule;
import com.mao.cn.learnRxJava2.ui.commons.BaseActivity;
import com.mao.cn.learnRxJava2.ui.features.ILoading;
import com.mao.cn.learnRxJava2.ui.presenter.LoadingPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class LoadingActivity extends BaseActivity implements ILoading {


    @Inject
    LoadingPresenter presenter;
    @BindView(R.id.iv_loading_background)
    SimpleDraweeView ivLoadingBackground;
    @BindView(R.id.tv_show)
    TextView tvShow;

    @Override
    public void getArgs(Bundle bundle) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    @Override
    public int setView() {
        return R.layout.aty_loading;
    }

    @Override
    public void initView() {

        Observable.interval(5,TimeUnit.SECONDS).compose(timer()).subscribe(aLong -> startActivity(MainActivity.class, true));

        //当应用是使用系统安装器安装并且运行的时候，category中是没有任何信息的，这个时候会导致按home键后，点击图标重启app。
        /*if (!isTaskRoot()) {
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }*/

    }
    @Override
    public void setListener() {

    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerLoadingComponent.builder()
                .appComponent(appComponent)
                .loadingModule(new LoadingModule(this))
                .build().inject(this);
    }
}
