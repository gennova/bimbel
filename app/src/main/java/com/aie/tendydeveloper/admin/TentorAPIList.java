package com.aie.tendydeveloper.admin;

import com.aie.tendydeveloper.PublicURL;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TentorAPIList {
    String JSONURL = PublicURL.getAPIURL();;

    @GET("tentor.php")
    Call<String> getString();
}
