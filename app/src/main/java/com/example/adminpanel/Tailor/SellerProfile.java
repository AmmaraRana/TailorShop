package com.example.adminpanel.Tailor;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.databinding.ActivitySellerProfileBinding;
import com.example.adminpanel.ui.setting.Setting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SellerProfile extends AppCompatActivity {
    ActivitySellerProfileBinding binding;
    TextView et_name;
    String sName, sShop, sAddress, sEmail, sPhone, sCity, sBankID, sBanknum;
   EditText  et_shopname, et_email, et_address, et_bank_id, et_phone, account_Number,et_city;
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        et_address = binding.etAddress;
        et_city=binding.etCity;
        button=binding.updateAccountbtn;
        et_name = binding.etName;
        et_shopname = binding.etShopname;
        et_email = binding.etEmail;
        et_bank_id = binding.etBankId;
        et_phone = binding.etPhone;
        account_Number = binding.accountNumber;
        sellerinformation();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        etaddress = binding.etAddress;
//        etphone = binding.etPhone;
//        etbankinfo = binding.etBankId;
//        etbankdetail = binding.accountNumber;
//        etname = binding.etname;
//
//        profileimageview = binding.settingProfileImage;
//        edit = binding.profileEdit;
//        update = binding.updateAccountSetting;
//        profileimage=binding.settingProfileImage;
//
//        storageprofilepicturerefrence= FirebaseStorage.getInstance().getReference().child("TailorProfilePicture");
//
//        Userinfodaisplay(profileimageview, etaddress, etphone, etbankdetail, etbankinfo,etname);
//
//        binding.back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checker.equals("clicked")) {
//                    userinfosaved();
//                } else {
//                    updateonlyuserinfo();
//                }
//            }
//        });
//        profileimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checker="clicked";
//                CropImage.activity(imageuri)
//                        .setAspectRatio(1, 1)
//                        .start(SellerProfile.this);
//            }
//        });
//    }
//
//    private void updateonlyuserinfo() {
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Tailors");
//        HashMap<String, Object> userMap = new HashMap<>();
//        userMap.put("name", etname.getText().toString());
//        userMap.put("shopaddress", etaddress.getText().toString());
//        userMap.put("phone", etname.getText().toString());
//        userMap.put("Account_ID", etbankinfo);
//        userMap.put("Account_Number", etbankdetail);
//
//        ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(userMap);
//
//        startActivity(new Intent(this,ContentActivity.class));
//        Toast.makeText(this, "Information Updated sucessfully", Toast.LENGTH_SHORT).show();
//        finish();
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            imageuri = result.getUri();
//       profileimage.setImageURI(imageuri);
//
//        } else {
//            Toast.makeText(this, "Error! Try again", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(SellerProfile.this, SellerProfile.class));
//            finish();
//        }
//
//
//    }
//
//    private void userinfosaved() {
//        if (TextUtils.isEmpty(etname.getText().toString())) {
//            Toast.makeText(this, "User Name is mandatory", Toast.LENGTH_SHORT).show();
//        } else if (TextUtils.isEmpty(etphone.getText().toString())) {
//            Toast.makeText(this, "Phone Number is mandatory", Toast.LENGTH_SHORT).show();
//        } else if (TextUtils.isEmpty(etbankinfo.getText().toString())) {
//            Toast.makeText(this, "Address  is mandatory", Toast.LENGTH_SHORT).show();
//        } else if (TextUtils.isEmpty(etbankdetail.getText().toString())) {
//            Toast.makeText(this, "Address  is mandatory", Toast.LENGTH_SHORT).show();
//        } else if (TextUtils.isEmpty(etaddress.getText().toString())) {
//            Toast.makeText(this, "Address  is mandatory", Toast.LENGTH_SHORT).show();
//        }
//
//        else if (checker.equals("clicked")) {
//            uploadimage();
//        }
//    }
//
//    private void uploadimage() {
//     final   ProgressDialog progressDialog=new ProgressDialog(this);
//     progressDialog.setTitle("Update Profile");
//     progressDialog.setMessage("Please Wait! while we are updating your profile info");
//     progressDialog.setCanceledOnTouchOutside(false);
//     progressDialog.show();
//
//     if(imageuri!=null){
//         final StorageReference fileRef = storageprofilepicturerefrence.
//                 child(FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");
//         uploadtask = fileRef.putFile(imageuri);
//         uploadtask.continueWithTask(task -> {
//
//             if (!task.isSuccessful()) {
//                 throw task.getException();
//             }
//             return fileRef.getDownloadUrl();
//         }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
//             if (task.isSuccessful()) {
//                 Uri downloadUri = task.getResult();
//                 myUrl = downloadUri.toString();
//                 DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Tailors");
//                 HashMap<String, Object> userMap = new HashMap<>();
//                 userMap.put("name", etname.getText().toString());
//                 userMap.put("shopaddress", etaddress.getText().toString());
//                 userMap.put("phone", etname.getText().toString());
//                 userMap.put("Account_ID", etbankinfo);
//                 userMap.put("Account_Number", etbankdetail);
//                 userMap.put("image", myUrl);
//                 ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(userMap);
//                 progressDialog.dismiss();
//                 startActivity(new Intent(this,ContentActivity.class));
//                 Toast.makeText(this, "Information Updated sucessfully", Toast.LENGTH_SHORT).show();
//                 finish();
//
//             } else {
//                 progressDialog.dismiss();
//                 Toast.makeText(this, "Error Ocurred", Toast.LENGTH_SHORT).show();
//             }
//         });
//     } else {
//         Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
//     }
//    }
//
//    private void Userinfodaisplay(CircleImageView profileimageview, EditText etaddress, EditText etphone, EditText etbankdetail, EditText etbankinfo,EditText etname) {
//        DatabaseReference tailor = FirebaseDatabase.
//                getInstance().getReference().child("Tailors")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        tailor.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    if (snapshot.child("image").exists()) {
//                        String name = snapshot.child("name").getValue().toString();
//                        String image = snapshot.child("image").getValue().toString();
//                        String phone = snapshot.child("phone").getValue().toString();
//                        String address = snapshot.child("shopaddress").getValue().toString();
//                        String id = snapshot.child("Account_ID").getValue().toString();
//                        String accountNumber = snapshot.child("Account_Number").getValue().toString();
//                        Picasso.get().load(image).into(profileimageview);
//                        etaddress.setText(address);
//                        etphone.setText(phone);
//                        etbankdetail.setText(id);
//                        etbankinfo.setText(accountNumber);
//                        etname.setText(name);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
button.setOnClickListener(v -> {
    updateprofile();
});

    }

    private void updateprofile() {
        if (TextUtils.isEmpty(et_name.getText().toString())) {
            Toast.makeText(this, " Name is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_phone.getText().toString())) {
            Toast.makeText(this, "Phone Number is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_address.getText().toString())) {
            Toast.makeText(this, "Address  is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_shopname.getText().toString())) {
            Toast.makeText(this, "Shop Name is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_bank_id.getText().toString())) {
            Toast.makeText(this, "Bank ID is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(account_Number.getText().toString())) {
            Toast.makeText(this, "Address  is mandatory", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Updating Profile");
            progressDialog.setMessage("Please wait! Updating profile...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Tailors")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("name", et_name.getText().toString());
            userMap.put("ShopName", et_shopname.getText().toString());
            userMap.put("Account_Number",account_Number.getText().toString());
            userMap.put("phone", et_phone.getText().toString());
            userMap.put("Account_ID",et_bank_id.getText().toString());
            userMap.put("sellerCity",et_city.getText().toString());
            userMap.put("shopaddress",et_address.getText().toString());
            ref.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    Toast.makeText(SellerProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(SellerProfile.this, "Update Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sellerinformation() {

        final DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference().child("Tailors");
        sellerRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            sName = snapshot.child("name").getValue().toString();
                            sEmail = snapshot.child("name").getValue().toString();
                            sPhone = snapshot.child("phone").getValue().toString();
                            sAddress = snapshot.child("shopaddress").getValue().toString();
                            sBankID = snapshot.child("Account_ID").getValue().toString();
                            sBanknum = snapshot.child("Account_Number").getValue().toString();
                            sShop = snapshot.child("ShopName").getValue().toString();
                            sCity = snapshot.child("sellerCity").getValue().toString();

                            et_name.setText(sName);
                            et_email.setText(sEmail);
                            et_shopname.setText(sShop);
                            et_phone.setText(sPhone);
                            et_address.setText(sAddress );
                            et_city.setText(sCity);
                            et_bank_id.setText(sBankID);
                            account_Number.setText(sBanknum);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}