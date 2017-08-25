package com.mao.cn.learnRxJava2.ui.commons;

import com.mao.cn.learnRxJava2.common.CommViewInterface;
import com.mao.cn.learnRxJava2.converter.RetrofitError;

/**
 * Created by zhangkun on 2017/6/9.
 */

public interface BaseViewInferface extends CommViewInterface {


    void interError(RetrofitError error);

    void interError(Throwable throwable);
}
