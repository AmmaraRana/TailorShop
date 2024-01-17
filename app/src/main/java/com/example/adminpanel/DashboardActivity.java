package com.example.adminpanel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.Tailor.ContentActivity;
import com.example.adminpanel.Tailor.SellerProductDetailActivity;
import com.example.adminpanel.Tailor.TailorAdapter.MainProductAdapter;
import com.example.adminpanel.ViewHolder.ProductViewHolder;
import com.example.adminpanel.activites.AdminMaintainProductActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private DatabaseReference productsRef;
    private RecyclerView recyclerView;
    private DatabaseReference sellerlist;
    MainProductAdapter adapter;
    List<Product> productList;
    MaterialSearchBar searchBar;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        sellerlist = FirebaseDatabase.getInstance().getReference()
                .child("Products");
        type = getIntent().getExtras().get("Admin").toString();

//        searchBar = findViewById(R.id.searchBar);

//        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
//            @Override
//            public void onSearchStateChanged(boolean enabled) {
//
//            }
//
//            @Override
//            public void onSearchConfirmed(CharSequence text) {
//
//                Intent intent = new Intent(DashboardActivity.this, SearchProductActivity.class);
//                intent.putExtra("query", text.toString());
//                startActivity(intent);
//            }
//
//            @Override
//            public void onButtonClicked(int buttonCode) {
//
//            }
//        });


        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        productList = new ArrayList<>();
        adapter = new MainProductAdapter(this, productList);

        recyclerView.setAdapter(adapter);



        productsRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    productList.add(product);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        sellerlist.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            productList.add(product);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors

                    }
                });

    }
}