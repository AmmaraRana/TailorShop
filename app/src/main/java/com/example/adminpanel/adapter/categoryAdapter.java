package com.example.adminpanel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Category;
import com.example.adminpanel.R;

import java.util.Calendar;
import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {
private Context context;
private List<Category> list;

    @NonNull
    @Override
    public categoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull categoryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

ImageView catImage,catName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImage=itemView.findViewById(R.id.cat_img);
            catName=itemView.findViewById(R.id.cat_name);
        }
    }
}
