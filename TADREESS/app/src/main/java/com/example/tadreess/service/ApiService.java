package com.example.tadreess.service;


import com.example.tadreess.model.TokenResponce;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiService {

//    @POST(APIConstantURL.Login)
//    Observable<Labourloginresponse> getLoginPage(@Body JsonObject data);
//
//
//
//    @POST(APIConstantURL.SyncRequestHeaders)
//    Observable<SyncRequestHeaderResponse> getSyncRequestHeaders(@Body JsonObject data);
//
//    @POST(APIConstantURL.GetCount)
//    Observable<GetCountResponse> getCountPage(@Body JsonObject data);
//
//    @POST(APIConstantURL.post_syncLabourRequests)
//    Observable<SyncLabourResponse> getSyncLabourRequestPage(@Body JsonObject data);
//
//

    @GET
    Observable<TokenResponce> getlernings(@Url String url);



}
