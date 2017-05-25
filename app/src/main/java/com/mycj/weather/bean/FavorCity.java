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
    private String leadCity;

    public FavorCity(String cityName, String cityCode, String leadCity) {
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.leadCity = leadCity;
    }

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

    public String getLeadCity() {
        return leadCity;
    }

    public void setLeadCity(String leadCity) {
        this.leadCity = leadCity;
    }

    @Override
    public String toString() {
        return "FavorCity{" +
                "cityName='" + cityName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", leadCity='" + leadCity + '\'' +
                '}';
    }
}
