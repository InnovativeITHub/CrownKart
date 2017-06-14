package com.innovative.crownkart.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.activities.DashboardActivity;
import com.innovative.crownkart.adapter.SpecificProductAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment {
    @BindView(R.id.rv_items)
    RecyclerView rvItems;

    public CategoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvItems.setLayoutManager(new GridLayoutManager(App.getAppContext(), 2));
        SharedPreferences preferences=App.getAppContext().getSharedPreferences("crown", Context.MODE_PRIVATE);
        String id=preferences.getString("product_id","1");
        App.getApiHelper().getSpecificProduct(id, new ApiCallback<Map>() {
            @Override
            public void onSuccess(Map map) {
                ArrayList<LinkedTreeMap> productDetailList = new ArrayList();
                productDetailList = (ArrayList) map.get("response");
                rvItems.setAdapter(new SpecificProductAdapter(productDetailList));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
