package com.aie.tendydeveloper.admin;

import com.aie.tendydeveloper.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddLesAPI {
    String AKTIVASIURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("addles.php")
    Call<String> tambahles(
            @Field("namatentor") String namatentor,
            @Field("namamurid") String namamurid,
            @Field("jadwal") String jadwal,
            @Field("tarif") String tarif,
            @Field("status") String status
    );
}
