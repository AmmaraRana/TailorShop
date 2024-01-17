package com.example.adminpanel.orders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.MainActivity;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.R;
import com.example.adminpanel.orderModel.PendingModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserPendingOrders extends AppCompatActivity {
    RecyclerView pendinglist;
    DatabaseReference orderref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pending_orders);


//        // Check if the user is logged in
//        if (Prevalent.currentonlineUser == null || TextUtils.isEmpty(Prevalent.currentonlineUser.getPhone())) {
//            // User is not logged in, redirect to the login activity
//            Intent loginIntent = new Intent(UserPendingOrders.this, MainActivity.class);
//            startActivity(loginIntent);
//            finish(); // Finish the current activity to prevent the user from coming back to it
//            return;
//        }
        orderref = FirebaseDatabase.getInstance().getReference().child("Orders").child("UserOrders").child(Prevalent.currentonlineUser.getPhone());
        pendinglist = findViewById(R.id.pending_recycler);
        pendinglist.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<PendingModel> options =
                new FirebaseRecyclerOptions.Builder<PendingModel>()
                        .setQuery(orderref, PendingModel.class)
                        .build();
        FirebaseRecyclerAdapter<PendingModel, PendinggViewHolder> adapter =
                new FirebaseRecyclerAdapter<PendingModel, PendinggViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PendinggViewHolder holder, int position, @NonNull PendingModel model) {
                        holder.username.setText("Name :" + model.getName());
                        holder.userphonenumber.setText("Phone :" + model.getContactinfo());
                        holder.totalamount.setText("Billing Amount :" + model.getTotalAmount());
                        holder.recieveraddress.setText("Reciever Address:" + model.getAddress()+","+model.getCity());
                        holder.state.setText("State :"+model.getState()+"Postal Code  :"+model.getPostalcode());
                        holder.orderdate.setText("Order at :"+ model.getTime()+","+model.getDate());
                        holder.requirement.setText(model.getCustomrequirement());

                    }

                    @NonNull
                    @Override
                    public PendinggViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_layout, parent, false);

                        return new PendinggViewHolder(view);
                    }
                };
        pendinglist.setAdapter(adapter);
        adapter.startListening();
    }

    public static class PendinggViewHolder extends RecyclerView.ViewHolder {
        public TextView username, userphonenumber, totalamount, recieveraddress, orderdate, requirement,state;

        public PendinggViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.order_user_name);
            userphonenumber = itemView.findViewById(R.id.order_phone_number);
            totalamount = itemView.findViewById(R.id.order_price);
            recieveraddress = itemView.findViewById(R.id.order_address_city);
            orderdate = itemView.findViewById(R.id.order_date_time);
            requirement = itemView.findViewById(R.id.customRequirementTitle);
            state=itemView.findViewById(R.id.code_state_city);
        }
    }
}