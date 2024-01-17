package com.example.adminpanel.Tailor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.R;
import com.example.adminpanel.ViewHolder.ProductViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CheckNewProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Product> productList;
    ProductViewHolder adapter;

    private DatabaseReference unverifiedproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_product);
        recyclerView = findViewById(R.id.admin_product_checklist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        unverifiedproduct = FirebaseDatabase.getInstance().getReference()
                .child("Products");

        productList = new ArrayList<>();
        adapter = new ProductViewHolder(this, productList);
        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener((position, productId) -> {
//            // Open another activity with the correct product ID
//            // Handle the click event in the activity
//            Product clickedItem = productList.get(position);
//            final String productID = clickedItem.getpID();
//            CharSequence[] option = new CharSequence[]{
//                    "Yes",
//                    "No"
//            };
//            AlertDialog.Builder builder=new AlertDialog.Builder(CheckNewProductActivity.this);
//            builder.setTitle("Do you want to approve this product");
//            builder.setItems(option, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    if(which==0){
//                        changeproductstate(productID);
//                    } else if (which==1) {
//
//                    }
//                }
//            });
//            builder.show();
//        });

    }

    private void changeproductstate(String productID) {

        unverifiedproduct.child(productID)
                .child("productstate")
                .setValue("approved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CheckNewProductActivity.this, "Item has been approved and will be a" +
                                "dded to available prodech ", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();


        unverifiedproduct.orderByChild("productstate").equalTo("not approved").
                addValueEventListener(new ValueEventListener() {
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