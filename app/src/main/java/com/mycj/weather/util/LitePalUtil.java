package com.mycj.weather.util;

import com.mycj.weather.bean.FavorCity;
import com.mycj.weather.citylist.GroupItem;
import com.mycj.weather.service.ChinaCity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<ChinaCity> chinaCities = DataSupport.where("cityZh like ? ","%"+ city+"%").find(ChinaCity.class);
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


    public static List<String> getProvince(){
        List<ChinaCity> provincesClass = DataSupport.select("provinceZh").find(ChinaCity.class);
        Set<String> provincesSet = new HashSet<String>();

        for(ChinaCity chinaCity : provincesClass){
            provincesSet.add(chinaCity.getProvinceZh());
        }

        List<String> provinces = new ArrayList<String>();
        provinces.addAll(provincesSet);

        return provinces;
    }


    public static List<String> getLeadCityByProvince(String province){
        List<ChinaCity> leaderZhClass = DataSupport.select("leaderZh").where("provinceZh = ?", province).find(ChinaCity.class);
        List<String> leaderCities = new ArrayList<String>();
        Set<String> leaderCitesSet = new HashSet<String>();
        for(ChinaCity chinaCity : leaderZhClass){
            leaderCitesSet.add(chinaCity.getLeaderZh());
        }
        leaderCities.addAll(leaderCitesSet);
        return leaderCities;
    }


    public static List<String> getCityByLeadCity(String leadCity){
        List<ChinaCity> citiesZhClass = DataSupport.select("cityZh").where("leaderZh = ?", leadCity).find(ChinaCity.class);
        List<String> cities = new ArrayList<String>();
        for(ChinaCity chinaCity : citiesZhClass){
            cities.add(chinaCity.getCityZh());
        }
        return cities;
    }

    public static String getCityCodeByCityAndLeadCity(String city, String leadCity){
        List<ChinaCity> cityid = DataSupport.select("cityid").where("cityZh = ? and leaderZh = ?", city, leadCity).find(ChinaCity.class);
        if(cityid.size() == 1){
            String id = cityid.get(0).getId();
            return id;
        }
        return "无此城市服务";
    }

    /**
     * 通过城市名和直辖市名寻找收藏的城市（因为有的城市名重名）
     * @param cityName
     * @param leadCity
     * @return
     */
    public static List<FavorCity> getFavorCityByCityNameAndLeadCity(String cityName,String leadCity){
        List<FavorCity> favorCities = DataSupport.where("cityName = ? and leadCity=?", cityName,leadCity).find(FavorCity.class);
        return favorCities;
    }

    public static List<FavorCity> findAllFavorCities(){
        List<FavorCity> all = DataSupport.findAll(FavorCity.class);
        return all;
    }

    /**
     * 将ChinaCities的参数传入GroupItems
     * @param chinaCities
     * @param groupItems
     */
    public static void setChinaCitiesToGroupItems(List<ChinaCity> chinaCities, List<GroupItem> groupItems){
        if(chinaCities.size() < 1)
            return;
        for(ChinaCity chinaCity : chinaCities){
            groupItems.add(new GroupItem(chinaCity.getCityZh(),chinaCity.getLeaderZh()));
        }
    }

}
