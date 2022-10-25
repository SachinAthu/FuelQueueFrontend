package com.example.fuelqueue.HelperClasses;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    private String id;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("mobile_number")
    private String mobileNumber;

    @SerializedName("type")
    private String type;

    private String password;

    private StationModel station;

    @SerializedName("vehicle_type")
    private String vehicleType;

    private String token;

    public UserModel() { }

    public UserModel(String mobileNumber, String type, String password) {
        this.mobileNumber = mobileNumber;
        this.type = type;
        this.password = password;
    }

    public UserModel(String fullName, String mobileNumber, String type, String password, String vehicleType) {
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.type = type;
        this.password = password;
        this.vehicleType = vehicleType;
    }

    public StationModel getStation() {
        return station;
    }

    public void setStation(StationModel station) {
        this.station = station;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String userType) {
        this.type = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void display() {
        if(id != null)
            Log.i("id", id);

        if(token != null)
            Log.i("token", token);

        if(fullName != null)
            Log.i("full name", fullName);

        if(mobileNumber != null)
            Log.i("mobile number", mobileNumber);

        if(password != null)
            Log.i("password", password);

        if(type != null)
            Log.i("type", type);

        if(vehicleType != null)
            Log.i("vehicle type", vehicleType);

    }
}
