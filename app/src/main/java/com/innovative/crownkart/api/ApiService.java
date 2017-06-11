package com.innovative.crownkart.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Pulkit on 10-Jun-17.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("registerUser.php")
    Call<Map> registerUser(@Field("name") String username,
                           @Field("phone") String mobile,
                           @Field("email") String emailAddress,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<Map> loginUser(@Field("email") String emailAddress,
                        @Field("password") String password);
}
