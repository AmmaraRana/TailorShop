package com.example.adminpanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Billing;
import com.example.adminpanel.activites.BillPaymentActivity;

import java.util.ArrayList;

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.BillingViewHolder> {
    private ArrayList<Billing> list;
    Context context;

    public BillingAdapter(ArrayList<Billing> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BillingAdapter.BillingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipped_layout, parent, false);
        return new BillingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillingAdapter.BillingViewHolder holder, int position) {
        holder.textProductName.setText(list.get(position).getProductName().toString());
        holder.textDeliveryMinimum.setText(list.get(position).getDeliveredwithin().toString());
        holder.textAmountToPay.setText(list.get(position).getBill_amount().toString());
        holder.textTapToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BillPaymentActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BillingViewHolder extends RecyclerView.ViewHolder {
        TextView textProductName, textDeliveryMinimum, textAmountToPay, textTapToPay;

        public BillingViewHolder(@NonNull View itemView) {


            super(itemView);
            textTapToPay = itemView.findViewById(R.id.textTapToPay);
            textAmountToPay = itemView.findViewById(R.id.textAmountToPay);
            textProductName = itemView.findViewById(R.id.textProductName);
            textDeliveryMinimum = itemView.findViewById(R.id.textDeliveryMinimum);

        }
    }
}
