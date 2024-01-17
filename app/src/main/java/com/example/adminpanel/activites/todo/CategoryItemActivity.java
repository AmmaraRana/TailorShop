package com.example.adminpanel.activites.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.R;
import com.example.adminpanel.ViewHolder.FragmentViewHolder;
import com.example.adminpanel.ViewHolder.ProductViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemActivity extends AppCompatActivity {
    String catname;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Product> productList;
    FragmentViewHolder adapter;
    DatabaseReference sellerlist;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);
        catname = getIntent().getStringExtra("catname");
        recyclerView = findViewById(R.id.recyclerView);
        back=findViewById(R.id.toolbarImageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        sellerlist = FirebaseDatabase.getInstance().getReference()
                .child("Products");

        productList = new ArrayList<>();
        adapter = new FragmentViewHolder(this, productList);
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        super.onStart();

        sellerlist.orderByChild("category").equalTo(catname)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            productList.add(product);
                        }

                        // Update the adapter with the full list

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors
                    }
                });
    }
}