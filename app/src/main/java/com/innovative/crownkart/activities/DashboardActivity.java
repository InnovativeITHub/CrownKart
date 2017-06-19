package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.adapter.ExpandableListAdapter;
import com.innovative.crownkart.adapter.SpecificProductAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.fragments.CategoryFragment;
import com.innovative.crownkart.fragments.DashboardDrawerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardActivity extends AppCompatActivity implements DashboardDrawerFragment.NavigationDrawerCallbacks {
    @BindView(R.id.main_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout containerLayout;

    private DashboardDrawerFragment dashboardDrawerFragment;
    private CharSequence mTitle;
    static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        setupToolbar();
        initDrawerFragment();
        initMainFragment();


    }

    private static void initMainFragment() {
        fragmentManager.beginTransaction().replace(R.id.container, new CategoryFragment()).commit();
    }

    private void initDrawerFragment() {
        /*dashboardDrawerFragment = (DashboardDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        dashboardDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);*/

        Demo demo = (Demo) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        demo.setUp(R.id.navigation_drawer, mDrawerLayout);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
    }

    public static class Demo extends Fragment {
        private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
        private DashboardDrawerFragment.NavigationDrawerCallbacks mCallbacks;
        private ActionBarDrawerToggle mDrawerToggle;
        private DrawerLayout mDrawerLayout;
        private ExpandableListView mDrawerListView;
        private View mFragmentContainerView;
        private int mCurrentSelectedPosition = 0;

        List<String> expandableListTitle;
        HashMap<String, List<String>> expandableListDetail;
        private int lastExpandedPosition = -1;

        public static interface NavigationDrawerCallbacks {
            void onNavigationDrawerItemSelected(int position);
        }

        public Demo() {

        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setHasOptionsMenu(true);
        }

        List<String> stringList;
        HashMap<String, List<String>> hash = new HashMap<String, List<String>>();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mDrawerListView = (ExpandableListView) inflater.inflate(R.layout.fragment_dashboard_drawer, container, false);

            //Todo
            expandableListDetail = new HashMap<>();
            SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences("crownkart", Context.MODE_PRIVATE);
            String json = sharedPreferences.getString("crown", "hello");

            //LinkedHashMap<String, List<String>> expandableListDetails = new LinkedHashMap<String, List<String>>();
            HashMap<String, List<String>> expandableListDetail = new HashMap<>();
            List<String> list = new ArrayList<>();
            List<String> list1 = new ArrayList<>();
            try {
                //new JSONArray(new JSONArray(sharedPreferences.getString("crown", "hello")).getJSONObject(0).get("subcategoryDTOList").toString()).getJSONObject(0).getString("has_product")
                JSONArray parentArray = new JSONArray(json);
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject jsonObject = parentArray.getJSONObject(i);
                    jsonObject.getString("main_id");
                    jsonObject.getString("main_category");
                    JSONArray childArray = jsonObject.getJSONArray("subcategoryDTOList");
                    for (int j = 0; i < childArray.length(); j++) {
                        JSONObject childObject = childArray.getJSONObject(j);
                        childObject.getString("has_product");

                        list1.add(childObject.getString("has_product"));
                        list1.add(childObject.getString("category_name"));
                    }

                    expandableListDetail.put("main_category", list1);
                    list.add(jsonObject.getString("main_category"));

                    mDrawerListView.setAdapter(new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            /*App.getApiHelper().getDrawerItem(new ApiCallback<Map>() {
                @Override
                public void onSuccess(Map map) {
                    if (map != null) {
                        for (int i = 0; i < ((ArrayList) map.get("response")).size(); i++) {
                            stringList=new ArrayList<String>();
                            List<String> list = new ArrayList<String>();
                            for (int j = 0; j < ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).size(); j++) {
                                stringList.add(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("product_id").toString());
                                list.add(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("category_name").toString());
                            }
                            hash.put(i + "", stringList);
                            expandableListDetail.put(((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("main_category").toString(), list);

                            expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                        }
                        mDrawerListView.setAdapter(new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail, stringList));

                    }
                }

                @Override
                public void onFailure(String message) {

                }
            });*/
            View header = inflater.inflate(R.layout.header_profile_drawer, null);
            mDrawerListView.addHeaderView(header);

            mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectItem(position);
                }
            });

            mDrawerListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                        mDrawerListView.collapseGroup(lastExpandedPosition);
                    }
                    lastExpandedPosition = groupPosition;
                }
            });

            mDrawerListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                /*boolean retVal = true;
                if (groupPosition == groupPosition) {
                    retVal = false;
                }*/

                    return false;
                }
            });

            mDrawerListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                    mDrawerLayout.closeDrawers();
                    //Toast.makeText(App.getAppContext(), childPosition + "", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = App.getAppContext().getSharedPreferences("crown", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("product_id", hash.get(groupPosition + "").get(childPosition));
                    editor.commit();

                    initMainFragment();
                    return true;
                }
            });

            mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);


            return mDrawerListView;
        }

        public boolean isDrawerOpen() {
            return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
        }

        public void setUp(int fragmentId, DrawerLayout drawerLayout) {
            mFragmentContainerView = getActivity().findViewById(fragmentId);
            mDrawerLayout = drawerLayout;
            mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);

                    if (!isAdded()) {
                        return;
                    }

                    getActivity().supportInvalidateOptionsMenu();
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);

                    if (!isAdded()) {
                        return;
                    }
                    getActivity().supportInvalidateOptionsMenu();
                }
            };


            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });

            mDrawerLayout.setDrawerListener(mDrawerToggle);
        }

        private void selectItem(int position) {
            mCurrentSelectedPosition = position;
            if (mDrawerListView != null) {
                mDrawerListView.setItemChecked(position, true);
            }
            if (mDrawerLayout != null) {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
            if (mCallbacks != null) {
                mCallbacks.onNavigationDrawerItemSelected(position);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            mDrawerToggle.onConfigurationChanged(newConfig);


        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        private LinkedHashMap<String, List<String>> expandableListDetails;

        public HashMap<String, List<String>> getData() {
            expandableListDetails = new LinkedHashMap<String, List<String>>();

            /*App.getApiHelper().getDrawerItem(new ApiCallback<Map>() {
                @Override
                public void onSuccess(Map map) {
                    if (map != null) {
                        for (int i = 0; i < ((ArrayList) map.get("response")).size(); i++) {
                            List<String> list = new ArrayList<String>();
                            for (int j = 0; j < ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).size(); j++) {
                                list.add(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("category_name").toString());
                            }
                            expandableListDetails.put(((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("main_category").toString(), list);
                        }
                    }
                }

                @Override
                public void onFailure(String message) {

                }
            });*/

            return expandableListDetails;
        /*LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> menu3 = new ArrayList<String>();
        List<String> menu4 = new ArrayList<String>();

        List<String> list1 = new ArrayList<String>();
        list1.add("Submenu 1");
        list1.add("Submenu 2");
        list1.add("Submenu 3");
        list1.add("Submenu 4");

        List<String> list2 = new ArrayList<String>();
        list2.add("Submenu 1");
        list2.add("Submenu 2");
        list2.add("Submenu 3");
        list2.add("Submenu 4");

        expandableListDetail.put("Menu 1", list1);
        expandableListDetail.put("Menu 2", list2);
        expandableListDetail.put("Menu 3", menu3);
        expandableListDetail.put("Menu 4", menu4);

        return expandableListDetail;*/
        }
    }
}
