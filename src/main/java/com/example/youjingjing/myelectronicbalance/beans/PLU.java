package com.example.youjingjing.myelectronicbalance.beans;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youjingjing on 2017/7/27.
 */

public class PLU extends DataSupport implements Parcelable {
    private int id;
    private String name;
    private String time;
    private String price;
    private String plu_id;
    private String imageUri;
    //数据库中数据可以为空
    private String indexTag;
    private List<Store> stores = new ArrayList<>();
    public PLU() {
    }


    protected PLU(Parcel in) {
        id = in.readInt();
        name = in.readString();
        time = in.readString();
        price = in.readString();
        plu_id = in.readString();
        imageUri = in.readString();
        indexTag = in.readString();
        stores = in.createTypedArrayList(Store.CREATOR);
    }

    public String getIndexTag() {
        return indexTag;
    }

    public void setIndexTag(String indexTag) {
        this.indexTag = indexTag;
    }

    public static final Creator<PLU> CREATOR = new Creator<PLU>() {
        @Override
        public PLU createFromParcel(Parcel in) {
            return new PLU(in);
        }

        @Override
        public PLU[] newArray(int size) {
            return new PLU[size];
        }
    };

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlu_id() {
        if(getStores().size()<2) {
            plu_id = ""+id;
        }else{
            plu_id = name;
        }
        return plu_id;
    }

    public void setPlu_id(String plu_id) {
        this.plu_id = plu_id;
    }

    public List<Store> getStores() {
//        return stores;
        Cursor cursor = DataSupport.findBySQL("select * from plu_store where plu_id = ?", String.valueOf(id));
        List<Store> findstores = new ArrayList<>();
        if (cursor!=null&&cursor.moveToFirst()){
            do{
                int store_id = cursor.getInt(cursor.getColumnIndex("store_id"));
                List<Store> stores = DataSupport.where("id = ?", String.valueOf(store_id)).find(Store.class);
                findstores.add(stores.get(0));
            }while(cursor.moveToNext());
        }
        return findstores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(time);
        parcel.writeString(price);
        parcel.writeString(plu_id);
        parcel.writeString(imageUri);
        parcel.writeTypedList(stores);
        parcel.writeString(indexTag);
    }


    @Override
    public String toString() {
        return "id:"+id+" name:"+name+" plu_id:"+plu_id+" price:"+price+" stores:"+getStores()+" time:"+time;
    }
}
