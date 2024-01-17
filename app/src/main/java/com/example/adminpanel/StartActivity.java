package com.example.adminpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adminpanel.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {
ActivityStartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));

            }
        });
        binding.tailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));

            }
        });

    }
}