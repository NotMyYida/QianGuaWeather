package com.mycj.weather.citylist;

import com.mycj.weather.service.ChinaCity;

/**
 * Created by Hqs on 2017/5/17.
 * Company : MYCJ
 * 加载城市列表
 */
public interface OnLoadCityListener {

    void onSuccess(ChinaCity[] chinaCities);

    void onFail(String msg);

}
