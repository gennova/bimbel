package com.aie.tendydeveloper.tentor;

import com.aie.tendydeveloper.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PROFILTENTORAPI {
    String PROFILURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("getprofiltentor.php")
    Call<String> getProfilTentor(
            @Field("iduser") int iduser
    );
}
