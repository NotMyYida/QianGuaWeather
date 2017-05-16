package com.mycj.weather;


import org.litepal.LitePalApplication;

/**
 * Created by Hqs on 2017/5/16.
 * Company : MYCJ
 */
public class App extends LitePalApplication {

    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static App getContext(){
        return  app;
    }
}
