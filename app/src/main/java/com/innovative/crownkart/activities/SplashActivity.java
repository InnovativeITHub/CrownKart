package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.innovative.crownkart.R;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.CategoryDTO;
import com.innovative.crownkart.dto.SubcategoryDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        initDataAndSplashLogic();
    }

    private List<CategoryDTO> categoryDTOList = new ArrayList<>();

    private void initDataAndSplashLogic() {
        App.getApiHelper().getDrawerItem(new ApiCallback<Map>() {
            @Override
            public void onSuccess(Map map) {
                //categoryDTOList = new ArrayList<CategoryDTO>();
                List<SubcategoryDTO> subCategoryDTOList = new ArrayList<SubcategoryDTO>();
                for (int i = 0; i < ((ArrayList) map.get("response")).size(); i++) {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setMainCategoryName(((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("main_category").toString());
                    categoryDTO.setMainCatId(((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("main_id").toString());
                    for (int j = 0; j < ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).size(); j++) {
                        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
                        if (Boolean.parseBoolean(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("has_product").toString())) {
                            subcategoryDTO.setProductId(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("product_id").toString());
                            subcategoryDTO.setHasProduct(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("has_product").toString());
                            subcategoryDTO.setSubCategoryName(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("category_name").toString());
                        } else {
                            subcategoryDTO.setProductId(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("error").toString());
                            subcategoryDTO.setHasProduct(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("has_product").toString());
                        }


                        subCategoryDTOList.add(subcategoryDTO);
                    }
                    categoryDTO.setSubcategoryDTOList(subCategoryDTOList);

                    categoryDTOList.add(categoryDTO);

                    //setList("list", categoryDTOList);

                }
                sharedPreferences = getSharedPreferences("crownkart", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(categoryDTOList);
                editor.putString("crown", json);
                editor.commit();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SplashActivity.this, categoryDTOList.size() + "", Toast.LENGTH_SHORT).show();
                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(App.getAppContext(), LoginActivity.class));
                    }
                }, 1500);
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(key, json);
    }

    public void set(String key, String value) {
        //editor.putString(key, value);
        //editor.commit();
    }
}
