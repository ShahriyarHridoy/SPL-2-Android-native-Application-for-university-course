package com.example.selfiehelper.presenter;

import android.content.Intent;

import net.grandcentrix.thirtyinch.TiPresenter;

import com.example.selfiehelper.view.ConnectToStreamView;

public class ConnectToStreamPresenter extends TiPresenter<ConnectToStreamView> {

    public void onIntent(Intent intent) {
        if (getView() != null) getView().passIntentToNfcReader(intent);
    }
}
