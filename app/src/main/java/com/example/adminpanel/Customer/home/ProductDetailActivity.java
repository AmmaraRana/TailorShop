package com.example.adminpanel.Customer.home;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.ImageModel;
import com.example.adminpanel.activites.todo.Variation1Adapter;
import com.example.adminpanel.activites.todo.Variation3Adapter;
import com.example.adminpanel.activites.todo.VariationAdapter;
import com.example.adminpanel.adapter.detailAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class ProductDetailActivity extends AppCompatActivity implements VariationAdapter.OnImageClickListener, Variation1Adapter.OnImageClickListener, Variation3Adapter.OnImageClickListener {
    String productID = "";

    String sid = "";

    TextView name, price, category, contact, detail, shop, address;
    int intValue;
    String fabric=null;
    Button addtocart;
    RecyclerView recvariation;
    RecyclerView recvariation2;
    RecyclerView recvariation3;

    private String selectedImage1, selectedImage2, selectedImage3;
    private TextView tvCounter;
    private int counter = 0;
    private ArrayList<ImageModel> list;
    private ArrayList<ImageModel> list1, list2, list3;
    private detailAdapter adapter;
    private TextView textvar1, textvar2, textvar3;
    private VariationAdapter adapter1;
    private Variation1Adapter adapter2;

    private Variation3Adapter adapter3;
    RadioButton fab1, fab2, fab3;
    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference("Products");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        Paper.init(this);

        addtocart = findViewById(R.id.add_To_CartButton);
        tvCounter = findViewById(R.id.tvCounter);

        Button btnIncrement = findViewById(R.id.btnIncrement);
        Button btnDecrement = findViewById(R.id.btnDecrement);
        textvar1 = findViewById(R.id.text_var1);
        textvar2 = findViewById(R.id.text_var2);
        textvar3 = findViewById(R.id.text_var3);
        // Set click listeners
        btnIncrement.setOnClickListener(v -> updateCounter(++counter));
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        btnDecrement.setOnClickListener(v -> {
            if (counter > 0) {
                updateCounter(--counter);
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

        getvarname();

        addtocart.setOnClickListener(v -> addtoCart());


        address = findViewById(R.id.et_address);
        name = findViewById(R.id.product_name_detail);
        price = findViewById(R.id.product_price_detail);

        recvariation = findViewById(R.id.recycler_variation);
        recvariation2 = findViewById(R.id.recycler_variation2);
        recvariation3 = findViewById(R.id.recycler_variation3);


        shop = findViewById(R.id.product_shop);
        contact = findViewById(R.id.product_phone_detail);

        category = findViewById(R.id.product_Category_detail);
        detail = findViewById(R.id.product_description_detail);
        productID = getIntent().getStringExtra("pId");

        sid = getIntent().getStringExtra("sid");
        getProductDetail(productID);


    }


    @Override
    protected void onStart() {
        super.onStart();
        recvariation.setHasFixedSize(true);
        recvariation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ViewPager2 recyclerView = findViewById(R.id.multiimage);

        // Initialize lists before setting adapters
        list1 = new ArrayList<>();

        list2 = new ArrayList<>();
        adapter2 = new Variation1Adapter(list2, this);
        recvariation2.setAdapter(adapter2);

        list3 = new ArrayList<>();
        adapter3 = new Variation3Adapter(list3, this);
        recvariation3.setAdapter(adapter3);

        recvariation2.setHasFixedSize(true);
        recvariation2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recvariation3.setHasFixedSize(true);
        recvariation3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list = new ArrayList<>();
        adapter = new detailAdapter(list, this);
        adapter1 = new VariationAdapter(list1, this);
        recvariation.setAdapter(adapter1);
        recyclerView.setAdapter(adapter);


        root.child(productID).child("variations").child("images1").child("imageUrls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String imageModel = dataSnapshot.getValue(String.class);
                    list1.add(new ImageModel(imageModel));
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        root.child(productID).child("multi:images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
//                list1.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String imageModel = dataSnapshot.getValue(String.class);
                    list.add(new ImageModel(imageModel));
//                    list1.add(new ImageModel(imageModel));
                }
                adapter.notifyDataSetChanged();
//                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Set the click listener for the adapter

        adapter1.setOnImageClickListener(this);
        adapter2.setOnImageClickListener(this);
        adapter3.setOnImageClickListener(this);
    }

    private void addtoCart() {
        // Check if the user is logged in

        // User is logged in, proceed with adding to the cart
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        // Get a reference to your Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference cartlistref = database.getReference("CartList"); // "products" is the node where you want to store product information
if(fabric!=null){

    // Create a HashMap to store product information
    HashMap<String, Object> cartMap = new HashMap<>();
    cartMap.put("name", name.getText().toString());
    cartMap.put("price", price.getText().toString());
    cartMap.put("category", category.getText().toString());
    cartMap.put("shop", shop.getText().toString());
    cartMap.put("contact", contact.getText().toString());
    cartMap.put("description", detail.getText().toString());
    cartMap.put("fabric", fabric.toString());
    cartMap.put("time", saveCurrentTime);
    cartMap.put("date", saveCurrentDate);
    cartMap.put("pID", productID);
    cartMap.put("discount", "");
    cartMap.put("quantity", tvCounter.getText().toString());
    cartMap.put("sid", sid);

    String key = cartlistref.push().getKey();
    cartlistref.child("UserView").child(Prevalent.currentonlineUser.getPhone()).child("Products")
            .child(productID)
            .updateChildren(cartMap)
            .addOnCompleteListener(task -> {
                cartlistref.child("AdminView").child(sid).child(Prevalent.currentonlineUser.getPhone()).child("Products").child(productID)
                        .updateChildren(cartMap)
                        .addOnCompleteListener(task1 -> {
                            Toast.makeText(ProductDetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                            finish();
                        });
            });
}else {
    Toast.makeText(this, "Select fabric ", Toast.LENGTH_SHORT).show();
}
    }

    private void updateCounter(int newCounter) {
        counter = newCounter;
        tvCounter.setText(String.valueOf(counter));
        // You can use 'counter' as an integer
        Log.d("MainActivity", "Current Counter: " + counter);


    }

    private void getvarname() {
        DatabaseReference varref = FirebaseDatabase.getInstance().getReference().child("Products").child(productID).child("variations");
        varref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot variationSnapshot : snapshot.getChildren()) {
                    // Retrieve the name inside each "images1", "images2", and "images3"
                    String nameImages1 = variationSnapshot.child("images1").child("name").getValue(String.class);
                    String nameImages2 = variationSnapshot.child("images2").child("name").getValue(String.class);
                    String nameImages3 = variationSnapshot.child("images3").child("name").getValue(String.class);
                    textvar1.setText(nameImages1);
                    if (nameImages1 == null) {
                        Toast.makeText(ProductDetailActivity.this, "null insie", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                    fab1.setText(product.getFabric1());
                    fab2.setText(product.getFabric2());
                    fab3.setText(product.getFabric3());
                    shop.setText(product.getsShop());
                    contact.setText(product.getSellerPhone());
                    address.setText(product.getSellerAddress() + "," + product.getSellerCity());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

// Add click listeners to RadioButton fab1, fab2, fab3
        fab1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fabric = fab1.getText().toString();
            }
        });

        fab2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fabric = fab2.getText().toString();
            }
        });

        fab3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fabric = fab3.getText().toString();
            }
        });
    }

    @Override
    public void onImageClick(String selectedImage, int position) {
        switch (position) {
            case 0:
                // Handle the case when an image from the first adapter is selected
                addImageToCart1(selectedImage);
                break;
            case 1:
                // Handle the case when an image from the second adapter is selected
                addImageToCart2(selectedImage);
                break;
            case 2:
                // Handle the case when an image from the third adapter is selected
                addImageToCart3(selectedImage);
                break;
            // Add more cases if you have additional adapters
        }
    }


    private void saveImageDownloadUrl(String toString) {

    }

    private byte[] getByteArrayFromImageURI(String imageUri) {
        if (imageUri != null) {
            try {
                // Open an input stream from the image URI
                InputStream inputStream = getContentResolver().openInputStream(Uri.parse(imageUri));

                // Check if the input stream is not null
                if (inputStream != null) {
                    // Calculate the length of the input stream
                    int length = inputStream.available();

                    // Create a byte array to hold the data
                    byte[] data = new byte[length];

                    // Read the entire input stream into the byte array
                    inputStream.read(data);

                    // Close the input stream
                    inputStream.close();

                    return data;
                } else {
                    // Handle the case when inputStream is null
                    // For example, log an error message or return a default byte array
                    Log.e("ImageUpload", "Input stream is null for image URI: " + imageUri);
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle IOException (e.g., log an error message or return a default byte array)
            }
        } else {
            // Handle the case when imageUri is null
            // For example, log an error message or return a default byte array
            Log.e("ImageUpload", "Image URI is null");
        }

        return null;
    }

    private void addImageToCart3(String selectedImage) {
        if (selectedImage != null) {
            String saveCurrentTime, saveCurrentDate;
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());

            // Get a reference to your Firebase Realtime Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference cartlistref = database.getReference("CartList");

            // Create a HashMap to store product information
            HashMap<String, Object> cartMap = new HashMap<>();
            // ... (other cart information)

            // Check and add the image URL to the cart data
            cartMap.put("image3", selectedImage);

            // ... (other cart information)

            String key = cartlistref.push().getKey();
            cartlistref.child("UserView").child(Prevalent.currentonlineUser.getPhone()).child("Products")
                    .child(productID)
                    .updateChildren(cartMap)
                    .addOnCompleteListener(task -> {
                        cartlistref.child("AdminView").child(sid).child(Prevalent.currentonlineUser.getPhone()).child("Products").child(productID)
                                .updateChildren(cartMap)
                                .addOnCompleteListener(task1 -> {
                                    Toast.makeText(ProductDetailActivity.this, "Selected Variation", Toast.LENGTH_SHORT).show();
                                    // Set selectedImage3 to null after completing database operations

                                });
                    });
            selectedImage3 = null;
            selectedImage = null;
        }
    }

    private void addImageToCart2(String selectedImage) {
        if (selectedImage != null) {
            String saveCurrentTime, saveCurrentDate;
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());

            // Get a reference to your Firebase Realtime Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference cartlistref = database.getReference("CartList");

            // Create a HashMap to store product information
            HashMap<String, Object> cartMap = new HashMap<>();
            // ... (other cart information)

            // Check and add the image URL to the cart data
            cartMap.put("image2", selectedImage);

            // ... (other cart information)

            String key = cartlistref.push().getKey();
            cartlistref.child("UserView").child(Prevalent.currentonlineUser.getPhone()).child("Products")
                    .child(productID)
                    .updateChildren(cartMap)
                    .addOnCompleteListener(task -> {
                        cartlistref.child("AdminView").child(sid).child(Prevalent.currentonlineUser.getPhone()).child("Products").child(productID)
                                .updateChildren(cartMap)
                                .addOnCompleteListener(task1 -> {
                                    Toast.makeText(ProductDetailActivity.this, "Selcted Variation 2", Toast.LENGTH_SHORT).show();
                                    // Set selectedImage2 to null after completing database operations

                                });
                    });
            selectedImage2 = null;
            selectedImage = null;
        }
    }

    private void addImageToCart1(String selectedImage) {
        if (selectedImage != null) {
            String saveCurrentTime, saveCurrentDate;
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());

            // Get a reference to your Firebase Realtime Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference cartlistref = database.getReference("CartList");

            // Create a HashMap to store product information
            HashMap<String, Object> cartMap = new HashMap<>();
            // ... (other cart information)

            // Check and add the image URL to the cart data
            cartMap.put("image1", selectedImage);

            // ... (other cart information)

            String key = cartlistref.push().getKey();
            cartlistref.child("UserView").child(Prevalent.currentonlineUser.getPhone()).child("Products")
                    .child(productID)
                    .updateChildren(cartMap)
                    .addOnCompleteListener(task -> {
                        cartlistref.child("AdminView").child(sid).child(Prevalent.currentonlineUser.getPhone()).child("Products").child(productID)
                                .updateChildren(cartMap)
                                .addOnCompleteListener(task1 -> {
                                    Toast.makeText(ProductDetailActivity.this, "Selected Successfully", Toast.LENGTH_SHORT).show();
                                    // Set selectedImage1 to null after completing database operations

                                });
                    });
            selectedImage = null;
            selectedImage1 = null;
        }
    }
}