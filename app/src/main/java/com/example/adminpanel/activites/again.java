package com.example.adminpanel.activites;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class again extends AppCompatActivity {
    String productID = "";
    ImageView productImage;
    TextView name, price, category, contact, detail, shop, fabric;
    private TextView tvCounter;
    int intValue;
    private int counter = 0;
    Button addtocart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_again);

        addtocart = findViewById(R.id.add_To_CartButton);
        tvCounter = findViewById(R.id.tvCounter);
        Button btnIncrement = findViewById(R.id.btnIncrement);
        Button btnDecrement = findViewById(R.id.btnDecrement);

        // Set click listeners
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCounter(++counter);
            }
        });

        btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter > 0) {
                    updateCounter(--counter);
                }
            }
        });
        String stringValue = tvCounter.getText().toString();
        try {
            intValue = Integer.parseInt(stringValue);
            // Now you have the integer value
        } catch (NumberFormatException e) {
            // Handle the case where the string is not a valid integer
            e.printStackTrace();
        }

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addtoCart();
            }
        });


        productImage = findViewById(R.id.product_image_detail);
        name = findViewById(R.id.product_name_detail);
        price = findViewById(R.id.product_price_detail);
        shop = findViewById(R.id.product_shop_detail);
        contact = findViewById(R.id.product_contact_detail);
        fabric = findViewById(R.id.product_fabric_detail);
        category = findViewById(R.id.product_Category_detail);
        detail = findViewById(R.id.product_description_detail);
        productID = getIntent().getStringExtra("pId");
        getProductDetail(productID);


    }

    private void addtoCart() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());


        SimpleDateFormat currenTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currenTime.format(calForDate.getTime());


        final DatabaseReference cartListref = FirebaseDatabase.getInstance().getReference().child("CartList");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pID", productID);
        cartMap.put("name", name.getText().toString());

        cartMap.put("shop", shop.getText().toString());
        cartMap.put("price", price.getText().toString());
        cartMap.put("description", detail.getText().toString());
        cartMap.put("fabric", fabric.getText().toString());
        cartMap.put("category", category.getText().toString());
        cartMap.put("quantity", intValue);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("image", productImage);
        cartListref.child("UserView").child(Prevalent.currentonlineUser.getPhone())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(again.this, "i am done", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });




    }

    private void updateCounter(int newCounter) {
        counter = newCounter;
        tvCounter.setText(String.valueOf(counter));
        // You can use 'counter' as an integer
        Log.d("MainActivity", "Current Counter: " + counter);


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
                    fabric.setText(product.getFabric1());
                    shop.setText(product.getsShop());
                    contact.setText(product.getSellerPhone());
                    Picasso.get().load(product.getImage()).into(productImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}