package com.example.adminpanel.activites.todo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Customer.home.ProductDetailActivity;
import com.example.adminpanel.Model.Product;
import com.example.adminpanel.R;
import com.example.adminpanel.ViewHolder.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class filterAdapter extends RecyclerView.Adapter<filterAdapter.ViewHolder> {
    Context context;
    List<Product> list;

    public filterAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_home_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product model = list.get(position);
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.description.setText(model.getDescription());
        Picasso.get().load(model.getImage()).into(holder.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("pId", model.getpID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<Product> newDataList) {
        list.clear();
        list.addAll(newDataList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView price;
        public TextView name;
        public TextView description;
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
}
