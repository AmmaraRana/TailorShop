package com.example.adminpanel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.Customer.nav_bar;

public class SplashScreen extends AppCompatActivity {
    // Duration of the splash screen in milliseconds
    private static final int SPLASH_DURATION = 3000;

    private TextView textViewSplash;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textViewSplash = findViewById(R.id.textViewSplash);
        progressBar = findViewById(R.id.progressBar);


//       change status bar color

        // Get the window object
        Window window = getWindow();

// Check if the Android version is at least Lollipop (API 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Set the status bar color
            window.setStatusBarColor(Color.parseColor("#3498db")); // Replace with your desired color code
        }
//        itz done


//        start

        if (getIntent().getExtras() != null) {
            String sellerid = getIntent().getExtras().getString("userid");

            Intent mainintent=new Intent(this, nav_bar.class);
            mainintent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(mainintent);

            Intent intent=new Intent(this,UserOrderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            // Set up the progress bar animation
            progressBar.setIndeterminate(true);

            // Simulate a task that takes some time
            simulateTask();

            // Delay the start of the main activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Start the main activity
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, SPLASH_DURATION);

        }
//        end


    }

    private void simulateTask() {
        // Simulate a task that takes some time
        // You can replace this with your actual initialization task
        try {
            Thread.sleep(SPLASH_DURATION);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}