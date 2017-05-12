package com.mycj.weather;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mycj.weather.bean.NowWeather;
import com.mycj.weather.bean.WeatherData;
import com.mycj.weather.config.Config;
import com.mycj.weather.service.WeatherService;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvNowWeather;
    private TextView tvCurrentCity;
    private TextView tvCurrentTemp;
    private TextView tvCurrentWind;
    private TextView tvCurrentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNowWeather = (TextView) findViewById(R.id.tv_now_weather);
        tvCurrentCity = (TextView) findViewById(R.id.tv_current_city);
        tvCurrentTemp = (TextView) findViewById(R.id.tv_current_temp);
        tvCurrentWind = (TextView) findViewById(R.id.tv_current_wind);
        tvCurrentWeather = (TextView) findViewById(R.id.tv_current_weather);

        Retrofit retrofit =new  Retrofit.Builder().
                baseUrl(Config.base_url).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<NowWeather> callWeatherData = weatherService.getNowWeather("深圳", Config.api_key);
        callWeatherData.enqueue(new Callback<NowWeather>() {
            @Override
            public void onResponse(Call<NowWeather> call, Response<NowWeather> response) {
                Log.e("onResponse",response.body().toString());
                List<NowWeather.HeWeather5Bean> heWeather5 = response.body().getHeWeather5();
                tvNowWeather.setText(heWeather5.toString());
                updateView(heWeather5);
            }

            @Override
            public void onFailure(Call<NowWeather> call, Throwable t) {
                Log.e("onFailure",t.toString());
            }
        });

    }

    private void updateView(List<NowWeather.HeWeather5Bean> heWeather5) {
        NowWeather.HeWeather5Bean heWeather5Bean = heWeather5.get(0);
        NowWeather.HeWeather5Bean.BasicBean basic = heWeather5Bean.getBasic();
        tvCurrentCity.setText("当前城市："+basic.getCity());
        NowWeather.HeWeather5Bean.NowBean now = heWeather5Bean.getNow();
        tvCurrentTemp.setText("温度："+now.getTmp());
        tvCurrentWeather.setText("当前天气："+now.getCond().getTxt());
        tvCurrentWind.setText("风向："+now.getWind().getDir());
    }
}
