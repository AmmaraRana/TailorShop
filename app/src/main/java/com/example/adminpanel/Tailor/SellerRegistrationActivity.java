package com.example.adminpanel.Tailor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.R;
import com.example.adminpanel.databinding.ActivitySellerRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.validator.routines.EmailValidator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity {
    ActivitySellerRegistrationBinding binding;
    ProgressDialog progressDialog;
    EditText sellername, sellerphone, selleremail, sellerpassword, sellershopaddress, bankdeatil, banknumber, shopname, sellercity;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog();

        mauth = FirebaseAuth.getInstance();
        bankdeatil = findViewById(R.id.seller_bankDetail);
        banknumber = findViewById(R.id.seller_banknumber);
        selleremail = findViewById(R.id.seller_email);
        sellername = findViewById(R.id.seller_name);
        sellerpassword = findViewById(R.id.seller_password);
        sellerphone = findViewById(R.id.seller_phone);
        sellershopaddress = findViewById(R.id.seller_address);
        sellercity = findViewById(R.id.seller_city);
        shopname = findViewById(R.id.seller_shopname);
       sellername.setInputType(InputType.TYPE_CLASS_TEXT);
       sellercity.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);
                if (!Character.isLetter(character)) {
                    return "";
                }
            }
            return null;
        }});
       sellername.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);
                if (!Character.isLetter(character)) {
                    return "";
                }
            }
            return null;
        }});
       bankdeatil.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
           for (int i = start; i < end; i++) {
               char character = source.charAt(i);
               if (!Character.isLetter(character)) {
                   return "";
               }
           }
           return null;
       }});

        // Add a text change listener to validate email format dynamically
        selleremail.addTextChangedListener(new EmailFormatTextWatcher());
        sellerphone.addTextChangedListener(new PakistaniPhoneNumberTextWatcher());


        binding.sellerRegisterBtn.setOnClickListener(v -> {
            progressDialog.show();
            registerSeller();
        });
    }

    public void progressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Checking Credentials");
        progressDialog.setMessage("Account Registration in progress! Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void registerSeller() {
        String name = sellername.getText().toString().trim();
        String shopaddress = sellershopaddress.getText().toString().trim();
        String phone = sellerphone.getText().toString().trim();
        String bankD = bankdeatil.getText().toString().trim();
        String bankN = banknumber.getText().toString().trim();
        String shopn = shopname.getText().toString().trim();
        String scity = sellercity.getText().toString().trim();
        String email = selleremail.getText().toString().trim();
        String password = sellerpassword.getText().toString().trim();

        if (!isValidEmail(email)) {
            progressDialog.dismiss();
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!EmailValidator.getInstance().isValid(email)) {
                progressDialog.dismiss();
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
                return;
            }
        }if (!isValidBankName(bankD)) {
            progressDialog.dismiss();
            Toast.makeText(this, "Invalid bank name. Please enter only alphabets.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            progressDialog.dismiss();
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isStrongPassword(password)) {
            Toast.makeText(SellerRegistrationActivity.this, "Choose a stronger password", Toast.LENGTH_LONG).show();
            binding.sellerPassword.setError("Weak Password");
            binding.sellerPassword.requestFocus();
           progressDialog.dismiss();
            return;
        }

       

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(shopaddress) ||
                TextUtils.isEmpty(phone) || TextUtils.isEmpty(bankN) || TextUtils.isEmpty(bankD) ||
                TextUtils.isEmpty(shopn) || TextUtils.isEmpty(scity)) {
            Toast.makeText(this, "Fill up the form completely", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        mauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();

                            String sid = mauth.getCurrentUser().getUid();
                            HashMap<String, Object> sellerMap = new HashMap<>();
                            sellerMap.put("uid", sid);
                            sellerMap.put("name", name);
                            sellerMap.put("phone", phone);
                            sellerMap.put("shopaddress", shopaddress);
                            sellerMap.put("email", email);
                            sellerMap.put("password", password);
                            sellerMap.put("Account_ID", bankD);
                            sellerMap.put("Account_Number", bankN);
                            sellerMap.put("ShopName", shopn);
                            sellerMap.put("sellerCity", scity);
                            rootref.child("Tailors").child(sid).updateChildren(sellerMap)
                                    .addOnCompleteListener(task1 -> {
                                        Toast.makeText(SellerRegistrationActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                        progressDialog.dismiss();
                                        Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }).addOnFailureListener(e -> {
                                        progressDialog.dismiss();
                                        selleremail.setError(e.getMessage().toString());
                                        Toast.makeText(SellerRegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });

                        }
                    }
                });
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 7 &&

                containsLowercase(password) &&
                containsDigit(password) &&
                containsSpecialChar(password);
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


    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private class EmailFormatTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Not needed
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            isValidEmail();
        }
        private void isValidEmail() {
            String emails = selleremail.getText().toString().trim();
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

            if (!emails.matches(emailRegex)) {
                selleremail.setError("Invalid email format.");
            } else {
                selleremail.setError(null);
            }
        }

    }

    private class PakistaniPhoneNumberTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            validatePakistaniPhoneNumber();
        }

        private void validatePakistaniPhoneNumber() {
            String phoneNumber = sellerphone.getText().toString().trim();
            if (!phoneNumber.matches("03\\d{8,9}")) {
                sellerphone.setError("Invalid  phone number!");
            } else {
                sellerphone.setError(null);
            }
        }
    }
    private boolean isValidBankName(String bankName) {
        return bankName.matches("[a-zA-Z]+");
    }
}
