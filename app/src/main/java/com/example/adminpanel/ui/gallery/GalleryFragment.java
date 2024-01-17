package com.example.adminpanel.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.adminpanel.R;
import com.example.adminpanel.UserOrderActivity;
import com.example.adminpanel.activites.todo.ShippedOrderActivity;
import com.example.adminpanel.databinding.FragmentGalleryBinding;
import com.example.adminpanel.orders.UserPendingOrders;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private CardView pending, shipped, complete;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);


        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        pending = view.findViewById(R.id.cardViewPending);
        shipped = view.findViewById(R.id.cardViewShipped);
        complete = view.findViewById(R.id.cardViewComplete);


        pending.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UserPendingOrders.class);
            getContext().startActivity(intent);
        });
       complete.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ShippedOrderActivity.class);
            getContext().startActivity(intent);
        });

        shipped.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UserOrderActivity.class);
            getContext().startActivity(intent);
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}