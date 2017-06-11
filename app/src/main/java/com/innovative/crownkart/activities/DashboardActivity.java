package com.innovative.crownkart.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.adapter.SpecificProductAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        rvItems.setLayoutManager(new GridLayoutManager(App.getAppContext(), 2));
        App.getApiHelper().getSpecificProduct("1", new ApiCallback<Map>() {
            @Override
            public void onSuccess(Map map) {
                //(ArrayList)map.get("response")
                //((LinkedTreeMap)((ArrayList)map.get("response")).get(0))

                ArrayList<LinkedTreeMap> productDetailList=new ArrayList();
                productDetailList=(ArrayList)map.get("response");
                rvItems.setAdapter(new SpecificProductAdapter(DashboardActivity.this, productDetailList));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }


}
