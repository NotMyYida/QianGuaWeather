package com.mycj.weather.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mycj.weather.R;
import com.mycj.weather.activity.ListActivity;
import com.mycj.weather.bean.FavorCity;
import com.mycj.weather.citylist.GroupItem;
import com.mycj.weather.citylist.GroupItemAdapter;
import com.mycj.weather.service.ChinaCity;
import com.mycj.weather.util.LitePalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hqs on 2017/5/22.
 * Company : MYCJ
 */
public class KeyWordSearchFragment extends Fragment {

    private EditText etCity;
    private ImageView btnSearch;
    private ListView lvSearchList;
    private List<GroupItem> groupItemList;
    private GroupItemAdapter adapter;
    private ProgressDialog mProgressDialog;
    public static final int MSG_LOAD_CITY_SUCCESS = 0;

    private Handler mHandler ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keywords, null, false);
        etCity = (EditText) view.findViewById(R.id.et_city);
        btnSearch = (ImageView) view.findViewById(R.id.btn_search);
        lvSearchList = (ListView) view.findViewById(R.id.lv_search_list);
        groupItemList = new ArrayList<GroupItem>();
        adapter = new GroupItemAdapter(getActivity(),groupItemList);
        lvSearchList.setAdapter(adapter);
        mProgressDialog = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupItem groupItem = groupItemList.get(position);
                List<FavorCity> favorCities = LitePalUtil.getFavorCityByCityNameAndLeadCity(groupItem.getmTitle(), groupItem.getmContent());
                if(favorCities.size() > 0){
                    Toast.makeText(getActivity(),"已经收藏过这个城市了",Toast.LENGTH_SHORT).show();
                }else{
                    String cityId = LitePalUtil.getCityCodeByCityAndLeadCity(groupItem.getmTitle(), groupItem.getmContent());
                    FavorCity favorCity = new FavorCity(groupItem.getmTitle(),cityId,groupItem.getmContent());
                    boolean save = favorCity.save();
                    if(save){
                        Toast.makeText(getActivity(),"收藏成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra("city",cityId);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getActivity(),"收藏失败 可以联系开发者：QQ 330589703",Toast.LENGTH_SHORT).show();

                }

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String city = etCity.getText().toString();
                if(TextUtils.equals(city,"")){
                    Toast.makeText(getActivity(),"城市为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(groupItemList.size()>0) {
                    groupItemList.clear();
                }
                mProgressDialog.setMessage("正在加载……");
                mProgressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<ChinaCity> chinaCities = LitePalUtil.getCityByName(city);
                        LitePalUtil.setChinaCitiesToGroupItems(chinaCities,groupItemList);
                        mHandler.sendEmptyMessage(MSG_LOAD_CITY_SUCCESS);
                    }
                }).start();
            }
        });
    }


    public void flushListItem(){
        adapter.notifyDataSetChanged();
        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        if(groupItemList.size() == 0){
            Toast.makeText(getActivity(),"没有这个城市",Toast.LENGTH_SHORT).show();
        }
    }


    public void setHandler(Handler handler){
        this.mHandler = handler;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
