package com.example.selfiehelper.adapter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;

import com.example.selfiehelper.ui.fragment.IpAddressFragment;
import com.example.selfiehelper.ui.fragment.QrCodeFragment;


public class StartStreamPagerAdapter extends FragmentPagerAdapter {

    public StartStreamPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new IpAddressFragment();
            case 1:
                return new QrCodeFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
