package com.aie.bimbelaie.admin;

import com.aie.bimbelaie.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AktivasiTENTORAPI {
    String AKTIVASIURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("aktivasitentor.php")
    Call<String> postAktivasiTentor(
            @Field("nama") String namamurid,
            @Field("email") String email,
            @Field("status") String status
    );
}
