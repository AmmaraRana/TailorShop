package com.example.adminpanel.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.AdminCategoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductActivity extends AppCompatActivity {
    private Button applyChangebtn, deleteproductbtn;

    private TextView name, description, fabric, fabric2, fabric3, price, custom, size;
    private ImageView imageView;


    String productID = "";
    private DatabaseReference productref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_product);
        applyChangebtn = findViewById(R.id.product_maintain_btn);
        name = findViewById(R.id.product_name_mantain);
        description = findViewById(R.id.product_description_maintain);
        price = findViewById(R.id.product_price_mantain);
        fabric = findViewById(R.id.product_fabric_mantain);
        fabric2 = findViewById(R.id.product_fabric2_mantain);
        fabric3 = findViewById(R.id.product_fabric3_mantain);
        custom = findViewById(R.id.product_for_mantain);
        size = findViewById(R.id.product_size_mantain);
        deleteproductbtn = findViewById(R.id.product_delete_btn);
        imageView = findViewById(R.id.product_image_mantain);

        productID = getIntent().getStringExtra("pId");
        productref = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);
        displaysepcificproductinfo();
        deleteproductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletethisproduct();
            }
        });
        applyChangebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applychange();
            }
        });
    }

    private void deletethisproduct() {

        productref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminMaintainProductActivity.this, "product deleted sucessfullay", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminMaintainProductActivity.this, AdminCategoryActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void applychange() {
        String pfabric = fabric.getText().toString();
        String pname = name.getText().toString();
        String pdescription = description.getText().toString();
        String pprice = price.getText().toString();
        String psize = size.getText().toString();
        String pcustom = custom.getText().toString();
        String pfabric2 = fabric2.getText().toString();
        String pfabric3 = fabric3.getText().toString();
        if (TextUtils.isEmpty(pdescription)) {
            Toast.makeText(this, "Description is mandatory", Toast.LENGTH_SHORT).show();
            description.setError("Name field is empty");
            description.requestFocus();
        } else if (TextUtils.isEmpty(pname)) {
            Toast.makeText(this, "Pname is mandatory", Toast.LENGTH_SHORT).show();
            name.setError("Name field is empty");
            name.requestFocus();
        } else if (TextUtils.isEmpty(pprice)) {
            Toast.makeText(this, "Price is mandatory", Toast.LENGTH_SHORT).show();
            price.setError("Name field is empty");
            price.requestFocus();
        } else if (TextUtils.isEmpty(pcustom)) {
            Toast.makeText(this, "Shop Name is mandatory", Toast.LENGTH_SHORT).show();
            custom.setError(" field is empty");
            custom.requestFocus();
        } else if (TextUtils.isEmpty(pfabric)) {
            Toast.makeText(this, "fabric is mandatory", Toast.LENGTH_SHORT).show();
            fabric.setError(" field is empty");
            fabric.requestFocus();
        } else if (TextUtils.isEmpty(psize)) {
            Toast.makeText(this, "Type is mandatory", Toast.LENGTH_SHORT).show();
            size.setError(" field is empty");
            size.requestFocus();
        } else {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pID", productID);

            productMap.put("description", pdescription);
            productMap.put("name", pname);
            productMap.put("price", pprice);
            productMap.put("fabric2", pfabric2);
            productMap.put("fabric3", pfabric3);
            productMap.put("Type", psize);

            productMap.put("fabric1", pfabric);
            productMap.put("sex", pcustom);

            productref.updateChildren(productMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminMaintainProductActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }
                    });
        }


    }

    private void displaysepcificproductinfo() {


        productref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String mname = snapshot.child("name").getValue().toString();
                    String mfabric = snapshot.child("fabric1").getValue().toString();
                    String mfabric2 = snapshot.child("fabric2").getValue().toString();
                    String mfabric3 = snapshot.child("fabric3").getValue().toString();

                    String mdescription = snapshot.child("description").getValue().toString();
                    String mprice = snapshot.child("price").getValue().toString();
                    String mcustom = snapshot.child("gender").getValue().toString();
                    String mType = snapshot.child("Type").getValue().toString();
                    String mimage = snapshot.child("image").getValue().toString();

                    name.setText(mname);
                    description.setText(mdescription);
                    price.setText(mprice);
                    custom.setText(mcustom);
                    fabric.setText(mfabric);
                    fabric2.setText(mfabric2);
                    fabric3.setText(mfabric3);
                    size.setText(mType);
                    Picasso.get().load(mimage).into(imageView);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}