package com.bigfat.lmusicplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bigfat.lmusicplayer.common.BaseActivity;
import com.bigfat.lmusicplayer.common.Const;
import com.bigfat.lmusicplayer.fragment.AlbumFragment;
import com.bigfat.lmusicplayer.fragment.AudioListFragment;
import com.bigfat.lmusicplayer.service.AudioService;
import com.bigfat.lmusicplayer.task.AudioUpdateTask;
import com.bigfat.lmusicplayer.util.SPUtil;
import com.bigfat.lmusicplayer.view.widget.SlidingTabLayout;
import com.kale.activityoptions.transition.TransitionCompat;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    //Service
    public static AudioService.AudioBinder binder;
    //控件
    private DrawerLayout dlMain;
    private SlidingTabLayout stlMain;
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
        initView();
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

        TransitionCompat.startTransition(this, R.layout.activity_main);
    }

    private void initService() {
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);
    }

    private void initView() {
        dlMain = (DrawerLayout) findViewById(R.id.dl_main);
        stlMain = (SlidingTabLayout) findViewById(R.id.stl_main);
        vpMain = (ViewPager) findViewById(R.id.vp_main);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, dlMain, tbTop, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        dlMain.setDrawerListener(mDrawerToggle);
    }

    private void initViewPager() {
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(AudioListFragment.newInstance(AudioListFragment.AudioListType.All, null));
        fragments.add(AlbumFragment.newInstance());
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                return new String[]{"所有", "专辑"}[position];
            }
        });

        stlMain.setViewPager(vpMain);
        stlMain.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }

            @Override
            public int getDividerColor(int position) {
                return Color.TRANSPARENT;
            }
        });
        stlMain.setBackgroundColor(Color.BLUE);
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

    public void onPlayButtonClick(final View view) {
        Drawable drawable = ((ImageView) view).getDrawable();
        if (drawable instanceof Animatable) {
            if (binder.isPlaying()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((ImageView) view).setImageResource(R.drawable.animated_stop);
                    }
                }, 600);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((ImageView) view).setImageResource(R.drawable.animated_play);
                    }
                }, 600);
            }
            ((Animatable) drawable).start();
            binder.startOrPause();
        }
    }
}
