package com.example.adminpanel.adapter;

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

import com.bumptech.glide.Glide;
import com.example.adminpanel.Model.Product;
import com.example.adminpanel.R;
import com.example.adminpanel.Customer.home.ProductDetailActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<Product, myadapter.myviewHolder> {


    Context mcontext;

    public myadapter(@NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewHolder holder, int position, @NonNull Product model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.description.setText(model.getDescription());
        Glide.with(holder.productImage.getContext()).load(model.getImage()).into(holder.productImage);
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, ProductDetailActivity.class);
                intent.putExtra("image",model.getImage());
                intent.putExtra("Name",model.getName());
                intent.putExtra("Describtion",model.getDescription());
                intent.putExtra("Category",model.getCategory());
                intent.putExtra("Shop",model.getCategory());
                intent.putExtra("Price",model.getPrice());
                intent.putExtra("Fabric",model.getFabric1());
                intent.putExtra("Contact",model.getSellerPhone());
                intent.putExtra("pid",model.getpID());
                mcontext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);

        return new myviewHolder(view);
    }

    public class myviewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView price, name, description;
        CardView cardrec;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            price = itemView.findViewById(R.id.product_price);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);

            cardrec = itemView.findViewById(R.id.cardrec);

        }
    }


}
