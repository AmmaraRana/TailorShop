package com.example.adminpanel.Tailor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SellerLoginActivity extends AppCompatActivity {
    Button loginsellerbtn, login_register_btn;
    String email;
    EditText selleremail, sellerpassword;
    private FirebaseAuth mauth;
    CheckBox checkBox;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        progressDialog();
        mauth = FirebaseAuth.getInstance();
        checkBox = findViewById(R.id.checkbox_checked);
        login_register_btn = findViewById(R.id.login_register_btn);
        login_register_btn.setOnClickListener(v -> {
            startActivity(new Intent(SellerLoginActivity.this, SellerRegistrationActivity.class));
        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sellerpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());


            } else {
                sellerpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        });


        loginsellerbtn = findViewById(R.id.seller_login_btn);
        selleremail = findViewById(R.id.seller_ligin_email);
        sellerpassword = findViewById(R.id.seller_login_password);

        loginsellerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                loginseller();
            }
        });

    }

    public void progressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Checking Credentials");
        progressDialog.setMessage("Logging In! please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mauth.getCurrentUser();
        if (currentUser != null) {
            // User is already authenticated, navigate to the main activity
            Intent intent = new Intent(SellerLoginActivity.this, ContentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void loginseller() {
        final String email = selleremail.getText().toString();
        final String password = sellerpassword.getText().toString();

        if ((email.isEmpty() && password.isEmpty())) {
            sellerpassword.setError("Password Required");
            Toast.makeText(this, "Each field required ", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Check if the entered email has a valid email pattern
            selleremail.setError("Email Required");
            selleremail.requestFocus();
        } else {
            mauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Intent intent = new Intent(SellerLoginActivity.this, ContentActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        }
                    }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    if (e instanceof FirebaseAuthInvalidUserException) {
                        // If the user account does not exist
                        Toast.makeText(SellerLoginActivity.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle other authentication failures
                        Toast.makeText(SellerLoginActivity.this, "Authentication failed: " +e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}