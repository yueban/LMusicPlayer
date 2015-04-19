package com.bigfat.lmusicplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bigfat.lmusicplayer.common.BaseActivity;
import com.bigfat.lmusicplayer.model.Audio;
import com.bigfat.lmusicplayer.service.AudioService;
import com.bigfat.lmusicplayer.util.AudioUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private AudioService.AudioBinder binder;
    private List<Audio> data;
    private ListView lvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTransition();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initService();
        initData();
        initView();
        initEvent();
    }

    private void initTransition() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode().setDuration(1000));
            getWindow().setExitTransition(null);
        }
    }

    private void initService() {
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "onServiceConnected");
                binder = (AudioService.AudioBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
    }

    private void initData() {
        data = AudioUtil.getAudioData(this);
    }

    private void initView() {
        lvMain = (ListView) findViewById(R.id.lv_main);

        List<String> data_temp = new ArrayList<>();
        for (Audio audio : data) {
            data_temp.add(audio.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, android.R.id.text1, data_temp);
        lvMain.setAdapter(adapter);
    }

    private void initEvent() {
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binder.playAudio(data.get(position).getData());
            }
        });
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
