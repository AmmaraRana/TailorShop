package com.example.adminpanel;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.Customer.nav_bar;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.activites.todo.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private final OkHttpClient client = new OkHttpClient();
    Button confirmOrderbtn, input_size;
    String totalamount = "", sid = "", pid = "", quantity = "", image = "";
    String leg, neck, shoulder, arm;
    String key;
    LinearLayout layout;
    TextView edit_fun;
    TextView textViewResult;
    TextView productPriceTextView, kquantity;
    EditText editText1, editText2, editText3, editText4;
    private EditText nameedt, addressedt, cityest, stateet, postalet, customet;
    private double armLength, legLength, neckCircumference, shoulderWidth;
    private File selectedImageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        confirmOrderbtn = findViewById(R.id.confirm_final_order_btn);
        input_size = findViewById(R.id.input_size);
        image = getIntent().getStringExtra("image");


        edit_fun = findViewById(R.id.edit_fun);
        quantity = getIntent().getStringExtra("quantity");
        pid = getIntent().getStringExtra("pid");
        sid = getIntent().getStringExtra("sid");

//        textViewResult=findViewById(R.id.textViewResult);
        textViewResult = findViewById(R.id.textViewResult);
        totalamount = getIntent().getStringExtra("Total Price");
        stateet = findViewById(R.id.stateEditText);
        stateet.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // Only allow alphabets in the input
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                        return ""; // Ignore the character
                    }
                }
                return null; // Accept the character
            }
        }});
        postalet = findViewById(R.id.postalCodeEditText);
        customet = findViewById(R.id.custome_requirement);
        kquantity = findViewById(R.id.quantity);
        nameedt = findViewById(R.id.shipment_name);
        nameedt.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // Only allow alphabets in the input
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                        return ""; // Ignore the character
                    }
                }
                return null; // Accept the character
            }
        }});
        addressedt = findViewById(R.id.shipment_address);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        layout = findViewById(R.id.visible);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText4);
        editText4 = findViewById(R.id.editText3);

        kquantity.setText(quantity);
        productPriceTextView.setText(totalamount);


        input_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmFinalOrderActivity.this, "start", Toast.LENGTH_SHORT).show();
                openImagePicker();
                edit_fun.setVisibility(View.VISIBLE);

                layout.setVisibility(View.VISIBLE);
            }
        });


        cityest = findViewById(R.id.shipment_city);
        cityest.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            // Only allow alphabets in the input
            for (int i = start; i < end; i++) {
                if (!Character.isLetter(source.charAt(i))) {
                    return ""; // Ignore the character
                }
            }
            return null; // Accept the character
        }});

        confirmOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }

            private void check() {
                if (TextUtils.isEmpty(nameedt.getText().toString())) {
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please Fill  your name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(addressedt.getText().toString())) {
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please Fill Address", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(cityest.getText().toString())) {
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please Fill  City", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(postalet.getText().toString())) {
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please Fill  Postal Code", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(stateet.getText().toString())) {
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please Fill Up City", Toast.LENGTH_SHORT).show();
                } else {
                    confirmOrder();
                }
            }

            private void confirmOrder() {


                String saveCurrentTime, saveCurrentDate, orderkey;
                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
                saveCurrentDate = currentDate.format(calForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForDate.getTime());

                orderkey = saveCurrentTime + saveCurrentDate;


                // Get a reference to your Firebase Realtime Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference orderRef = database.getReference("Orders");
                String name = nameedt.getText().toString();
                String address = addressedt.getText().toString();
                String city = cityest.getText().toString();
                String state = stateet.getText().toString();
                String postal = postalet.getText().toString();
                String phoneedt = Prevalent.currentonlineUser.getPhone();
                String require = customet.getText().toString();
                HashMap<String, Object> orderMap = new HashMap<>();
                orderMap.put("name", name.toString());
                orderMap.put("address", address.toString());
                orderMap.put("city", city.toString());
                orderMap.put("contactinfo", phoneedt.toString());
                orderMap.put("time", saveCurrentTime.toString());
                orderMap.put("date", saveCurrentDate.toString());
                orderMap.put("TotalAmount", totalamount.toString());
                orderMap.put("status", "not shipped");
                orderMap.put("state", state.toString());
                orderMap.put("customrequirement", require.toString());
                orderMap.put("postalcode", postal.toString());
                orderMap.put("sid", sid.toString());
                orderMap.put("customer", Prevalent.currentonlineUser.getPhone());
                orderMap.put("pid", pid.toString());
                orderMap.put("armLength", arm);
                orderMap.put("LegLength", leg);
                orderMap.put("neck_circumference", neck);
                orderMap.put("shoulderwidth", shoulder);
                orderMap.put("quantity", quantity.toString());

                orderRef.child("UserOrders").child(Prevalent.currentonlineUser.getPhone()).child(pid).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference().child("CartList")
                                    .child("UserView")
                                    .child(Prevalent.currentonlineUser.getPhone())
                                    .child("Products")
                                    .child(pid)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                orderRef.child("AdminOrders").child(sid).child(pid).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Order Request has been placed sucessfully",
                                                                Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this, nav_bar.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });

//

                                            }
                                        }
                                    });
                        }
                    }
                });

            }
        });


    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    selectedImageFile = FileUtil.from(this, selectedImageUri);

                    // Now you have the selected image file, you can use it in your postImage method
//                    postImage(157, selectedImageFile);
                    postImage(157, new File(selectedImageFile.getAbsolutePath()));
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("message for you", e.toString());
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    // Handle exception, maybe show user a message
                }
            }
        }
    }

    private void postImage(int heightCm, File selectedImageFile) {

        if (selectedImageFile == null) {
            Toast.makeText(this, "No imag is selected", Toast.LENGTH_SHORT).show();
            Log.d("@@@", "test1" + selectedImageFile + heightCm);
            // Handle case where no image is selected
            return;
        }


        Log.d("@@@", "test" + selectedImageFile + heightCm);

        // Create RequestBody instances for the image file and the height
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image.jpg", RequestBody.create(MediaType.parse("image/jpeg"), selectedImageFile))
                .addFormDataPart("height_cm", String.valueOf(heightCm))
                .build();

        // Create an OkHttpClient with a custom timeout
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS) // Set connection timeout to 15 seconds
                .readTimeout(15, TimeUnit.SECONDS)    // Set read timeout to 15 seconds
                .build();

        // Build the request
        Request request = new Request.Builder()
                .url("http://34.72.177.214:8080/measurements")
                .post(requestBody)
                .build();

        Log.d("Request URL", request.url().toString());

        // Asynchronously execute the HTTP request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

                String vr = e.getLocalizedMessage();

                Log.d("@@@", "test vr" + vr);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        Log.d("Response Code", String.valueOf(response.code()));
                        Log.d("Response Body", responseData);

                        // Parse the JSON response
                        JSONObject measurements = new JSONObject(responseData);
                        armLength = measurements.getDouble("arm_length_cm");
                        legLength = measurements.getDouble("leg_length_cm");
                        neckCircumference = measurements.getDouble("neck_circumference_cm");
                        shoulderWidth = measurements.getDouble("shoulder_width_cm");

                        // Update UI on the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Set the measurements in EditText widgets
                                editText1.setText(String.valueOf(armLength));
                                editText2.setText(String.valueOf(legLength));
                                editText3.setText(String.valueOf(neckCircumference));
                                editText4.setText(String.valueOf(shoulderWidth));
                                neck = editText3.getText().toString();
                                arm = editText1.getText().toString();
                                leg = editText2.getText().toString();
                                shoulder = editText4.getText().toString();

                            }
                        });
                    } else {
                        Log.e("@@@", "test empty");
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    Log.e("Response Handling Error", "Error: " + e.getLocalizedMessage());
                }
                Log.i("message", "work done");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        showDelayedDialog();
    }

    private void showDelayedDialog() {
        // Replace "Your Message" with the message you want to display
        final String messages = "Remember to add a full size frontal image by clicking on the  Input_Size button ";

        // Delay for 3 seconds (3000 milliseconds)
        int delayMillis = 3000;

        // Create a handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create and show the AlertDialog
                showDialog(messages);
            }
        }, delayMillis);
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notification Dialog");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}