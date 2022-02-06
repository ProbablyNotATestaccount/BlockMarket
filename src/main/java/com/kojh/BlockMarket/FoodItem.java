package com.kojh.BlockMarket;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodItem implements Parcelable {
    String name;
    double price;

    public FoodItem(String n, double p){
        name = n;
        price = p;
    }

    public FoodItem(Parcel p){
        name = p.readString();
        price = p.readDouble();
    }

    public static final Parcelable.Creator<FoodItem> CREATOR
            = new Parcelable.Creator<FoodItem>() {
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[0];
        }
    };
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
    }
}
