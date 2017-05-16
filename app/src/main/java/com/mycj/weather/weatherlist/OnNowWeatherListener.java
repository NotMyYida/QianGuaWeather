package com.mycj.weather.weatherlist;

import com.mycj.weather.bean.Weather;

/**
 * Created by Hqs on 2017/5/16.
 * Company : MYCJ
 */
public interface OnNowWeatherListener {

    void onNowWeatherData(Weather weather);

    void onFail(String msg);
}
