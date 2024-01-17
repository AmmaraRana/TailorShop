package com.example.adminpanel.Customer;

import static com.example.adminpanel.Prevalent.Prevalent.UserPasswordKey;
import static com.example.adminpanel.Prevalent.Prevalent.UserPhoneKey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.Model.Users;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.ResetPasswordActivity;
import com.example.adminpanel.Tailor.SellerLoginActivity;
import com.example.adminpanel.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ProgressDialog progressDialog;
    TextView customerlogin, sellerlogin;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog();
        Paper.init(this);
        binding.forgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "login");
                startActivity(intent);
            }
        });

        customerlogin = binding.customerLogin;
        sellerlogin = binding.sellerLogin;
        customerlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerlogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                customerlogin.setTypeface(null, Typeface.BOLD);

                // Reset styles for sellerLogin when unselected
                sellerlogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                sellerlogin.setTypeface(null, Typeface.NORMAL);


            }
        });
        sellerlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerlogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                sellerlogin.setTypeface(null, Typeface.BOLD);

                // Reset styles for customerLogin when unselected
                customerlogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                customerlogin.setTypeface(null, Typeface.NORMAL);
                Intent intent = new Intent(LoginActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });

        binding.Registerbtn.setOnClickListener(v -> {
            progressDialog.show();
            LoginUsers();

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        customerlogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        customerlogin.setTypeface(null, Typeface.BOLD);
    }


    private void LoginUsers() {

        String phone = binding.Lphone.getText().toString();
        String password = binding.Lpassword.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter your Phone Number... ", Toast.LENGTH_SHORT).show();
            binding.Lphone.setError("Contact Number required..");
            binding.Lphone.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your Password... ", Toast.LENGTH_SHORT).show();
            binding.Lpassword.setError("Password required..");
            binding.Lpassword.requestFocus();

        } else {


            AllowAcess(phone, password);

        }
    }

//    private void AllowAcessToAccount(String phone, String password) {
//
//        if (binding.rememberMeLink.isChecked()) {
//            Paper.book().write(UserPhoneKey, phone);
//            Paper.book().write(UserPasswordKey, password);
//        }
//
//
//        final DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();
//        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child(parentDbName).child("Customers").child(phone).exists()) {
//                    Users userData = snapshot.child(parentDbName).child("Customers").child(phone).getValue(Users.class);
//                    if (userData.getPhone().equals(phone)) {
//                        if (userData.getPassword().equals(password)) {
//                            if (parentDbName.equals("Admin")) {
//                                Toast.makeText(LoginActivity.this, "welcome admin you are logged in  ", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
//                            }
////                           else if(parentDbName.equals("Users")){
////                               Toast.makeText(LoginActivity.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
////                               startActivity(new Intent(LoginActivity.this, HomeActivity.class));
////                           }
//                            else {
//                                Toast.makeText(LoginActivity.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(LoginActivity.this, nav_bar.class));
//                                Prevalent.currentonlineUser = userData;
//                            }
//                        } else {
//                            binding.Lpassword.setError("Invalid");
//                            binding.Lpassword.requestFocus();
//                            progressDialog.dismiss();
//                            Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        binding.Lphone.setError("Invalid");
//                        binding.Lphone.requestFocus();
//                        progressDialog.dismiss();
//                        Toast.makeText(LoginActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                } else {
//                    progressDialog.dismiss();
//                    Toast.makeText(LoginActivity.this,
//                            "Account does not exist", Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(LoginActivity.this, "failed here", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        if (UserPhoneKey != null && UserPasswordKey != null) {
//            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {
//                AllowAcess(UserPhoneKey, UserPasswordKey);
//            }
//
//        }
//
//    }

    private void AllowAcess(final String phone, final String password) {

        final DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers");
        if (binding.rememberMeLink.isChecked()) {
            Paper.book().write(UserPhoneKey, phone);
            Paper.book().write(UserPasswordKey, password);
        }

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(phone).exists()) {

                    Users userData = snapshot.child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone)) {
                        if (userData.getPassword().equals(password)) {
                            progressDialog.dismiss();

                            Toast.makeText(LoginActivity.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, nav_bar.class));
                            Prevalent.currentonlineUser = userData;

                        } else {
                            progressDialog.dismiss();
                            binding.Lpassword.clearComposingText();
                            Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.dismiss();
                        binding.Lphone.clearComposingText();
                        Toast.makeText(LoginActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void progressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Checking Credentials");
        progressDialog.setMessage("Logging In! please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }


}