package com.mycj.weather.citylist;


import com.mycj.weather.widget.wheelView.WheelAdapter;

import java.util.List;

/**
 * Created by Hqs on 2017/5/24.
 * Company : MYCJ
 */
public class WheelStringArrayAdapter implements WheelAdapter {

    private List<String> strings;

    public WheelStringArrayAdapter(List<String> strings){
        this.strings = strings;
    }

    @Override
    public int getItemsCount() {
        return strings.size();
    }

    @Override
    public String getItem(int index) {
        return strings.get(index);
    }

    @Override
    public int getMaximumLength() {
        return strings.size();
    }
}
