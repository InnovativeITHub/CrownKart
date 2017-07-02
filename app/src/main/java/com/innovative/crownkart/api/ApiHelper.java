package com.innovative.crownkart.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innovative.crownkart.R;
import com.innovative.crownkart.config.App;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pulkit on 10-Jun-17.
 */

public class ApiHelper {
    private static App app;
    private static ApiHelper apiHelper;
    private ApiService apiService;

    private ApiHelper() {
    }

    public static ApiHelper init(App app) {
        if (apiHelper == null) {
            apiHelper = new ApiHelper();
            apiHelper.initApiService();
            setApp(app);
        }
        return apiHelper;
    }

    public static void setApp(App app) {
        ApiHelper.app = app;
    }

    private void initApiService() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://crownkar.escuela.in/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //http://crownkart.com/crownKart/api/
        //http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors
        //http://lifebeat.16mb.com/
        apiService = retrofit.create(ApiService.class);
    }

    public void registerUser(String username, String mobile, String emailAddress, String password, final ApiCallback<Map> apiCallback) {
        apiService.registerUser(username, mobile, emailAddress, password).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void loginUser(String emailAddress, String password, final ApiCallback<Map> apiCallback) {
        apiService.loginUser(emailAddress, password).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void getSpecificProduct(String productId, final ApiCallback<Map> apiCallback) {
        apiService.getSpecificProduct(productId).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void getDrawerItem(final ApiCallback<Map> apiCallback) {
        apiService.getDrawerItems().enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void getMainProduct(String mainId, final ApiCallback<Map> apiCallback) {
        apiService.getMainProduct(mainId).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void getSingleProductDetails(String pro_id, final ApiCallback<Map> apiCallback) {
        apiService.getSingleProductDetails(pro_id).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void buyProducts(String email, String pro_id, String size, String quantity, String price, String total_price, final ApiCallback<Map> apiCallback) {
        apiService.buyProducts(email, pro_id, size, quantity, price, total_price).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });

    }

    public void addToCart(String email, String pro_id, String size, String quantity, String price, final ApiCallback<Map> apiCallback) {
        apiService.addToCart(email, pro_id, size, quantity, price).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void viewCart(String email, final ApiCallback<Map> apiCallback) {
        apiService.viewCart(email).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }
}
