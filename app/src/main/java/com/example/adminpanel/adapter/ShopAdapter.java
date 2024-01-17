package com.example.adminpanel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.ShopViewActivity;
import com.example.adminpanel.Tailor.TailorModel.Tailor;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private Context context;
    private List<Tailor> list;

    public ShopAdapter(Context context, List<Tailor> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getShopName());
        holder.name2.setText(list.get(position).getName());
        holder.address.setText("Address"+list.get(position).getShopaddress() + "," + list.get(position).getSellerCity());
        holder.contact.setText("Contact"+list.get(position).getPhone());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShopViewActivity.class);
            intent.putExtra("sid", list.get(position).getUid());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, name2, address, contact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shopNameTextView);
            name2 = itemView.findViewById(R.id.shopOwnerNameTextView);
            address = itemView.findViewById(R.id.shopContactTextView);
            contact = itemView.findViewById(R.id.shopAddressTextView);

        }
    }
}
