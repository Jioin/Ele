package com.example.youjingjing.myelectronicbalance.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youjingjing.myelectronicbalance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youjingjing on 2017/8/2.
 */

public abstract class AbstractSpinerAdapter <T> extends android.widget.BaseAdapter {

    public static interface IOnItemSelectListener {
        public void onItemClick(int pos);
    }

    public Context mContext;
    private List<T> mObjects = new ArrayList<T>();
    private int mSelectItem = 0;



    public AbstractSpinerAdapter(Context context) {
        mContext = context;
    }

    public void refreshData(List<T> objects, int selIndex) {
        mObjects = objects;
        if (selIndex < 0) {
            selIndex = 0;
        }
        if (selIndex >= mObjects.size()) {
            selIndex = mObjects.size() - 1;
        }

        mSelectItem = selIndex;
    }


    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Object getItem(int pos) {
        return mObjects.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup arg2) {
       return getView(pos, convertView);
    }

    public abstract View getView(int pos, View convertView);

    public static class ViewHolder
    {
        View viewHoler;
        public static  TextView mTextView;
        public static ImageView mImageView;

        public ViewHolder(View view) {
            this.viewHoler = view;
            mTextView = view.findViewById(R.id.tv_name);
        }
    }


}