package com.example.adminpanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Buyer.CustomDialogFragment;
import com.example.adminpanel.Model.Billing;
import com.example.adminpanel.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference ref;
    String sid;
    String pid;
    private Billing model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

        recyclerView = findViewById(R.id.confirm_recycler);
        ref = FirebaseDatabase.getInstance().getReference().child("OrderedBill")
                .child("UserView")
                .child(Prevalent.currentonlineUser.getPhone())
                .child("Bills");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        showproduct();


    }

    private void showproduct() {
        FirebaseRecyclerOptions<Billing> options =
                new FirebaseRecyclerOptions.Builder<Billing>()
                        .setQuery(ref, Billing.class)
                        .build();
        FirebaseRecyclerAdapter<Billing, BillingViewHolder> adapter =
                new FirebaseRecyclerAdapter<Billing, BillingViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull BillingViewHolder holder, int position, @NonNull Billing model) {
                        if (model != null) {
                            UserOrderActivity.this.model = model;
                            holder.textAmountToPay.setText("Amount to be paid :\t" + model.getBill_amount());
                            holder.textDeliveryMinimum.setText("Our best product delivery time is 10 days.");
                            holder.textProductName.setText("Product Name: \t\t" + model.getProductName());

                            holder.textTapToPay.setOnClickListener(v -> {
                                // Create an instance of the CustomDialogFragment
                                CustomDialogFragment dialogFragment = new CustomDialogFragment(UserOrderActivity.this);

                                // Set data from the current item to the dialog
                                dialogFragment.setData(model.getProductName(), model.getBill_amount(), model.getDeliveredwithin() + "   Days"
                                        , model.getStatus(), model.getShipmentAddress(), model.getConinfo(),
                                        model.getProductId(), model.getSellerid(), model.getBillid());


                                // Show the dialog
                                dialogFragment.show(getSupportFragmentManager(), "custom_dialog");
                            });
                        }
                    }

                    @NonNull
                    @Override
                    public BillingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipped_layout, parent, false);

                        return new BillingViewHolder(view);
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void product() {

    }


    public static class BillingViewHolder extends RecyclerView.ViewHolder {
        TextView textProductName, textDeliveryMinimum, textAmountToPay, textTapToPay;
        String amount;

        public BillingViewHolder(@NonNull View itemView) {


            super(itemView);
            textTapToPay = itemView.findViewById(R.id.textTapToPay);
            textAmountToPay = itemView.findViewById(R.id.textAmountToPay);
            textProductName = itemView.findViewById(R.id.textProductName);
            textDeliveryMinimum = itemView.findViewById(R.id.textDeliveryMinimum);


        }
    }

//    @Override
//    public void onOkButtonClicked() {
//        // Access the Billing model for the clicked item
//        Billing clickedBilling = model; // Use the model object
//
//        // Check if the clickedBilling is not null (to avoid NullPointerException)
//        if (clickedBilling != null) {
//            // Extract the bill_amount from the Billing model
//            String billAmount = clickedBilling.getBill_amount();
//            sid = clickedBilling.getSellerid();
//            pid = clickedBilling.getProductId();
//            Toast.makeText(this, pid, Toast.LENGTH_SHORT).show();
//            // Create an Intent and pass the billAmount as an extra
//            Intent intent = new Intent(UserOrderActivity.this, BillPaymentActivity.class);
//            intent.putExtra("amount", billAmount);
//            intent.putExtra("sid", sid);
//            intent.putExtra("pid", pid);
//            // Start the BillPaymentActivity with the intent
//            startActivity(intent);
//        }
//
//    }
}