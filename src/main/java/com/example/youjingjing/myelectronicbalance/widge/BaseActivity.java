package com.example.youjingjing.myelectronicbalance.widge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.youjingjing.myelectronicbalance.presenter.BasePresenterIml;
import com.example.youjingjing.myelectronicbalance.view.BaseView;

import org.litepal.tablemanager.Connector;


/**
 * Created by youjingjing on 2017/7/19.
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenterIml> extends AppCompatActivity implements BaseView {
    protected P presenter;
    SharedPreferences pref;
    SharedPreferences.Editor share_edit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建presenter
        presenter = createPresenter();
        //内存泄露
        //关联View
        if (presenter != null)
            presenter.attachView((V) this);//presenter取得与界面的联系
        pref = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        share_edit = pref.edit();

        isFirstUse();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView(true);//presenter断开与界面的联系
    }

    protected abstract P createPresenter();

    @Override
    public void jumpActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this,cls);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void jumpActivity(Class<?> cls) {
        Intent intent = new Intent(this,cls);
        startActivity(intent);
//        finish();
    }
    public void writeBoolean(String a, boolean boo) {
        share_edit.putBoolean(a, boo);
        share_edit.commit();
    }

    public void writeString(String a, String s) {
        share_edit.putString(a, s);
        share_edit.commit();
    }

    public String readData(String data) {
        String str = pref.getString(data, "");
        return str;
    }

    public boolean readBoolean(String data) {
        return pref.getBoolean(data, false);
    }


    public void hideKeyBoard() {
        View view = getCurrentFocus();
        if(view!=null){
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void isFirstUse() {
        if(pref.getBoolean("isFirst",true)){
//            Toast.makeText(this, "首次使用app", Toast.LENGTH_SHORT).show();
            share_edit.putBoolean("isFirst",false);
            share_edit.commit();
            SQLiteDatabase database = Connector.getDatabase();
        }else{
//            Toast.makeText(this, "再次开启", Toast.LENGTH_SHORT).show();
        }
    }
}

