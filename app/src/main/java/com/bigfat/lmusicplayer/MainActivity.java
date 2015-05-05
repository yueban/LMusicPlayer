package com.bigfat.lmusicplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bigfat.lmusicplayer.view.widget.PagerSlidingTabStrip;
import com.bigfat.lmusicplayer.common.BaseActivity;
import com.bigfat.lmusicplayer.common.Const;
import com.bigfat.lmusicplayer.fragment.AlbumFragment;
import com.bigfat.lmusicplayer.fragment.ArtistFragment;
import com.bigfat.lmusicplayer.fragment.AudioListFragment;
import com.bigfat.lmusicplayer.service.AudioService;
import com.bigfat.lmusicplayer.task.AudioUpdateTask;
import com.bigfat.lmusicplayer.util.SPUtil;
import com.bigfat.lmusicplayer.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    //Service
    public static AudioService.AudioBinder binder;
    //控件
    private Toolbar tbTop;
    private DrawerLayout dlMain;
    private PagerSlidingTabStrip pstsMain;
    private ViewPager vpMain;
    //音频Service连接对象
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
        bindView();
        initToolbar();
        initViewPager();
        initEvent();

        //第一次启动执行
        if (SPUtil.getBoolean(Const.SP_IS_FIRST_START, true)) {
            new AudioUpdateTask(this) {
                @Override
                protected void doInUIThread() {
                    initViewPager();
                    //设置：不再是第一次启动
                    SPUtil.putBoolean(Const.SP_IS_FIRST_START, false);
                }
            }.execute();
        }
    }

    private void initService() {
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);
    }

    private void bindView() {
        tbTop = (Toolbar) findViewById(R.id.tb_top);
        dlMain = (DrawerLayout) findViewById(R.id.dl_main);
        pstsMain = (PagerSlidingTabStrip) findViewById(R.id.psts_main);
        vpMain = (ViewPager) findViewById(R.id.vp_main);
    }

    private void initToolbar() {
        setSupportActionBar(tbTop);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, dlMain, tbTop, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        dlMain.setDrawerListener(mDrawerToggle);
    }

    private void initViewPager() {
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(AudioListFragment.newInstance());
        fragments.add(AlbumFragment.newInstance());
        fragments.add(ArtistFragment.newInstance());
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final String[] titles = new String[]{"所有", "专辑", "艺术家"};

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        pstsMain.setViewPager(vpMain);
        pstsMain.setTextColor(0x50ffffff);
        pstsMain.setTextSelectColor(0xffffffff);
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
            new AudioUpdateTask(this) {

                @Override
                protected void doInUIThread() {
                    initViewPager();
                }
            }.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPlayButtonClick(final View view) {
        ToastUtil.show("这个还没做");
    }
}
