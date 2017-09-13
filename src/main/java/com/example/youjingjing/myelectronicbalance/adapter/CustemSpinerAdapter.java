package com.example.youjingjing.myelectronicbalance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.beans.Store;

/**
 * Created by youjingjing on 2017/8/2.
 */

public class CustemSpinerAdapter extends AbstractSpinerAdapter<Store> {
    public CustemSpinerAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int pos, View convertView) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Store item = (Store) getItem(pos);
        AbstractSpinerAdapter.ViewHolder.mTextView.setText(item.getName());

        return convertView;
    }
}
