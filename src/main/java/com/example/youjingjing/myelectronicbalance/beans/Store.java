package com.example.youjingjing.myelectronicbalance.beans;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youjingjing on 2017/7/31.
 */

public class Store extends DataSupport implements Parcelable{
    private int id;
    private String name;
    private String time;
    private List<PLU> plus = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public Store() {
    }

    protected Store(Parcel in) {
        id = in.readInt();
        name = in.readString();
        time = in.readString();
        plus = in.createTypedArrayList(PLU.CREATOR);
        users = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    public List<PLU> getPlus() {
//        return DataSupport.where("store_id = ?", String.valueOf(id)).find(PLU.class);
//        return plus;
        Cursor cursor = DataSupport.findBySQL("select * from plu_store where store_id = ?", String.valueOf(id));
        List<PLU> findplus = new ArrayList<>();
        if (cursor!=null&&cursor.moveToFirst()){
            do{
                int plu_id = cursor.getInt(cursor.getColumnIndex("plu_id"));
                List<PLU> users = DataSupport.where("id = ?", String.valueOf(plu_id)).find(PLU.class);
                findplus.add(users.get(0));
            }while(cursor.moveToNext());
        }
        return findplus;
    }

    public List<User> getUsers() {
        Cursor cursor = DataSupport.findBySQL("select * from store_user where store_id = ?", String.valueOf(id));
        List<User> findusers = new ArrayList<>();
        if (cursor!=null&&cursor.moveToFirst()){
            do{
                int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
                List<User> users = DataSupport.where("id = ?", String.valueOf(user_id)).find(User.class);
                findusers.add(users.get(0));
            }while(cursor.moveToNext());
        }
        return findusers;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setPlus(List<PLU> plus) {
        this.plus = plus;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        parcel.writeTypedList(plus);
        parcel.writeTypedList(users);
    }

    @Override
    public String toString() {
        return "id:"+id+" name:"+name+" plus:"+plus+" time:"+time;
    }
    //ge't'plus会报错
}
