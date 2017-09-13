package com.example.youjingjing.myelectronicbalance.presenter;


import com.example.youjingjing.myelectronicbalance.view.BaseView;

/**
 * Created by youjingjing on 2017/7/19.
 */

public interface BasePresenter<V extends BaseView> {
    public void attachView(V view);
    public void detachView(boolean retainInstance);
}
