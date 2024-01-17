package com.example.adminpanel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.Model.Cart;
import com.example.adminpanel.Prevalent.Prevalent;
import com.example.adminpanel.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {
    String fabric;
    ImageView back;
    int oneproductprice;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn, see_variations_btn;
    private int overalltotalprice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        back = findViewById(R.id.backed);
        back.setOnClickListener(v -> finish());
        initialization();


    }

    private void initialization() {

        recyclerView = findViewById(R.id.cartlist);
        nextProcessBtn = findViewById(R.id.next_process_btn);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


    }

    @Override
    protected void onStart() {
        super.onStart();


        final DatabaseReference cartlistref = FirebaseDatabase.getInstance().getReference().child("CartList");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartlistref.child("UserView")
                                        .child(Prevalent.currentonlineUser.getPhone())
                                        .child("Products")
                                , Cart.class
                        )
                        .build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                holder.txtcartprice.setText(model.getPrice());
                holder.txtcartquantity.setText("Product Price:\t\t" + model.getQuantity());
                holder.txtCartname.setText("Product Name:\t" + model.getName());

                fabric = model.getFabric();

                holder.txtcatfab.setText(fabric);
                oneproductprice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                overalltotalprice = overalltotalprice + oneproductprice;


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {

                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);

                        builder.setTitle("Cart Options:");
                        builder.setItems(options
                                , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (which == 0) {
                                            cartlistref.child("UserView")
                                                    .child(Prevalent.currentonlineUser.getPhone())
                                                    .child("Products")
                                                    .child(model.getpID())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(CartActivity.this, "Product Removed from CartList", Toast.LENGTH_SHORT).show();
//                                                                    Intent intent = new Intent(CartActivity.this, CartActivity.class);
//                                                                    startActivity(intent);

                                                            }
                                                        }
                                                    });

                                        }
                                    }
                                });
                        builder.show();
                    }
                });
                holder.see_variations_btn.setOnClickListener(v -> {
                    // Inflate the custom dialog layout
                    View dialogView = LayoutInflater.from(CartActivity.this).inflate(R.layout.dialog_variations, null);

                    // Create the AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setView(dialogView);
                    builder.setTitle("Variations");

                    // Retrieve ImageViews from the inflated layout
                    ImageView imageView1 = dialogView.findViewById(R.id.imageView1);
                    ImageView imageView2 = dialogView.findViewById(R.id.imageView2);
                    ImageView imageView3 = dialogView.findViewById(R.id.imageView3);

                    // Fetch and set the images from Firebase or any other source
                    // Example using Picasso for image loading
                    // Replace with your actual image URLs
                    Picasso.get().load(model.getImage1()).into(imageView1);
                    Picasso.get().load(model.getImage2()).into(imageView2);
                    Picasso.get().load(model.getImage3()).into(imageView3);

                    // Create and show the dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                });

                holder.cartbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                        intent.putExtra("Total Price", String.valueOf(oneproductprice));
                        intent.putExtra("sid", model.getSid());
                        intent.putExtra("pid", model.getpID());
                        intent.putExtra("quantity", model.getQuantity());
                        intent.putExtra("image", model.getSelectedImage());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);


                return new CartViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                // Check if the adapter has no items, then show the empty dialog
                if (getItemCount() == 0) {
                    showEmptyCartDialog();
                }
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void showEmptyCartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Empty Cart");
        builder.setMessage("Your cart is empty.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "OK" button click
                dialog.dismiss(); // Dismiss the dialog
                finish(); // Optionally finish the activity or navigate to another screen
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

