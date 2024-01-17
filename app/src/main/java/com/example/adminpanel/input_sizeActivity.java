package com.example.adminpanel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.adminpanel.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class input_sizeActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 1;
    private ImageView imageView;
    private EditText height, armlength, chest, neck, shoulder, width;
    private Uri imageUri;
    private ImageView backButton;
    private String productID, sid,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_size);
        productID = getIntent().getStringExtra("pid");
        amount=getIntent().getStringExtra("Total Price");
        sid = getIntent().getStringExtra("sid");
        imageView = findViewById(R.id.imageView);
        Button selectImageButton = findViewById(R.id.sendsize);

        height=findViewById(R.id.length);
        shoulder=findViewById(R.id.Shoulder);
        width=findViewById(R.id.width);
        neck=findViewById(R.id.neck);
        chest=findViewById(R.id.chest);
        armlength=findViewById(R.id.armlenght);


        productID = getIntent().getStringExtra("pid");
        sid = getIntent().getStringExtra("sid");

        imageView = findViewById(R.id.imageView);
        selectImageButton = findViewById(R.id.sendsize);

        height = findViewById(R.id.length);
        shoulder = findViewById(R.id.Shoulder);
        width = findViewById(R.id.width);
        neck = findViewById(R.id.neck);
        chest = findViewById(R.id.chest);
        armlength = findViewById(R.id.armlenght);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent=new Intent(input_sizeActivity.this,ConfirmFinalOrderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void openCamera() {
        // Create an Intent to open the camera app
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Start the camera activity and wait for the result
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the request is from the camera and if the result is OK
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the captured image as a Bitmap
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            // Set the Bitmap to the ImageView
            imageView.setImageBitmap(photo);
        }
    }

    private void saveData() {
        // Get data from EditText views
        String heightValue = height.getText().toString().trim();
        String shoulderValue = shoulder.getText().toString().trim();
        String widthValue = width.getText().toString().trim();
        String neckValue = neck.getText().toString().trim();
        String chestValue = chest.getText().toString().trim();
        String armlengthValue = armlength.getText().toString().trim();

        // Check if any EditText is empty
        if (TextUtils.isEmpty(heightValue) || TextUtils.isEmpty(shoulderValue) ||
                TextUtils.isEmpty(widthValue) || TextUtils.isEmpty(neckValue) ||
                TextUtils.isEmpty(chestValue) || TextUtils.isEmpty(armlengthValue)) {
            Toast.makeText(this, "Each field is mandatory. Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // All EditText fields have values, proceed with saving data to the database
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Sizes");

            // You can include the data in a HashMap and save it to the database using your preferred method
            // For example, you can use Firebase Realtime Database or Firestore to save the data
            HashMap<String, Object> sizeMap = new HashMap<>();
            sizeMap.put("height", heightValue);
            sizeMap.put("shoulder", shoulderValue);
            sizeMap.put("width", widthValue);
            sizeMap.put("neck", neckValue);
            sizeMap.put("chest", chestValue);
            sizeMap.put("armlength", armlengthValue);

            // Save the data to the database using the sizeMap
            databaseRef.child(productID).child("userSizes").setValue(sizeMap);

            // Show a success message
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();

            // Assuming you want to go to the next activity after saving data
            Intent intent = new Intent(input_sizeActivity.this, ConfirmFinalOrderActivity.class);
            intent.putExtra("Total Price",amount);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

}