package com.innovative.crownkart.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.activities.RegisterActivity;
import com.innovative.crownkart.activities.ViewCartActivity;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.CartDTO;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.utils.SnackbarUtil;
import com.innovative.crownkart.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Created by pulkit on 2/7/17.
 */

public class ViewCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CartDTO> viewCartList;
    private Context context;
    SharedPreferences sharedPreferences;
    String emailAddress;
    String sizeset, qtyset;

    public ViewCartAdapter(Context context, ArrayList<CartDTO> viewCartList) {
        this.context = context;
        this.viewCartList = viewCartList;
        sharedPreferences = context.getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewCartHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CartDTO cartDTO = viewCartList.get(position);
        final ViewCartHolder holder = (ViewCartHolder) viewHolder;
        final String pro_id = cartDTO.getPro_id();
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
            holder.tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = holder.showAlertBox(context, pro_id);
                    dialog.show();
                }
            });
            holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getApiHelper().deleteProduct(emailAddress, pro_id, new ApiCallback<Map>() {
                        @Override
                        public void onSuccess(Map map) {
                            String message = ((LinkedTreeMap) ((LinkedTreeMap) map.get("response")).get("result")).get("message").toString();
                            Toast.makeText(App.getAppContext(),message,Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(String message) {
                            Toast.makeText(App.getAppContext(),message,Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
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
        @BindView(R.id.iv_cancel)
        ImageView iv_cancel;
        @BindView(R.id.root)
        RelativeLayout relativeLayout;

        String pro_id;

        public ViewCartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        AlertDialog showAlertBox(final Context context, final String pro_id) {
            AlertDialog.Builder builder;
            Context mContext = context;
            this.pro_id = pro_id;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialog_layout, (ViewGroup) relativeLayout.findViewById(R.id.layout_root));
            Button btn_save_continue = (Button) layout.findViewById(R.id.btn_save_continue);
            Spinner size = (Spinner) layout.findViewById(R.id.size);
            Spinner qty = (Spinner) layout.findViewById(R.id.quantity);
            size.setAdapter(ArrayAdapter.createFromResource(context, R.array.sizes, R.layout.dialog));
            qty.setAdapter(ArrayAdapter.createFromResource(context, R.array.quantity, R.layout.dialog));
            size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sizeset = parent.getItemAtPosition(position).toString();
                    tv_size.setText(sizeset);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    qtyset = parent.getSelectedItem().toString();
                    tv_quantity.setText(qtyset);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btn_save_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getApiHelper().updateProduct(emailAddress, pro_id, sizeset, qtyset, new ApiCallback<Map>() {
                        @Override
                        public void onSuccess(Map map) {
                            String message = ((LinkedTreeMap) ((LinkedTreeMap) map.get("response")).get("result")).get("message").toString();
                            Toast.makeText(App.getAppContext(),message,Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(String message) {
                            Toast.makeText(App.getAppContext(),message,Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            builder = new AlertDialog.Builder(mContext);
            builder.setView(layout);
            AlertDialog dialog = builder.create();
            return dialog;
        }
    }
}

