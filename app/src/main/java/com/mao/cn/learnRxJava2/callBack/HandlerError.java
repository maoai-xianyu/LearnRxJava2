//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.callBack;

import com.mao.cn.learnRxJava2.converter.RetrofitError;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class HandlerError {
    Throwable throwable;

    public HandlerError(Throwable throwable) {
        this.throwable = throwable;
    }

    public RetrofitError execute(String requestUrl) {
        RetrofitError error = new RetrofitError();
        if(this.throwable == null) {
            error.setCode(0);
        } else if(!(this.throwable instanceof UnknownHostException) && !(this.throwable instanceof SocketTimeoutException)) {
            if(this.throwable instanceof HttpException) {
                HttpException exception = (HttpException)this.throwable;
                error.setCode(exception.code());
                error.setMessage(exception.getMessage());
                Response re = exception.response();
                if(re != null && re.errorBody() != null) {
                    try {
                        error.setBody(re.errorBody().string());
                    } catch (IOException var6) {
                        error.setBody((String)null);
                    }
                }
            } else {
                this.throwable.printStackTrace();
                error.setCode(3);
                error.setMessage(this.throwable.getMessage());
            }
        } else {
            error.setCode(1);
            error.setMessage(this.throwable.getMessage());
        }

        error.setRequestUrl(requestUrl);
        return error;
    }

    public RetrofitError execute() {
        RetrofitError error = new RetrofitError();
        if(this.throwable == null) {
            error.setCode(0);
        } else if(!(this.throwable instanceof UnknownHostException) && !(this.throwable instanceof SocketTimeoutException)) {
            if(this.throwable instanceof HttpException) {
                HttpException exception = (HttpException)this.throwable;
                error.setCode(exception.code());
                error.setMessage(exception.getMessage());
                Response re = exception.response();
                if(re != null && re.errorBody() != null) {
                    try {
                        error.setBody(re.errorBody().string());
                    } catch (IOException var5) {
                        error.setBody((String)null);
                    }
                }
            } else {
                this.throwable.printStackTrace();
                error.setCode(3);
                error.setMessage(this.throwable.getMessage());
            }
        } else {
            error.setCode(1);
            error.setMessage(this.throwable.getMessage());
        }

        return error;
    }
}
