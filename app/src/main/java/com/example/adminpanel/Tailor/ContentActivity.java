package com.example.adminpanel.Tailor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.R;
import com.example.adminpanel.SearchProductActivity;
import com.example.adminpanel.ViewHolder.ProductViewHolder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends AppCompatActivity {
    String sName, sPhone, sShop;
    TextView sellername, sellershop, sellerphone;
    RecyclerView.LayoutManager layoutManager;
    List<Product> productList;
    ProductViewHolder adapter;
    MaterialSearchBar materialSearchBar;
    Button orders;
    private RecyclerView recyclerView;
    private DatabaseReference sellerlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
//        getFCMToken();
//        findiews
        sellername = findViewById(R.id.usernameText);
        sellerphone = findViewById(R.id.userNumberText);
//o
        orders = findViewById(R.id.checkorder);

        orders.setOnClickListener(v -> {
            startActivity(new Intent(ContentActivity.this, SellerCheckOrderActivty.class));
        });
        sellerinformation();


//        findviews

        materialSearchBar = findViewById(R.id.searchBar);
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                Intent intent = new Intent(ContentActivity.this, SearchProductActivity.class);
                intent.putExtra("query", text.toString().toLowerCase());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


//        bottom_nav_bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ShopView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                {
                    if (item.getItemId() == R.id.navigation_addition) {

                        startActivity(new Intent(ContentActivity.this, AdminCategoryActivity.class));
                        overridePendingTransition(0, 0);


                        return true;
                    } else if (item.getItemId() == R.id.navigation_ShopView) {
                        return true;
                    } else if (item.getItemId() == R.id.navigation_notifications) {
                        startActivity(new Intent(ContentActivity.this, DashboardActivity2.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
                return false;
            }
        });


        recyclerView = findViewById(R.id.seller_home_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        sellerlist = FirebaseDatabase.getInstance().getReference()
                .child("Products");

        productList = new ArrayList<>();
        adapter = new ProductViewHolder(this, productList);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

        sellerlist.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
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

    private void sellerinformation() {

        final DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference().child("Tailors");
        sellerRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            sName = snapshot.child("name").getValue().toString();

                            sPhone = snapshot.child("phone").getValue().toString();


                            sShop = snapshot.child("ShopName").getValue().toString();

                            sellername.setText(sShop);
                            sellerphone.setText("Shop Contact: " + sPhone);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}