package com.example.adminpanel.Tailor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.DashboardActivity;
import com.example.adminpanel.MainActivity;
import com.example.adminpanel.R;
import com.example.adminpanel.activites.todo.AllOrderActivity;
import com.example.adminpanel.activites.todo.FeedBackActivity;
import com.example.adminpanel.databinding.ActivityDashboard2Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity2 extends AppCompatActivity {
    ActivityDashboard2Binding binding;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboard2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.cardProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity2.this, SellerProfile.class);
            startActivity(intent);
        });
        binding.backB.setOnClickListener(v -> {
            finish();
        });
//        binding.orderdetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity2.this, AllOrderActivvity.class);
//                startActivity(intent);
//            }
//        });
        binding.notifycust.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity2.this, FeedBackActivity.class);
            startActivity(intent);
        });


        binding.editDelete.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity2.this, DashboardActivity.class);
            intent.putExtra("Admin", "Admin");
            startActivity(intent);
        });
        binding.readytoship.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity2.this, ShippmentOrder.class);
            startActivity(intent);
        });

        back = findViewById(R.id.logOutB);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(new Intent(DashboardActivity2.this, MainActivity.class));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
        //        bottom_nav_bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_notifications);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            {
                if (item.getItemId() == R.id.navigation_ShopView) {

                    startActivity(new Intent(DashboardActivity2.this, ContentActivity.class));
                    overridePendingTransition(0, 0);


                    return true;
                } else if (item.getItemId() == R.id.navigation_addition) {
                    startActivity(new Intent(DashboardActivity2.this, AdminCategoryActivity.class));
                    overridePendingTransition(0, 0);


                    return true;
                } else return item.getItemId() == R.id.navigation_notifications;
            }
        });
        binding.logOutB.setOnClickListener(v -> {

            FirebaseAuth mauth = FirebaseAuth.getInstance();
            mauth.signOut();


            Intent intent = new Intent(DashboardActivity2.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });


        binding.customorder.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity2.this, AllOrderActivity.class);

            startActivity(intent);
        });
    }
}