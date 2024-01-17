package com.example.adminpanel.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Interface.ItemClickListener;
import com.example.adminpanel.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtCartname, txtcartprice, txtcartquantity,txtcatfab;
    private ItemClickListener itemClickListener;
    public Button cartbtn,see_variations_btn;
    public ImageView imageView;

    public CartViewHolder(View itemView) {
        super(itemView);
        cartbtn = itemView.findViewById(R.id.cart_require_btn);
        txtCartname = itemView.findViewById(R.id.cart_product_name);
        txtcatfab=itemView.findViewById(R.id.cart_product_fabric);
        txtcartprice = itemView.findViewById(R.id.cart_product_price);
         see_variations_btn=itemView.findViewById(R.id.see_variations_btn);
        txtcartquantity = itemView.findViewById(R.id.cart_product_quantity);
//        imageView = itemView.findViewById(R.id.cart_product_image);
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false


        );
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
