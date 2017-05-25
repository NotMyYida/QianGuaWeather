package com.mycj.weather.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;
import com.mycj.weather.R;
import com.mycj.weather.activity.ListActivity;
import com.mycj.weather.bean.FavorCity;
import com.mycj.weather.citylist.WheelStringArrayAdapter;
import com.mycj.weather.util.LitePalUtil;
import com.mycj.weather.widget.wheelView.WheelAdapter;
import com.mycj.weather.widget.wheelView.WheelView;

import java.util.List;

/**
 * Created by Hqs on 2017/5/22.
 * Company : MYCJ
 */
public class ProvinceFragment extends Fragment implements View.OnClickListener {

    public static final int MSG_LOAD_PROVINCE_SUCCESS = 1;
    private Button btnCertain;
    private String city;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_province,null,false);

        btnCertain = (Button) view.findViewById(R.id.btn_certain);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCertain.setOnClickListener(this);


        }



    public void createPickCitiesDialog(){
        CityPicker cityPicker = new CityPicker.Builder(getActivity())
                .textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#888888")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("广东省")
                .city("深圳市")
                .district("福田区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                String leadcity = city;
                int cityIndex  ;
                if(city.endsWith("地区")){
                    cityIndex = city.indexOf("地区");
                    leadcity = city.substring(0,cityIndex);
                }else if(city.endsWith("市")){
                    cityIndex = city.indexOf("市");
                    leadcity = city.substring(0,cityIndex);
                }
                if(city.contains("自治")){
                    leadcity = city.substring(0,2);
                }


                String cityKeyWord = district;
                int districtIndex ;
                if(district.endsWith("市")){
                    districtIndex = district.indexOf("市");
                    cityKeyWord = district.substring(0,districtIndex);
                }else if(district.endsWith("县")){
                    districtIndex = district.indexOf("县");
                    cityKeyWord = district.substring(0,districtIndex);
                }else if(district.endsWith("区")){
                    districtIndex = district.indexOf("区");
                    cityKeyWord = district.substring(0,districtIndex);
                }

                if(district.contains("自治")){
                    cityKeyWord= district.substring(0,2);
                }

                if(cityKeyWord.equals("城区")){
                    cityKeyWord = leadcity;
                }


                String cityCode = LitePalUtil.getCityCodeByCityAndLeadCity(cityKeyWord, leadcity);
                if(cityCode.equals("无此城市服务")){
                    Toast.makeText(getActivity(),cityCode,Toast.LENGTH_SHORT).show();
                    return;
                }
                List<FavorCity> favorCities = LitePalUtil.getFavorCityByCityNameAndLeadCity(cityKeyWord, leadcity);
                if(favorCities.size() > 0){
                    Toast.makeText(getActivity(),"已经收藏过这个城市了",Toast.LENGTH_SHORT).show();
                }else{

                    FavorCity favorCity = new FavorCity(cityKeyWord,cityCode,leadcity);
                    boolean save = favorCity.save();
                    if(save){
                        Toast.makeText(getActivity(),"收藏成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra("city",cityCode);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getActivity(),"收藏失败 可以联系开发者：QQ 330589703",Toast.LENGTH_SHORT).show();

                }

//                Toast.makeText(getActivity(),province+" "+city+" "+ district,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
//                Toast.makeText(getActivity(), "已取消", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.btn_certain:
                createPickCitiesDialog();
                break;
        }
    }

    class LoadDataAsynTask extends AsyncTask<Integer,Void,Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {
            int type = params[0];
            switch(type){
                case 1:
//                    leadCities =  LitePalUtil.getLeadCityByProvince(province);
                    break;

                case 2:
//                    cities = LitePalUtil.getCityByLeadCity(leadCity);
                    break;

                case 3:

                    break;
            }
            return type;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer == 1){
//                wheelViewLeadCity.setItems(leadCities);
            }else if(integer == 2){
//                wheelViewCity.setItems(cities);
            }
        }
    }
}
