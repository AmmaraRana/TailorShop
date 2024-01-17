package com.example.adminpanel.Customer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.adminpanel.R;

import org.w3c.dom.Text;


public class ProductDetailFragment extends Fragment {

    String name, Price, Description, Image;
    public ProductDetailFragment(String name, String Price, String Description, String Image) {

        this.Price = Price;
        this.Description = Description;

        this.Image = Image;
        this.name = name;


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_product_detail, container, false);

        ImageView imageView=view.findViewById(R.id.product_image_detail);
        TextView name=view.findViewById(R.id.product_name_detail);
       TextView Price=view.findViewById(R.id.fabric_Price_detail);
       TextView Description=view.findViewById(R.id.product_description_detail   );
       name.setText((CharSequence) name);
       Price.setText((CharSequence) Price);
       Description.setText((CharSequence) Description);
        Glide.with(getContext()).load(Image).into(imageView);
       return view;
    }


}