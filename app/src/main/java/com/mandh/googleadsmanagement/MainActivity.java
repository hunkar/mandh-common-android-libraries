package com.mandh.googleadsmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mandh.googleadmanager.AdManager;
import com.mandh.googleadmanager.BannerView;
import com.mandh.googleadmanager.InterstitialAdManager;
import com.mandh.splashview.SplashView;
import com.mandh.splashview.interfaces.SplashListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAds();
        setSplash();
    }

    private void setAds() {
        BannerView banner = findViewById(R.id.mandh_ads);

        AdManager.setAdManager(getApplicationContext(),
                getString(R.string.app_id),
                getString(R.string.banner_id),
                getString(R.string.interstitial_id));

        AdManager.setTestDeviceIds(Arrays.asList(getString(R.string.device_id)));

        AdManager.setActivity(MainActivity.this);
        AdManager.setInterstitialAdTimeout(2000);

        banner.show();

        InterstitialAdManager.initInterstitialAd();
        InterstitialAdManager.showOnReady();

        findViewById(R.id.show_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InterstitialAdManager.showAd();
            }
        });
    }

    private void setSplash() {
        findViewById(R.id.show_splash).setOnClickListener(view -> {
            SplashView splashView = findViewById(R.id.splash_view_normal);
            splashView.show();
            splashView.setOnSplashListener(splashView1 -> {
                Toast.makeText(MainActivity.this, "Set duration is finished.", Toast.LENGTH_SHORT).show();
                splashView1.hide();
            });
        });

        findViewById(R.id.show_custom_splash).setOnClickListener(view -> {
            SplashView splashView = findViewById(R.id.splash_view_with_custom_color_text);
            splashView.show();
            splashView.setOnSplashListener(splashView1 -> {
                Toast.makeText(MainActivity.this, "Set duration is finished.", Toast.LENGTH_SHORT).show();
                splashView1.hide();
            });
        });
    }
}