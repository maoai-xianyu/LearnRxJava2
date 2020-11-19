package com.mao.cn.learnRxJava2.model;

import com.mao.cn.learnRxJava2.utils.tools.LogU;

public class Translation {

    private int status;

    private content content;

    private static class content {

        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        LogU.d("RxJava" + content.out);
    }
}
