package com.example.adminpanel.Customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.SellerRegistrationActivity;
import com.example.adminpanel.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    TextInputEditText fullname;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingbar = new ProgressDialog(this);
        binding.fullname.setInputType(InputType.TYPE_CLASS_TEXT);


        binding.tailorregisterfrom.setOnClickListener(v -> {
            // Set the text style for Tailor Registration TextView
            binding.registerfrom.setTypeface(null, Typeface.NORMAL);
            binding.tailorregisterfrom.setTypeface(null, Typeface.BOLD);
            Intent intent=new Intent(RegisterActivity.this, SellerRegistrationActivity.class);
            startActivity(intent);
        });

        mAuth = FirebaseAuth.getInstance();
        //setting click listener on checkbok
        binding.checkboxChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.Rpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.cpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    binding.Rpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.cpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        binding.createAccountbtn.setOnClickListener(v -> {
//            createAccount();
            AccountCreation();
        });
    }

    private void AccountCreation() {
        fullname=findViewById(R.id.fullname);
        fullname.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);
                if (!Character.isLetter(character)) {
                    return "";
                }
            }
            return null;
        }});
        String address = binding.Raddress.getText().toString();
        String phone = binding.Rphone.getText().toString();
        String name =fullname.getText().toString();
        String password = binding.Rpassword.getText().toString();
        String cpassword = binding.cpassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name... ", Toast.LENGTH_SHORT).show();
            binding.fullname.setError("Name field is empty");
            binding.fullname.requestFocus();
        }
        else if (!isValidName(name)) {
            Toast.makeText(this, "Invalid name. Please enter only alphabets.", Toast.LENGTH_SHORT).show();
            binding.fullname.setError("Invalid name");
            binding.fullname.requestFocus();
        }
        else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter your Phone Number... ", Toast.LENGTH_SHORT).show();
            binding.Rphone.setError("Contact Number required..");
            binding.Rphone.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your Password... ", Toast.LENGTH_SHORT).show();
            binding.Rpassword.setError("Password required..");
            binding.Rpassword.requestFocus();

        } else if (TextUtils.isEmpty(cpassword)) {
            Toast.makeText(this, "Password Confirmation required ", Toast.LENGTH_SHORT).show();
            binding.cpassword.setError("Re-type your password here..");
            binding.cpassword.requestFocus();
        } else if (!(password.equals(cpassword))) {
            Toast.makeText(RegisterActivity.this, " Password confirmation failed", Toast.LENGTH_LONG).show();
            binding.cpassword.setError("Confirm your password again");
            binding.cpassword.requestFocus();
//             make field empty
            binding.cpassword.clearComposingText();
            binding.Rpassword.clearComposingText();
        } else if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password should be atleast 6_character long", Toast.LENGTH_LONG).show();
            binding.Rpassword.setError("Weak Password..");
            binding.Rpassword.requestFocus();

        } else if (!(phone.length() == 11)) {
            Toast.makeText(RegisterActivity.this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
            binding.Rphone.setError("Invalid");
            binding.Rphone.requestFocus();

        } else if (!(isValidPakistaniPhoneNumber(phone))) {
            Toast.makeText(RegisterActivity.this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
            binding.Rphone.setError("Invalid");
            binding.Rphone.requestFocus();
        }else if (TextUtils.isEmpty(address)) {
            Toast.makeText(RegisterActivity.this, "Address required", Toast.LENGTH_LONG).show();
            binding.etAddress.setError("Empty Field");
            binding.etAddress.requestFocus();
        }else if (!isStrongPassword(password)) {
            Toast.makeText(RegisterActivity.this, "Choose a stronger password", Toast.LENGTH_LONG).show();
            binding.Rpassword.setError("Weak Password");
            binding.Rpassword.requestFocus();
            loadingbar.dismiss();
            return;
        } else {
            loadingbar.setTitle("Create Account");
            loadingbar.setMessage("Please! wait.while we are checking credientials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            accountcreation(phone, password, address, name);

        }

    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                containsUppercase(password) &&
                containsLowercase(password) &&
                containsDigit(password) &&
                containsSpecialChar(password);
    }
    private boolean containsUppercase(String s) {
        return !s.equals(s.toLowerCase());
    }

    private boolean containsLowercase(String s) {
        return !s.equals(s.toUpperCase());
    }

    private boolean containsDigit(String s) {
        return s.matches(".*\\d.*");
    }

    private boolean containsSpecialChar(String s) {
        return !s.matches("[A-Za-z0-9]*");
    }
    private boolean isValidPakistaniPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("03\\d{8,9}");
    }

    private void accountcreation(String phone, String password, String address, String name) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("Customers");
        // Check if the user with the given phone number already exists
        databaseReference.orderByChild("phone").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // The user with this phone number already exists
                    Toast.makeText(RegisterActivity.this, "An account with this " + phone + " already exists", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                } else {
                    // The user with this phone number does not exist, create a new account

                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phone);
                    userDataMap.put("name", name);
                    userDataMap.put("password", password);
                    userDataMap.put("address", address);

                    databaseReference.child(phone).setValue(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Account Created successfully", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                loadingbar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Error creating account", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.registerfrom.setTypeface(null, Typeface.BOLD);
        binding.tailorregisterfrom.setTypeface(null, Typeface.NORMAL);
    }
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]");
    }
}