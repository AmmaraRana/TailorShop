package com.example.adminpanel.activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.Customer.nav_bar;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.Tailor;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class BillPaymentActivity extends AppCompatActivity {
    private static final int gallerypic = 1;
    String amount, sid, user, billid;
    String customercontact;
    String productid;

    Uri Imageuri;
    String accountid, accountnum;


    private TextView accountIdTextView;
    private TextView accountNumberTextView;
    private TextView amountTextView;
    private StorageReference storage;
    private ImageView uploadImageView;
    private Button sendPaymentProofButton;
    private String downloadIMageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);
        productid = getIntent().getStringExtra("deliverdwithin");
        sid = getIntent().getStringExtra("sid");
        amount = getIntent().getStringExtra("amount");
        user = getIntent().getStringExtra("customer");
        billid = getIntent().getStringExtra("billid");
        accountIdTextView = findViewById(R.id.accountIdTextView);
        accountNumberTextView = findViewById(R.id.accountNumberTextView);
        amountTextView = findViewById(R.id.amountTextView);
        uploadImageView = findViewById(R.id.uploadImageView);
        sendPaymentProofButton = findViewById(R.id.sendPaymentProofButton);
        displayInfo();
        sendPaymentProofButton.setOnClickListener(v -> BillingProof());
        uploadImageView.setOnClickListener(v -> openGallery());
        amountTextView.setText("Paid Amount:" + amount);


    }

    private void displayInfo() {

        final DatabaseReference displayref = FirebaseDatabase.getInstance().getReference().child(
                        "OrderedBill")
                .child("UserView")
                .child(user)
                .child("Bills");
        displayref.orderByChild("sellerid").equalTo(sid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DatabaseReference tailor = FirebaseDatabase.getInstance().getReference().child("Tailors").child(sid);
                    tailor.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Tailor tailor1 = snapshot.getValue(Tailor.class);
                                accountid = "AccountID" + tailor1.getAccount_ID();
                                accountnum = "Account Number" + tailor1.getAccount_Number();
                                accountIdTextView.setText(accountid);
                                accountNumberTextView.setText(accountnum);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(BillPaymentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BillPaymentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void BillingProof() {
        String saveCurrentTime, saveCurrentDate, productRandomKey;
        storage = FirebaseStorage.getInstance().getReference().child("Bill:Proofs");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss  a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;
        StorageReference filepath = storage.child(Imageuri.getLastPathSegment() + productRandomKey + ".jpg");
        final UploadTask uploadTask = filepath.putFile(Imageuri);
        uploadTask.addOnFailureListener(e -> {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }).addOnSuccessListener(taskSnapshot -> {

            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                if (!(task.isSuccessful())) {
                    throw task.getException();
                }
                downloadIMageUrl = filepath.getDownloadUrl().toString();
                return filepath.getDownloadUrl();
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadIMageUrl = task.getResult().toString();
                        if (downloadIMageUrl != null) {
                            saveBillingInfo();
                        } else {
                            Toast.makeText(BillPaymentActivity.this, "Something went wrong.Please Try again later ", Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            });
        });


    }

    private void saveBillingInfo() {

        String saveCurrentTime, saveCurrentDate, billingkey;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        billingkey = saveCurrentTime + saveCurrentDate;

        if (billingkey.isEmpty() && customercontact.isEmpty() && sid.isEmpty()) {
            Toast.makeText(this, "Failour ", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BillingSection").
                    child(sid);

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("Paidammount", amount.toString());
            hashMap.put("CustomerContact", Prevalent.currentonlineUser.getPhone().toString());
            hashMap.put("AccountID", accountid.toString());
            hashMap.put("AccountNum", accountnum.toString());
            hashMap.put("sellerid", sid.toString());
            hashMap.put("Status", "paid".toString());
            hashMap.put("Image", downloadIMageUrl.toString());
            hashMap.put("id", billingkey.toString());
            databaseReference.child(billingkey).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        DatabaseReference databaseReferences = FirebaseDatabase.getInstance().getReference()
                                .child("OrderedBill").child("UserView")
                                .child(Prevalent.currentonlineUser.getPhone())
                                .child("Bills");
                        databaseReferences.child(billid).removeValue();
                        Intent intent=new Intent(BillPaymentActivity.this,nav_bar.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

//                        showMessageDialog("Product will be delivered within :" + productid, 5000);
                    }

                }

                private void showMessageDialog(String message, int timeoutMillis) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BillPaymentActivity.this);

                    // Create a custom layout for the message
                    LinearLayout layout = new LinearLayout(BillPaymentActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setPadding(32, 32, 32, 32);  // Adjust padding as needed

                    // Set the message
                    TextView messageTextView = new TextView(BillPaymentActivity.this);
                    messageTextView.setText(message);
                    messageTextView.setTextSize(18);  // Adjust font size as needed

                    // Add the message to the layout
                    layout.addView(messageTextView);

                    // Set the custom layout to the builder
                    builder.setView(layout);

                    // Create and show the dialog
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    clearValues();
                    DatabaseReference databaseR = FirebaseDatabase.getInstance().getReference().child("OrderedBill")
                            .child("AdminView")
                            .child(sid)
                            .child("Bills");

                    // Set up a handler to dismiss the dialog after the specified timeout
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Intent intent = new Intent(BillPaymentActivity.this, nav_bar.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }, timeoutMillis);
                }

                private void clearValues() {
                    accountIdTextView.setText("");
                    accountNumberTextView.setText("");
                    uploadImageView.setImageURI(null);
                }
            });
        }

    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, gallerypic);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallerypic && resultCode == RESULT_OK && data != null) {
            Imageuri = Uri.parse(data.getDataString());
            if (Imageuri != null) {
                uploadImageView.setImageURI(Imageuri);
            } else {
                // Handle the case where Imageuri is null
                Toast.makeText(this, "Failed to retrieve selected image", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private void displayInfo() {
//        final DatabaseReference displayref = FirebaseDatabase.getInstance().getReference().child(
//                        "OrderedBill")
//                .child("AdminView")
//                .child(sid)
//                .child("Bills");
//
//
//        displayref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("DataSnapshot", "Snapshot: " + snapshot.toString());
//                if (snapshot.exists()) {
//                    Billing billing = snapshot.getValue(Billing.class);
//                    if (billing.getConinfo().equals(Prevalent.currentonlineUser.getPhone())) {
//
//                    }
//
//                    final DatabaseReference tailor = FirebaseDatabase.getInstance().getReference().child("Tailors")
//                            .child(sid);
//                    tailor.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Log.d("DataSnapshot", "Snapshot: " + snapshot.toString());
//                            if (snapshot.exists()) {
//                                Tailor tailor1 = snapshot.getValue(Tailor.class);
//                                accountid = tailor1.getAccount_ID();
//                                accountnum = tailor1.getAccount_Number();
//                                accountIdTextView.setText(accountid);
//                                accountNumberTextView.setText(accountnum);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(BillPaymentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(BillPaymentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

}
