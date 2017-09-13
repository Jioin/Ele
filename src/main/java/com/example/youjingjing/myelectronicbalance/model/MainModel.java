package com.example.youjingjing.myelectronicbalance.model;

import android.content.Context;

/**
 * Created by youjingjing on 2017/7/13.
 */

public interface MainModel {
    void loadPLU(Context context,String name);
    void updatePLU(Context context,String price);
}
