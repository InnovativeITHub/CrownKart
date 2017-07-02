package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.adapter.ViewCartAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewCart extends AppCompatActivity {

    @BindView(R.id.rv_view_cart_items)
    RecyclerView rv_view_cart_items;

    private String emailAddress;
    private SharedPreferences sharedPreferences;
    private ViewCartAdapter viewCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");
        getViewCartItems();

    }

    private void getViewCartItems() {
        App.getApiHelper().viewCart(emailAddress, new ApiCallback<Map>() {
            @Override
            public void onSuccess(final Map map) {

                System.out.println("map" + map);
                ArrayList<LinkedTreeMap> viewCartList = new ArrayList<LinkedTreeMap>();

                viewCartList = ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result"));
//                for (int i = 0; i < viewCartList.size(); i++) {
                rv_view_cart_items.setVisibility(View.VISIBLE);
                viewCartAdapter = new ViewCartAdapter(viewCartList);
                rv_view_cart_items.setAdapter(viewCartAdapter);
            }
//            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
