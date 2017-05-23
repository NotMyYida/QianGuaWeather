package com.mycj.weather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.mycj.weather.R;
import com.mycj.weather.service.NowWeather;
import com.mycj.weather.bean.Weather;
import com.mycj.weather.config.Config;
import com.mycj.weather.service.WeatherService;
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

    private List<Weather> weatherList;
    private RecyclerView recyclerView_weather;
    private WeatherListAdapter adapter;
    private long mExitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        recyclerView_weather = (RecyclerView) findViewById(R.id.recyclerView_weather_list);

        Retrofit retrofit =new  Retrofit.Builder().
                baseUrl(Config.base_url).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        weatherList = new ArrayList<Weather>();
        adapter = new WeatherListAdapter(weatherList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_weather.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_weather.setAdapter(adapter);
        weatherList.add(new Weather("","","深圳","","",""));
        weatherList.add(new Weather("","","湛江","","",""));
        weatherList.add(new Weather("","","连州","","",""));
        weatherList.add(new Weather("","","天津","","",""));
        weatherList.add(new Weather("","","上海","","",""));
        for(int i = 0; i < weatherList.size(); i++){
            final Weather weather =  weatherList.get(i);
            String city = weather.getCity();
            WeatherService weatherService = retrofit.create(WeatherService.class);
            Call<NowWeather> nowWeather = weatherService.getNowWeather(city, Config.api_key);
            nowWeather.enqueue(new Callback<NowWeather>() {
                @Override
                public void onResponse(Call<NowWeather> call, Response<NowWeather> response) {
                    final List<NowWeather.HeWeather5Bean> heWeather5 = response.body().getHeWeather5();
                    Log.e(ListActivity.class.getSimpleName(),"heWeather:"+heWeather5);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            setWeaher(heWeather5,weather);
                        }
                    }).start();
                }

                @Override
                public void onFailure(Call<NowWeather> call, Throwable t) {

                }
            });


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
