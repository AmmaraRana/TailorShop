package com.example.adminpanel;

import static com.example.adminpanel.Prevalent.Prevalent.UserPasswordKey;
import static com.example.adminpanel.Prevalent.Prevalent.UserPhoneKey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.adminpanel.Customer.LoginActivity;
import com.example.adminpanel.Customer.RegisterActivity;
import com.example.adminpanel.Customer.nav_bar;
import com.example.adminpanel.Model.Users;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.Tailor.SellerLoginActivity;
import com.example.adminpanel.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private Button login, signup;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.textViewStartAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, nav_bar.class));
            }
        });
        setContentView(binding.getRoot());
//progressDialog();
//
//
////        autamate login
//        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
//        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
//        Log.e("UserPhonekey", UserPasswordKey + UserPhoneKey);
//        if (UserPhoneKey != null && UserPasswordKey != null) {
//            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {
//                AllowedAcess(UserPhoneKey, UserPasswordKey);
//            }
//        } else {
//            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
//        }

//        binding.seller.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                  startActivity(new Intent(MainActivity.this, SellerLoginActivity.class));
//            }
//        });
        login = findViewById(R.id.main_login_btn);
        signup = findViewById(R.id.main_join_now_btn);
//        login
        Paper.init(this);
        login.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        });
//        signup
        signup.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });



        }
//    private void AllowedAcess(String userPhoneKey, String userPasswordKey) {
//        final DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers");
//        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child(UserPhoneKey).exists()) {
//
//                    Users userData = snapshot.child(UserPhoneKey).getValue(Users.class);
//
//                    if (userData.getPhone().equals(UserPhoneKey)) {
//                        if (userData.getPassword().equals(UserPasswordKey)) {
//                            progressDialog.dismiss();
//
//                            Toast.makeText(MainActivity.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(MainActivity.this, nav_bar.class));
//                            Prevalent.currentonlineUser = userData;
//
//                        } else {
//                            progressDialog.dismiss();
//
//                            Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        progressDialog.dismiss();
//
//                        Toast.makeText(MainActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "Anonymous", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    public void progressDialog() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Checking Credentials");
//        progressDialog.setMessage("Logging In! please wait..");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//
//    }


}





