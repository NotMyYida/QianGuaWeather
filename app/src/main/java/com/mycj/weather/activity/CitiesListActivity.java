package com.mycj.weather.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mycj.weather.R;
import com.mycj.weather.fragment.KeyWordSearchFragment;
import com.mycj.weather.fragment.ProvinceFragment;
import com.mycj.weather.util.L;
import com.mycj.weather.util.LitePalUtil;

/**
 * Created by Hqs on 2017/5/17.
 * Company : MYCJ
 */
public class CitiesListActivity extends AppCompatActivity {

    private RadioGroup rgSearchWay;
    private RadioButton rbProvince;
    private RadioButton rbKeyWord;
    private KeyWordSearchFragment keyWordSearchFragment;
    private ProvinceFragment provinceFragment;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == KeyWordSearchFragment.MSG_LOAD_CITY_SUCCESS){
                if(keyWordSearchFragment.isAdded()){
                    keyWordSearchFragment.flushListItem();
                }
            }else if(msg.what == ProvinceFragment.MSG_LOAD_PROVINCE_SUCCESS){
                String[] provinces = (String[]) msg.obj;
                for(String province : provinces){
                    L.e("provinces count:"+provinces.length+ "  " +province);
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);

        findView();
        initFragment();
        setListener();
    }


    private void findView() {
        rgSearchWay = (RadioGroup) findViewById(R.id.rg_search_way);
        rbProvince = (RadioButton) findViewById(R.id.rb_province);
        rbKeyWord = (RadioButton) findViewById(R.id.rb_keyword);
    }

    private void setListener() {

        rgSearchWay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_keyword){
                    rbKeyWord.setTextColor(Color.parseColor("#ffffff"));
                    rbProvince.setTextColor(Color.parseColor("#000000"));
                    replaceFragment(keyWordSearchFragment);
                }else if(checkedId == R.id.rb_province){
                    rbProvince.setTextColor(Color.parseColor("#ffffff"));
                    rbKeyWord.setTextColor(Color.parseColor("#000000"));

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String[] provinces = LitePalUtil.getProvince();
                            Message message = mHandler.obtainMessage(ProvinceFragment.MSG_LOAD_PROVINCE_SUCCESS, provinces);
                            mHandler.sendMessage(message);
                        }
                    }).start();

                }
            }
        });
        rgSearchWay.check(R.id.rb_province);
    }

    private void initFragment() {
        keyWordSearchFragment = new KeyWordSearchFragment();
        provinceFragment = new ProvinceFragment();
        keyWordSearchFragment.setHandler(mHandler);
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content,fragment);
        fragmentTransaction.commit();
    }


}
