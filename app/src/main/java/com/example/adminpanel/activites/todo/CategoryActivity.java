package com.example.adminpanel.activites.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Adapters.Categoryadapter;
import com.example.adminpanel.Customer.home.ProductDetailActivity;
import com.example.adminpanel.Customer.nav_bar;
import com.example.adminpanel.Model.Product;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.AddCategory;
import com.example.adminpanel.ViewHolder.ViewHolder;
import com.example.adminpanel.databinding.ActivityCategoryBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;

    MaterialSearchBar materialSearchBar;
    RecyclerView recyclerView;

    FirebaseRecyclerAdapter<Product, ViewHolder> adapter;
    private DatabaseReference catref;
    private RecyclerView RRecyclerview;
    Categoryadapter categoryadapter;
    List<AddCategory> list;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.productsRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        query = getIntent().getStringExtra("cat");
        applyQuery();
        binding.backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(CategoryActivity.this, nav_bar.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);

            }
        });
        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
              query=text.toString();
                if (adapter != null) {
                    adapter.stopListening();
                }
                // Apply the new query
                applyQuery();
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        catwork();
        procwork();

        binding.seeAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(CategoryActivity.this, CategoryExplorerActivity.class);
            }
        });


        // Search bar setup
        materialSearchBar = findViewById(R.id.searchBar);
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {



                query = text.toString();
                if (adapter != null) {
                    adapter.stopListening();
                }
                // Apply the new query
                applyQuery();
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

    }

    private void applyQuery() {

        String combinedQuery = query;

        // Use the combined query to fetch data
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(reference.orderByChild("category").startAt(combinedQuery), Product.class)
                        .build();

        adapter =
                new FirebaseRecyclerAdapter<Product, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Product model) {
                        holder.name.setText(model.getName());
                        holder.price.setText(model.getPrice());
                        holder.description.setText(model.getDescription());
                        Picasso.get().load(model.getImage()).into(holder.productImage);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(CategoryActivity.this, ProductDetailActivity.class);
                                intent.putExtra("pId", model.getpID());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_home_layout, parent, false);
                        ViewHolder holder = new ViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    private void procwork() {

    }

    private void catwork() {

        catref = FirebaseDatabase.getInstance().getReference().child("Categories");
        RRecyclerview = findViewById(R.id.categoryRecyclerView);
        RRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RRecyclerview.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        categoryadapter = new Categoryadapter(list, this);
        RRecyclerview.setAdapter(categoryadapter);

        catref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                int categoryCount = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddCategory category = dataSnapshot.getValue(AddCategory.class);
                    list.add(category);

                    // Increment the counter
                    categoryCount++;

                    // Break out of the loop if you've reached 8 categories
                    if (categoryCount >= 8) {
                        break;
                    }
                }

                categoryadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}