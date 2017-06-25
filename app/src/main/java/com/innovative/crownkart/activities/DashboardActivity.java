package com.innovative.crownkart.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.innovative.crownkart.R;
import com.innovative.crownkart.adapter.ExpandableAdapter;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.CategoryDTO;
import com.innovative.crownkart.dto.SubcategoryDTO;
import com.innovative.crownkart.fragments.CategoryCompleteItemsFragment;
import com.innovative.crownkart.fragments.CategoryFragment;
import com.innovative.crownkart.fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    List<CategoryDTO> categoryDTOList = new ArrayList<>();
    List<SubcategoryDTO> subcategoryDTOList;
    HashMap<String, List<SubcategoryDTO>> map = new HashMap<>();

    private ExpandableAdapter expandableAdapter;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String toolbarTitle;
    private CharSequence mDrawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        elvCategory.setGroupIndicator(null);
        getListFromPref();

        toolbarTitle = categoryDTOList.get(0).getMainCategoryName() + " - " + map.get(categoryDTOList.get(0).getMainCategoryName()).get(0).getSubCategoryName();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(toolbarTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(toolbarTitle);
                invalidateOptionsMenu();
            }
        };

        if (savedInstanceState == null) {
            //SelectItem(0);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
//        getSupportActionBar().setTitle(toolbarTitle);

        elvCategory.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                toolbarTitle = categoryDTOList.get(groupPosition).getMainCategoryName() + " : "
                        + map.get(categoryDTOList.get(groupPosition).getMainCategoryName()).get(childPosition).getSubCategoryName();

                SharedPreferences preferences = getSharedPreferences("crown", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("product_id", map.get(categoryDTOList.get(groupPosition).getMainCategoryName()).get(childPosition).getProductId());
                editor.commit();

                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CategoryFragment()).commit();
                getSupportActionBar().setTitle(toolbarTitle);
                drawerLayout.closeDrawers();

                return false;
            }
        });

        elvCategory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup) {
                    elvCategory.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }

        });
    }

    private void SelectItem(int position) {
        //FragmentManager frgManager = getFragmentManager();
        //frgManager.beginTransaction().replace(R.id.container, new CategoryFragment()).commit();

        //getSupportActionBar().setTitle(toolbarTitle);
    }

    private void getListFromPref() {
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences("crownkart", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("crown", "hello");
        try {
            JSONArray parentArray = new JSONArray(json);
            for (int i = 0; i < parentArray.length(); i++) {
                subcategoryDTOList = new ArrayList<>();
                CategoryDTO categoryDTO = new CategoryDTO();
                JSONObject jsonObject = parentArray.getJSONObject(i);
                categoryDTO.setMainCatId(jsonObject.getString("main_id"));
                categoryDTO.setMainCategoryName(jsonObject.getString("main_category"));
                JSONArray childArray = jsonObject.getJSONArray("subcategoryDTOList");
                for (int j = 0; j < childArray.length(); j++) {
                    JSONObject childObject = childArray.getJSONObject(j);
                    SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
                    if (Boolean.parseBoolean(childObject.getString("has_product"))) {
                        subcategoryDTO.setHasProduct(childObject.getString("has_product"));
                        subcategoryDTO.setProductId(childObject.getString("product_id"));
                        subcategoryDTO.setSubCategoryName(childObject.getString("category_name"));
                    } else {
                        subcategoryDTO.setHasProduct(childObject.getString("has_product"));
                        subcategoryDTO.setProductId("");
                        subcategoryDTO.setSubCategoryName("");
                    }
                    subcategoryDTOList.add(subcategoryDTO);

                }
                categoryDTO.setSubcategoryDTOList(subcategoryDTOList);
                categoryDTOList.add(categoryDTO);
            }

            for (int i = 0; i < categoryDTOList.size(); i++) {
                map.put(categoryDTOList.get(i).getMainCategoryName(), categoryDTOList.get(i).getSubcategoryDTOList());
            }

            expandableAdapter = new ExpandableAdapter(App.getAppContext(), categoryDTOList, map);
            elvCategory.setAdapter(expandableAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//
//    @Override
//    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getSupportActionBar().setTitle(mTitle);
//    }

    @OnClick(R.id.home)
    public void onClickHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        getSupportActionBar().setTitle("CrownKart");
        drawerLayout.closeDrawers();
    }
}
