package com.bigfat.lmusicplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bigfat.lmusicplayer.common.BaseActivity;
import com.bigfat.lmusicplayer.fragment.AudioListFragment;
import com.bigfat.lmusicplayer.service.AudioService;
import com.kale.activityoptions.transition.TransitionCompat;


public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    //控件
    private ViewPager vpMain;
    //Service
    public static AudioService.AudioBinder binder;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            binder = (AudioService.AudioBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initService();
        initView();
        initViewPager();
        initEvent();

        TransitionCompat.startTransition(this, R.layout.activity_main);
    }

    private void initService() {
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);
    }

    private void initView() {
        vpMain = (ViewPager) findViewById(R.id.vp_main);
    }

    private void initViewPager() {
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return AudioListFragment.newInstance(AudioListFragment.AudioListType.All, "");
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
    }

    private void initEvent() {

    }

    @Override
    protected void onDestroy() {
        unbindService(sc);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
