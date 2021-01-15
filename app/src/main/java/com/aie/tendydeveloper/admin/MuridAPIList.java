package com.aie.tendydeveloper.admin;

import com.aie.tendydeveloper.PublicURL;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MuridAPIList {
    String JSONURL = PublicURL.getAPIURL();;

    @GET("muridlist.php")
    Call<String> getString();
}
