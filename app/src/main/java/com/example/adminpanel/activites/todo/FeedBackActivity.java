package com.example.adminpanel.activites.todo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorAdapter.FeedbackAdapter;
import com.example.adminpanel.Tailor.TailorModel.FeedbackModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeedBackActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FeedbackAdapter adapter;
    List<FeedbackModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new FeedbackAdapter(list, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FeedbackModel model = dataSnapshot.getValue(FeedbackModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FeedBackActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}