package com.kojh.BlockMarket;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantObj implements Parcelable {
    String name;
    ArrayList<FoodItem> menu;
    public RestaurantObj(String n, ArrayList<FoodItem> arr){
        name = n;
        menu = arr;
    }
    public RestaurantObj(Parcel p){
        name = p.readString();
        p.readTypedList(menu,FoodItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(menu);
    }

    public static final Creator<RestaurantObj> CREATOR = new Creator<RestaurantObj>() {
        @Override
        public RestaurantObj createFromParcel(Parcel in) {
            return new RestaurantObj(in);
        }

        @Override
        public RestaurantObj[] newArray(int size) {
            return new RestaurantObj[size];
        }
    };

    public int describeContents(){
        return 0;
    }

}
