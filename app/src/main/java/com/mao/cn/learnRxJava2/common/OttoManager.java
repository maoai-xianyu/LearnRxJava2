package com.mao.cn.learnRxJava2.common;

import com.squareup.otto.Bus;

/**
 * Created by zhangkun on 2017/6/9.
 */

public class OttoManager {
    private static final Bus BUS = new Bus();

    public OttoManager() {
    }

    public static Bus getInstance() {
        return BUS;
    }

    public static void register(Object o) {
        getInstance().register(o);
    }

    public static void unregister(Object o) {
        getInstance().unregister(o);
    }
}
