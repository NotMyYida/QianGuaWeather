package com.mycj.weather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mycj.weather.R;
import com.mycj.weather.util.RetrofitUtil;

/**
 * Created by Hqs on 2017/5/16.
 * Company : MYCJ
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalish);

        RetrofitUtil retrofitUtil = RetrofitUtil.newinstance();
        retrofitUtil.loadAllChinaCity();
    }
}
