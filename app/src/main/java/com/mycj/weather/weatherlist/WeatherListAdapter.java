package com.mycj.weather.weatherlist;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycj.weather.R;
import com.mycj.weather.bean.Weather;

import java.util.List;

/**
 * Created by Hqs on 2017/5/9.
 * Company : MYCJ
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder> {

    private LayoutInflater inflater;
    private List<Weather> list;

    public WeatherListAdapter(List<Weather> list,Context context){
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listview_item, parent);
        WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        Weather weather = list.get(position);
        holder.tvCity.setText(weather.getCity());
        holder.tvTemp.setText(weather.getTemperature()+"°");
        holder.tvWind.setText("风向："+weather.getWind());
        holder.tvVisible.setText("能见度："+weather.getVisible());
        holder.tvTime.setText(weather.getTime());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class WeatherViewHolder extends RecyclerView.ViewHolder{

        ImageView ivWeather;
        TextView tvTemp;
        TextView tvCity;
        TextView tvTime;
        TextView tvWind;
        TextView tvVisible;
        LinearLayout ll_layout;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ivWeather = (ImageView) itemView.findViewById(R.id.iv_weather);
            tvTemp = (TextView) itemView.findViewById(R.id.tv_temp);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvWind = (TextView) itemView.findViewById(R.id.tv_wind);
            tvVisible = (TextView) itemView.findViewById(R.id.tv_visible);
            ll_layout = (LinearLayout) itemView.findViewById(R.id.ll_layout);
        }
    }
}
