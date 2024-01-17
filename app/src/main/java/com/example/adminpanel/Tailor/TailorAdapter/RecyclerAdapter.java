package com.example.adminpanel.Tailor.TailorAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminpanel.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Uri> uri;
    private Context context;
    CountWhenItemRemoved countWhenItemRemoved;


    public RecyclerAdapter(ArrayList<Uri> uri, Context context, CountWhenItemRemoved countWhenItemRemoved
    ) {
        this.uri = uri;
        this.context = context;
        this.countWhenItemRemoved = countWhenItemRemoved;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view, countWhenItemRemoved);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
//        holder.imageView.setImageURI(uriArrayList.get(position));
        Glide.with(context).load(uri.get(position))
                .into(holder.imageView);
//        holder.delete.setOnClickListener(v -> {
//            uri.remove(uri.get(position));
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, getItemCount());
//            countWhenItemRemoved.clicked(uri.size());
//
//        });
    }

    @Override
    public int getItemCount() {
        return uri.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, delete;
        CountWhenItemRemoved countWhenItemRemoved;

        public ViewHolder(@NonNull View itemView, CountWhenItemRemoved countWhenItemRemoved) {
            super(itemView);
            this.countWhenItemRemoved = countWhenItemRemoved;
            imageView = itemView.findViewById(R.id.image);
//            delete = itemView.findViewById(R.id.delete);
        }
    }

    public interface CountWhenItemRemoved {
        void clicked(int getSize);
    }

}
