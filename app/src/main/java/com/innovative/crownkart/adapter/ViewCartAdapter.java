package com.innovative.crownkart.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pulkit on 2/7/17.
 */

public class ViewCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<LinkedTreeMap> viewCartList;

    public ViewCartAdapter(ArrayList<LinkedTreeMap> viewCartList) {
//        this.context= context;
        this.viewCartList = viewCartList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewCartHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final LinkedTreeMap linkedTreeMap = viewCartList.get(position);

        final ViewCartHolder holder = (ViewCartHolder) viewHolder;
        if (linkedTreeMap.get("") != null) {
            holder.tvCatName.setText(linkedTreeMap.get("category_name").toString());
        }

//        ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((LinkedTreeMap) map.get("response"))).get("result")).get(0)).get("product_description")).get(0))
//        ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("subtotal");

    }

    @Override
    public int getItemCount() {
        return viewCartList.size();
    }

    class ViewCartHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product_image)
        ImageView ivProductImage;
        @BindView(R.id.tv_cat_name)
        CustomTextView tvCatName;
        @BindView(R.id.tv_product_description)
        CustomTextView tvProductDescription;
        @BindView(R.id.tv_price)
        CustomTextView tvPrice;
        @BindView(R.id.cardView)
        CardView cardView;

        public ViewCartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
