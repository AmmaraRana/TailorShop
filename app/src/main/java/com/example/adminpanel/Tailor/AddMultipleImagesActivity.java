package com.example.adminpanel.Tailor;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorAdapter.RecyclerAdapter;
import com.example.adminpanel.databinding.ActivityAddMultipleImagesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AddMultipleImagesActivity extends AppCompatActivity implements RecyclerAdapter.CountWhenItemRemoved{
    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    private ImageAdapter imageAdapter1, imageAdapter2, imageAdapter3;

    private EditText editTextName1, editTextName2, editTextName3;
    private Button btnChooseImages1, btnChooseImages2, btnChooseImages3;
    private static final int PICK_IMAGES_REQUEST = 1;
    private List<Uri> selectedImages1, selectedImages2, selectedImages3;
    private StorageReference storageReference;


    ActivityAddMultipleImagesBinding binding;
    ArrayList<Uri> uri;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    TextView textView;
    private Uri imageuri;
    ProgressDialog progressDialog;
    StorageReference mstorage;
    Button pick, resset, upload;  // Added 'upload' button

    private static final int READ_PERMISSION = 101;
    private static final int PICK_IMAGE = 1;
    String productId;
    DatabaseReference databaseReferences;
    DatabaseReference databaseReference; // Added DatabaseReference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMultipleImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        productId = getIntent().getStringExtra("productId");


        editTextName1 = findViewById(R.id.editTextName1);
        btnChooseImages1 = findViewById(R.id.btnChooseImages1);
        recyclerView1 = findViewById(R.id.recyclerView1);

        editTextName2 = findViewById(R.id.editTextName2);
        btnChooseImages2 = findViewById(R.id.btnChooseImages2);
        recyclerView2 = findViewById(R.id.recyclerView2);

        editTextName3 = findViewById(R.id.editTextName3);
        btnChooseImages3 = findViewById(R.id.btnChooseImages3);
        recyclerView3 = findViewById(R.id.recyclerView3);



        selectedImages1 = new ArrayList<>();
        selectedImages2 = new ArrayList<>();
        selectedImages3 = new ArrayList<>();

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
//        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        imageAdapter1 = new ImageAdapter(selectedImages1);
        imageAdapter2 = new ImageAdapter(selectedImages2);
        imageAdapter3 = new ImageAdapter(selectedImages3);

        recyclerView1.setAdapter(imageAdapter1);
        recyclerView2.setAdapter(imageAdapter2);
        recyclerView3.setAdapter(imageAdapter3);

        btnChooseImages1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser(30);
                btnChooseImages1.setVisibility(View.GONE);
            }
        });

        btnChooseImages2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser(2);
                btnChooseImages2.setVisibility(View.GONE);
            }
        });

        btnChooseImages3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser(3);
                btnChooseImages3.setVisibility(View.GONE);
            }
        });




        progressDialog();

        mstorage = FirebaseStorage.getInstance().getReference("images").child("multi:images").child(productId);

        textView = binding.totalPhotos;
        resset = binding.reset;
        upload = binding.upload; // Added 'upload' button

        // Initialize DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(productId).child("multi:images");
        databaseReferences = FirebaseDatabase.getInstance().getReference("Products").child(productId).child("variations");
        resset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!uri.isEmpty()) {
                    // Remove images from Firebase
                    for (Uri imageUri : uri) {
                        removeImageFromFirebase(imageUri);
                    }

                    // Clear local list and notify the adapter
                    uri.clear();
                    adapter.notifyDataSetChanged();
                    textView.setText("Photos(" + uri.size() + ")");}

            }


        });

        recyclerView = findViewById(R.id.recyclerView_gallery_image);
        pick = findViewById(R.id.pick);
        uri = new ArrayList<>();
        adapter = new RecyclerAdapter(uri, getApplicationContext(), this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        upload.setOnClickListener(v -> {
            uploadImages();
            if (!uri.isEmpty()) {
                progressDialog.show();
              Intent intent=new Intent(AddMultipleImagesActivity.this,ContentActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(intent);

            } else {
                Toast.makeText(AddMultipleImagesActivity.this, "Please select atleast one image to upload", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void progressDialog() {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Getting images");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }



    private void openImageChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }
    private void updateRecyclerViewAdapter(int requestCode) {
        switch (requestCode) {
            case 1:
                imageAdapter1.notifyDataSetChanged();
                break;
            case 2:
                imageAdapter2.notifyDataSetChanged();
                break;
            case 3:
                imageAdapter3.notifyDataSetChanged();
                break;
        }
    }
    private void uploadImages() {
        final String name1 = editTextName1.getText().toString().trim();
        final String name2 = editTextName2.getText().toString().trim();
        final String name3 = editTextName3.getText().toString().trim();

        if (name1.isEmpty() || selectedImages1.isEmpty() ||
                name2.isEmpty() || selectedImages2.isEmpty() ||
                name3.isEmpty() || selectedImages3.isEmpty()) {
            // Handle validation
            return;
        }

        final String nameKey1 = databaseReference.push().getKey();
        final DatabaseReference nameRef1 = databaseReferences.child("images1");
        nameRef1.child("name").setValue(name1);

        final String nameKey2 = databaseReference.push().getKey();
        final DatabaseReference nameRef2 = databaseReferences.child("images2");
        nameRef2.child("name").setValue(name2);

        final String nameKey3 = databaseReference.push().getKey();
        final DatabaseReference nameRef3 = databaseReferences.child("images3");
        nameRef3.child("name").setValue(name3);

        uploadImagesToStorage(selectedImages1, nameRef1);
        uploadImagesToStorage(selectedImages2, nameRef2);
        uploadImagesToStorage(selectedImages3, nameRef3);
    }

    private void uploadImagesToStorage(final List<Uri> selectedImages, final DatabaseReference nameRef) {
        final AtomicInteger uploadCount = new AtomicInteger(selectedImages.size());

        for (final Iterator<Uri> iterator = selectedImages.iterator(); iterator.hasNext();) {
            final Uri imageUri = iterator.next();
            final String imageName = System.currentTimeMillis() + "_" + imageUri.getLastPathSegment();
            final StorageReference imageReference = storageReference.child(imageName);

            imageReference.putFile(imageUri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                imageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            Uri downloadUri = task.getResult();
                                            if (downloadUri != null) {
                                                nameRef.child("imageUrls").push().setValue(downloadUri.toString());

                                                if (uploadCount.decrementAndGet() == 0) {
                                                    // Remove the uploaded image from the list
                                                    iterator.remove();
                                                    updateRecyclerViewAdapterByList(selectedImages);

                                                    if (selectedImages1.isEmpty() && selectedImages2.isEmpty() && selectedImages3.isEmpty()) {
                                                        // Clear the lists and hide RecyclerViews and Upload button
                                                        selectedImages1.clear();
                                                        selectedImages2.clear();
                                                        selectedImages3.clear();
                                                        recyclerView1.setVisibility(View.GONE);
                                                        recyclerView2.setVisibility(View.GONE);
                                                        recyclerView3.setVisibility(View.GONE);

                                                    }
                                                }
                                            }
                                        }
                                    }
                                });
                            } else {
                                // Handle failure
                            }
                        }
                    });
        }
    }
    private void updateRecyclerViewAdapterByList(List<Uri> selectedImages) {
        if (selectedImages == selectedImages1) {
            imageAdapter1.notifyDataSetChanged();
        } else if (selectedImages == selectedImages2) {
            imageAdapter2.notifyDataSetChanged();
        } else if (selectedImages == selectedImages3) {
            imageAdapter3.notifyDataSetChanged();
        }
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
        private List<Uri> imageList;

        public ImageAdapter(List<Uri> imageList) {
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            Uri imageUri = imageList.get(position);
            holder.imageView.setImageURI(imageUri);
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                // Select multiple images
                int countOfImages = data.getClipData().getItemCount();
                for (int i = 0; i < countOfImages; i++) {
                    if (uri.size() < 100) {
                        imageuri = data.getClipData().getItemAt(i).getUri();
                        uri.add(imageuri);
                        uploadtofirebase();
                        progressDialog.show();
                    } else {
                        Toast.makeText(this, "You are not allowed to pick more than 8 images", Toast.LENGTH_SHORT).show();
                    }
                }
                // Notify the adapter
                adapter.notifyDataSetChanged();
                textView.setText("Photos(" + uri.size() + ")");
            } else {
                if (uri.size() < 100) {
                    // Get a single image
                    imageuri = data.getData();
                    uri.add(imageuri);
                    uploadtofirebase();
                    progressDialog.show();
                } else {
                    Toast.makeText(this, "You are not allowed to pick more than 8 images", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
                textView.setText("Photos(" + uri.size() + ")");
            }
        } else if (resultCode == RESULT_OK && data != null) {
            // Handle another result code here
            List<Uri> selectedImages;
            RecyclerView recyclerView;

            switch (requestCode) {
                case 30:
                    selectedImages = selectedImages1;
                    recyclerView = recyclerView1;
                    break;
                case 2:
                    selectedImages = selectedImages2;
                    recyclerView = recyclerView2;
                    break;
                case 3:
                    selectedImages = selectedImages3;
                    recyclerView = recyclerView3;
                    break;
                default:
                    return;
            }

            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                selectedImages.add(imageUri);
            }

            recyclerView.setVisibility(View.VISIBLE);

            updateRecyclerViewAdapter(requestCode);
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadtofirebase() {
        StorageReference fileReference = mstorage
                .child(System.currentTimeMillis() + "." + getFileExtension((imageuri)));

        fileReference.putFile(imageuri)
                .addOnSuccessListener(taskSnapshot -> {


                    // Get the download URL
                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Store the download URL in the database
                        storeImageUrl(uri.toString());
                    });


                })
                .addOnFailureListener(e -> {

                    Toast.makeText(AddMultipleImagesActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void storeImageUrl(String toString) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Products").child(productId).child("multi:images");
        String key = databaseReference.push().getKey();

        if (key != null) {

            databaseReference.child(key).setValue(toString)
                    .addOnSuccessListener(aVoid -> {
                        progressDialog.dismiss();
                        // Image URL stored successfully in the database
                        // You can perform additional actions here if needed
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        // Handle the failure to store the image URL
                        Toast.makeText(AddMultipleImagesActivity.this, "Failed to store image URL in database", Toast.LENGTH_SHORT).show();
                    });
        }
    }


    @Override
    public void clicked(int getSize) {
        textView.setText("Photos(" + uri.size() + ")");
    }
    private void removeImageFromFirebase(Uri imageUri) {
        final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Products")
                .child(productId).child("multi:images");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    databaseReference1.removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddMultipleImagesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
