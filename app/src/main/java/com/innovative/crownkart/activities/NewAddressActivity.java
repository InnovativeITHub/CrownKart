package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.innovative.crownkart.R;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.views.CustomButton;
import com.innovative.crownkart.views.CustomEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAddressActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name)
    CustomEditText et_first_name;
    @BindView(R.id.et_last_name)
    CustomEditText et_last_name;
    @BindView(R.id.et_address_name)
    CustomEditText et_address_name;
    @BindView(R.id.et_landmark_name)
    CustomEditText et_landmark_name;
    @BindView(R.id.et_pincode_name)
    CustomEditText et_pincode_name;
    @BindView(R.id.et_city_name)
    CustomEditText et_city_name;
    @BindView(R.id.et_state_name)
    CustomEditText et_state_name;
    @BindView(R.id.spinner_country_name)
    Spinner spinner_country_name;
    @BindView(R.id.et_phone_name)
    CustomEditText et_phone_name;
    @BindView(R.id.btn_save_continue)
    CustomButton btn_save_continue;
    @BindView(R.id.btn_cancel)
    CustomButton btn_cancel;

    private String firstName, lastName, addressName, landmarkName, pincodeName,
            cityName, stateName, countryName, phoneName, saveContinue;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);

    }

    @OnClick(R.id.btn_save_continue)
    public void onClickSaveContinue() {

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        firstName = et_first_name.getText().toString();
        lastName = et_last_name.getText().toString();
        addressName = et_address_name.getText().toString();
        landmarkName = et_landmark_name.getText().toString();
        pincodeName = et_pincode_name.getText().toString();
        cityName = et_city_name.getText().toString();
        stateName = et_state_name.getText().toString();
        phoneName = et_phone_name.getText().toString();


        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");
        list.add("Item 4");
        list.add("Item 5");
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_country_name.setAdapter(adapter);

        editor.putString(SharedPrefernceValue.FIRST_NAME, firstName);
        editor.commit();
    }


}
