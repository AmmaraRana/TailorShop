package com.example.adminpanel.DialogueJava;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.ContentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class orderDjava extends AppCompatDialogFragment {
    private EditText Deliverydate;
    private orderDjavaListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.tailor_bill_activity, null);
        builder.setView(view).
                setNegativeButton("Cancel", (dialog, which) -> {

                }).setPositiveButton("Confirm", (dialog, which) -> {
                    String deliveryDays = Deliverydate.getText().toString();
                    listener.applyTexts(deliveryDays);

                    sendNotification("Order Confirmed..Proceed to payment!");

                    Intent intent = new Intent(getContext(), ContentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getActivity().startActivity(intent);

                });
        Deliverydate = view.findViewById(R.id.et_DeliveryDate);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (orderDjavaListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + "Must send bill to the Customer");
        }
    }

    private void sendNotification(String s) {
        // Get the current Firebase user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Get the UID of the current user
            String uid = currentUser.getUid();

            // Reference to the "users" node in the Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

            // Retrieve data from the Realtime Database
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Handle the data snapshot
                    if (dataSnapshot.exists()) {
                        // Data exists, you can access it using dataSnapshot.getValue()
                        // For example, if "username" is a field in your database
                        String username = dataSnapshot.child("username").getValue(String.class);
                        System.out.println("Username: " + username);
                    } else {
                        // Data does not exist
                        System.out.println("User data does not exist");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle the error
                    System.out.println("Error: " + databaseError.getMessage());
                }
            });
        }


    }

    public interface orderDjavaListener {
        void applyTexts(String deliveryDays);
    }
}


