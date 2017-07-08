package com.innovative.crownkart.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pulkit on 2/7/17.
 */

public class ViewCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<LinkedTreeMap> viewCartList;
    private Context context;

    public ViewCartAdapter(Context context, ArrayList<LinkedTreeMap> viewCartList) {
        this.context = context;
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
        String cartID = linkedTreeMap.get("cart_id").toString();
        String imageURL = "http://crownkar.escuela.in/admin/";

        String product_images = ((LinkedTreeMap) ((ArrayList) linkedTreeMap.get("product_description")).get(0)).get("product_images").toString();
        String full_url = imageURL + product_images;
        String total_charge = linkedTreeMap.get("total_charge").toString();

        if (cartID != null) {
            holder.tvCatName.setText(((LinkedTreeMap) ((ArrayList) linkedTreeMap.get("product_description")).get(0)).get("category_name").toString());
//            holder.tv_size.setText(((LinkedTreeMap)((ArrayList)linkedTreeMap.get("product_description")).get(0)).get("category_name").toString());
//            holder.tv_quantity.setText(((LinkedTreeMap)((ArrayList)linkedTreeMap.get("product_description")).get(0)).get("category_name").toString());
            holder.tv_price.setText(((LinkedTreeMap) ((ArrayList) linkedTreeMap.get("product_description")).get(0)).get("price").toString());
//            holder.ivProductImage.setText(((LinkedTreeMap)((ArrayList)linkedTreeMap.get("product_description")).get(0)).get("category_name").toString());
            Picasso.with(context).load(full_url).into(holder.ivProductImage);
        }


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
        @BindView(R.id.tv_size)
        CustomTextView tv_size;
        @BindView(R.id.tv_quantity)
        CustomTextView tv_quantity;
        @BindView(R.id.tv_price)
        CustomTextView tv_price;
        @BindView(R.id.tv_edit)
        CustomTextView tv_edit;

        public ViewCartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
