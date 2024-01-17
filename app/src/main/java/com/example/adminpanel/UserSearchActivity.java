package com.example.adminpanel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.activites.todo.filterAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserSearchActivity extends AppCompatActivity {

    MaterialSearchBar materialSearchBar;
    RecyclerView recyclerView;
    filterAdapter adapter;
    TextView noResultTextView;
    ImageView backimage;
    List<Product> datalist;
    Chip selectedChip;
    String query = "", chips = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        datalist = new ArrayList<>();
        backimage = findViewById(R.id.backimage);
        noResultTextView = findViewById(R.id.noResultTextView);

        backimage.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.filter_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new filterAdapter(this, datalist);
        recyclerView.setAdapter(adapter);

        // Apply chips
        TextView textView = findViewById(R.id.tv);
        ChipGroup chipGroup = findViewById(R.id.chipgroup);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("men");
        arrayList.add("women");
        arrayList.add("custom stitched");
        arrayList.add("ready to wear");
        Random random = new Random();

        for (String s : arrayList) {
            final Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.chip_layout, chipGroup, false);
            chip.setText(s);
            chip.setId(random.nextInt());
            chipGroup.addView(chip);

            // Set OnClickListener for each chip
            chip.setOnClickListener(view -> {
                // Check if there is a previously selected chip
                if (selectedChip != null) {
                    // Unselect the previous chip
                    selectedChip.setChecked(false);
                }

                // Update the query string when a chip is selected
                chips = chip.getText().toString();

                // Update UI based on the selected chip
                textView.setText("Selected: " + chips);

                // Set the current chip as selected
                selectedChip = chip;
                selectedChip.setChecked(true);

                // Apply the query with both chip and search bar
                applyQuery();
            });
        }

        // Search bar setup
        materialSearchBar = findViewById(R.id.searchBar);
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                query = text.toString().toLowerCase();
                applyQuery();
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });
    }

    private void applyQuery() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Product> newDataList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);

                    if (product != null && containsQuery(product, query, chips)) {
                        newDataList.add(product);
                    }
                }

                updateAdapterAndTextView(newDataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });
    }

    private void updateAdapterAndTextView(List<Product> newDataList) {
        if (adapter != null) {
            adapter.updateData(newDataList);
            if (noResultTextView != null) {
                noResultTextView.setVisibility(newDataList.isEmpty() ? View.VISIBLE : View.GONE);
            }
        }
    }

    private boolean containsQuery(Product product, String query, String chips) {
        boolean queryMatch = productMatchesQuery(product, query);
        boolean chipMatch = chips.isEmpty() || productMatchesChip(product, chips);
        return queryMatch && chipMatch;
    }

    private boolean productMatchesQuery(Product product, String query) {
        return containsIgnoreCase(product.getName(), query) ||
                containsIgnoreCase(product.getSellerCity(), query) ||
                containsIgnoreCase(product.getCategory(), query) ||
                containsIgnoreCase(product.getsShop(), query) ||
                containsIgnoreCase(product.getFabric3(), query) ||
                containsIgnoreCase(product.getFabric1(), query) ||
                containsIgnoreCase(product.getFabric2(), query);
    }

    private boolean productMatchesChip(Product product, String chips) {
        return containsIgnoreCase(product.getType(), chips) ||
                containsIgnoreCase(product.getSex(), chips);
    }

    private boolean containsIgnoreCase(String text, String query) {
        return text != null && text.toLowerCase().contains(query.toLowerCase());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}