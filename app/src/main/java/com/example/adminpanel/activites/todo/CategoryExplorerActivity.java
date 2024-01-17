package com.example.adminpanel.activites.todo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.AddCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryExplorerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference catref;
    ADapter categoryadapter;
    List<AddCategory> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_explorer);
        recyclerView = findViewById(R.id.categoryRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        catwork();

    }

    private void catwork() {

        catref = FirebaseDatabase.getInstance().getReference().child("Categories");


        list = new ArrayList<>();
        categoryadapter = new ADapter(list, this);
        recyclerView.setAdapter(categoryadapter);

        catref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddCategory category = dataSnapshot.getValue(AddCategory.class);
                    list.add(category);

                }

                categoryadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}