package com.example.adminpanel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminpanel.Customer.nav_bar;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorAdapter.MultiImageAdapter;
import com.example.adminpanel.Tailor.TailorModel.ImageModel;
import com.example.adminpanel.Tailor.TailorModel.itemImageModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class detailAdapter extends RecyclerView.Adapter<detailAdapter.tiViewHolder> {

    private ArrayList<ImageModel> list;
    private Context context;

    // Define an interface for the click listener
    public interface OnImageClickListener {
        void onImageClick(String selectedImage);
    }

    // Add a member variable for the click listener
    private OnImageClickListener onImageClickListener;

    // Add a setter method for the click listener
    public void setOnImageClickListener(OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }

    public detailAdapter(ArrayList<ImageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public tiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_image, parent, false);

        return new tiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tiViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.imageView);

        // Set click listener for the image
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageClickListener != null) {
                    // Get the selected image URL
                    String selectedImage = list.get(position).getImageUrl();
                    // Notify the listener about the click event
                    onImageClickListener.onImageClick(selectedImage);

                    // Store the clicked image URL to another node in Firebase
                    storeClickedImage(selectedImage);
                }
            }
        });

        holder.backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, nav_bar.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class tiViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView, backimage;

        public tiViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.getMultiIMage);
            backimage = itemView.findViewById(R.id.detailbackbtn);
        }
    }

    private void storeClickedImage(String imageUrl) {
        // Use a DatabaseReference to store the imageUrl in another node
        DatabaseReference clickedImagesRef = FirebaseDatabase.getInstance().getReference("ClickedImages");
        String key = clickedImagesRef.push().getKey();

        if (key != null) {
            clickedImagesRef.child(key).setValue(imageUrl);
        }
    }
}
