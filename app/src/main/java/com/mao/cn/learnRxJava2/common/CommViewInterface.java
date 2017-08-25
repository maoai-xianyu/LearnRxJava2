package com.mao.cn.learnRxJava2.common;

/**
 * Created by zhangkun on 2017/6/9.
 */

public interface CommViewInterface {

    void showLoadingDialog(String msg);

    void showLoadingDialog(int msg);

    void hideLoadingDialog();

    void onTip(String msg);

    void onTip(int msg);
}
