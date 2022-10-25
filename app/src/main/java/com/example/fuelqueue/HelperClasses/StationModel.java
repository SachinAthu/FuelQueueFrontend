package com.example.fuelqueue.HelperClasses;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class StationModel {
    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("type")
    private String type;

    @SerializedName("fuel")
    private FuelModel fuel;

    public StationModel(String name, String address, String type) {
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public FuelModel getFuel() {
        return fuel;
    }

    public void setFuel(FuelModel fuel) {
        this.fuel = fuel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void display() {
        if(name != null)
            Log.i("name", name);

        if(address != null)
            Log.i("address", address);

        if(type != null)
            Log.i("type", type);

    }
}
