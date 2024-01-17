package com.example.adminpanel.Tailor;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorAdapter.ShipOrderAdapter;
import com.example.adminpanel.Tailor.TailorModel.ShipModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShippmentOrder extends AppCompatActivity {
    ImageView back;
    private RecyclerView recyclerView;
    private DatabaseReference tailorRef;
    private String uid;  // Variable to store the UID
    private ShipOrderAdapter adapter;
    private List<ShipModel> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shippment_order);
        back = findViewById(R.id.backed);
        back.setOnClickListener(v -> finish());
        recyclerView = findViewById(R.id.recyclershipment);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();  // Get the UID

        // Reference to the "shipments" node for the specific user
        tailorRef = FirebaseDatabase.getInstance().getReference().child("BillingSection").child(uid);

        productList = new ArrayList<>();
        adapter = new ShipOrderAdapter(this, productList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        tailorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("snapshot", dataSnapshot.toString());
                productList.clear(); // Clear the list before adding new data


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ShipModel shipModel = snapshot.getValue(ShipModel.class);
                    productList.add(shipModel);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.e("ShippmentOrder", "Database Error: " + databaseError.getMessage());
            }
        });
    }
}