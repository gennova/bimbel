package com.aie.bimbelaie.admin;

import com.aie.bimbelaie.PublicURL;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MuridAPIList {
    String JSONURL = PublicURL.getAPIURL();;

    @GET("muridlist.php")
    Call<String> getString();
}
