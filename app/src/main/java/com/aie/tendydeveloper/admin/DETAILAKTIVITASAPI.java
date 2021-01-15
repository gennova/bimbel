package com.aie.tendydeveloper.admin;

import com.aie.tendydeveloper.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DETAILAKTIVITASAPI {
    String PROFILURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("aktivitastentoradmin.php")
    Call<String> getAktivitasTentor(
            @Field("nama") String nama,
            @Field("tglawal") String tglawal,
            @Field("tglakhir") String tglakhir
    );
}
