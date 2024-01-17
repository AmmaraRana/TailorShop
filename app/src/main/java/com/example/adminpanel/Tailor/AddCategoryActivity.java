package com.example.adminpanel.Tailor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.adminpanel.databinding.ActivityAddCategoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddCategoryActivity extends AppCompatActivity {
    ActivityAddCategoryBinding binding;
    DatabaseReference categoryRef;
    TextView categoryname;
    ImageView imageView;
    ProgressDialog progressDialog;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 2;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    // Uri for the selected image
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog();
        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Categories");
        storageReference = FirebaseStorage.getInstance().getReference("Categories");
        categoryname = binding.selectCategoryName;
        imageView = binding.selectCategoryImage;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestReadExternalStoragePermission();
        }


        binding.selectCategoryImage.setOnClickListener(v -> {
            selectImageFromGallery();
        });


        binding.addUpload.setOnClickListener(v -> {
            progressDialog.show();
            uploadData();
        });
    }

    private void uploadData() {

        if (imageUri != null) {
            // Create a unique key for the new data
            final String cId = databaseReference.push().getKey();

            // Storage reference for the image
            StorageReference fileReference = storageReference.child(cId + "." + getFileExtension(imageUri));

            // Upload the image to Firebase Storage
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get the download URL
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Check if the category already exists in the database
                                    checkCategoryExistence(cId, uri.toString());


//                                    // Save the data to the Realtime Database
//                                    AddCategory upload = new AddCategory(cId, categoryname.getText().toString().trim(), uri.toString());
//                                    databaseReference.child(cId).setValue(upload);
//                                    progressDialog.dismiss();
//                                    Toast.makeText(AddCategoryActivity.this, "Added successful", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(AddCategoryActivity.this, AdminCategoryActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddCategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void checkCategoryExistence(String cId, String toString) {

        categoryRef = FirebaseDatabase.getInstance().getReference().child("Categories");

        categoryRef.orderByChild("categoryName").equalTo(categoryname.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // The category already exists
                            progressDialog.dismiss();
                            Toast.makeText(AddCategoryActivity.this, "Category already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            // The category does not exist, save the data
                            saveData(cId, toString);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                        Toast.makeText(AddCategoryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveData(String categoryId, String imageUrl) {
        // Get other data you want to save, e.g., category name
        String categoryName = categoryname.getText().toString();

        // Create a HashMap to store the data
        HashMap<String, Object> categoryData = new HashMap<>();
        categoryData.put("categoryId", categoryId);
        categoryData.put("categoryName", categoryName.toLowerCase());
        categoryData.put("imageUrl", imageUrl);

        // Save the data to the "category" node with the unique category ID
        categoryRef.child(categoryId).setValue(categoryData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(AddCategoryActivity.this, "Category added successfully", Toast.LENGTH_SHORT).show();

                            // Navigate to the desired activity
                            Intent intent = new Intent(AddCategoryActivity.this, AdminCategoryActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AddCategoryActivity.this, "Failed to add category", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String getFileExtension(Uri imageUri) {

        // Get the file extension of the selected image
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(imageUri));
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void requestReadExternalStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_PERMISSION_CODE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    public void progressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Adding Category");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }
}