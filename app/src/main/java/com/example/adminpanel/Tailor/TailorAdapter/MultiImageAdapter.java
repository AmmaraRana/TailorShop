package com.example.adminpanel.Tailor.TailorAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.itemImageModel;

import java.util.ArrayList;

public class MultiImageAdapter extends RecyclerView.Adapter<MultiImageAdapter.MultiViewHolder> {
    private ArrayList<itemImageModel> list;
    Context context;

    public MultiImageAdapter(ArrayList<itemImageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MultiImageAdapter.MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_image, parent, false);

        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiImageAdapter.MultiViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MultiViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.getMultiIMage);
        }
    }
}
