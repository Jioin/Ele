package com.example.youjingjing.myelectronicbalance.model;

import android.content.Context;

import com.example.youjingjing.myelectronicbalance.beans.Store;

import java.util.ArrayList;

/**
 * Created by youjingjing on 2017/7/13.
 */

public interface LoginModel {
    public void login(Context context, String name, String password,String host,int port, OnLoginResultListener callback);

    //登录回调接口
    public interface OnLoginResultListener{
        void onSuccess(ArrayList<Store> stores);
        void onFailure(String msg);
    }
}
