package com.bigfat.lmusicplayer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigfat.lmusicplayer.common.BaseActivity;
import com.bigfat.lmusicplayer.model.Audio;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by yueban on 6/5/15.
 */
public class PlayActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = PlayActivity.class.getSimpleName();

    //控件
    private ImageView imgBg;
    private TextView tvTitle;
    private TextView tvArtist;
    private ImageView imgPlay;
    private ImageView imgPrevious;
    private ImageView imgNext;
    private ImageView imgRepeat;
    private ImageView imgRandom;
    //数据
    private Audio audio;//当前正在播放歌曲

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        audio = MainActivity.binder.getCurrentAudio();

        bindView();
        initView();
        initEvent();
    }

    private void bindView() {
        imgBg = (ImageView) findViewById(R.id.img_play_bg);
        tvTitle = (TextView) findViewById(R.id.tv_play_title);
        tvArtist = (TextView) findViewById(R.id.tv_play_artist);
        imgPlay = (ImageView) findViewById(R.id.img_play_play);
        imgPrevious = (ImageView) findViewById(R.id.img_play_previous);
        imgNext = (ImageView) findViewById(R.id.img_play_next);
        imgRepeat = (ImageView) findViewById(R.id.img_play_repeat);
        imgRandom = (ImageView) findViewById(R.id.img_play_random);
    }

    private void initView() {
        if (audio != null) {
            String img_url = audio.getAlbum_art();
            if (!TextUtils.isEmpty(img_url)) {
                img_url = "file:////" + img_url;
            }
            ImageLoader.getInstance().displayImage(img_url, imgBg);
            tvTitle.setText(audio.getTitle());
            tvArtist.setText(audio.getArtist());
        }
    }

    private void initEvent() {
        imgPlay.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        imgRepeat.setOnClickListener(this);
        imgRandom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_play_play:
                MainActivity.binder.startOrPause();
                break;

            case R.id.img_play_previous:
                MainActivity.binder.previous();
                break;

            case R.id.img_play_next:
                MainActivity.binder.next();
                break;

            case R.id.img_play_repeat:
                MainActivity.binder.repeat();
                break;

            case R.id.img_play_random:
                MainActivity.binder.random();
                break;
        }
    }
}
