package com.example.adminpanel.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Interface.ItemClickListener;
import com.example.adminpanel.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public ImageView productImage;
    public TextView price;
    public TextView name;
    public TextView description;

    CardView cardrec;
    private ItemClickListener itemClickListener;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.product_image);
        price = itemView.findViewById(R.id.product_price);
        name = itemView.findViewById(R.id.product_name);
        description = itemView.findViewById(R.id.product_description);

        cardrec = itemView.findViewById(R.id.cardrec);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false


        );}

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
}
