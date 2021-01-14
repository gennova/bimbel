package com.aie.bimbelaie;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegistrasiTentorAPI {
    String REGISTRASIURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("registrasibimbel.php")
    Call<String> getRegistrasiMurid(
            @Field("nama") String namamurid,
            @Field("jenjang") String jenjang,
            @Field("mapel") String mapelmurid,
            @Field("harijam") String haridanjam,
            @Field("kisaran") String kisaran,
            @Field("alamat") String alamatmurid,
            @Field("email") String email,
            @Field("password") String password
    );
}
