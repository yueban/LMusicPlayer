package com.bigfat.lmusicplayer.common;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by yueban on 15/4/12.
 */
public class BaseActivity extends ActionBarActivity {

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
}
