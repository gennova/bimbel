package com.aie.tendydeveloper.murid;

import com.aie.tendydeveloper.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PROFILAPI {
    String PROFILURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("getprofilsiswa.php")
    Call<String> getProfilSiswa(
            @Field("iduser") String iduser
    );
}
