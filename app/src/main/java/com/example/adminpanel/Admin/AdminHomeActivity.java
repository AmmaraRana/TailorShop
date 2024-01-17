package com.example.adminpanel.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adminpanel.AdminNewOrderActivity;
import com.example.adminpanel.Customer.LoginActivity;
import com.example.adminpanel.Tailor.CheckNewProductActivity;
import com.example.adminpanel.databinding.ActivityAdminHomeBinding;
import com.example.adminpanel.trial.admin;

public class AdminHomeActivity extends AppCompatActivity {
ActivityAdminHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminHomeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

binding.approveProductBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
Intent intent=new Intent(AdminHomeActivity.this, CheckNewProductActivity.class);
startActivity(intent);

    }
});
        binding.maintainProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, admin.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);

            }
        });

        binding.checkOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, AdminNewOrderActivity.class));
                finish();
            }
        });
        binding.adminLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminHomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }
}