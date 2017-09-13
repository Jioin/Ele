package com.example.youjingjing.myelectronicbalance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.beans.PLURecond;

/**
 * Created by youjingjing on 2017/8/24.
 */

public class HistorySpinnerAdapter extends AbstractSpinerAdapter<PLURecond>{

    public HistorySpinnerAdapter(Context context) {
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

        PLURecond item = (PLURecond) getItem(pos);
        AbstractSpinerAdapter.ViewHolder.mTextView.setText(item.getName());

        return convertView;
    }
}
