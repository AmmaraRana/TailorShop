package com.example.adminpanel.ui.setting;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingFragment extends Fragment {
    CircleImageView profileimage;
    private EditText fullNameEDT, userPhoneEDT, addressEDT;
    private TextView profileChangeTextbtn;
    private StorageTask uploadtask;
    private Button updateTxtbtn;

     Uri imageuri;
    private String myUrl = "";

    private String checker = "";
    private StorageReference storageprofilepictureRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        storageprofilepictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");


        profileimage = view.findViewById(R.id.setting_profile_image);
        fullNameEDT = view.findViewById(R.id.setting_full_name);
        userPhoneEDT = view.findViewById(R.id.setting_phone_number);
        addressEDT = view.findViewById(R.id.setting_address);
        profileChangeTextbtn = view.findViewById(R.id.profile_image_change);
        updateTxtbtn = view.findViewById(R.id.buttonWithImage);

        userinfodisplay(profileimage, fullNameEDT, userPhoneEDT, addressEDT);

//        upload or save info
        updateTxtbtn.setOnClickListener(v -> {
            if (checker.equals("clicked")) {


                userinfosaved();
            } else {
                updateonlyuserinfo();
            }
        });


        profileChangeTextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";
                CropImage.activity(imageuri)
                        .setAspectRatio(1, 1)
                        .start(getActivity());
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK
                && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageuri = result.getUri();
            profileimage.setImageURI(imageuri);
        } else {
            Toast.makeText(getContext(), "Error! Try again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), SettingFragment.class));
getActivity().finish();
        }
    }

    private void userinfosaved() {
        if (TextUtils.isEmpty(fullNameEDT.getText().toString())) {
            Toast.makeText(getContext(), "User Name is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPhoneEDT.getText().toString())) {
            Toast.makeText(getContext(), "Phone Number is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(addressEDT.getText().toString())) {
            Toast.makeText(getContext(), "Address  is mandatory", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            uploadimage();
        }

    }

    private void uploadimage() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating Profile");
        progressDialog.setMessage("Please wait! while we are updating your profile info");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (imageuri != null) {
            final StorageReference fileRef = storageprofilepictureRef.
                    child(Prevalent.currentonlineUser.getPhone() + ".jpg");
            uploadtask = fileRef.putFile(imageuri);
            uploadtask.continueWithTask(task -> {

                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileRef.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    myUrl = downloadUri.toString();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                    HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put("name", fullNameEDT.getText().toString());
                    userMap.put("address", addressEDT.getText().toString());
                    userMap.put("image", myUrl);
                    ref.child(Prevalent.currentonlineUser.getPhone()).updateChildren(userMap);
                    progressDialog.dismiss();
                    startActivity(new Intent(getContext(), SettingFragment.class));
                    Toast.makeText(getContext(), "Information Updated sucessfully", Toast.LENGTH_SHORT).show();
                    getActivity().finish();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Error Ocurred", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateonlyuserinfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", fullNameEDT.getText().toString());
        userMap.put("address", addressEDT.getText().toString());
        ref.child(Prevalent.currentonlineUser.getPhone()).updateChildren(userMap);

        startActivity(new Intent(getContext(), SettingFragment.class));
        Toast.makeText(getContext(), "Information Updated sucessfully", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    private void userinfodisplay(CircleImageView profileimage, EditText fullNameEDT, EditText userPhoneEDT, EditText addressEDT) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(Prevalent.currentonlineUser.getPhone());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("image").exists()) {
                        String image = snapshot.child("image").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();
                        Picasso.get().load(image).into(profileimage);
                        fullNameEDT.setText(name);
                        userPhoneEDT.setText(phone);
                        addressEDT.setText(address);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}