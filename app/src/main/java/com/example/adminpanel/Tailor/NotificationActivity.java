package com.example.adminpanel.Tailor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorAdapter.NotificationAdapter;
import com.example.adminpanel.Tailor.TailorModel.SellerOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView sellercartlist;

    RecyclerView.LayoutManager linearlayout;
    DatabaseReference cartref;
    NotificationAdapter adapter;
    List<SellerOrder> catList;
    String uid = "", productid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        uid = getIntent().getStringExtra("uid");
        productid = getIntent().getStringExtra("pId");

        sellercartlist = findViewById(R.id.sellorcart);
        linearlayout = new LinearLayoutManager(this);
        sellercartlist.setHasFixedSize(true);
        sellercartlist.setLayoutManager(linearlayout);

        catList = new ArrayList<>();
        adapter = new NotificationAdapter(catList, this);
        sellercartlist.setAdapter(adapter);

        cartref = FirebaseDatabase.getInstance().getReference().child("CartList")
                .child("AdminView")
                .child(FirebaseAuth.getInstance().getUid())
                .child(uid)
                .child("Products");


        cartref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                catList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SellerOrder order = dataSnapshot.getValue(SellerOrder.class);
                    catList.add(order);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
