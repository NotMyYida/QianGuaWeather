package com.mycj.weather.citylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mycj.weather.R;

import java.util.List;

/**
 * Created by Hqs on 2017/5/18.
 * Company : MYCJ
 */
public class GroupItemAdapter extends BaseAdapter {

    private List<GroupItem> mItems;
    private final LayoutInflater inflater;

    public GroupItemAdapter(Context mContext, List<GroupItem> mItems) {
        this.mItems = mItems;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public GroupItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_groupitem, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(getItem(position).getmTitle());
        viewHolder.tvContent.setText(getItem(position).getmContent());

        return convertView;
    }


    class ViewHolder{
        TextView tvTitle;
        TextView tvContent;
    }
}
