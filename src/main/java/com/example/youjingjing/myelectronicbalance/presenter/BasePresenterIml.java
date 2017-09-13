package com.example.youjingjing.myelectronicbalance.presenter;


import com.example.youjingjing.myelectronicbalance.view.BaseView;

import java.lang.ref.WeakReference;

/**
 * Created by youjingjing on 2017/7/19.
 */

public class BasePresenterIml<V extends BaseView> implements BasePresenter<V> {
    private WeakReference<V> viewRef;

    /**
     * 界面创建，Presenter与界面取得联系
     */
    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    /**
     * 获取界面的引用
     */
    protected V getView() {
        return viewRef == null ? null : viewRef.get();//不空的时候才能得到
    }

    /**
     * 判断界面是否销毁
     */
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }


    /**
     * 界面销毁，Presenter与界面断开联系
     */
    @Override
    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}