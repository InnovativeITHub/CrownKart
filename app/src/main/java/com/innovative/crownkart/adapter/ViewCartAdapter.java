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
import com.innovative.crownkart.dto.CartDTO;
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

    private ArrayList<CartDTO> viewCartList;
    private Context context;

    public ViewCartAdapter(Context context, ArrayList<CartDTO> viewCartList) {
        this.context = context;
        this.viewCartList = viewCartList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewCartHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {


        CartDTO cartDTO = viewCartList.get(position);

        final ViewCartHolder holder = (ViewCartHolder) viewHolder;
        String pro_id = cartDTO.getPro_id();
        String imageURL = "http://crownkar.escuela.in/admin/";

       String item_price = cartDTO.getPrice();

        if (pro_id != null) {
            holder.tvCatName.setText(cartDTO.getCategory_name());
            String product_images = cartDTO.getProduct_images();
            String full_url = imageURL + product_images;
            holder.tv_price.setText(item_price);
            holder.tv_quantity.setText(cartDTO.getQuantity());
            holder.tv_size.setText(cartDTO.getSize());
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
