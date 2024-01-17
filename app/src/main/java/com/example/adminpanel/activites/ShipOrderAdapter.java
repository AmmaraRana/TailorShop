package com.example.adminpanel.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Customer.home.ProductDetailActivity;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShipOrderAdapter extends RecyclerView.Adapter<ShipOrderAdapter.ViewHolder> {
    Context context;
    List<Notify> list;

    public ShipOrderAdapter(Context context, List<Notify> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ShipOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ship_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipOrderAdapter.ViewHolder holder, int position) {

        holder.textView1.setText("Product ID" + list.get(position).getId());
        holder.textView2.setText("Reciever Contact:" + list.get(position).getCustomerContact());
        holder.textView3.setText("Paid Amount :\t" + list.get(position).getPaidammount());
        holder.textview4.setText("Swift Delivery Commitment within  10 Days");
        holder.seeproduct.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("pId", list.get(position).getId());
            context.startActivity(intent);
        });
        // Set a click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the dialog when the item is clicked
                showReceivedDialog(list.get(position).getId());
            }
        });
    }


    private void showReceivedDialog(String position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Product Received?");
        builder.setMessage("Have you received the product?");

        // Add "Yes" button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the case when the user clicks "Yes"
                // You can perform any action you need, such as marking the product as received in the database
                // For now, let's just show a toast
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                        .child("Notification").child("Recipt")
                        .child(Prevalent.currentonlineUser.getPhone()).child(position);
                reference.removeValue();
                dialog.dismiss();
            }
        });

        // Add "No" button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the case when the user clicks "No"
                // You can perform any action you need
                dialog.dismiss();
            }
        });

        // Show the dialog
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3, textview4;
        Button seeproduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.order_user_name);
            textView2 = itemView.findViewById(R.id.order_phone_number);
            textView3 = itemView.findViewById(R.id.order_price);
            textview4 = itemView.findViewById(R.id.order_requirement);
            seeproduct = itemView.findViewById(R.id.show_all_product);
        }
    }
}
