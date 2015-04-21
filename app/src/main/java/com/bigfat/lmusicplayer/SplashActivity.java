package com.bigfat.lmusicplayer;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bigfat.lmusicplayer.common.BaseActivity;


public class SplashActivity extends BaseActivity {

    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        splash();
    }

    private void initView() {
        imgLogo = (ImageView) findViewById(R.id.img_splash_logo);
    }

    private void splash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                ActivityOptionsCompatICS options = ActivityOptionsCompatICS.makeSceneTransitionAnimation(
//                        SplashActivity.this, imgLogo, R.id.img_main_logo);
//                ActivityCompatICS.startActivity(SplashActivity.this, intent, options.toBundle());
//                finish();
            }
        }, 1000);
    }
}