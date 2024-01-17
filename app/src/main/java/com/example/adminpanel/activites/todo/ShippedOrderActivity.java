package com.example.adminpanel.activites.todo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.R;
import com.example.adminpanel.activites.Notify;
import com.example.adminpanel.activites.ShipOrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShippedOrderActivity extends AppCompatActivity {
    ImageView back;
    List<Notify> list;
    ShipOrderAdapter adapter;
    private RecyclerView orderlist;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipped_order);
        back = findViewById(R.id.backed);
        back.setOnClickListener(v -> {
            finish();
        });
        list = new ArrayList<>();
        adapter = new ShipOrderAdapter(this, list);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Notification")
                .child("Recipt").child(Prevalent.currentonlineUser.getPhone());
        orderlist = findViewById(R.id.pending_recycler);
        orderlist.setLayoutManager(new LinearLayoutManager(this));
        orderlist.setHasFixedSize(true);
        orderlist.setAdapter(adapter);
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list to avoid duplicates when data changes

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Assuming YourItemModel has a constructor that takes a DataSnapshot
                    Notify item = dataSnapshot.getValue(Notify.class);

                    if (item != null) {
                        list.add(item);
                    }
                }

                adapter.notifyDataSetChanged(); // Notify the adapter that the data set has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShippedOrderActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}