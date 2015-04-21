package com.bigfat.lmusicplayer.common;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.bigfat.lmusicplayer.R;

/**
 * Created by yueban on 15/4/12.
 */
public class BaseActivity extends ActionBarActivity {
    protected Toolbar tbTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.add(this);
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.remove(this);
        super.onDestroy();
    }

    protected void initToolbar() {
        tbTop = (Toolbar) findViewById(R.id.tb_top);
        setSupportActionBar(tbTop);

//        getSupportActionBar().setDisplayUseLogoEnabled(false);//是否显示默认Logo
//        getSupportActionBar().setDisplayShowTitleEnabled(true);//是否显示默认Title
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//是否显示默认navigation(左上角的返回按钮)
//        getSupportActionBar().setHomeButtonEnabled(true);//navigation是否可以点击(必须可以啊...)
    }
}
