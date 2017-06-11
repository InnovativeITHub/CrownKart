package com.innovative.crownkart.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Pulkit on 11-Jun-17.
 */

public class SpecificProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private ArrayList<LinkedTreeMap> productDetailList;

    public SpecificProductAdapter(Activity activity, ArrayList<LinkedTreeMap> productDetailList) {
        this.activity = activity;
        this.productDetailList = productDetailList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpecificProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //Toast.makeText(activity, productDetailList.size()+"", Toast.LENGTH_SHORT).show();
        LinkedTreeMap linkedTreeMap=productDetailList.get(position);

        SpecificProductHolder holder= (SpecificProductHolder) viewHolder;
        holder.tvCatName.setText(linkedTreeMap.get("category").toString());
        holder.tvProductDescription.setText(linkedTreeMap.get("product_description").toString());

        Picasso.with(activity).load(linkedTreeMap.get("product_images").toString()).centerCrop().fit().into(holder.ivProductImage);
    }

    @Override
    public int getItemCount() {
        return productDetailList.size();
    }

    private class SpecificProductHolder extends RecyclerView.ViewHolder{
        ImageView ivProductImage;
        com.innovative.crownkart.views.CustomTextView tvCatName, tvProductDescription;

        public SpecificProductHolder(View itemView) {
            super(itemView);

            ivProductImage= (ImageView) itemView.findViewById(R.id.iv_product_image);
            tvCatName= (CustomTextView) itemView.findViewById(R.id.tv_cat_name);
            tvProductDescription= (CustomTextView) itemView.findViewById(R.id.tv_product_description);

            /*ivProductImage= ButterKnife.findById(activity, R.id.iv_product_image);
            tvCatName=ButterKnife.findById(activity, R.id.tv_cat_name);
            tvProductDescription=ButterKnife.findById(activity, R.id.tv_product_description);*/
        }
    }
}
