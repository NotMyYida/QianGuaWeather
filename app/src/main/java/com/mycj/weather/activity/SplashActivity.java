package com.mycj.weather.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.mycj.weather.R;
import com.mycj.weather.citylist.OnLoadCityListener;
import com.mycj.weather.config.Config;
import com.mycj.weather.service.ChinaCity;
import com.mycj.weather.util.L;
import com.mycj.weather.util.RetrofitUtil;
import com.mycj.weather.util.SpUtil;

/**
 * Created by Hqs on 2017/5/16.
 * Company : MYCJ
 */
public class SplashActivity extends AppCompatActivity implements OnLoadCityListener {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spalish);

        boolean hasSave = SpUtil.readBoolean(Config.SP_HAS_ALREADY_SAVE_CITIES);
        if(hasSave){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToListActivity();
                }
            },2000);
        }else{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("第一次进入会加载城市……");
            progressDialog.show();
            RetrofitUtil retrofitUtil = RetrofitUtil.newinstance();
            retrofitUtil.loadAllChinaCity();
            retrofitUtil.setOnLoadCityListener(this);
        }

    }

    @Override
    public void onSuccess(ChinaCity[] chinaCities) {
        if(chinaCities != null)
            new SaveChinaCityTask().execute(chinaCities);

    }

    @Override
    public void onFail(String msg) {

    }


    public void goToListActivity(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
        finish();
    }


    class SaveChinaCityTask extends AsyncTask<ChinaCity[],Void,Void>{

        @Override
        protected Void doInBackground(ChinaCity[]... params) {
            ChinaCity[] chinaCities = params[0];
            for(ChinaCity chinaCity : chinaCities){
                chinaCity.saveThrows();
                L.e(SplashActivity.class,"保存了城市："+chinaCity.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Toast.makeText(SplashActivity.this,"加载完成",Toast.LENGTH_SHORT).show();
            SpUtil.writeBoolean(Config.SP_HAS_ALREADY_SAVE_CITIES,true);
            goToListActivity();
        }
    }

}
