// +----------------------------------------------------------------------
// | FileName:   ${file_name}  
// +----------------------------------------------------------------------
// | CreateTime: 15/6/28  @下午7:39
// +----------------------------------------------------------------------
// | Author:     xab(admin@xueyong.net.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.http;

import com.mao.cn.learnRxJava2.model.Movie;
import com.mao.cn.learnRxJava2.model.MovieDetail;
import com.mao.cn.learnRxJava2.utils.config.Config;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * author:  zhangkun .
 * date:    on 2017/8/1.
 */

public interface HttpApi {


    //---------------------GET请求

    /**
     * 返回string，在处理的时候需要进行转换
     * Headers 是在需要的时候才加上的
     *
     * @return
     */
    @GET("v2/movie/top250")
    @Headers(Config.HEADER_MAO)
    Call<String> getMovieTop(@Query("start") int start, @Query("count") int count);

    /**
     * @param start
     * @param count
     * @return
     */
    @GET("v2/movie/top250")
    @Headers(Config.HEADER_MAO)
    Call<Movie> getMovieTopNew(@Query("start") int start, @Query("count") int count);

    /**
     * 获得我的成就
     *
     * @param token 如果需要校验那么用token
     */
    @GET("v2/movie/top250")
    @Headers(Config.HEADER_MAO)
    Call<MovieDetail> getMovieList(@Query("token") String token);


    //----------------------------------------------------------------------------------------------

    //---------------------POST请求

    /**
     * post 请求有的需要 FormUrlEncoded  有的不需要
     * Headers 是在需要的时候才加上的 同理
     *
     * @param temp
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("v2/movie/subject/1764796")
    @Headers(Config.HEADER_MAO)
    Call<String> postMovieList(@Field("temp") String temp, @Query("token") String token);

    /**
     * @param open_id
     * @param platName
     * @param nickname
     * @param figure_url
     * @return
     */
    @POST("v2/movie/subject/1764796")
    @Headers(Config.HEADER_MAO)
    Call<MovieDetail> postMovieList(@Field("open_id") String open_id, @Field("platform_name") String platName, @Field("nickname") String nickname, @Field("figure_url") String figure_url);

    /**
     * Response
     *
     * @param resource
     * @return
     */
    @GET("tv2/movie/subject/{resoure}")
    @Headers(Config.HEADER_MAO)
    @Streaming
    Response download(@Path(value = "resoure") String resource);


    /**
     * ResponseBody
     *
     * @param id
     * @param token
     * @return
     */
    @GET("v2/movie/{courseid}/1764796")
    @Headers(Config.HEADER_MAO)
    @Streaming
    Call<ResponseBody> parseLessonInfo(@Path("courseid") String id, @Query("token") String token);

    @POST("v2/movie/subject/1764796")
    @Headers(Config.HEADER_MAO)
    Call<String> sendArticleData(@Header("Request-ID") String uuid, @Body RequestBody typedJson, @Query("access_token") String access_token);

    /**
     * @param key
     * @param access_token
     * @return
     */
    @FormUrlEncoded
    @POST("v2/movie/subject/1764796")
    @Headers(Config.HEADER_MAO)
    Call<String> storeCourseIdForToday(@Field("key") String key, @Query("access_token") String access_token);

    /**
     * VERSION  是用于判断版本的
     *
     * @param token
     * @return
     */
    @GET("v2/movie/subject/1764796")
    @Headers({Config.HEADER_MAO, Config.VERSION})
    Call<String> checkAwardState(@Query("access_token") String token);

    //----------------------------------------------------------------------------------------------

    //---------------RxJava
    @GET("v2/movie/top250")
    @Headers(Config.HEADER_MAO)
    Observable<String> getTodayMovie(@Query("start") int start, @Query("count") int count);

    /**
     * @param start
     * @param count
     * @return
     */
    @GET("v2/movie/top250")
    @Headers(Config.HEADER_MAO)
    Observable<Movie> getTodayMovieTop(@Query("start") int start, @Query("count") int count);

    /**
     * @param start
     * @param count
     * @param access_token
     * @return
     */
    @GET("v2/movie/subject/1764796")
    @Headers(Config.HEADER_MAO)
    Observable<String> getTodayMovie(@Query("start") int start, @Query("count") int count, @Query("access_token") String access_token);


}
