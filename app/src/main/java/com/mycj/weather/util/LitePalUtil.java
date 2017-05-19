package com.mycj.weather.util;

import com.mycj.weather.service.ChinaCity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Hqs on 2017/5/17.
 * Company : MYCJ
 * 用于操作LitePal数据库的Util
 */
public class LitePalUtil {

    /**
     * 模糊查询
     * @param city
     * @return
     */
    public static List<ChinaCity> getCityByName(String city){
        List<ChinaCity> chinaCities = DataSupport.where("cityZh like ?", city).find(ChinaCity.class);
        return chinaCities;
    }


    /**
     * 获取某省份的所有城市
     * @param province
     * @return
     */
    public static List<ChinaCity> getCityForProvince(String province){
        List<ChinaCity> chinaCities = DataSupport.where("provinceZh = ?", province).find(ChinaCity.class);
        return chinaCities;
    }


    /**
     * 根据市级城市获得县级城市
     * @param leaderCity
     * @return
     */
    public static List<ChinaCity> getCityForLeaderCity(String leaderCity){
        List<ChinaCity> chinaCities = DataSupport.where("leaderZh = ?", leaderCity).find(ChinaCity.class);
        return chinaCities;
    }


}
