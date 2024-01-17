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
import com.example.adminpanel.Interface.ItemClickListener;
import com.example.adminpanel.Model.Product;
import com.example.adminpanel.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearcTailorhAdapter extends RecyclerView.Adapter<SearcTailorhAdapter.ViewHolder> {
    Context context;
    List<Product> list;

    public SearcTailorhAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }
    public void updateData(List<Product> newDataList) {
        this.list = newDataList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SearcTailorhAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_home_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearcTailorhAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());
        holder.description.setText(list.get(position).getDescription());
        Picasso.get().load(list.get(position).getImage()).into(holder.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("pId", list.get(position).getpID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements com.example.adminpanel.activites.todo.ViewHolder {
        public ImageView productImage;
        public TextView price;
        public TextView name;
        public TextView description;
        CardView cardrec;
        private ItemClickListener itemClickListener;

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
