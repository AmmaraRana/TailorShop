package com.example.adminpanel.Tailor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorAdapter.getCategoryAdapter;
import com.example.adminpanel.Tailor.TailorModel.AddCategory;
import com.example.adminpanel.databinding.ActivityAdminCategoryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminCategoryActivity extends AppCompatActivity {
    ActivityAdminCategoryBinding binding;
    private DatabaseReference catRef;
    private RecyclerView recyclerView;
    getCategoryAdapter adapter;
    List<AddCategory> catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        catRef = FirebaseDatabase.getInstance().getReference().child("Categories");
        recyclerView = findViewById(R.id.categorieslist);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);

        recyclerView.setLayoutManager(layoutManager);

        catList = new ArrayList<>();
        adapter = new getCategoryAdapter(catList, this);

        recyclerView.setAdapter(adapter);
//        bottom_nav_bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_addition);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                {
                    if (item.getItemId() == R.id.navigation_ShopView) {

                        startActivity(new Intent(AdminCategoryActivity.this, ContentActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    } else if (item.getItemId() == R.id.navigation_addition) {
                        return true;
                    } else if (item.getItemId() == R.id.navigation_notifications) {
                        startActivity(new Intent(AdminCategoryActivity.this, DashboardActivity2.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
                return false;
            }
        });
        binding.floatingActionButton.setOnClickListener(v -> {
            startActivity(new Intent(AdminCategoryActivity.this, AddCategoryActivity.class));
        });


        catRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                catList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AddCategory category = snapshot.getValue(AddCategory.class);
                    catList.add(category);
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