package com.example.adminpanel.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.adminpanel.CartActivity;
import com.example.adminpanel.R;
import com.example.adminpanel.UserSearchActivity;
import com.example.adminpanel.databinding.ActivityNavBarBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class nav_bar extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavBarBinding binding;
    private DatabaseReference productRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(this);
        productRef = FirebaseDatabase.getInstance().getReference().child("Products").child("ProductList");
//        toolbar
        setSupportActionBar(binding.appBarNavBar.toolbar);
        binding.appBarNavBar.toolbar.setTitle("Home");
        ImageView searchImage = binding.appBarNavBar.searchImage;

        searchImage.setOnClickListener(v -> startActivity(new Intent(nav_bar.this, UserSearchActivity.class)));

        binding.appBarNavBar.fab.setOnClickListener(view -> {
            Intent intent = new Intent(nav_bar.this, CartActivity.class);
            startActivity(intent);
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_gallery, R.id.nav_setting, R.id.nav_home,R.id.nav_shop)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_bar);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerview = navigationView.getHeaderView(0);
        TextView userNameTextView = headerview.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerview.findViewById(R.id.user_profile_image);
        // Check if the user is signed in and prevalent information is available
//        if (Prevalent.currentonlineUser != null && !TextUtils.isEmpty(Prevalent.currentonlineUser.getPhone())) {
//            userNameTextView.setText(Prevalent.currentonlineUser.getName());
//            Picasso.get().load(Prevalent.currentonlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
//            getFCMToken(); // Call getFCMToken() if the user is signed in
//        } else {
//            // User is not signed in or prevalent information is empty
//            // You can handle this situation, for example, by displaying a message to create an account
//            Toast.makeText(this, "Please sign in or create an account", Toast.LENGTH_SHORT).show();
//        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_bar, menu);
//        Paper.book().destroy();
//        startActivity(new Intent(this,MainActivity.class));
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_bar);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}