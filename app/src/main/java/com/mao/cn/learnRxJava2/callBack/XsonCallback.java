//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.callBack;

import org.json.JSONException;

import retrofit2.Call;

public abstract class XsonCallback extends StringCallback {
    public XsonCallback() {
    }

    public void success(String s) {
        if(null != s && !"".equals(s)) {
            try {
                this.success(new XsonObject(s));
            } catch (JSONException var3) {
                this.onFailure((Call)null, var3);
            }
        } else {
            this.success(new XsonObject());
        }

    }

    public abstract void success(XsonObject var1);
}
