package com.example.adminpanel.ViewHolder;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminpanel.Model.Product;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.SellerProductDetailActivity;

import java.util.List;

public class ProductViewHolder extends RecyclerView.Adapter<ProductViewHolder.ViewHolder> {
    private Context context;

    private List<Product> list;


    public ProductViewHolder(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
            }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.productImage);
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.price.setText("RS :   " + list.get(position).getPrice());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SellerProductDetailActivity.class);
            intent.putExtra("pId", list.get(position).getpID());
            intent.putExtra("sid", list.get(position).getSid());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();


    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView price, name, description;

        CardView cardrec;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            price = itemView.findViewById(R.id.product_price);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);

            cardrec = itemView.findViewById(R.id.cardrec);

        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position, String productId);
    }

}