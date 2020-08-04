// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/16/2017 15:27 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.component.AppComponent;
import com.mao.cn.learnRxJava2.component.DaggerRxjavaLearnRxBingdingComponent;
import com.mao.cn.learnRxJava2.contants.ValueMaps;
import com.mao.cn.learnRxJava2.modules.RxjavaLearnRxBingdingModule;
import com.mao.cn.learnRxJava2.ui.adapter.RxJavaRcStringAdapter;
import com.mao.cn.learnRxJava2.ui.adapter.RxListStringAdapter;
import com.mao.cn.learnRxJava2.ui.commons.BaseActivity;
import com.mao.cn.learnRxJava2.ui.features.IRxjavaLearnRxBingding;
import com.mao.cn.learnRxJava2.ui.funcitonMethod.InitDataMethodFunc;
import com.mao.cn.learnRxJava2.ui.presenter.RxjavaLearnRxBingdingPresenter;
import com.mao.cn.learnRxJava2.utils.tools.LogU;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class RxjavaLearnRxBingdingActivity extends BaseActivity implements IRxjavaLearnRxBingding {

    @Inject
    RxjavaLearnRxBingdingPresenter presenter;
    @BindView(R.id.ib_header_back)
    ImageButton ibHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.e_mail)
    EditText etEmail;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @BindView(R.id.et_list_view)
    EditText etListView;
    @BindView(R.id.ls_data)
    ListView lsData;
    @BindView(R.id.et_recycler_view)
    EditText etRecyclerView;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    private RxListStringAdapter arrayAdapter;
    private List<String> strings;
    private RxJavaRcStringAdapter rcStringAdapter;

    @Override
    public void getArgs(Bundle bundle) {

    }

    @Override
    public int setView() {
        return R.layout.aty_rxjava_learn_rxbingding;
    }

    @Override
    public void initView() {
        ibHeaderBack.setVisibility(View.VISIBLE);
        tvHeaderTitle.setVisibility(View.VISIBLE);
        tvHeaderTitle.setText(getString(R.string.header_rxjava_binding));

        arrayAdapter = new RxListStringAdapter(this);
        strings = InitDataMethodFunc.initStringData();
        arrayAdapter.addStringList(strings);
        lsData.setAdapter(arrayAdapter);

        LinearLayoutManager linearLayoutCourse = new LinearLayoutManager(context);
        linearLayoutCourse.setOrientation(LinearLayoutManager.VERTICAL);
        rvData.setLayoutManager(linearLayoutCourse);
        rcStringAdapter = new RxJavaRcStringAdapter(this);
        rcStringAdapter.addStringList(strings);
        rvData.setAdapter(rcStringAdapter);
    }

    @Override
    public void setListener() {

        RxView.clicks(ibHeaderBack).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish(), throwable -> LogU.e(throwable.getMessage()));

        RxView.longClicks(ibHeaderBack).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish(), throwable -> LogU.e(throwable.getMessage()));

        RxAdapterView.itemClicks(lsData)
                .subscribe(integer -> Toast.makeText(RxjavaLearnRxBingdingActivity.this, "item click " + integer + " ==值 " + strings.get(integer), Toast.LENGTH_SHORT).show());

        RxAdapterView.itemLongClicks(lsData)
                .subscribe(integer -> Toast.makeText(RxjavaLearnRxBingdingActivity.this, "item long click " + integer + " ==值 " + strings.get(integer), Toast.LENGTH_SHORT).show());

        RxTextView.textChanges(etListView)
                .debounce(600, TimeUnit.MILLISECONDS)
                .map(charSequence -> {
                    //get the keyword
                    String key = charSequence.toString();
                    return key;
                })

                //
                .switchMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull String s) throws Exception {
                        return Observable.just(s);
                    }
                })
                .observeOn(Schedulers.io())
                .map(keyWord -> {
                    //get list
                    List<String> dataList = new ArrayList<>();
                    if (!TextUtils.isEmpty(keyWord)) {
                        for (String s : strings) {
                            if (s != null) {
                                if (s.contains(keyWord)) {
                                    dataList.add(s);
                                }
                            }
                        }
                    } else {
                        dataList.addAll(strings);
                    }
                    return dataList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings1 -> {

                    arrayAdapter.clear();
                    arrayAdapter.addStringList(strings1);
                    arrayAdapter.notifyDataSetChanged();

                });


        RxRecyclerView.scrollStateChanges(rvData).subscribe(integer -> LogU.i(" integer " + integer));

        RxRecyclerView.childAttachStateChangeEvents(rvData)
                .subscribe(recyclerViewChildAttachStateChangeEvent -> LogU.i(" recyclerViewChildAttachStateChangeEvent " + recyclerViewChildAttachStateChangeEvent.child()));

        RxRecyclerView.scrollEvents(rvData).subscribe(recyclerViewScrollEvent -> LogU.i(" recyclerViewScrollEvent " + recyclerViewScrollEvent));

        /*RxRecyclerViewAdapter.dataChanges(rcStringAdapter).subscribe(new Action1<RxJavaRcStringAdapter>() {
            @Override
            public void call(RxJavaRcStringAdapter rxJavaRcStringAdapter) {

            }
        });*/

        RxTextView.textChanges(etRecyclerView)
                .debounce(600, TimeUnit.MILLISECONDS)
                .map(charSequence -> {
                    //get the keyword
                    String key = charSequence.toString();
                    return key;
                })
                .observeOn(Schedulers.io())
                .map(keyWord -> {
                    //get list
                    List<String> dataList = new ArrayList<>();
                    if (!TextUtils.isEmpty(keyWord)) {
                        for (String s : strings) {
                            if (s != null) {
                                if (s.contains(keyWord)) {
                                    dataList.add(s);
                                }
                            }
                        }
                    } else {
                        dataList.addAll(strings);
                    }
                    return dataList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings1 -> {
                    rcStringAdapter.clear();
                    rcStringAdapter.addStringList(strings1);
                });


        Observable<String> mapEtMail = RxTextView.textChanges(etEmail).map(CharSequence::toString);
        Observable<String> observablePassword = RxTextView.textChanges(etPwd).map(CharSequence::toString);


        Observable.combineLatest(mapEtMail, observablePassword, (email, password) -> isEmailValid(email) && isPasswordValid(password)).subscribe(aBoolean -> {
            if (aBoolean) {
                btnConfirm.setVisibility(View.VISIBLE);
            } else {
                btnConfirm.setVisibility(View.GONE);
            }
        });
    }


    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerRxjavaLearnRxBingdingComponent.builder()
                .appComponent(appComponent)
                .rxjavaLearnRxBingdingModule(new RxjavaLearnRxBingdingModule(this))
                .build().inject(this);
    }

}
