package com.example.youjingjing.myelectronicbalance.beans;

import android.database.Cursor;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youjingjing on 2017/7/27.
 */

public class User extends DataSupport{

    private String name;
    private List<Store> authority = new ArrayList<>();
    private String password;
    private String time;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Store> getAuthority() {
//        return DataSupport.where("user_id = ?", String.valueOf(id)).find(Store.class);
        Cursor cursor = DataSupport.findBySQL("select * from store_user where user_id = ?", String.valueOf(id));
        List<Store> findstores = new ArrayList<>();
        if (cursor!=null&&cursor.moveToFirst()){
            do{
                int store_id = cursor.getInt(cursor.getColumnIndex("store_id"));
                List<Store> stores = DataSupport.where("id = ?", String.valueOf(store_id)).find(Store.class);
                findstores.add(stores.get(0));
            }while(cursor.moveToNext());
        }
            return findstores;
//        return authority;
    }

    public void setAuthority(List<Store> authority) {
        this.authority = authority;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    @Override
    public String toString() {
        return "id:"+id+" name:"+name+" password:"+password+" authority:"+authority+" time:"+time;
    }
}
