package com.example.selfiehelper.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.selfiehelper.R;
import com.example.selfiehelper.presenter.WriteIpAddressPresenter;
import com.example.selfiehelper.ui.activity.ConnectToStreamActivity;
import com.example.selfiehelper.view.WriteIpAddressView;

public class WriteIpAddressFragment extends TiFragment<WriteIpAddressPresenter, WriteIpAddressView> implements WriteIpAddressView {
    @BindView(R.id.ip_edit_text)
    EditText inputIpAddress;

    @NonNull
    @Override
    public WriteIpAddressPresenter providePresenter() {
        return new WriteIpAddressPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_write_ip, container, false);
        ButterKnife.bind(this, v);
        int inputMaxLength = 15;
        inputIpAddress.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputMaxLength)});
        return v;
    }

    @OnClick(R.id.connect_button)
    public void onClickConnect() {
        String streamIpAddress = inputIpAddress.getText().toString();
        getPresenter().validateIpAddress(streamIpAddress);
    }

    @Override
    public void intentToPlayStreamActivity(String ipAddress) {
        ConnectToStreamActivity connectToStreamActivity = (ConnectToStreamActivity) getActivity();
        connectToStreamActivity.intentToPlayStreamActivity(ipAddress);
    }

    @Override
    public void onWrongIpAddress() {
        Toast.makeText(getContext(), "Wrong IP", Toast.LENGTH_SHORT).show();
    }
}
