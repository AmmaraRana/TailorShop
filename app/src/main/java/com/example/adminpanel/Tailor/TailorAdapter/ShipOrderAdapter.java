package com.example.adminpanel.Tailor.TailorAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.ShipModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipOrderAdapter extends RecyclerView.Adapter<ShipOrderAdapter.ViewHolder> {
    private Context context;

    private List<ShipModel> list;

    public ShipOrderAdapter(Context context, List<ShipModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ShipOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("DEBUG", "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipment_bill_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipOrderAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.imageView);
        holder.usernameTextView.setText(list.get(position).getCustomerContact());
        holder.uidTextView.setText(list.get(position).getStatus());
        holder.emailTextView.setText("RS :   " + list.get(position).getPaidammount());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(v -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notification/Recipt")
                    .child(list.get(position).getCustomerContact()).child(list.get(position).getId());
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("sellerid", list.get(position).getSellerid());
            dataMap.put("Paidammount", list.get(position).getPaidammount());
            dataMap.put("id", list.get(position).getId());
            dataMap.put("CustomerContact", list.get(position).getCustomerContact());
            dataMap.put("DeliveryDays", "10 Days");
            reference.updateChildren(dataMap);
            showOrderShippedDialog(position);
        });

    }

    private void showOrderShippedDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Order Shipped");
        builder.setMessage("Has the order been shipped?");

        // Add "Order Shipped" button
        builder.setPositiveButton("Order Shipped", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String itemId = list.get(position).getId();
                // Remove the item from the list
                list.remove(position);

                removeFromDatabase(itemId);

                // Notify the adapter that the data set has changed
                notifyDataSetChanged();

                // Dismiss the dialog
                dialog.dismiss();
            }

            private void removeFromDatabase(String itemId) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("BillingSection")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                // Remove the item from the database using its ID
                databaseReference.child(itemId).removeValue();

            }
        });

        // Add "Cancel" button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
        TextView usernameTextView, emailTextView, uidTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            uidTextView = itemView.findViewById(R.id.uidTextView);
            imageView = itemView.findViewById(R.id.profileImageView);
        }
    }
}
