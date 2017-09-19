package com.example.youjingjing.myelectronicbalance.widge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.beans.PLU;
import com.example.youjingjing.myelectronicbalance.fragment.ContentFragment;
import com.example.youjingjing.myelectronicbalance.utils.CommonUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    String mRecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!=null){
            mRecond = intent.getStringExtra("plu_name");
        }

        List<PLU> plus = DataSupport.where("name = ?", mRecond).find(PLU.class);
        showKeyBoard();
        //对数据源进行排序
        CommonUtil.sortData(plus);
        ContentFragment contentFragment = new ContentFragment(ResultActivity.this,plus);
        FragmentTransaction replace = getSupportFragmentManager().beginTransaction().replace(R.id.content, contentFragment);
        replace.commit();
//        if (myAscyncTask == null) {
//            myAscyncTask = new MainActivity.MyAscyncTask(ResultActivity.this);
//
//            plus.get(0).getStores().get(0));
//        }
    }
    public void showKeyBoard() {
        View view = getCurrentFocus();
        if(view!=null){
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                    .showSoftInputFromInputMethod(view.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
}
