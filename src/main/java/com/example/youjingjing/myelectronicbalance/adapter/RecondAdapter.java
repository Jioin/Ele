package com.example.youjingjing.myelectronicbalance.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.beans.PLURecond;

import java.util.List;

/**
 * Created by youjingjing on 2017/9/11.
 */

public class RecondAdapter extends BaseAdapter<PLURecond>{

    private RecyclerItemClickListener.OnItemClickListener mListener;
    public RecondAdapter(Context context, List<PLURecond> data, int layoutId) {
        super(context, data, layoutId);
    }
    public void setListener(RecyclerItemClickListener.OnItemClickListener listener) {
        mListener = listener;
    }
    @Override
    public void onBind(BaseHolder holder, PLURecond pluRecond, final int position) {
        TextView tv = holder.getView(R.id.recycle_recond_tv);
        tv.setText(pluRecond.getName());

        holder.setOnClickListener(R.id.recycle_recond_tv, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view,position);
            }
        });
    }
}
