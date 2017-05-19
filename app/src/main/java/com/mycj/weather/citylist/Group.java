package com.mycj.weather.citylist;

import java.util.List;

/**
 * Created by Hqs on 2017/5/18.
 * Company : MYCJ
 */
public class Group {

    private String mGroupName;
    private List<GroupItem> mGroupItem;

    public Group(String mGroupName, List<GroupItem> mGroupItem) {
        this.mGroupName = mGroupName;
        this.mGroupItem = mGroupItem;
    }

    public String getmGroupName() {
        return mGroupName;
    }

    public void setmGroupName(String mGroupName) {
        this.mGroupName = mGroupName;
    }

    public List<GroupItem> getmGroupItem() {
        return mGroupItem;
    }

    public void setmGroupItem(List<GroupItem> mGroupItem) {
        this.mGroupItem = mGroupItem;
    }
}
