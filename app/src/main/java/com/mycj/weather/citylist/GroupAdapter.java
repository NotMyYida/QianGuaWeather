package com.mycj.weather.citylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mycj.weather.R;

import java.util.List;

/**
 * Created by Hqs on 2017/5/18.
 * Company : MYCJ
 */
public class GroupAdapter extends BaseAdapter {

    private List<Group> mGroupList;
    private boolean isShowGroupItem = false;
    private LayoutInflater inflater;
    private Context mContext;

    public GroupAdapter(Context mContext,List<Group> mGroupList){
        this.mGroupList = mGroupList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }



    @Override
    public int getCount() {
        return mGroupList.size();
    }

    @Override
    public Group getItem(int position) {
        return mGroupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView =  inflater.inflate(R.layout.item_group,null);
            holder.tvGroupName = (TextView) convertView.findViewById(R.id.tv_group_name);
            holder.lvGroupItem = (ListView) convertView.findViewById(R.id.lv_group_item);
            holder.rlGroupItem = (RelativeLayout) convertView.findViewById(R.id.rl_group_item);
            holder.ivIsExpand = (ImageView) convertView.findViewById(R.id.iv_is_expanded);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Group item = getItem(position);
        holder.tvGroupName.setText(item.getmGroupName());
        holder.lvGroupItem.setAdapter(new GroupItemAdapter(mContext,item.getmGroupItem()));
        holder.lvGroupItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext,"position："+position+"  "+item.getmGroupItem().get(position),Toast.LENGTH_SHORT).show();
            }
        });

        setGroupHeight(holder.lvGroupItem);

        holder.rlGroupItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowGroupItem){
                    holder.lvGroupItem.setVisibility(View.VISIBLE);
                    isShowGroupItem = !isShowGroupItem;
                }else{
                    holder.lvGroupItem.setVisibility(View.INVISIBLE);
                    isShowGroupItem = !isShowGroupItem;
                }

            }
        });

        return convertView;
    }




    /**
     * 这是一个神奇的方法，在所有的View嵌套问题中都需要解决这个问题
     * @param listView
     */
    private void setGroupHeight(ListView listView){
        int totalHeight = 0;
        ListAdapter adapter = listView.getAdapter();
        for(int i = 0; i < adapter.getCount() ; i++){
            View itemView = adapter.getView(i, null, listView);
            itemView.measure(0,0);
            totalHeight += itemView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setMinimumHeight(totalHeight);
    }

    class ViewHolder{
        ListView lvGroupItem;
        TextView tvGroupName;
        ImageView ivIsExpand;
        RelativeLayout rlGroupItem;
    }
}
