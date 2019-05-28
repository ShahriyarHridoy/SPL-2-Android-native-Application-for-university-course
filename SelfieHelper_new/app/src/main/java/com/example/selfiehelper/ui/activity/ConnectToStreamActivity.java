package com.example.selfiehelper.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import net.grandcentrix.thirtyinch.TiActivity;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.selfiehelper.R;
import com.example.selfiehelper.adapter.ConnectToStreamPagerAdapter;
import com.example.selfiehelper.presenter.ConnectToStreamPresenter;
import com.example.selfiehelper.receiver.WiFiStateChangeReceiver;
import com.example.selfiehelper.ui.listener.OnDotPageChangeListener;
import com.example.selfiehelper.view.ConnectToStreamView;

public class ConnectToStreamActivity extends TiActivity<ConnectToStreamPresenter, ConnectToStreamView>
        implements ConnectToStreamView {
    public static final int PAGE_INPUT = 0;
    public static final int PAGE_QR_CODE = 1;
    public static final int PAGE_GroupCreate = 2;
    public static final String INTENT_EXTRA_IP_CONNECT = "ip_connect";
    private ViewPager viewPager;
    private WiFiStateChangeReceiver wiFiStateChangeReceiver = new WiFiStateChangeReceiver();
    @BindViews({R.id.circle_page1, R.id.circle_page2, R.id.circle_page3}) List<ImageView> dots;
    private ConnectToStreamPagerAdapter connectToStreamPagerAdapter;

    @NonNull
    @Override
    public ConnectToStreamPresenter providePresenter() {
        return new ConnectToStreamPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_stream);
        ButterKnife.bind(this);
        initViewPager();
        registerReceivers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wiFiStateChangeReceiver);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        getPresenter().onIntent(intent);
    }



    @Override
    @OnClick(R.id.input_layout)
    public void SlideToInputPage() {
        viewPager.setCurrentItem(PAGE_INPUT);
    }

    @Override
    @OnClick(R.id.qr_code_layout)
    public void SlideToQrCodePage() {
        viewPager.setCurrentItem(PAGE_QR_CODE);
    }

    @Override
    @OnClick(R.id.btn_Group_Layout)
    public void SlideToGroupCreatePage() {
        viewPager.setCurrentItem(PAGE_GroupCreate);
    }





    @Override
    public void intentToPlayStreamActivity(String ipAddress) {
        Intent i = new Intent(ConnectToStreamActivity.this, PlayStreamActivity.class);
        i.putExtra(INTENT_EXTRA_IP_CONNECT, ipAddress);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ConnectToStreamActivity.this, MainMenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.about_connect_pager);
        connectToStreamPagerAdapter = new ConnectToStreamPagerAdapter(getSupportFragmentManager());
        viewPager.addOnPageChangeListener(new OnDotPageChangeListener(dots));
        viewPager.setAdapter(connectToStreamPagerAdapter);
    }

    private void registerReceivers() {
        registerReceiver(wiFiStateChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(wiFiStateChangeReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
    }
}
