// +----------------------------------------------------------------------
// | FileName:   ${file_name}  
// +----------------------------------------------------------------------
// | CreateTime: 15/6/28  @下午7:36
// +----------------------------------------------------------------------
// | Author:     xab(admin@xueyong.net.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.http;

import com.google.gson.Gson;
import com.mao.cn.learnRxJava2.LearnRxJava2Application;
import com.mao.cn.learnRxJava2.factory.StringConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Create By Xueyong
 */
public class RestApiAdapter {

    private static Retrofit sharedStringInstance = null;
    private static Retrofit timeoutStringInstance = null;
    private static Retrofit rxHttpsStringInstance;
    private static Retrofit rxHttpsRxInstance;
    private static Retrofit rxGsonInstance;

    private static OkHttpClient client;
    private static OkHttpClient timeoutClient;


    public static Retrofit getHttpsRxStringInstance() {
        if (client == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        }

        if (rxHttpsStringInstance == null) {
            rxHttpsStringInstance = new Builder().baseUrl("https://api.douban.com/")
                .client(client)
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        }
        return rxHttpsStringInstance;
    }

    public static Retrofit getHttpsRxFyInstance() {
        if (client == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        }

        if (rxHttpsRxInstance == null) {
            rxHttpsRxInstance = new Builder().baseUrl("http://fy.iciba.com/")
                .client(client)
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        }
        return rxHttpsRxInstance;
    }

    public static Retrofit getStringInstance() {
        if (client == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        }

        if (sharedStringInstance == null) {
            sharedStringInstance = new Builder()
                .baseUrl(LearnRxJava2Application.serverInfo().getServerHost())
                .client(client)
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        }
        return sharedStringInstance;
    }

    public static Retrofit getStringInstanceTimeOut() {
        if (timeoutClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            timeoutClient = new OkHttpClient.Builder()
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        }

        if (timeoutStringInstance == null) {
            timeoutStringInstance = new Builder()
                .baseUrl(LearnRxJava2Application.serverInfo().getServerHost())
                .client(timeoutClient)
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        }
        return timeoutStringInstance;
    }


    public static Retrofit getRxGsonInstance() {
        if (client == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        }

        if (rxGsonInstance == null) {
            rxGsonInstance = new Builder().baseUrl(LearnRxJava2Application.serverInfo().getServerHost())
                .client(client)
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        }
        return rxGsonInstance;
    }


    public static void clean() {
        sharedStringInstance = null;
        rxHttpsStringInstance = null;
        rxGsonInstance = null;
        client = null;
        sharedStringInstance = null;
        timeoutStringInstance = null;
        rxHttpsRxInstance = null;
    }

}
