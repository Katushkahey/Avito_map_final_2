package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Service implements Parcelable {
    private String name;
    private double[] lat;
    private double[] lng;
    boolean checked = false;

    public Service(String name) {
        this.name = name;
    }

    public Service(Parcel in) {
        name = in.readString();
        lat = in.createDoubleArray();
        lng = in.createDoubleArray();
    }

    public void setChecked(Boolean value) {
        this.checked = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double[] lat) {
        this.lat = lat;
    }

    public void setLng(double[] lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public boolean getChecked() {
        return checked;
    }

    public double[] getLat() {
        return lat;
    }

    public double[] getLng() {
        return lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDoubleArray(lat);
        dest.writeDoubleArray(lng);
    }

    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() {

        @Override
        public Service createFromParcel(Parcel source) {
            return new Service(source);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
}
