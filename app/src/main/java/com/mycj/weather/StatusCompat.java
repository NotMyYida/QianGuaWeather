package com.mycj.weather;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hqs on 2017/5/22.
 * Company : MYCJ
 * Author： zhy on 15/9/21
 */
public class StatusCompat {
    private static final int INVALID_VAL = -1;
    private static final int COLOR_DEFAULT = Color.parseColor( "#20000000" );

    public static void compat( Activity ac ,int statusColor ){
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            if( statusColor != INVALID_VAL ){
                ac.getWindow().setStatusBarColor(statusColor);
            }
            return;
        }

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ){
            int color = COLOR_DEFAULT;
            ViewGroup contentView = (ViewGroup) ac.findViewById(android.R.id.content);
            if( statusColor != INVALID_VAL ){
                color = statusColor;
            }
            View statusBarView = new View( ac );
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                                ( ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight( ac ) );
            statusBarView.setBackgroundColor( color );
            contentView.addView( statusBarView,lp );
        }
    }


    public static void compat( Activity ac ){
        compat( ac,INVALID_VAL );
    }


    public static int getStatusBarHeight( Context context ){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if( resourceId > 0 ){
            result = context.getResources().getDimensionPixelOffset(resourceId);
        }
        return result;
    }

}
