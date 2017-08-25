package com.mao.cn.learnRxJava2.converter;

public class RetrofitError {
    public static final int ERROR_NULL = 0;
    public static final int ERROR_CONNECTION = 1;
    public static final int ERROR_TIMEOUT = 2;
    public static final int ERROR_OTHER = 3;
    private int code;
    private String message;
    private String body;
    private String requestUrl;

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public RetrofitError() {
    }

    public RetrofitError(int code, String message, String body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String toString() {
        return "{\"body\":\"" + this.body + '\"' + ", \"message\":\"" + this.message + '\"' + ", \"code\":" + this.code + '}';
    }
}