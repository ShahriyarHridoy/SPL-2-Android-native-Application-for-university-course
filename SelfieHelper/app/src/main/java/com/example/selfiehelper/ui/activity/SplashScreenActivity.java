package com.example.selfiehelper.ui.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.selfiehelper.R;
import com.example.selfiehelper.presenter.SplashScreenPresenter;
import com.example.selfiehelper.ui.animation.SmallBang;
import com.example.selfiehelper.ui.animation.SmallBangListener;
import com.example.selfiehelper.util.SettingsPreferencesUtil;
import com.example.selfiehelper.view.SplashScreenView;


public class SplashScreenActivity extends TiActivity<SplashScreenPresenter, SplashScreenView> implements SplashScreenView {
    private static final int SMALLBANG_RADIUS = 180;
    private SplashScreenPresenter splashScreenPresenter;
    private SmallBang smallBang;
    @BindView(R.id.logo_text) ImageView logoTextSwap;
    @BindView(R.id.logo_aparat_icon) ImageView logoIcon;

    @NonNull
    @Override
    public SplashScreenPresenter providePresenter() {
        final SettingsPreferencesUtil settingsPreferencesUtil = new SettingsPreferencesUtil(PreferenceManager.getDefaultSharedPreferences(this));
        splashScreenPresenter = new SplashScreenPresenter(settingsPreferencesUtil);
        return splashScreenPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        smallBang = SmallBang.attach2Window(this);
    }

    @Override
    public void startSplashAnimation() {
        smallBang.bang(logoTextSwap, SMALLBANG_RADIUS, new SmallBangListener() {
            @Override
            public void onAnimationStart() {
                ((AnimationDrawable) logoIcon.getBackground()).start();
                logoTextSwap.setImageResource(R.drawable.selfiehelper_logo_text);
            }

            @Override
            public void onAnimationEnd() {
                splashScreenPresenter.delayRunActivity();
            }
        });
    }

    @Override
    public void intentToMainMenu() {
        Intent intent = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



}
