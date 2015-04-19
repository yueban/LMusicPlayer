package com.bigfat.lmusicplayer;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.view.Window;
import android.widget.ImageView;

import com.bigfat.lmusicplayer.common.BaseActivity;


public class SplashActivity extends BaseActivity {

    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTransition();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                if (Build.VERSION.SDK_INT >= 21) {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, imgLogo, imgLogo.getTransitionName()).toBundle();
                    startActivity(intent, bundle);
                } else {
                    startActivity(intent);
                }
                finish();
            }
        }, 2000);
    }

    private void initView() {
        imgLogo = (ImageView) findViewById(R.id.img_splash_logo);
    }

    private void initTransition() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new ChangeBounds().setDuration(1000));
        }
    }
}
