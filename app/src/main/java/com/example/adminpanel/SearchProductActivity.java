package com.example.adminpanel;

import static android.graphics.ColorSpace.match;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.Tailor.ContentActivity;
import com.example.adminpanel.activites.todo.SearcTailorhAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity {
    String query = "";
    TextView textView, noitemfound;
    String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
    ImageView back;
    SearcTailorhAdapter adapter;
    List<Product> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        query = getIntent().getStringExtra("query");
        back = findViewById(R.id.backbtn);
        noitemfound = findViewById(R.id.no_item_found);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(SearchProductActivity.this, ContentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        textView = findViewById(R.id.search_product_name);
        textView.setText(query);
        recyclerView = findViewById(R.id.search_list);
        list = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new SearcTailorhAdapter(this, list);
        recyclerView.setAdapter(adapter); // Set the adapter to the RecyclerView

        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        reference.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User has products, proceed with the search

                    retrieveProducts();
                } else {
                    // User has no products, handle the situation (e.g., show a message)
                    noitemfound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchProductActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrieveProducts() {
        DatabaseReference productsReference = FirebaseDatabase.getInstance().getReference().child("Products");

        productsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    if (product != null && containsQuery(product, query) && matcher(product,id)) {
                        list.add(product);
                    }
                }
                updateAdapterAndTextView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });
    }

    private boolean matcher(Product product, String id) {
        return id !=null && product.getSid().matches(id);
    }

    private void updateAdapterAndTextView() {
        adapter.notifyDataSetChanged();
        if (noitemfound != null) {
            noitemfound.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    private boolean containsQuery(Product product, String query) {
        return containsIgnoreCase(product.getName(), query) ||
                containsIgnoreCase(product.getSellerCity(), query) ||
                containsIgnoreCase(product.getCategory(), query) ||
                containsIgnoreCase(product.getsShop(), query) ||
                containsIgnoreCase(product.getFabric3(), query) ||
                containsIgnoreCase(product.getFabric1(), query) ||
                containsIgnoreCase(product.getFabric2(), query);
    }

    private boolean containsIgnoreCase(String text, String query) {
        return text != null && text.toLowerCase().contains(query.toLowerCase());
    }
}
