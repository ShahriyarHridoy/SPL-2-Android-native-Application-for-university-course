package com.example.selfiehelper.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.selfiehelper.R;
import com.example.selfiehelper.presenter.NfcReaderPresenter;
import com.example.selfiehelper.ui.activity.ConnectToStreamActivity;
import com.example.selfiehelper.view.NfcReaderView;

public class NfcReaderFragment extends TiFragment<NfcReaderPresenter, NfcReaderView>
        implements NfcReaderView {
    @BindView(R.id.ip_address_text_view) TextView ipTextVIew;
    @BindView(R.id.connect_button) Button button;

    @NonNull
    @Override
    public NfcReaderPresenter providePresenter() {
        return new NfcReaderPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nfc_reader, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void showNfcResult(String ipAddress) {
        ipTextVIew.setVisibility(View.VISIBLE);
        ipTextVIew.setText(ipAddress);
        button.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.connect_button)
    public void onClickConnect() {
        getPresenter().connectToStream();
    }

    @Override
    public void intentToPlayStreamActivity(String ipAddress) {
        ConnectToStreamActivity connectToStreamActivity = (ConnectToStreamActivity) getActivity();
        connectToStreamActivity.intentToPlayStreamActivity(ipAddress);
    }

    @Override
    public void showNfcError() {
        Toast.makeText(getContext(), R.string.nfc_error, Toast.LENGTH_LONG).show();
    }

    public void onNfcIntent(Intent intent) {
        if (getPresenter() != null) {
            getPresenter().handleNfcIntent(intent);
        }
    }
}
