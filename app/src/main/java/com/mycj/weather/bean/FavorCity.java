package com.mycj.weather.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Hqs on 2017/5/16.
 * Company : MYCJ
 * 收藏的城市
 */
public class FavorCity extends DataSupport {
    private String cityName;
    private String cityCode;
    private String province;

    public FavorCity(){}

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
