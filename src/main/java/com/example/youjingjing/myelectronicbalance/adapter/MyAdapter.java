package com.example.youjingjing.myelectronicbalance.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.beans.PLU;
import com.example.youjingjing.myelectronicbalance.beans.Store;
import com.example.youjingjing.myelectronicbalance.dialog.CustomDialog;
import com.example.youjingjing.myelectronicbalance.dialog.OnSureListener;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by youjingjing on 2017/7/24.
 */

public class MyAdapter extends BaseAdapter<PLU> {
    CustomDialog mDialog;
    OnSureListener onSureListener;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private RecyclerItemClickListener.OnItemClickListener mListener;

    public MyAdapter(Context context, List<PLU> data, int layoutId,IonSlidingViewClickListener ionSlidingViewClickListener) {
        super(context, data, layoutId);
        mIDeleteBtnClickListener = ionSlidingViewClickListener;
    }

    public void setListener(RecyclerItemClickListener.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onBind(BaseHolder holder, final PLU plu, final int position) {
        //根据控件Id获取Item内部的控件
        TextView tvName = holder.getView(R.id.recycle_title);
        final TextView tvContent = holder.getView(R.id.recycle_content);

        tvContent.setText(plu.getPrice());
        tvName.setText(plu.getName());
        ImageView imageView = holder.getView(R.id.recycle_imageview);

//        Glide.with(context).load(R.drawable.camera).placeholder(R.drawable.yu).error(R.drawable.bg).into(imageView);
        Glide.with(context).load(plu.getImageUri()).override(60,60)
                .centerCrop()//剪裁比例
//                .placeholder(R.drawable.yu)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//图片缓存模式
                .error(R.drawable.yu)
                .skipMemoryCache(false)
                .into(imageView);


       holder.setOnClickListener(R.id.recycle_update, new View.OnClickListener() {

           @Override
           public void onClick(View view) {

               onSureListener = new OnSureListener() {
                   @Override
                   public void getText(String text) {

                           tvContent.setText(text);

                           plu.setPrice(text);
                           ContentValues values = new ContentValues();
                           values.put("price", text);
                           DataSupport.update(PLU.class, values, plu.getId());

                           List<Store> stores = plu.getStores();
                           ContentValues v = new ContentValues();
                           SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                           Date d = new Date();
                           String date = sDateFormat.format(d);
                           Log.e( "getText: ", ""+d.getDate());
                           v.put("time", date);
                           DataSupport.update(Store.class,v,stores.get(0).getId());
                           Toast.makeText(context, " " + text, Toast.LENGTH_LONG).show();
                   }
               };
               mDialog=new CustomDialog(context, "修改价格", "￥：", "确定", onSureListener, new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
//                       mDialog.dismiss();

                   }
               },"取消");
//                mDialog.setCanotBackPress();//设置屏蔽返回键
//                mDialog.setCanceledOnTouchOutside(false);
               mDialog.show();
           }
       });

        holder.setOnClickListener(R.id.recycle_imageview, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view,position);
            }
        });


        holder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIDeleteBtnClickListener.onDeleteBtnCilck(view);
            }
        });
      holder.setOnClickListener(R.id.btnTop, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    public void addData(int position){
        PLU plu  = new PLU();
        plu.setName("PLU"+position);
        plu.setPrice("10");
        data.add(position, plu);
        //通知适配器item内容插入
        notifyItemInserted(position);
    }
    public void RemoveData(int position){
        data.remove(position);
        //通知适配器item内容删除
        notifyItemRemoved(position);
    }


    //    @Override
//    public void onBind(BaseHolder holder, final Contact contact, int position) {
//        //根据控件Id获取Item内部的控件
//        TextView tvName = holder.getView(R.id.tvName);
//        tvName.setText(contact.getName());
//        //根据Item中的控件Id向控件添加事件监听
//        holder.setOnClickListener(R.id.ivPhone, new View.OnClickListener() {
//            @Override
//               public void onClick(View v) {
//                 //自定义的代码
//              }
//        });
//    }
public interface IonSlidingViewClickListener {
    void onItemClick(View view);
    void onDeleteBtnCilck(View view);
}
}