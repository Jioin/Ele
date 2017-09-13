package com.example.youjingjing.myelectronicbalance.manager;

import android.content.Context;

import com.example.youjingjing.myelectronicbalance.beans.Store;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by youjingjing on 2017/8/3.
 */

public class StoreInfoManager {
    private Context context;//activity

    public StoreInfoManager(Context context) {
        this.context = context;
    }

    public  List<Store> getStores(String user_id){
        List<Store> stores = DataSupport.select("name").where("user_id = ?", user_id).find(Store.class);
        return stores;
    }
}
