package com.example.youjingjing.myelectronicbalance.view;

import android.os.Bundle;

/**
 * Created by youjingjing on 2017/7/19.
 */

public interface BaseView {
    void jumpActivity(Class<?> cls, Bundle bundle);
    void jumpActivity(Class<?> cls);
    void hideKeyBoard();
}
