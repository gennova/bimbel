package com.aie.tendydeveloper.tentor;

import com.aie.tendydeveloper.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PresensiAPI {
    String PRESENSIURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("presensitentor.php")
    Call<String> postPresensiTentor(
            @Field("email") String namatentor,
            @Field("hari") String jenjang,
            @Field("tanggal") String hpmurid,
            @Field("topik") String mapelmurid,
            @Field("durasi") String haridanjam,
            @Field("murid") String namamurid
    );
}
