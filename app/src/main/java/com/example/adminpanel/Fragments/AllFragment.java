package com.example.adminpanel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.Tailor;
import com.example.adminpanel.adapter.ShopAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AllFragment extends Fragment {
    RecyclerView recyclerview;
    private DatabaseReference sellerlist;
    List<Tailor> tailorList;
    ShopAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        recyclerview = view.findViewById(R.id.recycler_shop);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        sellerlist = FirebaseDatabase.getInstance().getReference()
                .child("Tailors");

        tailorList = new ArrayList<>();
        adapter = new ShopAdapter(getContext(), tailorList);
        recyclerview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sellerlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tailorList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Tailor tailor = dataSnapshot.getValue(Tailor.class);
                    tailorList.add(tailor);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}