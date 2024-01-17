package com.example.adminpanel.ui.setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.Customer.nav_bar;
import com.example.adminpanel.MainActivity;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.ResetPasswordActivity;
import com.example.adminpanel.databinding.ActivitySettingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Setting extends AppCompatActivity {
    ActivitySettingBinding binding;
    private EditText fullnameEditText, addressEditText, phonenumberEditText, setting_password;
    private TextView  closeTextbtn, saveTextbtn;
    private String myUrl = "";

    private String checker = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialization();
        // Check if the user is logged in
        if (Prevalent.currentonlineUser == null || TextUtils.isEmpty(Prevalent.currentonlineUser.getPhone())) {
            // User is not logged in, redirect to the login activity
            Intent loginIntent = new Intent(Setting.this, MainActivity.class);
            startActivity(loginIntent);
            finish(); // Finish the current activity to prevent the user from coming back to it
            return;
        }
        Paper.init(this);
        userInfoDisplay( fullnameEditText, addressEditText, phonenumberEditText,setting_password);
        closeTextbtn.setOnClickListener(v -> finish());
        saveTextbtn.setOnClickListener(v -> {
updateinfo();
        });

        binding.securityQuestions.setOnClickListener(v -> {
            Intent intent = new Intent(Setting.this, ResetPasswordActivity.class);
            intent.putExtra("check", "settings");
            startActivity(intent);
        });
        binding.buttonWithImage.setOnClickListener(v -> FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Paper.book().destroy();

                Intent intent = new Intent(new Intent(Setting.this, MainActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        }));

    }

    private void updateinfo() {
        if (TextUtils.isEmpty(fullnameEditText.getText().toString())) {
            Toast.makeText(this, "User Name is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phonenumberEditText.getText().toString())) {
            Toast.makeText(this, "Phone Number is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            Toast.makeText(this, "Address  is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(setting_password.getText().toString())) {
            Toast.makeText(this, "Password  is mandatory", Toast.LENGTH_SHORT).show();
        }else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers")
                    .child(Prevalent.currentonlineUser.getPhone());
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("address", addressEditText.getText().toString());
            userMap.put("name", fullnameEditText.getText().toString());
            userMap.put("password",setting_password.getText().toString());
            userMap.put("phone", phonenumberEditText.getText().toString());
            ref.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Setting.this, "Updated Sucessfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Setting.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    private void updateonlyuserinfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", fullnameEditText.getText().toString());
        userMap.put("address", addressEditText.getText().toString());
        userMap.put("phone", phonenumberEditText.getText().toString());
        userMap.put("password",setting_password.getText().toString());
        ref.child(Prevalent.currentonlineUser.getPhone()).updateChildren(userMap);

//        startActivity(new Intent(this, nav_bar.class));
        Toast.makeText(this, "Information Updated sucessfully", Toast.LENGTH_SHORT).show();
//        finish();
    }

    private void usersinfosaved() {


    }

    private void uploadimage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating Profile");
        progressDialog.setMessage("Please wait! while we are updating your profile info");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                    HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put("name", fullnameEditText.getText().toString());
                    userMap.put("address", addressEditText.getText().toString());
                    userMap.put("phone", phonenumberEditText.getText().toString());
                    userMap.put("password",setting_password.getText().toString());
                    userMap.put("image", myUrl);
                    ref.child(Prevalent.currentonlineUser.getPhone()).updateChildren(userMap);
                    progressDialog.dismiss();
                    Toast.makeText(this, "Information Updated sucessfully", Toast.LENGTH_SHORT).show();
                    finish();



    }

    public void initialization() {
        setting_password = binding.settingPassword;

        fullnameEditText = binding.settingFullName;
        addressEditText = binding.settingAddress;
        phonenumberEditText = binding.settingPhoneNumber;
        closeTextbtn = binding.closeSettingBtn;
        saveTextbtn = binding.updateAccountSetting;
    }

    private void userInfoDisplay( EditText fullnameEditText,
                                 EditText addressEditText, EditText phonenumberEditText,EditText setting_password) {
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference()
                .child("Users").child("Customers").child(Prevalent.currentonlineUser.getPhone());

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);
                    String Saddress = snapshot.child("address").getValue(String.class);
                    String password = snapshot.child("password").getValue(String.class);
                    setting_password.setText(password);
                    fullnameEditText.setText(name);
                    phonenumberEditText.setText(phone);
                    addressEditText.setText(Saddress);
                    if (snapshot.child("image").exists()) {
                        String image = snapshot.child("image").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);


                        fullnameEditText.setText(name);
                        phonenumberEditText.setText(phone);
                        addressEditText.setText(address);
                        setting_password.setText(password);
                    } else {
                        // Handle the case where the 'image' field does not exist in the snapshot
                    }
                } else {
                    // Handle the case where the snapshot does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}