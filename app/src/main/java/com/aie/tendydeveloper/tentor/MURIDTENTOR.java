package com.aie.tendydeveloper.tentor;

import com.aie.tendydeveloper.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MURIDTENTOR {
    String PROFILURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("muridlisttentor.php")
    Call<String> getMuridTentor(
            @Field("iduser") int iduser
    );
}
