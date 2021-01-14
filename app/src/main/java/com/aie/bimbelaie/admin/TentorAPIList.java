package com.aie.bimbelaie.admin;

import com.aie.bimbelaie.PublicURL;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TentorAPIList {
    String JSONURL = PublicURL.getAPIURL();;

    @GET("tentor.php")
    Call<String> getString();
}
