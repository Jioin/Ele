package com.example.youjingjing.myelectronicbalance.view;

/**
 * Created by youjingjing on 2017/7/19.
 */

public interface LoginView extends BaseView {
    void showLoginSuccess();//显示登录成功

    void showLoginFailure(String msg);//显示登录失败

    void showLogging();//显示在登录中

    void showNetWorkError(); //显示网络错误

    void rememberKey();//记住密码

    void autoLogin();//自动登录

    void setStatus(boolean boo);//登录状态

}

