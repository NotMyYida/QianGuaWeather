package com.mycj.weather.weatherlist;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycj.weather.R;
import com.mycj.weather.bean.Weather;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Hqs on 2017/5/9.
 * Company : MYCJ
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder> {

    private LayoutInflater inflater;
    private List<Weather> list;

    private int[] imgSourceBackground = new int[]{R.drawable.pink_drawable,R.drawable.green_drawable,
                                                                    R.drawable.purple_drawable,R.drawable.yellow_drawable,
                                                                    R.drawable.blue_drawable};
    private int[] infoSourceBackground = new int[]{R.color.colorPinkLight,R.color.colorBlueGreenLight,
                                                                    R.color.colorPurpleLight,R.color.colorYellowLight,
                                                                    R.color.colorBlueLight};
    private final AssetManager assets;

    public WeatherListAdapter(List<Weather> list,Context context){
        inflater = LayoutInflater.from(context);
        this.list = list;
        assets = context.getAssets();
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.weather_listview_item, null);//注意这里是null
        WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, final int position) {
        Weather weather = list.get(position);
        holder.tvCity.setText(weather.getCity());
        holder.tvTemp.setText(weather.getTemperature()+"°");
        holder.tvWind.setText("风向："+weather.getWind());
        holder.tvVisible.setText("能见度："+weather.getVisible());
        holder.tvTime.setText(weather.getTime());
        setBackGroundByPosition(holder,position);
        setWeatherImageVIew(holder,weather.getInfo());
        holder.ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
        holder.ll_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemLongClickListener.onItemLongClick(position);
                return false;
            }
        });

    }

    private void setWeatherImageVIew(WeatherViewHolder holder,String info) {

        try {
            InputStream imgInputStream = assets.open(info + ".png");
            Bitmap weatherIcon = BitmapFactory.decodeStream(imgInputStream);
            holder.ivWeather.setImageBitmap(weatherIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setBackGroundByPosition(WeatherViewHolder holder,int position) {
        int point = position % 5;
        holder.ivWeather.setBackgroundResource(imgSourceBackground[point]);
        holder.ll_right.setBackgroundResource(infoSourceBackground[point]);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{

        ImageView ivWeather;
        TextView tvTemp;
        TextView tvCity;
        TextView tvTime;
        TextView tvWind;
        TextView tvVisible;
        LinearLayout ll_layout;
        LinearLayout ll_right;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ivWeather = (ImageView) itemView.findViewById(R.id.iv_weather);
            tvTemp = (TextView) itemView.findViewById(R.id.tv_temp);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvWind = (TextView) itemView.findViewById(R.id.tv_wind);
            tvVisible = (TextView) itemView.findViewById(R.id.tv_visible);
            ll_layout = (LinearLayout) itemView.findViewById(R.id.ll_layout);
            ll_right = (LinearLayout) itemView.findViewById(R.id.ll_right);
        }
    }
}
