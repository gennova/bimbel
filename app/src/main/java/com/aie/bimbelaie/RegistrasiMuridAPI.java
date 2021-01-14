package com.aie.bimbelaie;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegistrasiMuridAPI {
    String REGISTRASIURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("registrasimurid.php")
    Call<String> getRegistrasiMurid(
            @Field("nama") String namamurid,
            @Field("jenjang") String jenjang,
            @Field("hp") String hpmurid,
            @Field("mapel") String mapelmurid,
            @Field("harijam") String haridanjam,
            @Field("alamat") String alamatmurid,
            @Field("jenisbimbel") String jenisbimbelnya,
            @Field("email") String email,
            @Field("password") String password
    );
}
