package com.innovative.crownkart.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.innovative.crownkart.R;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;

import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(App.getAppContext(), LoginActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                finish();
            }
        }, 2000);

        /*new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                App.getApiHelper().getDrawerItem(new ApiCallback<Map>() {
                    @Override
                    public void onSuccess(Map map) {

                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


            }
        }.execute();*/
    }

    class ExpandableList{

    }
}
