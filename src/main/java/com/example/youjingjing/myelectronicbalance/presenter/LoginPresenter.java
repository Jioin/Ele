package com.example.youjingjing.myelectronicbalance.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.youjingjing.myelectronicbalance.beans.Store;
import com.example.youjingjing.myelectronicbalance.model.LoginModel;
import com.example.youjingjing.myelectronicbalance.model.LoginModelIml;
import com.example.youjingjing.myelectronicbalance.utils.ToolUtils;
import com.example.youjingjing.myelectronicbalance.view.LoginView;
import com.example.youjingjing.myelectronicbalance.widge.MainActivity;

import java.util.ArrayList;


/**
 * Created by youjingjing on 2017/7/19.
 */

public class LoginPresenter extends BasePresenterIml<LoginView>{

    /**
     * 登录业务实现者，数据处理的操作者
     */
    private LoginModelIml loginModelIml;

    /**
     * 在构造方法中实例化model对象
     */
    public LoginPresenter() {
        loginModelIml = new LoginModelIml();
    }

    /**
     * 登录业务
     */
    public void login(Context context, final String username, final String password,final int port,final String host) {
        if (isViewAttached()) {//判断界面是否还在（用户没有点返回键）
            if (!ToolUtils.isNetWorkAvailable(context)) { //无网络，显示网络无连接
                getView().showNetWorkError();
                return;
            }

            getView().showLogging(); //显示正在登录中
            //登录 ，需要处理数据，所有要在model中执行
            loginModelIml.login(context, username, password,host,port,new LoginModel.OnLoginResultListener() {//访问Model

                @Override
                public void onSuccess(ArrayList<Store> stores) {
                    if (isViewAttached()) {

                        getView().showLoginSuccess(); //显示登录成功
                        //传递数据
                        Bundle bundle = new Bundle();
                        bundle.putString("name",username);
//                        ArrayList<String> names = new ArrayList<String>();
//                        for (Store store: stores
//                             ) {
//                            names.add(store.getName());
//                        }
//                        bundle.putStringArrayList("storename",names);

                        bundle.putParcelableArrayList("stores",stores);
//                        bundle.putSerializable("stores",stores);
                        Log.e( "onSuccess: ", "门店"+stores);
                        getView().jumpActivity(MainActivity.class, bundle);
                    }
                }

                @Override
                public void onFailure(String msg) {
                    if (isViewAttached()) {
                        getView().showLoginFailure(msg);//显示登录失败
//                            getView().jumpActivity();
                    }
                }
            });
        }
    }
}

