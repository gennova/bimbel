package com.aie.bimbelaie.admin;

import com.aie.bimbelaie.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TentorAktivitasAPI {
    String URL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("daftarpresensitentor.php")
    Call<String> getAktivitas(
            @Field("idtentor") String idtentor
    );
}
