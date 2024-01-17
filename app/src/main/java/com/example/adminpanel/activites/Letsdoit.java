package com.example.adminpanel.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.Tailor.SellerAddNewProduct;
import com.example.adminpanel.databinding.ActivityLetsdoitBinding;

public class Letsdoit extends AppCompatActivity {
    ActivityLetsdoitBinding binding;
    String selectedFabric1, selectedFabric2, selectedFabric3; // Three different strings
    String CategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLetsdoitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CategoryName = getIntent().getExtras().get("Idcategory").toString();

        CheckBox[] checkBoxes = new CheckBox[]{
                binding.checkBoxCotton,
                binding.checkBoxSilk,
                binding.checkBoxWool,
                binding.checkBoxChiffon,
                binding.checkBoxKhaddar,
                binding.checkBoxLawn,
                binding.checkBoxLinen,
                binding.checkBoxViscose,
                binding.checkBoxOrganza,
                binding.checkBoxVelvet,
                binding.checkBoxOthers
        };

        selectedFabric1 = "";
        selectedFabric2 = "";
        selectedFabric3 = "";

        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (selectedFabric1.isEmpty()) {
                        selectedFabric1 = checkBox.getText().toString();
                        Toast.makeText(this, selectedFabric1, Toast.LENGTH_SHORT).show();
                    } else if (selectedFabric2.isEmpty()) {
                        selectedFabric2 = checkBox.getText().toString();
                        Toast.makeText(this, selectedFabric2, Toast.LENGTH_SHORT).show();
                    } else if (selectedFabric3.isEmpty()) {
                        selectedFabric3 = checkBox.getText().toString();
                        Toast.makeText(this, selectedFabric3, Toast.LENGTH_SHORT).show();
                    } else {
                        // If more than 3 checkboxes are selected, uncheck the current one
                        checkBox.setChecked(false);
                        Toast.makeText(Letsdoit.this, "You can select only three fabrics", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        binding.procedNext.setOnClickListener(v -> {
            if (!selectedFabric1.isEmpty() && !selectedFabric2.isEmpty() && !selectedFabric3.isEmpty()) {
                Intent intent = new Intent(Letsdoit.this, SellerAddNewProduct.class);
                intent.putExtra("selectedFabric1", selectedFabric1);
                intent.putExtra("selectedFabric2", selectedFabric2);
                intent.putExtra("selectedFabric3", selectedFabric3);
                intent.putExtra("Idcategory", CategoryName);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Letsdoit.this, "Please select exactly three fabrics", Toast.LENGTH_SHORT).show();
            }
        });

        binding.imageBackButton.setOnClickListener(v -> finish());
    }
}