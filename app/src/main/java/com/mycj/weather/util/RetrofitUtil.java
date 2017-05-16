package com.mycj.weather.util;

import android.util.Log;

import com.mycj.weather.config.Config;
import com.mycj.weather.service.ChinaCity;
import com.mycj.weather.service.NowWeather;
import com.mycj.weather.service.WeatherService;
import com.mycj.weather.weatherlist.OnNowWeatherListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hqs on 2017/5/16.
 * Company : MYCJ
 */
public class RetrofitUtil {

    private final String tag = RetrofitUtil.class.getSimpleName();
    private static RetrofitUtil retrofitUtil;
    private final Retrofit retrofit;
    OnNowWeatherListener mOnNowWeatherListener;
    private final WeatherService weatherService;

    private RetrofitUtil(){
        retrofit = new  Retrofit.Builder().
                baseUrl(Config.base_url).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        weatherService = retrofit.create(WeatherService.class);
    }


    public static RetrofitUtil newinstance(){
        if(retrofitUtil == null){
            synchronized (RetrofitUtil.class){
                if(retrofitUtil == null){
                    retrofitUtil = new RetrofitUtil();
                }
            }
        }
        return retrofitUtil;
    }


    public void loadNowWeather(String city){
        Call<NowWeather> nowWeather = weatherService.getNowWeather(city, Config.api_key);
        nowWeather.enqueue(new Callback<NowWeather>() {
            @Override
            public void onResponse(Call<NowWeather> call, Response<NowWeather> response) {
                List<NowWeather.HeWeather5Bean> heWeather5 = response.body().getHeWeather5();
            }

            @Override
            public void onFailure(Call<NowWeather> call, Throwable t) {

            }
        });
    }


    public void loadAllChinaCity(){
        Call<ChinaCity[]> allChinaCity = weatherService.getAllChinaCity(Config.city_list_url);
        allChinaCity.enqueue(new Callback<ChinaCity[]>() {
            @Override
            public void onResponse(Call<ChinaCity[]> call, Response<ChinaCity[]> response) {
                Log.e(tag,"length:"+response.body().length);

            }

            @Override
            public void onFailure(Call<ChinaCity[]> call, Throwable t) {
                Log.e(tag,t.toString());
            }
        });
    }


    public void setOnNowWeatherListener(OnNowWeatherListener onNowWeatherListener){
        this.mOnNowWeatherListener = onNowWeatherListener;
    }


}
