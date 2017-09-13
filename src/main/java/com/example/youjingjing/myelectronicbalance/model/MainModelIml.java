package com.example.youjingjing.myelectronicbalance.model;

import android.content.Context;

import com.example.youjingjing.myelectronicbalance.beans.PLU;

import org.litepal.crud.DataSupport;

/**
 * Created by youjingjing on 2017/8/2.
 */

public class MainModelIml implements MainModel {

    @Override
    public void loadPLU(Context context,String storeName) {
        DataSupport.select("name").where("name = ?",storeName).find(PLU.class);
        DataSupport.select("price").where("name = ?",storeName).find(PLU.class);
    }

    @Override
    public void updatePLU(Context context, String price) {

    }

}
