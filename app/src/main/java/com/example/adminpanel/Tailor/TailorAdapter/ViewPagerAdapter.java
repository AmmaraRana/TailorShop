package com.example.adminpanel.Tailor.TailorAdapter;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.adminpanel.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    ArrayList<Uri> ImageUrls;
    LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context, ArrayList<Uri> imageUrls) {
        this.context = context;
        ImageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return ImageUrls.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=layoutInflater.inflate(R.layout.image_item,container,false);
        ImageView imageView=view.findViewById(R.id.image);
        imageView.setImageURI(ImageUrls.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((RelativeLayout)object).removeView(container);
    }
}
