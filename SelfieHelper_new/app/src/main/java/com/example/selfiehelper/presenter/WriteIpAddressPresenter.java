package com.example.selfiehelper.presenter;

import android.util.Patterns;

import net.grandcentrix.thirtyinch.TiPresenter;

import com.example.selfiehelper.view.WriteIpAddressView;

public class WriteIpAddressPresenter extends TiPresenter<WriteIpAddressView> {

    public void validateIpAddress(String streamIpAddress) {
        if (Patterns.IP_ADDRESS.matcher(streamIpAddress).matches()) {
            getView().intentToPlayStreamActivity(streamIpAddress);
        } else {
            getView().onWrongIpAddress();
        }
    }
}
