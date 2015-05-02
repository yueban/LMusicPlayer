package com.bigfat.lmusicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.bigfat.lmusicplayer.common.BaseActivity;
import com.bigfat.lmusicplayer.fragment.AudioListFragment;
import com.bigfat.lmusicplayer.model.Audio;

/**
 * 专辑/艺术家详情页
 * Created by yueban on 15/4/23.
 */
public class DetailActivity extends BaseActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private AudioListFragment.AudioListType type;//显示的界面type
    private String key;//显示界面关键字
    private Audio audio;//显示界面的数据

    //控件
    private Toolbar tbTop;

    /**
     * 跳转专辑/歌手详情页
     */
    public static void actionStart(Context context, @NonNull AudioListFragment.AudioListType type, String key, Audio audio) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("key", key);
        intent.putExtra("audio", audio);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        type = (AudioListFragment.AudioListType) intent.getSerializableExtra("type");
        key = intent.getStringExtra("key");
        audio = (Audio) intent.getSerializableExtra("audio");

        initView();
    }

    private void initView() {
        initToolbar();

        AudioListFragment audioListFragment = AudioListFragment.newInstance(type, key, audio);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_detail_container, audioListFragment, AudioListFragment.TAG).commit();
    }

    private void initToolbar() {
        tbTop = (Toolbar) findViewById(R.id.tb_top);
        setSupportActionBar(tbTop);
    }

    public Toolbar getToolbar() {
        return tbTop;
    }
}
