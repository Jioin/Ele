package com.example.youjingjing.myelectronicbalance.beans;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by youjingjing on 2017/8/23.
 */

public class PLURecond extends DataSupport implements Parcelable {
    private int _id;
    private String name;

    protected PLURecond(Parcel in) {
        _id = in.readInt();
        name = in.readString();
    }

    public static final Creator<PLURecond> CREATOR = new Creator<PLURecond>() {
        @Override
        public PLURecond createFromParcel(Parcel in) {
            return new PLURecond(in);
        }

        @Override
        public PLURecond[] newArray(int size) {
            return new PLURecond[size];
        }
    };

    public PLURecond() {

    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
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
        parcel.writeInt(_id);
        parcel.writeString(name);
    }
}
