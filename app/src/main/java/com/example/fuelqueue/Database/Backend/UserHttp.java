package com.example.fuelqueue.Database.Backend;

import com.example.fuelqueue.HelperClasses.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserHttp {

    @POST("users")
    Call<UserModel> signup(@Body UserModel user);

    @POST("users/login")
    Call<UserModel> login(@Body UserModel user);

}
