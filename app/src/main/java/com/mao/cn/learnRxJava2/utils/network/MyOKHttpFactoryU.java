package com.mao.cn.learnRxJava2.utils.network;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.builder.GetBuilder;
import com.tsy.sdk.myokhttp.response.IResponseHandler;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author:  zhangkun .
 * date:    on 2017/8/7.
 */

public class MyOKHttpFactoryU {
    private static MyOkHttp client;
    private static MyOKHttpFactoryU mInstance;


    public MyOKHttpFactoryU() {
        if (client == null)
            client = defaultOkHttpClient();
    }

    public static MyOKHttpFactoryU getInStance() {
        if (mInstance == null) {
            synchronized (MyOKHttpFactoryU.class) {
                if (mInstance == null) {
                    mInstance = new MyOKHttpFactoryU();
                }
            }
        }
        return mInstance;
    }

    private static MyOkHttp defaultOkHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000 * 30, TimeUnit.MILLISECONDS)
                .readTimeout(1000 * 15, TimeUnit.MILLISECONDS)
                .writeTimeout(1000 * 30, TimeUnit.MILLISECONDS)
                .addInterceptor(logging)
                .build();
        return new MyOkHttp(okHttpClient);
    }


    public void okHttpGetMovieTop(String url, Map<String, String> mapParams, IResponseHandler handler) {
        GetBuilder builder = client.get().url(url).params(mapParams);
        builder.enqueue(handler);
    }

}
