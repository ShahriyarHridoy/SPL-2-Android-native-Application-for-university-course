package com.example.selfiehelper.ui.fragment;

import android.os.Bundle;
//import androidx.core.app.Fragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfiehelper.R;

public class HowToUseFragmentFactory extends Fragment {

    public static HowToUseFragmentFactory newInstance(int position) {
        Bundle args = new Bundle();
        HowToUseFragmentFactory fragment = new HowToUseFragmentFactory();
        args.putInt("htu_fragment", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutResource = getProperLayout(getArguments().getInt("htu_fragment", 0));
        View v = inflater.inflate(layoutResource, container, false);
        return v;
    }

    int getProperLayout(int position) {
        switch (position) {
            case 0:
                return R.layout.fragment_htu1;



        }
        return 0;
    }
}
