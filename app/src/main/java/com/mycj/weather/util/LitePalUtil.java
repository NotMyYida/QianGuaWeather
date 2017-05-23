package com.mycj.weather.util;

import com.mycj.weather.citylist.GroupItem;
import com.mycj.weather.service.ChinaCity;

import org.litepal.crud.DataSupport;

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


    public static String[] getProvince(){
        List<ChinaCity> provincesClass = DataSupport.select("provinceZh").find(ChinaCity.class);
        Set<String> provincesSet = new HashSet<String>();

        for(ChinaCity chinaCity : provincesClass){
            provincesSet.add(chinaCity.getProvinceZh());
        }
        String[] provinces = new String[provincesSet.size()];
//        provinces = (String[]) provincesSet.toArray();
        String[] strings = provincesSet.toArray(provinces);
        return strings;
    }


    public static String[] getLeadCityByProvince(String province){
        List<ChinaCity> leaderZhClass = DataSupport.select("leaderZh").where("provinceZh = ?", province).find(ChinaCity.class);
        String[] leaderCities = new String[leaderZhClass.size()];
        int i = 0;
        for(ChinaCity chinaCity : leaderZhClass){
            leaderCities[i] = chinaCity.getLeaderZh();
            i++ ;
        }
        return leaderCities;
    }


    public static String[] getCityByLeadCity(String leadCity){
        List<ChinaCity> citiesZhClass = DataSupport.select("cityZh").where("leaderZh = ?", leadCity).find(ChinaCity.class);
        String[] cities = new String[citiesZhClass.size()];
        int i = 0;
        for(ChinaCity chinaCity : citiesZhClass){
            cities[i] = chinaCity.getCityZh();
            i++ ;
        }
        return cities;
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
