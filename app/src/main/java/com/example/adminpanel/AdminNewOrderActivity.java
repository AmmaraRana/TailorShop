package com.example.adminpanel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrderActivity extends AppCompatActivity {
    private RecyclerView orderlist;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        orderlist = findViewById(R.id.orderlist);
        orderlist.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(orderRef, AdminOrders.class)
                        .build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull AdminOrders model) {
                        holder.username.setText("Name :" + model.getName());
                        holder.usershippingAddress.setText("Shipping Address :" + model.getAddress() + "," + model.getCity());
                        holder.userTotalPrice.setText("Price  :" + model.getTotalAmount());

                        holder.userDateTime.setText("Ordered At :" + model.getDate() + "," + model.getTime());
                        holder.userphoneNumber.setText("Contact Info" + model.getContactinfo());

                        holder.productbill.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(AdminNewOrderActivity.this, AdminUserProductActivity.class);
                                intent.putExtra("uid", model.getContactinfo());
                                startActivity(intent);
                            }
                        });

                        holder.showorderbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(AdminNewOrderActivity.this, AdminUserProductActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrderActivity.this);
                                builder.setTitle("Confirm/take the order?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which == 0) {
                                            String uID = getRef(position).getKey();
                                            RemoveOrder(uID);

                                        } else if (which == 1) {
                                            startActivity(new Intent(AdminNewOrderActivity.this, AdminNewOrderActivity.class));
//                                            will be used for pending products
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);

                        return new AdminOrdersViewHolder(view);
                    }
                };
        orderlist.setAdapter(adapter);
        adapter.startListening();


    }

    private void RemoveOrder(String uID) {
        orderRef.child(uID).removeValue();


    }

    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder {
        TextView username, userphoneNumber, userTotalPrice, userDateTime, usershippingAddress;
        Button showorderbtn, productbill;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.order_user_name);
            userphoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            usershippingAddress = itemView.findViewById(R.id.order_address_city);
            productbill = itemView.findViewById(R.id.product_bill);
            showorderbtn = itemView.findViewById(R.id.show_all_product);

        }
    }

}