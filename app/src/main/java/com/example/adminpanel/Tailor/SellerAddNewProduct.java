package com.example.adminpanel.Tailor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.R;
import com.example.adminpanel.activites.Letsdoit;
import com.example.adminpanel.databinding.ActivityAdminAddNewProductBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SellerAddNewProduct extends AppCompatActivity {
    ActivityAdminAddNewProductBinding binding;
    private String CategoryName, Pname, description, price, saveCurrentDate, saveCurrentTime;

    private Uri Imageuri;
    private static final int gallerypic = 1;
    String productRandomKey, downloadIMageUrl;
    String selectedFabric1, selectedFabric2, selectedFabric3;
    ProgressDialog progressDialog;
    RadioButton radioButtonMen, radioButtonWomen;
    private TextView selected_fabrics;
    String selectedSex;
    String selectedType;
    StorageReference productImageRef;
    private String sName, sPhone, sAddress, sID, sEmail, sShop, sCity;
    private DatabaseReference productRef, sellerRef;

    String Fabrics = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminAddNewProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        sellerinformation();
        selectedFabric1="Select ";
        selectedFabric2="available ";
        selectedFabric3="fabric";
        productImageRef = FirebaseStorage.getInstance().getReference().child("Product:Images");

        setText();
//get
        selectedFabric1 = getIntent().getStringExtra("selectedFabric1");
        selectedFabric2 = getIntent().getStringExtra("selectedFabric2");
        selectedFabric3 = getIntent().getStringExtra("selectedFabric3");

//        Toast.makeText(this, Fabrics.toString(), Toast.LENGTH_SHORT).show();
        CategoryName = getIntent().getExtras().get("Idcategory").toString();

// Initialize RadioButtons
        RadioButton radioButtonReadyToWear = findViewById(R.id.buttonReadyToWear);
        RadioButton radioButtonUnstitched = findViewById(R.id.buttonUnstitched);

        // Set up OnCheckedChangeListener
        radioButtonReadyToWear.setOnCheckedChangeListener(radioButtonListener);
        radioButtonUnstitched.setOnCheckedChangeListener(radioButtonListener);

        radioButtonMen = findViewById(R.id.radioButtonMen);
        radioButtonWomen = findViewById(R.id.radioButtonWomen);


        // Set up OnCheckedChangeListener
        radioButtonMen.setOnCheckedChangeListener(radioButtonChangeListener);
        radioButtonWomen.setOnCheckedChangeListener(radioButtonChangeListener);

        if (savedInstanceState != null) {
            // Restore the image URI
            Imageuri = savedInstanceState.getParcelable("KEY_IMAGE_URI");
            CategoryName = savedInstanceState.getString("Name");
            selectedSex = savedInstanceState.getString("KEY_SELECTED_SEX");
            selectedType = savedInstanceState.getString("KEY_SELECTED_Type");
            price = savedInstanceState.getString("price");
        }

//        end

        binding.addNewProductBtn.setOnClickListener(v -> {
            progressDialog.show();
            ValidateProdductData();
        });
        binding.selectProductImage.setOnClickListener(v -> {
            OpenGallery();
        });
        progressDialog();
        binding.relativefabric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerAddNewProduct.this, Letsdoit.class);
                intent.putExtra("Idcategory", CategoryName);
                intent.putExtra("pName", Pname);
                intent.putExtra("type", selectedType);
                intent.putExtra("price", price);
                intent.putExtra("gender", selectedSex);
                startActivity(intent);
            }
        });
//if (selectedFabric3.isEmpty()){
//    Toast.makeText(this, selectedFabric3, Toast.LENGTH_SHORT).show();
//}

        binding.categoryName.setText(CategoryName);
        binding.selectedFabrics.setText(selectedFabric1 + "," + selectedFabric2 + "," + selectedFabric3);
    }

    private void gettext() {

    }

    private final CompoundButton.OnCheckedChangeListener radioButtonChangeListener = (buttonView, isChecked) -> {
        if (isChecked) {
            // Perform your action here when a RadioButton is selected
            if (buttonView.getId() == R.id.radioButtonMen) {
                // Handle Men selection
                selectedSex = "Men";
                // Do something with the selectedSex
                Toast.makeText(this, selectedSex, Toast.LENGTH_SHORT).show();

            } else if (buttonView.getId() == R.id.radioButtonWomen) {
                // Handle Women selection
                selectedSex = "Women";
                Toast.makeText(this, selectedSex, Toast.LENGTH_SHORT).show();

                // Do something with the selectedSex
            }
        }
    };
    // Listener to handle RadioButton selection
    private final CompoundButton.OnCheckedChangeListener radioButtonListener = (buttonView, isChecked) -> {
        if (isChecked) {
            // Perform your action here when a RadioButton is selected
            if (isChecked) {
                // Perform your action here when a RadioButton is selected
                if (buttonView.getId() == R.id.buttonReadyToWear) {
                    // Handle Ready to Wear selection
                    selectedType = "Ready to Wear";
                    Toast.makeText(this, selectedType, Toast.LENGTH_SHORT).show();
                    // Do something with the selectedType
                } else if (buttonView.getId() == R.id.buttonUnstitched) {
                    // Handle Unstitched selection
                    selectedType = "Unstitched";
                    // Do something with the selectedType
                }
            }
        }
    };

    private void setText() {

    }

    private void ValidateProdductData() {

        description = binding.productDescription.getText().toString();
        Pname = binding.productName.getText().toString();
        price = binding.productPrice.getText().toString();

        if (Imageuri == null) {
            Toast.makeText(this, "Product Image is required ", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            binding.selectProductImage.requestFocus();
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Description is mandatory", Toast.LENGTH_SHORT).show();
            binding.productDescription.setError("Name field is empty");
            binding.productDescription.requestFocus();
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Pname is mandatory", Toast.LENGTH_SHORT).show();
            binding.productName.setError("Name field is empty");
            binding.productName.requestFocus();
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Price is mandatory", Toast.LENGTH_SHORT).show();
            binding.productPrice.setError("Name field is empty");
            progressDialog.dismiss();
            binding.productPrice.requestFocus();
        } else {
            StoreProductInformation();

        }

    }

    public void progressDialog() {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Getting Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    private void StoreProductInformation() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss  a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        StorageReference filepath = productImageRef.child(Imageuri.getLastPathSegment() + productRandomKey + ".jpg");
        final UploadTask uploadTask = filepath.putFile(Imageuri);
        uploadTask.addOnFailureListener(e -> {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }).addOnSuccessListener(taskSnapshot -> {

            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                if (!(task.isSuccessful())) {
                    throw task.getException();
                }
                downloadIMageUrl = filepath.getDownloadUrl().toString();
                return filepath.getDownloadUrl();
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadIMageUrl = task.getResult().toString();

                        saveProductInfo();

                    }
                }
            });
        });

    }

    private void saveProductInfo() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pID", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", description);
        productMap.put("name", Pname.toLowerCase());
        productMap.put("price", price);
        productMap.put("image", downloadIMageUrl);
        productMap.put("category", CategoryName.toLowerCase());
        productMap.put("gender", selectedSex.toString().toLowerCase());
        productMap.put("Type", selectedType.toString().toLowerCase());
        productMap.put("fabric1", selectedFabric1.toLowerCase());
        productMap.put("fabric2", selectedFabric2.toLowerCase());
        productMap.put("fabric3", selectedFabric3.toLowerCase());
        productMap.put("SellerCity", sCity.toLowerCase());
        productMap.put("sellerAddress", sAddress.toLowerCase());
        productMap.put("sellerName", sName.toLowerCase());
        productMap.put("sellerPhone", sPhone);
        productMap.put("sellerEmail", sEmail);
        productMap.put("sid", sID);
        productMap.put("sShop", sShop.toLowerCase());
        productRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Intent intent = new Intent(SellerAddNewProduct.this, AddMultipleImagesActivity.class);
                    intent.putExtra("productId", productRandomKey);

                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SellerAddNewProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void OpenGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, gallerypic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallerypic && resultCode == RESULT_OK && data != null) {
            Imageuri = data.getData();
            binding.selectProductImage.setImageURI(Imageuri);
        }
    }

    private void sellerinformation() {

        sellerRef = FirebaseDatabase.getInstance().getReference().child("Tailors");
        sellerRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    sName = snapshot.child("name").getValue().toString();
                    sEmail = snapshot.child("email").getKey().toString();
                    sPhone = snapshot.child("phone").getValue().toString();
                    sAddress = snapshot.child("shopaddress").getValue().toString();
                    sID = snapshot.child("uid").getValue().toString();
                    sShop = snapshot.child("ShopName").getValue().toString();
                    sCity = snapshot.child("sellerCity").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save your values to the instance state
        outState.putString("Idcategory", CategoryName);  // Corrected key

        outState.putString("KEY_SELECTED_SEX", selectedSex);
        outState.putString("KEY_SELECTED_Type", selectedType);
        outState.putString("price", price);
        outState.putParcelable("KEY_IMAGE_URI", Imageuri);
    }

}