package com.innovative.crownkart.api;

import com.innovative.crownkart.R;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
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

    @FormUrlEncoded
    @POST("productDetails.php")
    Call<Map> getSpecificProduct(@Field("product_id") String productId);

    @POST("categoryProduct.php")
    Call<Map> getDrawerItems();

    @FormUrlEncoded
    @POST("mainProducts.php")
    Call<Map> getMainProduct(@Field("main_id") String mainId);

    @FormUrlEncoded
    @POST("singleProductDetail.php")
    Call<Map> getSingleProductDetails(@Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("buyProducts.php")
    Call<Map> buyProducts(@Field("email") String email,
                          @Field("pro_id") String pro_id,
                          @Field("size") String size,
                          @Field("quantity") String quantity,
                          @Field("price") String price,
                          @Field("total") String total);

    @FormUrlEncoded
    @POST("addToCart.php")
    Call<Map> addToCart(@Field("email") String email,
                        @Field("pro_id") String pro_id,
                        @Field("size") String size,
                        @Field("quantity") String quantity,
                        @Field("price") String price);

    @FormUrlEncoded
    @POST("viewCart.php")
    Call<Map> viewCart(@Field("email") String email);

    @FormUrlEncoded
    @POST("buySinglePrduct.php")
    Call<Map> buySingleProduct(@Field("email") String email, @Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("applyCoupon.php")
    Call<Map> applyCoupon(@Field("email") String email, @Field("coupon_code") String coupon_code);

    @FormUrlEncoded
    @POST("changePassword.php")
    Call<Map> getSubmitData(@Field("email") String email, @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("changePassword.php")
    Call<Map> getMyAccount(@Field("firstName") String firstName
            , @Field("lastName") String lastName
            , @Field("phone") String phone
            , @Field("emailAddress") String emailAddress);

}
