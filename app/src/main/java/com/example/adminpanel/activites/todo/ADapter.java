package com.example.adminpanel.activites.todo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.AddCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ADapter extends RecyclerView.Adapter<ADapter.ViewHolder> {
    List<AddCategory> list;
    Context context;

    public ADapter(List<AddCategory> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ADapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categories_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADapter.ViewHolder holder, int position) {
        holder.text.setText(list.get(position).getCategoryName());
        Picasso.get().load(list.get(position).getImageUrl()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,CategoryItemActivity.class);
                intent.putExtra("catname",list.get(position).getCategoryName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        CircleImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textView);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
