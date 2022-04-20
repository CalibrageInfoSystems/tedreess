package com.example.tadreess.service;


import com.example.tadreess.model.MyPojo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiDataInterface {
    // path to xml link on web site

    @GET("LoginUser?Username=nona&password=123456&cultureid=1")
    Call<MyPojo> onLogin();
}
