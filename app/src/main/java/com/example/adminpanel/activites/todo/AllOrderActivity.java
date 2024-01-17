package com.example.adminpanel.activites.todo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.DialogueJava.orderDjava;
import com.example.adminpanel.Model.SellerOrder;
import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.NotificationActivity;
import com.example.adminpanel.Tailor.SellerCheckOrderActivty;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllOrderActivity extends AppCompatActivity {
     String pid, totalAmount, contactinfo, name, address, city, sid;
    String seller;
    ImageView back;
    private RecyclerView orderlist;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order);
        seller = FirebaseAuth.getInstance().getCurrentUser().getUid();
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child("AdminOrders").child(seller);
        orderlist = findViewById(R.id.parent);
        orderlist.setLayoutManager(new LinearLayoutManager(this));
        back = findViewById(R.id.backed);
        back.setOnClickListener(v -> finish());
        FirebaseRecyclerOptions<SellerOrder> options =
                new FirebaseRecyclerOptions.Builder<SellerOrder>()
                        .setQuery(orderRef.orderByChild("status").equalTo("shipped"), SellerOrder.class)
                        .build();
        FirebaseRecyclerAdapter<SellerOrder, SellerCheckOrderActivty.SellerViewHolder> adapter =
                new FirebaseRecyclerAdapter<SellerOrder, SellerCheckOrderActivty.SellerViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull SellerCheckOrderActivty.SellerViewHolder holder, int position, @NonNull SellerOrder model) {
                        holder.username.setText("Name :" + model.getName());
                        holder.userphonenumber.setText("Phone :" + model.getContactinfo());
                        holder.totalamount.setText("Billing Amount :" + model.getTotalAmount());
                        holder.recieveraddress.setText("Reciever Address:" + model.getAddress() + "," + model.getCity());
                        holder.state.setText("State :" + model.getState() + "Postal Code  :" + model.getPostalcode());
                        holder.orderdate.setText("Order at :" + model.getTime() + "," + model.getDate());
                        holder.requirement.setText("User Custom Requirement  :   " + model.getCustomrequirement());
                        holder.btn.setOnClickListener(v -> showImageDialog(model.getLegLength(), model.getArmLength(), model.getNeck_circumference(), model.getShoulderwidth()));
                        holder.showdetail.setOnClickListener(v -> {
                            Intent intent = new Intent(AllOrderActivity.this, NotificationActivity.class);
                            intent.putExtra("uid", model.getContactinfo());
                            intent.putExtra("pId", model.getPid());

                            startActivity(intent);
                        });
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Complete",
                                        "Pending"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AllOrderActivity.this);
                                builder.setTitle("Order Status?");
                                builder.setItems(options, (dialog, which) -> {
                                    if (which == 0) {

                                        pid = model.getPid();
                                        totalAmount = model.getTotalAmount();
                                        contactinfo = model.getContactinfo();
                                        name = model.getName();
                                        address = model.getAddress();
                                        city = model.getCity();
                                        sid = model.getSid();
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                                                .child("Orders").child("AdminOrders")
                                                .child(seller).child(pid);
                                        reference.child("status").setValue("complete");

                                    } else if (which == 1) {
                                        finish();
                                    }
                                });
                                builder.show();
                            }

                            private void openDialog() {
                                orderDjava exampleDialoge = new orderDjava();
                                exampleDialoge.show(getSupportFragmentManager(), "Order Summary");

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public SellerCheckOrderActivty.SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_layout, parent, false);

                        return new SellerCheckOrderActivty.SellerViewHolder(view);
                    }
                };
        orderlist.setAdapter(adapter);
        adapter.startListening();
    }

    private void showImageDialog(String sizeImage, String armLength, String neckCircumference, String shoulderwidth) {

        Dialog dialog = new Dialog(AllOrderActivity.this);
        dialog.setContentView(R.layout.dialog_layout);

        // Find TextViews by ID
        TextView label1 = dialog.findViewById(R.id.label1);
        TextView value1 = dialog.findViewById(R.id.value1);

        TextView label2 = dialog.findViewById(R.id.label2);
        TextView value2 = dialog.findViewById(R.id.value2);

        TextView label3 = dialog.findViewById(R.id.label3);
        TextView value3 = dialog.findViewById(R.id.value3);

        TextView label4 = dialog.findViewById(R.id.label4);
        TextView value4 = dialog.findViewById(R.id.value4);

        label1.setText("ArmLength:");
        value1.setText(armLength);

        label2.setText("Shoulder:");
        value2.setText(shoulderwidth);

        label3.setText("NeckCircumference:");
        value3.setText(neckCircumference);

        label4.setText("LegLength:");
        value4.setText(sizeImage);

        Button closeDialogButton = dialog.findViewById(R.id.close_dialog_button);
        closeDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}