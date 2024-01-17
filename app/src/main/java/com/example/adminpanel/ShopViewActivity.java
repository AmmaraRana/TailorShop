package com.example.adminpanel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.Tailor.TailorAdapter.FeedbackAdapter;
import com.example.adminpanel.Tailor.TailorModel.FeedbackModel;
import com.example.adminpanel.ViewHolder.FragmentViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class ShopViewActivity extends AppCompatActivity {
    String sid;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    List<Product> list;
    String searchquery;
    String floating;
    List<FeedbackModel> lists;
    private FeedbackAdapter adapters;
    private DatabaseReference shoplistref;
    private RecyclerView feedbackRecyclerView;
    private FragmentViewHolder adapter;
    // Views in the layout
    private TextView nameTextView;
    private TextView contactTextView;

    private RatingBar ratingBar;

    private MaterialSearchBar searchBar;
    private EditText feedbackEditText;
    private Button feedbackButton;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_view);
        sid = getIntent().getStringExtra("sid");
        feedbackRecyclerView = findViewById(R.id.feedbackRecyclerView);
        recyclerView = findViewById(R.id.recycler_shopview);
        // Initialize views from the layout
        nameTextView = findViewById(R.id.name);
        back=findViewById(R.id.back);
        contactTextView = findViewById(R.id.contact);
        ratingBar = findViewById(R.id.ratingBar);
//        ratingText = findViewById(R.id.ratingText);
        feedbackEditText = findViewById(R.id.editfeedback);
        feedbackButton = findViewById(R.id.feedbackbtn);
        recyclerView.setHasFixedSize(true);
        lists = new ArrayList<>();
        adapters = new FeedbackAdapter(lists, this);
        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedbackRecyclerView.setHasFixedSize(true);
        back.setOnClickListener(v -> {
         finish();
        });
        floating = String.valueOf(ratingBar.getRating());
//        if(ratingText ==null && ratingText ==0.0)
//        ratingText.setText("Rating : " + floating);
        // Set an OnClickListener for the feedbackButton
        feedbackButton.setOnClickListener(view -> {
            // Get the text from the EditText
            String feedbackText = feedbackEditText.getText().toString();
            progressDialog = new ProgressDialog(ShopViewActivity.this);
            progressDialog.setMessage("Submitting...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            // Get the rating from the RatingBar
            float rating = ratingBar.getRating();


            // Save the rating and feedback in the Realtime Database

            saveRatingAndFeedback(rating, feedbackText);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        shoplistref = FirebaseDatabase.getInstance().getReference()
                .child("Products");

        list = new ArrayList<>();
        adapter = new FragmentViewHolder(this, list);
        recyclerView.setAdapter(adapter);
        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                searchquery = text.toString();
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void saveRatingAndFeedback(float rating, String feedbackText) {
        DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference().child("Feedback").child(sid).child(Prevalent.currentonlineUser.getPhone());

        // Create a unique key for each feedback entry
        String feedbackKey = feedbackRef.push().getKey();

        // Save the feedback data in the Realtime Database
        feedbackRef.child("Rating").setValue(rating);
        feedbackRef.child("FeedbackText").setValue(feedbackText);
        feedbackRef.child("Customer").setValue(Prevalent.currentonlineUser.getName()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        feedbackRecyclerView.setAdapter(adapters);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback").child(sid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lists.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FeedbackModel model = dataSnapshot.getValue(FeedbackModel.class);
                    lists.add(model);
                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShopViewActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        {
            // If no search query, display the full list
            shoplistref.orderByChild("sid").equalTo(sid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            list.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Product product = snapshot.getValue(Product.class);
                                list.add(product);
                            }

                            // Update the adapter with the full list
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors
                        }
                    });
        }

        sellerinfo();

    }

    private void sellerinfo() {
        final DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference().child("Tailors");
        sellerRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {


                            String phone = snapshot.child("phone").getValue().toString();


                            String sShop = snapshot.child("ShopName").getValue().toString();

                            nameTextView.setText(sShop);
                            contactTextView.setText("Shop Contact: " + phone);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}