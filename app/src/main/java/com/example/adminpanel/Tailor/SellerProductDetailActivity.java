package com.example.adminpanel.Tailor;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorAdapter.MultiImageAdapter;
import com.example.adminpanel.Tailor.TailorModel.ImageModel;
import com.example.adminpanel.Tailor.TailorModel.itemImageModel;
import com.example.adminpanel.activites.todo.sellervariationAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellerProductDetailActivity extends AppCompatActivity {
    String productID = "", state = "Normal";
    private ViewPager2 recyclerView;
    private ArrayList<itemImageModel> list;

    private MultiImageAdapter adapter;
    private ArrayList<ImageModel> list1, list2, list3;
    RecyclerView recvariation, recvariation2, recvariation3;
    sellervariationAdapter varadapter;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Products");

    //    ImageSlider productImage;
    TextView name, price, category, contact, detail, shop, fabric, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_detail);
        productID = getIntent().getStringExtra("pId");
//        productImage = findViewById(R.id.product_image_detail);
        name = findViewById(R.id.product_name_detail);
        address = findViewById(R.id.et_address);
        price = findViewById(R.id.product_price_detail);
//        shop = findViewById(R.id.product_shop_detail);
//        contact = findViewById(R.id.product_contact_detail);
        fabric = findViewById(R.id.product_fabric_detail);
        category = findViewById(R.id.product_Category_detail);
        detail = findViewById(R.id.product_description_detail);
        getProductDetail(productID);

//start writing
        recyclerView = findViewById(R.id.multiimage_recycler);
        recvariation = findViewById(R.id.recycler_variation);
        recvariation2 = findViewById(R.id.recycler_variation2);
        recvariation3 = findViewById(R.id.recycler_variation3);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

//        show item on top
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        //

        list = new ArrayList<>();
        adapter = new MultiImageAdapter(list, SellerProductDetailActivity.this);
        recyclerView.setAdapter(adapter);
        root.child(productID).child("multi:images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Make sure to retrieve the value as a String
                    String imageUrl = dataSnapshot.getValue(String.class);

                    // Add the URL to your list
                    list.add(new itemImageModel(imageUrl));
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
//        stop it

    }

    private void getProductDetail(String productID) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Product product = snapshot.getValue(Product.class);
                    name.setText(product.getName());
                    price.setText(product.getPrice());
                    category.setText(product.getCategory());
                    detail.setText(product.getDescription());
                    fabric.setText(product.getFabric1()+"," +product.getFabric2()+"," +product.getFabric3());
                    address.setText(product.getSellerAddress() + "," + product.getSellerCity());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        // Initialize lists before setting adapters
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        varadapter = new sellervariationAdapter(list1, this);
        recvariation.setAdapter(varadapter);
        recvariation.setLayoutManager(layoutManager1);

        varadapter = new sellervariationAdapter(list2, this);
        recvariation2.setAdapter(varadapter);
        recvariation2.setLayoutManager(layoutManager2);

        varadapter = new sellervariationAdapter(list3, this);
        recvariation3.setAdapter(varadapter);
        recvariation3.setLayoutManager(layoutManager3);

        // Set up listeners for images1, images2, and images3
        root.child(productID).child("variations").child("images1").child("imageUrls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String imageModel = dataSnapshot.getValue(String.class);
                    list1.add(new ImageModel(imageModel));
                }
                recvariation.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });

        root.child(productID).child("variations").child("images2").child("imageUrls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String imageModel = dataSnapshot.getValue(String.class);
                    list2.add(new ImageModel(imageModel));
                }
                recvariation2.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });

        root.child(productID).child("variations").child("images3").child("imageUrls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String imageModel = dataSnapshot.getValue(String.class);
                    list3.add(new ImageModel(imageModel));
                }
                recvariation3.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }
}