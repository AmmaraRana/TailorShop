package com.example.adminpanel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Product;
import com.example.adminpanel.Model.SellerOrder;
import com.example.adminpanel.R;
import com.example.adminpanel.ViewHolder.ProductViewHolder;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private Context context;
    private List<SellerOrder> list;
    private ProductViewHolder.OnItemClickListener onItemClickListener;

    public OrderAdapter(Context context, List<SellerOrder> list) {
        this.context = context;
        this.list = list;
    }
    public void setOnItemClickListener(ProductViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.username.setText(list.get(position).getName());
        holder.userphoneNumber.setText(list.get(position).getContactinfo());
        holder.userTotalPrice.setText(list.get(position).getTotalAmount());
        holder.userDateTime.setText(list.get(position).getDate());
        holder.usershippingAddress.setText(list.get(position).getAddress());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, userphoneNumber, userTotalPrice, userDateTime, usershippingAddress;
        Button showorderbtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.order_user_name);
            userphoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            usershippingAddress = itemView.findViewById(R.id.order_address_city);

            showorderbtn = itemView.findViewById(R.id.show_all_product);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
