package com.example.youjingjing.myelectronicbalance.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by youjingjing on 2017/7/13.
 */

public class ToolUtils {
    /*获取屏幕分辨率*/
    public static final int getHeightInPx(Context context){
        final int height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }
    public static final int getWidthInPx(Context context){
        final int width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }
    /**
     * 判断网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetWorkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            if(connectivityManager.getActiveNetworkInfo().isAvailable()){
                Log.e("network---","avaliabale");
                return true;
            }
        }
        return false;
    }

}
