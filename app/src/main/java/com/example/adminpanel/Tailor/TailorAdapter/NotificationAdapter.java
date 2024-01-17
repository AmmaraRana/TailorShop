package com.example.adminpanel.Tailor.TailorAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminpanel.CartActivity;
import com.example.adminpanel.Interface.ItemClickListener;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.SellerProductDetailActivity;
import com.example.adminpanel.Tailor.TailorModel.SellerOrder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    List<SellerOrder> list;
    Context context;

    public NotificationAdapter(List<SellerOrder> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout, parent, false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        holder.txtCartname.setText(list.get(position).getName());
        holder.txtcartquantity.setText(list.get(position).getQuantity());
        holder.txtcartprice.setText(list.get(position).getPrice());
        holder.txtcatfab.setText(list.get(position).getFabric());
       holder.see_variations_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // Inflate the custom dialog layout
               View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_variations, null);

               // Create the AlertDialog
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setView(dialogView);
               builder.setTitle("Variations");

               // Retrieve ImageViews from the inflated layout
               ImageView imageView1 = dialogView.findViewById(R.id.imageView1);
               ImageView imageView2 = dialogView.findViewById(R.id.imageView2);
               ImageView imageView3 = dialogView.findViewById(R.id.imageView3);

               // Fetch and set the images from Firebase or any other source
               // Example using Picasso for image loading
               // Replace with your actual image URLs
               Picasso.get().load(list.get(position).getImage1()).into(imageView1);
               Picasso.get().load(list.get(position).getImage2()).into(imageView2);
               Picasso.get().load(list.get(position).getImage3()).into(imageView3);

               // Create and show the dialog
               AlertDialog dialog = builder.create();
               dialog.show();
           }
       });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SellerProductDetailActivity.class);
                intent.putExtra("pId", list.get(position).getpID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCartname, txtcartprice, txtcartquantity,txtcatfab;
        private ItemClickListener itemClickListener;
        public Button cartbtn,see_variations_btn;
        public ImageView imageView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
txtcatfab=itemView.findViewById(R.id.cart_product_fabric);
            cartbtn = itemView.findViewById(R.id.cart_require_btn);
            txtCartname = itemView.findViewById(R.id.cart_product_name);
            txtcartprice = itemView.findViewById(R.id.cart_product_price);
            see_variations_btn=itemView.findViewById(R.id.see_variations_btn);
            txtcartquantity = itemView.findViewById(R.id.cart_product_quantity);
        }
    }
}
