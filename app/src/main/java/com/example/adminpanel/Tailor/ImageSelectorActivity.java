package com.example.adminpanel.Tailor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.adminpanel.R;
import com.example.adminpanel.databinding.ActivityImageSelectorBinding;

public class ImageSelectorActivity extends AppCompatActivity {
    ActivityImageSelectorBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityImageSelectorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}