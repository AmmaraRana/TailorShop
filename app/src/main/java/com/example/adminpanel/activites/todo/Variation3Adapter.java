package com.example.adminpanel.activites.todo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.ImageModel;

import java.util.ArrayList;

public class Variation3Adapter extends RecyclerView.Adapter<Variation3Adapter.ViewHolder> {
    private final ArrayList<ImageModel> list;
    private final Context context;
    private final ArrayList<Integer> selectedPositions; // List to store selected positions
    private Variation3Adapter.OnImageClickListener onImageClickListener;
    private int lastSelectedPosition = RecyclerView.NO_POSITION; // Track the last selected position

    public Variation3Adapter(ArrayList<ImageModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.selectedPositions = new ArrayList<>();
    }

    public interface OnImageClickListener {
        void onImageClick(String imageUrl, int position);
    }

    public void setOnImageClickListener(Variation3Adapter.OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }

    @NonNull
    @Override
    public Variation3Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.variation_layout, parent, false);
        return new Variation3Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Variation3Adapter.ViewHolder holder, int position) {
        ImageModel imageModel = list.get(position);

        Glide.with(context).load(imageModel.getImageUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {
            if (onImageClickListener != null) {
                String selectedImageUrl = imageModel.getImageUrl();
                onImageClickListener.onImageClick(selectedImageUrl, position);
            }

            // Toggle selection without notifyDataSetChanged
            toggleSelection(position);
        });

        // Highlight the selected item
        updateItemView(holder, selectedPositions.contains(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.myVariationImageView);
        }
    }

    // Method to toggle selection of an item
    private void toggleSelection(int position) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(Integer.valueOf(position));
            lastSelectedPosition = RecyclerView.NO_POSITION;
        } else {
            selectedPositions.clear();
            selectedPositions.add(position);
            lastSelectedPosition = position;

            // Add the following line to trigger the Firebase operation immediately when an item is selected
            addImageToCart3(list.get(position).getImageUrl());
        }
        notifyDataSetChanged();
    }

    private void updateItemView(ViewHolder holder, boolean isSelected) {
        // Set the background color based on the selection
        if (isSelected) {
            holder.imageView.setBackgroundColor(Color.parseColor("#88000000")); // Semi-transparent black background
            holder.imageView.setForeground(ContextCompat.getDrawable(context, R.drawable.baseline_check_circle_outline_24));
            holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.imageView.setBackgroundColor(Color.TRANSPARENT);
            holder.imageView.setForeground(null);
            holder.imageView.setColorFilter(null);
        }
    }

    // Method to add the selected image to the cart in Firebase
    private void addImageToCart3(String selectedImage) {
        if (selectedImage != null) {
            // Add your Firebase database logic here to save the selected image to the cart
            // ...
            // You can use the selectedImage for the image URL in Firebase
        }
    }

    public boolean isSelected() {
        return selectedPositions.size() > 0;
    }
}
