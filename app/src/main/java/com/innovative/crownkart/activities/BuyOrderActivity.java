package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.innovative.crownkart.R;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.views.CustomButton;
import com.innovative.crownkart.views.CustomTextView;

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

    private SharedPreferences sharedPreferences;
    private String emailAddress, firstName, lastName, addressName, landmarkName, pincodeName,
            cityName, stateName, countryName, phoneName;

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
