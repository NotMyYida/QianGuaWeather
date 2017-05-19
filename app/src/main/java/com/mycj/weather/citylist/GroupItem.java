package com.mycj.weather.citylist;

/**
 * Created by Hqs on 2017/5/18.
 * Company : MYCJ
 */
public class GroupItem {

    private String mTitle;
    private String mContent;

    public GroupItem(String mTitle, String mContent) {
        this.mTitle = mTitle;
        this.mContent = mContent;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}
