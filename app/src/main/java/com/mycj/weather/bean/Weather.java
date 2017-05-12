package com.mycj.weather.bean;

/**
 * Created by Hqs on 2017/5/9.
 * Company : MYCJ
 */
public class Weather {

    private String info;
    private int temperature;
    private String city;
    private String wind;
    private String visible;
    private String time;

    public Weather(){}

    public Weather(int temperature, String city, String wind, String visible, String time) {
        this.temperature = temperature;
        this.city = city;
        this.wind = wind;
        this.visible = visible;
        this.time = time;
    }

    public Weather(String info, int temperature, String city, String wind, String visible, String time) {
        this.info = info;
        this.temperature = temperature;
        this.city = city;
        this.wind = wind;
        this.visible = visible;
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
