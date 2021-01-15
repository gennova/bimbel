package com.aie.tendydeveloper;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginAPI {
    String LOGINURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("loginuser.php")
    Call<String> getUserLogin(

            @Field("username") String uname,
            @Field("password") String password
    );
}
