package com.mycj.weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mycj.weather.R;
import com.mycj.weather.bean.FavorCity;
import com.mycj.weather.service.NowWeather;
import com.mycj.weather.bean.Weather;
import com.mycj.weather.config.Config;
import com.mycj.weather.service.WeatherService;
import com.mycj.weather.util.L;
import com.mycj.weather.util.LitePalUtil;
import com.mycj.weather.weatherlist.WeatherListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hqs on 2017/5/15.
 * Company : MYCJ
 */
public class ListActivity extends AppCompatActivity {

    private List<Weather> weatherList = new ArrayList<Weather>();;
    private RecyclerView recyclerView_weather;
    private WeatherListAdapter adapter;
    private long mExitTime;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        recyclerView_weather = (RecyclerView) findViewById(R.id.recyclerView_weather_list);
        Button btnAddFavorCity = (Button) findViewById(R.id.btn_add_favor_city);

        btnAddFavorCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,CitiesListActivity.class);
                startActivity(intent);
            }
        });

        retrofit = new  Retrofit.Builder().
                baseUrl(Config.base_url).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        loadFavorCity();

        adapter = new WeatherListAdapter(weatherList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_weather.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_weather.setAdapter(adapter);

        loadWeatherData();

            adapter.setOnItemClickListener(new WeatherListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                }
            });

            adapter.setOnItemLongClickListener(new WeatherListAdapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(int position) {

                }
            });

    }

    private void loadFavorCity() {
        List<FavorCity> allFavorCities = LitePalUtil.findAllFavorCities();
        for(FavorCity favorCity : allFavorCities){
            String cityCode = favorCity.getCityCode();
            weatherList.add(new Weather("","",cityCode,"","",""));
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        L.e(ListActivity.class,"onNewIntent");
        String city = intent.getStringExtra("city");
        weatherList.add(new Weather("","",city,"","",""));
        loadWeatherData();
    }

    public void loadWeatherData(){
        for(int i = 0; i < weatherList.size(); i++) {
            final Weather weather = weatherList.get(i);
            String city = weather.getCity();
            WeatherService weatherService = retrofit.create(WeatherService.class);
            Call<NowWeather> nowWeather = weatherService.getNowWeather(city, Config.api_key);
            nowWeather.enqueue(new Callback<NowWeather>() {
                @Override
                public void onResponse(Call<NowWeather> call, Response<NowWeather> response) {
                    final List<NowWeather.HeWeather5Bean> heWeather5 = response.body().getHeWeather5();
                    Log.e(ListActivity.class.getSimpleName(), "heWeather:" + heWeather5);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            setWeaher(heWeather5, weather);
                        }
                    }).start();
                }

                @Override
                public void onFailure(Call<NowWeather> call, Throwable t) {

                }
            });
        }
    }



    private void setWeaher(List<NowWeather.HeWeather5Bean> heWeather5,Weather weather) {
        if(heWeather5 == null || weather == null){
            Log.e("ListActivity","数据为空");
            return;
        }

        NowWeather.HeWeather5Bean heWeather5Bean = heWeather5.get(0);
        NowWeather.HeWeather5Bean.BasicBean basic = heWeather5Bean.getBasic();
        NowWeather.HeWeather5Bean.BasicBean.UpdateBean update = basic.getUpdate();

        NowWeather.HeWeather5Bean.NowBean now = heWeather5Bean.getNow();
        weather.setCity(basic.getCity());
        weather.setInfo(now.getCond().getCode());
        weather.setTemperature(now.getTmp());
        weather.setVisible(now.getVis());
        weather.setTime(update.getUtc().split(" ")[1]);
        weather.setWind(now.getWind().getDir()+" "+now.getWind().getSc()+"级");
        Log.e("ListActivity","数据设置完毕："+weatherList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if( System.currentTimeMillis() - mExitTime > 2000 ){
                Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }else{
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
