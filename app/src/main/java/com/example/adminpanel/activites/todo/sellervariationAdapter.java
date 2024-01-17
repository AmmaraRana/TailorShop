package com.example.adminpanel.activites.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.SellerProductDetailActivity;
import com.example.adminpanel.Tailor.TailorModel.ImageModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class sellervariationAdapter extends RecyclerView.Adapter<sellervariationAdapter.ViewHolder> {
    private final ArrayList<ImageModel> list;
    SellerProductDetailActivity context;

    public sellervariationAdapter(ArrayList<ImageModel> list, SellerProductDetailActivity context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public sellervariationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.variation_layout, parent, false);
        return new sellervariationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sellervariationAdapter.ViewHolder holder, int position) {
        ImageModel imageModel = list.get(position);

//        Picasso.get().load(imageModel).into(holder.imageView);
        Glide.with(context)
                .load(imageModel.getImageUrl())
                .placeholder(R.drawable.gra) // Add your placeholder image
                .error(R.drawable.gra) // Add your error image
                .into(holder.imageView);

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
}
