package com.example.youjingjing.myelectronicbalance.widge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.beans.PLU;
import com.example.youjingjing.myelectronicbalance.beans.PLURecond;
import com.example.youjingjing.myelectronicbalance.fragment.ContentFragment;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    ArrayList<PLURecond> mRecond;
    ContentFragment.MyAscyncTask myAscyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_content);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!=null){
            mRecond = intent.getParcelableArrayListExtra("plu_name");
        }

        List<PLU> plus = DataSupport.where("name = ?", mRecond.get(0).getName()).find(PLU.class);

        ContentFragment contentFragment = new ContentFragment();
        if (myAscyncTask == null) {
            myAscyncTask = contentFragment.getInstance(this,plus.get(0).getStores().get(0));
        }
    }
}
