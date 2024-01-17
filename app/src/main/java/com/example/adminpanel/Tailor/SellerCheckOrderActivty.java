package com.example.adminpanel.Tailor;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.DialogueJava.orderDjava;
import com.example.adminpanel.Model.SellerOrder;
import com.example.adminpanel.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerCheckOrderActivty extends AppCompatActivity {
    String deliverylimit;
    String pid, totalAmount, contactinfo, name, address, city, sid;
    String seller;
    ImageView back;
    private RecyclerView orderlist;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_check_order_activty);
        seller = FirebaseAuth.getInstance().getCurrentUser().getUid();
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child("AdminOrders").child(seller);
        orderlist = findViewById(R.id.parent);
        back = findViewById(R.id.backed);
        back.setOnClickListener(v -> finish());
        orderlist.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<SellerOrder> options =
                new FirebaseRecyclerOptions.Builder<SellerOrder>()
                        .setQuery(orderRef.orderByChild("status").equalTo("not shipped"), SellerOrder.class)
                        .build();
        FirebaseRecyclerAdapter<SellerOrder, SellerViewHolder> adapter =
                new FirebaseRecyclerAdapter<SellerOrder, SellerViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull SellerViewHolder holder, int position, @NonNull SellerOrder model) {


                        holder.username.setText("Name :" + model.getName());
                        holder.userphonenumber.setText("Phone :" + model.getContactinfo());
                        holder.totalamount.setText("Billing Amount :" + model.getTotalAmount());
                        holder.recieveraddress.setText("Reciever Address:" + model.getAddress() + "," + model.getCity());
                        holder.state.setText("State :" + model.getState() + "Postal Code  :" + model.getPostalcode());
                        holder.orderdate.setText("Order at :" + model.getTime() + "," + model.getDate());
                        holder.requirement.setText("User Custom Requirement  :   " + model.getCustomrequirement());
//                        Glide.with(SellerCheckOrderActivty.this).load(model.getSize_image()).into(holder.sizeimage);
                        holder.btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showImageDialog(model.getLegLength(), model.getArmLength(), model.getNeck_circumference(), model.getShoulderwidth());
                            }
                        });
                        holder.showdetail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SellerCheckOrderActivty.this, NotificationActivity.class);
                                intent.putExtra("uid", model.getContactinfo());
                                intent.putExtra("pId", model.getPid());

                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(SellerCheckOrderActivty.this);
                                builder.setTitle("Confirm/Take order?");
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
                                        reference.child("status").setValue("shipped");
                                        billcreation();
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
                    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_layout, parent, false);

                        return new SellerViewHolder(view);
                    }
                };
        orderlist.setAdapter(adapter);
        adapter.startListening();
    }

    private void showImageDialog(String sizeImage, String armLength, String neckCircumference, String shoulderwidth) {

        Dialog dialog = new Dialog(SellerCheckOrderActivty.this);
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

    private void RemoveOrder(String uID) {
        orderRef.child(uID).removeValue();


    }


    private void billcreation() {
        if (pid != null && totalAmount != null && contactinfo != null && name != null && address != null && city != null && sid != null) {
            Log.d("SellerCheckOrderActivty", "Creating Bill - " +
                    "pid: " + pid + ", totalAmount: " + totalAmount +
                    ", contactinfo: " + contactinfo + ", name: " + name +
                    ", address: " + address + ", city: " + city);

            final DatabaseReference billquery = FirebaseDatabase.getInstance().getReference("OrderedBill");
            String billID = billquery.push().getKey();

            if (billID != null) {
                HashMap<String, Object> billMap = new HashMap<>();
                billMap.put("produtcId", pid);
                billMap.put("bill_amount", totalAmount);
                billMap.put("Coninfo", contactinfo);
                billMap.put("ProductName", name);
                billMap.put("ShipmentAddress", address);
                billMap.put("ShipmentCity", city);
                billMap.put("Status", "not shipped");
                billMap.put("billid", billID.toString());
                billMap.put("sellerid", sid);
                billquery.child("AdminView").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Bills")
                        .child(pid)
                        .updateChildren(billMap)
                        .addOnCompleteListener(task -> {
                            billquery.child("UserView").child(contactinfo).child("Bills").child(billID)
                                    .updateChildren(billMap)
                                    .addOnCompleteListener(innerTask -> {
                                        if (task.isSuccessful()) {
                                            FirebaseDatabase.getInstance().getReference().child("Orders")
                                                    .child("UserOrders")
                                                    .child(contactinfo)
                                                    .child(pid)
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(SellerCheckOrderActivty.this, "Bill sent", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }

                                    });
                        });
            } else {
                Log.e("SellerCheckOrderActivty", "BillID is null");
            }
        } else {
            Log.e("SellerCheckOrderActivty", "One or more values are null");
        }
    }

    public static class SellerViewHolder extends RecyclerView.ViewHolder {
        public TextView username, userphonenumber, totalamount, recieveraddress, orderdate, state;
        public Button showdetail, btn;
        public TextView requirement;
        ImageView sizeimage;

        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.order_user_name);
            userphonenumber = itemView.findViewById(R.id.order_phone_number);
            totalamount = itemView.findViewById(R.id.order_price);
            recieveraddress = itemView.findViewById(R.id.order_address_city);
            orderdate = itemView.findViewById(R.id.order_date_time);

            state = itemView.findViewById(R.id.code_state_city);
            showdetail = itemView.findViewById(R.id.show_all_product);
            btn = itemView.findViewById(R.id.see_image);
            requirement = itemView.findViewById(R.id.order_requirement);


        }
    }
}


