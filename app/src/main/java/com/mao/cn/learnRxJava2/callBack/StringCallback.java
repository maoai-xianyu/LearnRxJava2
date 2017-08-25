//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.callBack;

import retrofit2.Response;

public abstract class StringCallback extends BaseCallback<String> {
    public StringCallback() {
    }

    public void success(Response<String> response) {
        this.success((String)response.body());
    }

    public abstract void success(String var1);
}
