package com.aie.tendydeveloper.admin;

import com.aie.tendydeveloper.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AktivasiAPI {
    String AKTIVASIURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("aktivasi.php")
    Call<String> getAktivasiMurid(
            @Field("nama") String namamurid,
            @Field("email") String email,
            @Field("jadwal") String jadwal,
            @Field("tarif") String tarif,
            @Field("status") String status,
            @Field("tentor") String tentor
    );
}
