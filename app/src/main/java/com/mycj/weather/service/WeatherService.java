package com.mycj.weather.service;

import com.mycj.weather.config.Config;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Hqs on 2017/5/9.
 * Company : MYCJ
 */
public interface WeatherService {

    /**
     * 获取所有的某城市天气信息
     * @param city
     * @param key
     * @return
     */
    @GET(Config.alldata)
    Call<WeatherData> getAllData(@Query("city") String city, @Query("key") String key);

    @GET("now")
    Call<NowWeather> getNowWeather(@Query("city") String city, @Query("key") String key);

    @GET()
    Call<ChinaCity[]>  getAllChinaCity(@Url String url);



}
