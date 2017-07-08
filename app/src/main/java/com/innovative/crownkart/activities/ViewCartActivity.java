package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.adapter.ViewCartAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.views.CustomButton;
import com.innovative.crownkart.views.CustomTextView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewCartActivity extends AppCompatActivity {

    @BindView(R.id.rv_view_cart_items)
    RecyclerView rv_view_cart_items;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.sv)
    NestedScrollView scrollView;
    @BindView(R.id.tv_price_one)
    CustomTextView tv_price_one;
    @BindView(R.id.tv_price_two)
    CustomTextView tv_price_two;
    @BindView(R.id.tv_price_three)
    CustomTextView tv_price_three;
    @BindView(R.id.btn_place_order)
    CustomButton btn_place_order;

    private String emailAddress;
    private SharedPreferences sharedPreferences;
    private ViewCartAdapter viewCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");
        getViewCartItems();

        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCartActivity.this, BuyOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getViewCartItems() {
        rv_view_cart_items.setLayoutManager(new LinearLayoutManager(App.getAppContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        App.getApiHelper().viewCart(emailAddress, new ApiCallback<Map>() {
            @Override
            public void onSuccess(final Map map) {

                System.out.println("map" + map);
                ArrayList<LinkedTreeMap> viewCartList = new ArrayList<LinkedTreeMap>();

                viewCartList = ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result"));

                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

                String subTotal = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("subtotal").toString();
                String cart_id = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("cart_id").toString();
                String pro_id = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(0)).get("pro_id").toString();
                String product_id = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(0)).get("product_id").toString();
                String color_code = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(0)).get("color_code").toString();
                String discount = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(0)).get("discount").toString();
                String price = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(0)).get("price").toString();
                String product_images = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(0)).get("product_images").toString();
                String category_name = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(0)).get("category_name").toString();
                String product_description = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(0)).get("product_description").toString();
                String gender = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(0)).get("gender").toString();


                rv_view_cart_items.setVisibility(View.VISIBLE);
                tv_price_one.setText(subTotal);
                tv_price_two.setText(subTotal);
                tv_price_three.setText(subTotal);

                viewCartAdapter = new ViewCartAdapter(getApplicationContext(), viewCartList);
                rv_view_cart_items.setAdapter(viewCartAdapter);

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
