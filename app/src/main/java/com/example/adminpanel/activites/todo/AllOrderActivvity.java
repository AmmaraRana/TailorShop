package com.example.adminpanel.activites.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorAdapter.FeedbackAdapter;
import com.example.adminpanel.Tailor.TailorAdapter.NotificationAdapter;
import com.example.adminpanel.Tailor.TailorModel.FeedbackModel;
import com.example.adminpanel.Tailor.TailorModel.SellerOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllOrderActivvity extends AppCompatActivity {
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    List<SellerOrder> list;
    TextView emptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order_activvity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NotificationAdapter(list, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        // Create TextView dynamically
        emptyMessage = new TextView(this);
        emptyMessage.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        emptyMessage.setText("No orders available");
        emptyMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        emptyMessage.setTextColor(ContextCompat.getColor(this, R.color.black));
        emptyMessage.setGravity(Gravity.CENTER);

        // Add TextView to the layout
        ((FrameLayout) findViewById(android.R.id.content)).addView(emptyMessage);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("CartList")
                .child("AdminView")
                .child(FirebaseAuth.getInstance().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SellerOrder model = dataSnapshot.getValue(SellerOrder.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();

                // Check if the list is empty and show/hide the empty message
                if (list.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyMessage.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyMessage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AllOrderActivvity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}