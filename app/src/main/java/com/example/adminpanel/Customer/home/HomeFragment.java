package com.example.adminpanel.Customer.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Adapters.Categoryadapter;
import com.example.adminpanel.Model.Product;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.AddCategory;
import com.example.adminpanel.ViewHolder.FragmentViewHolder;
import com.example.adminpanel.activites.todo.CategoryExplorerActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {
    Categoryadapter categoryadapter;
    List<AddCategory> list;
    FragmentViewHolder adapter;
    List<Product> productList;
    TextView seelist;
    private DatabaseReference catref;
    private RecyclerView RRecyclerview;
    private DatabaseReference productsRef;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        seelist = root.findViewById(R.id.category_see_all);
        seelist.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(), CategoryExplorerActivity.class);
            getContext().startActivity(intent);
        });
        catref = FirebaseDatabase.getInstance().getReference().child("Categories");
        RRecyclerview = root.findViewById(R.id.category_recycler_menu);
        RRecyclerview.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        RRecyclerview.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        categoryadapter = new Categoryadapter(list, getContext());
        RRecyclerview.setAdapter(categoryadapter);

        catref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                int categoryCount = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddCategory category = dataSnapshot.getValue(AddCategory.class);
                    list.add(category);

                    // Increment the counter
                    categoryCount++;

                    // Break out of the loop if you've reached 8 categories
                    if (categoryCount >= 8) {
                        break;
                    }
                }

                categoryadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//  will be done later
//        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
//        List<SlideModel> slideModels = new ArrayList<>();
//        slideModels.add(new SlideModel(R.drawable.slide1, "", ScaleTypes.CENTER_CROP));
//        slideModels.add(new SlideModel(R.drawable.slid2, "", ScaleTypes.CENTER_CROP));
//        slideModels.add(new SlideModel(R.drawable.slid3, "", ScaleTypes.CENTER_CROP));
//        imageSlider.setImageList(slideModels);


        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = root.findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        productList = new ArrayList<>();
//        adapter = new ProductViewHolder(getContext(), productList);
        adapter = new FragmentViewHolder(getContext(), productList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);


        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                List<Product> tempList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    tempList.add(product);
                }

                // Reverse the order to have the latest products at the top
                Collections.reverse(tempList);
                productList.addAll(tempList);

                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error fetching data: " + databaseError.getMessage());

            }
        });


        return root;
    }

    private void filterchip(String selectedquery) {

    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}