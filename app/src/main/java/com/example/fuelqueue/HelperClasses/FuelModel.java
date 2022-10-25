package com.example.fuelqueue.HelperClasses;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class FuelModel {
    @SerializedName("name")
    private String name;

    @SerializedName("arrival_time")
    private String arrivalTime;

    @SerializedName("finishTime")
    private String finishTime;

    @SerializedName("status")
    private boolean status;

    public FuelModel(String name, String arrivalTime, String finishTime, boolean status) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.finishTime = finishTime;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void display() {
        if(name != null)
            Log.i("name", name);

        if(arrivalTime != null)
            Log.i("arrival time", arrivalTime);

        if(finishTime != null)
            Log.i("finish time", finishTime);

        if(status)
            Log.i("status", "Available");
        else
            Log.i("status", "Finished");

    }
}
