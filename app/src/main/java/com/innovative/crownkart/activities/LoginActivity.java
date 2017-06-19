package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.utils.SnackbarUtil;
import com.innovative.crownkart.utils.StringUtil;
import com.innovative.crownkart.views.CustomEditText;
import com.innovative.crownkart.views.CustomTextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_email_address)
    CustomEditText etEmailAddress;
    @BindView(R.id.et_password)
    CustomEditText etPassword;
    @BindView(R.id.tv_forgot_password)
    CustomTextView tvForgotPassword;
    @BindView(R.id.cb_password)
    CheckBox cbShowPassword;

    private String emailAddress, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        tvForgotPassword.setPaintFlags(tvForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //SharedPreferences sharedPreferences = getSharedPreferences("crownkart", Context.MODE_PRIVATE);
        //Log.v("json", sharedPreferences.getString("crown", "hello"));


        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    @OnClick(R.id.tv_forgot_password)
    public void onClickForgotPassword() {

    }

    @OnClick(R.id.btn_signin)
    public void onClickSignin() {
        emailAddress = etEmailAddress.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (validate()) {
            App.getApiHelper().loginUser(emailAddress, password, new ApiCallback<Map>() {
                @Override
                public void onSuccess(Map map) {
                    LinkedTreeMap treeMap=((LinkedTreeMap)((LinkedTreeMap)map.get("response")).get("result"));
                    SnackbarUtil.showLongSnackbar(LoginActivity.this, treeMap.get("msg").toString());

                    startActivity(new Intent(App.getAppContext(), DashboardActivity.class));
                }

                @Override
                public void onFailure(String message) {
                    SnackbarUtil.showShortSnackbar(LoginActivity.this, message);
                }
            });
        }
    }

    @OnClick(R.id.btn_skip)
    public void onClickSkip() {
        startActivity(new Intent(App.getAppContext(), DashboardActivity.class));
    }

    @OnClick(R.id.btn_signup)
    public void onClickSignup() {
        startActivity(new Intent(App.getAppContext(), RegisterActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }

    private boolean validate() {
        if (StringUtil.isEmpty(emailAddress)) {
            SnackbarUtil.showShortSnackbar(this, getResources().getString(R.string.fields_empty));
            return false;
        } else if (StringUtil.isEmpty(password)) {
            SnackbarUtil.showShortSnackbar(this, getResources().getString(R.string.fields_empty));
            return false;
        }
        return true;
    }
}
