package com.example.adminpanel.Buyer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.adminpanel.R;
import com.example.adminpanel.activites.BillPaymentActivity;

public class CustomDialogFragment extends DialogFragment {

    public interface DialogListener {
        void onOkButtonClicked();
    }

    // Add a constructor to receive the context
    public CustomDialogFragment(Context context) {
        this.context = context;
    }

    Context context;
    private DialogListener mListener;
    private String productName;
    private String sellerid;
    private String billAmount;
    private String deliveredWithin;
    private String shipmentstatus;
    private String buyeraddress;
    private String recievercontact;
    private String productid;
    private String billid;

    // Setter method to set data from Firebase
    public void setData(String productName, String billAmount, String deliveredWithin
            , String shipmentstatus, String buyeraddress, String recievercontact, String productid, String sellerid, String billid) {
        this.productName = productName;
        this.billAmount = billAmount;
        this.deliveredWithin = deliveredWithin;
        this.shipmentstatus = shipmentstatus;
        this.buyeraddress = buyeraddress;
        this.recievercontact = recievercontact;
        this.productid = productid;
        this.sellerid = sellerid;
this.billid=billid;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.billing_layout, null);
        // Set values from Firebase to the views in the custom dialog
        TextView textProductName = view.findViewById(R.id.textProductName);
        TextView textBillAmount = view.findViewById(R.id.textBillingAmount);
        TextView textDeliveredWithin = view.findViewById(R.id.textDeliveredWithin);
        TextView textaddress = view.findViewById(R.id.textShipmentAddress);
        TextView textstatus = view.findViewById(R.id.textStatus);
        TextView textcontact = view.findViewById(R.id.textContactInfo);

        textProductName.setText(productName);
        textBillAmount.setText(billAmount);
        textDeliveredWithin.setText("Our best product delivery time is 10 days.");
        textaddress.setText(buyeraddress);
        textstatus.setText(shipmentstatus);
        textcontact.setText(recievercontact);
        builder.setView(view)
                .setTitle("Billing Details");
        Button okButton = view.findViewById(R.id.btnPayNow);


        // Set an OnClickListener to handle the button click
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notify the listener (MainActivity) that the "OK" button was clicked
//                mListener.onOkButtonClicked();


                if (context != null) { // Ensure context is not null before using it
                    Intent intent = new Intent(context, BillPaymentActivity.class);
                    intent.putExtra("deliverdwithin", deliveredWithin);
                    intent.putExtra("amount", billAmount);
                    intent.putExtra("sid", sellerid);
                    intent.putExtra("customer",recievercontact);
                    intent.putExtra("billid",billid);
                    context.startActivity(intent);
                }
            }
        });

        return builder.create();
    }


}
