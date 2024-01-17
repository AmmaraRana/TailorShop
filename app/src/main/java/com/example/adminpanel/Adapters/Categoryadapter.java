package com.example.adminpanel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.AddCategory;
import com.example.adminpanel.activites.todo.CategoryActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class Categoryadapter extends RecyclerView.Adapter<Categoryadapter.categoryViewHolder> {
    List<AddCategory> list;
    Context context;

    public Categoryadapter(List<AddCategory> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Categoryadapter.categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categories, parent, false);
        return new categoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Categoryadapter.categoryViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getCategoryName());
        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("cat", list.get(position).getCategoryName().toString());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class categoryViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imageView;
        TextView textView;
        String cid;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.label);

        }
    }
}
