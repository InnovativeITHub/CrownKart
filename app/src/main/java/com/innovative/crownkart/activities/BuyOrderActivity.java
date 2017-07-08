package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.adapter.ViewCartAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.CartDTO;
import com.innovative.crownkart.dto.SingleProductDTO;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.views.CustomButton;
import com.innovative.crownkart.views.CustomTextView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyOrderActivity extends AppCompatActivity {

    @BindView(R.id.tv_logged_in)
    CustomTextView tv_logged_in;
    @BindView(R.id.add_new_address)
    CustomButton add_new_address;
    @BindView(R.id.tv_shipment_edit)
    CustomTextView tv_shipment_edit;
    @BindView(R.id.rv_cart_items)
    RecyclerView rv_cart_items;
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
    @BindView(R.id.cashback)
    CustomTextView tvCashback;
    private ViewCartAdapter viewCartAdapter;
    private SharedPreferences sharedPreferences;
    private String emailAddress, firstName, lastName, addressName, landmarkName, pincodeName,
            cityName, stateName, countryName, phoneName,pro_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_order);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");

        tv_logged_in.setText(emailAddress);

        add_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyOrderActivity.this, NewAddressActivity.class);
                startActivity(intent);
            }
        });

        tv_shipment_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @OnClick(R.id.tv_shipment_edit)
    public void onClickEdit() {

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Intent intent = new Intent(BuyOrderActivity.this, NewAddressActivity.class);
        startActivity(intent);
    }

}
