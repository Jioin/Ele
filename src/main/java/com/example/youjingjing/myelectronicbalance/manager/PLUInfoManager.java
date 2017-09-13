package com.example.youjingjing.myelectronicbalance.manager;

import android.content.Context;

import com.example.youjingjing.myelectronicbalance.beans.PLU;
import com.example.youjingjing.myelectronicbalance.beans.Store;

import java.util.List;

/**
 * Created by youjingjing on 2017/8/3.
 */

public class PLUInfoManager {
    private Context context;//activity

    public PLUInfoManager(Context context){
        this.context = context;
    }

    public List<PLU> getPLU(Store store){
//        List<PLU> name = DataSupport.where("store_id = ?", store_id).find(PLU.class);
//        List<Store> stores = DataSupport.select("id").where("name = ?", storeName).find(Store.class);
        List<PLU> plus = store.getPlus();

        return plus;
    }



}
