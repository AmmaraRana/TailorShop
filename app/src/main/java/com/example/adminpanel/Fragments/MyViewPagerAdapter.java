package com.example.adminpanel.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch (position)
       {
           case 0:
               return new AllFragment();
           case 1:
               return new payFragment();
           case 3:
               return new shipFragment();
           default:
               return new AllFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
