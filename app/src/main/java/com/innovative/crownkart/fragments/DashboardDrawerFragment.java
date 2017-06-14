package com.innovative.crownkart.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.adapter.ExpandableListAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardDrawerFragment extends Fragment {
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private NavigationDrawerCallbacks mCallbacks;
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

    public DashboardDrawerFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
    List<String> stringList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDrawerListView = (ExpandableListView) inflater.inflate(R.layout.fragment_dashboard_drawer, container, false);

        //Todo
        expandableListDetail = new HashMap<>();

        App.getApiHelper().getDrawerItem(new ApiCallback<Map>() {
            @Override
            public void onSuccess(Map map) {
                if (map != null) {
                    for (int i = 0; i < ((ArrayList) map.get("response")).size(); i++) {
                        List<String> list = new ArrayList<String>();
                        for (int j = 0; j < ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).size(); j++) {
                            stringList.add(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("product_id").toString());
                            list.add(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("category_name").toString());
                        }
                        expandableListDetail.put(((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("main_category").toString(), list);

                        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                        mDrawerListView.setAdapter(new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail,stringList));


                    }
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
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
                SharedPreferences preferences=App.getAppContext().getSharedPreferences("crown", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("product_id", stringList.get(childPosition));
                editor.commit();


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

        App.getApiHelper().getDrawerItem(new ApiCallback<Map>() {
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
        });

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
