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

public class Variation1Adapter extends RecyclerView.Adapter<Variation1Adapter.ViewHolder>{
    private final ArrayList<ImageModel> list;
    private final Context context;
    private final ArrayList<Integer> selectedPositions; // List to store selected positions
    private VariationAdapter.OnImageClickListener onImageClickListener;
    private int lastSelectedPosition = RecyclerView.NO_POSITION; // Track the last selected position

    public Variation1Adapter(ArrayList<ImageModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.selectedPositions = new ArrayList<>();
    }
    public interface OnImageClickListener {
        void onImageClick(String imageUrl, int position);
    }
    public void setOnImageClickListener(VariationAdapter.OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }
    @NonNull
    @Override
    public Variation1Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.variation_layout, parent, false);
        return new Variation1Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Variation1Adapter.ViewHolder holder, int position) {
        ImageModel imageModel = list.get(position);

        Glide.with(context).load(imageModel.getImageUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {
            if (onImageClickListener != null) {
                String selectedImageUrl = imageModel.getImageUrl();
                onImageClickListener.onImageClick(selectedImageUrl, position);
            }

            // Handle item selection without modifying ImageModel
            toggleSelection(position);
            notifyDataSetChanged(); // Notify the adapter to refresh views
        });

        // Highlight the selected item
        // Set the background color based on the selection
        if (selectedPositions.contains(position)) {
            // Item is selected, darken the image and set the check circle outline
            holder.imageView.setBackgroundColor(Color.parseColor("#88000000")); // Semi-transparent black background
            holder.imageView.setForeground(ContextCompat.getDrawable(context, R.drawable.baseline_check_circle_outline_24));

            // Apply a ColorFilter to darken the image
            holder.imageView.setColorFilter(R.color.gray);
        } else {
            // Item is not selected, clear the background and foreground
            holder.imageView.setBackgroundColor(Color.TRANSPARENT);
            holder.imageView.setForeground(null);

            // Clear the ColorFilter to restore the original image brightness
            holder.imageView.setColorFilter(null);
        }
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
            // Item is already selected, clear the selection
            selectedPositions.remove(Integer.valueOf(position));
            lastSelectedPosition = RecyclerView.NO_POSITION;
        } else {
            // Item is not selected, clear the previous selection and set the new one
            selectedPositions.clear();
            selectedPositions.add(position);
            lastSelectedPosition = position;
        }
    }
    public boolean isSelected() {
        return selectedPositions.size()>0;

    }
}
