package com.bigfat.lmusicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigfat.lmusicplayer.common.BaseActivity;
import com.bigfat.lmusicplayer.common.Const;
import com.bigfat.lmusicplayer.model.Audio;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 正在播放界面
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

    //广播接收器
    private BroadcastReceiver audioReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra(Const.EXTRA_COMMAND, -1)) {
                case Const.COMMAND_AUDIO_PLAY:
                    initData();
                    imgPlay.setImageResource(R.mipmap.ic_pause_circle_fill_grey600_48dp);
                    break;

                case Const.COMMAND_AUDIO_START:
                    imgPlay.setImageResource(R.mipmap.ic_pause_circle_fill_grey600_48dp);
                    break;

                case Const.COMMAND_AUDIO_PAUSE:
                    imgPlay.setImageResource(R.mipmap.ic_play_circle_fill_grey600_48dp);
                    break;

                case Const.COMMAND_AUDIO_PREVIOUS:

                    break;

                case Const.COMMAND_AUDIO_NEXT:

                    break;

                case Const.COMMAND_AUDIO_REPEAT:
                    setRepeatImage();
                    break;

                case Const.COMMAND_AUDIO_RANDOM:
                    setRandomImage();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        //注册广播接收器
        LocalBroadcastManager.getInstance(this).registerReceiver(audioReceiver, new IntentFilter(Const.ACTION_AUDIO));

        initView();
        initEvent();
        initData();
    }

    private void initView() {
        imgBg = (ImageView) findViewById(R.id.img_play_bg);
        tvTitle = (TextView) findViewById(R.id.tv_play_title);
        tvArtist = (TextView) findViewById(R.id.tv_play_artist);
        imgPlay = (ImageView) findViewById(R.id.img_play_play);
        imgPrevious = (ImageView) findViewById(R.id.img_play_previous);
        imgNext = (ImageView) findViewById(R.id.img_play_next);
        imgRepeat = (ImageView) findViewById(R.id.img_play_repeat);
        imgRandom = (ImageView) findViewById(R.id.img_play_random);
    }

    private void initEvent() {
        imgPlay.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        imgRepeat.setOnClickListener(this);
        imgRandom.setOnClickListener(this);
    }

    private void initData() {
        //获取界面数据
        audio = MainActivity.binder.getCurrentAudio();
        //显示数据
        if (audio != null) {
            String img_url = audio.getAlbum_art();
            if (!TextUtils.isEmpty(img_url)) {
                img_url = "file:////" + img_url;
            }
            ImageLoader.getInstance().displayImage(img_url, imgBg);
            tvTitle.setText(audio.getTitle());
            tvArtist.setText(audio.getArtist());
        }
        setPlayImage();
        setRepeatImage();
        setRandomImage();
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

    /**
     * 设置播放按钮图片
     */
    private void setPlayImage() {
        if (MainActivity.binder.isPlaying()) {
            imgPlay.setImageResource(R.mipmap.ic_pause_circle_fill_grey600_48dp);
        } else {
            imgPlay.setImageResource(R.mipmap.ic_play_circle_fill_grey600_48dp);
        }
    }

    /**
     * 设置随机播放图片
     */
    private void setRandomImage() {
        switch (MainActivity.binder.getRandomMode()) {
            case OFF:
                imgRandom.setColorFilter(null);
                break;

            case ON:
                imgRandom.setColorFilter(Color.BLACK);
                break;
        }
    }

    /**
     * 设置重复播放图片
     */
    private void setRepeatImage() {
        switch (MainActivity.binder.getRepeatMode()) {
            case OFF:
                imgRepeat.setImageResource(R.mipmap.ic_repeat_grey600_48dp);
                imgRepeat.setColorFilter(null);
                break;

            case REPEAT_TRACK:
                imgRepeat.setImageResource(R.mipmap.ic_repeat_one_grey600_48dp);
                imgRepeat.setColorFilter(Color.BLACK);
                break;

            case REPEAT_LIST:
                imgRepeat.setImageResource(R.mipmap.ic_repeat_grey600_48dp);
                imgRepeat.setColorFilter(Color.BLACK);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(audioReceiver);
        super.onDestroy();
    }
}
