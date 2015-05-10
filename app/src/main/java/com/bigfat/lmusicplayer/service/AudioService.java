package com.bigfat.lmusicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.bigfat.lmusicplayer.common.Const;
import com.bigfat.lmusicplayer.common.enums;
import com.bigfat.lmusicplayer.model.Audio;
import com.bigfat.lmusicplayer.util.ToastUtil;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * 音频播控服务
 * Created by yueban on 15/4/8.
 */
public class AudioService extends Service {
    private static final String TAG = AudioService.class.getSimpleName();

    private AudioBinder binder = new AudioBinder();
    private MediaPlayer mediaPlayer;
    private List<Audio> audioList;//当前播放列表
    private int position;//当前播放歌曲在播放列表中的位置
    private boolean isPlaying;//是否正在播放
    private enums.RepeatMode repeatMode = enums.RepeatMode.OFF;//循环播放模式，默认关闭
    private enums.RandomMode randomMode = enums.RandomMode.OFF;//随机播放模式，默认关闭

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
                //当前音频播放结束
                audioPlayComplete();
            }
        });
    }

    /**
     * 当前音频播放结束
     */
    private void audioPlayComplete() {
        boolean isPlayContinue = true;//是否继续播放

        switch (randomMode) {
            case ON://随机播放开启
                switch (repeatMode) {
                    case REPEAT_TRACK://单曲循环
                        break;

                    case REPEAT_LIST://列表循环
                    case OFF://顺序播放
                        position = new Random().nextInt(audioList.size() - 1);
                        break;
                }
                break;

            case OFF://随机播放关闭
                switch (repeatMode) {
                    case REPEAT_TRACK://单曲循环
                        break;

                    case REPEAT_LIST://列表循环
                        if (position == audioList.size() - 1) {
                            position = 0;
                        } else {
                            position++;
                        }
                        break;

                    case OFF://顺序播放
                        if (position == audioList.size() - 1) {
                            isPlayContinue = false;
                        } else {
                            position++;
                        }
                        break;
                }
                break;
        }
        //判断是否继续播放
        if (isPlayContinue) {
            play();
        }
    }

    /**
     * 根据当前播放列表，播放音频
     */
    private void play() {
        String url = audioList.get(position).getData();
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Const.ACTION_AUDIO);
        intent.putExtra(Const.EXTRA_COMMAND, Const.COMMAND_AUDIO_PLAY);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /**
     * 设置播放列表，播放音频
     *
     * @param audioList 播放列表
     * @param position  播放歌曲在列表中的位置
     */
    private void setAudioAndPlay(List<Audio> audioList, int position) {
        AudioService.this.audioList = audioList;
        AudioService.this.position = position;
        play();
    }

    /**
     * 播放/暂停
     */
    private void startOrPause() {
        if (isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        } else {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    /**
     * 点击上一首按钮
     */
    private void previous() {
        switch (randomMode) {
            case ON://随机播放开启
                position = new Random().nextInt(audioList.size() - 1);
                break;

            case OFF://随机播放关闭
                switch (repeatMode) {
                    case REPEAT_TRACK://单曲循环
                    case REPEAT_LIST://列表循环
                        if (position == 0) {
                            position = audioList.size() - 1;
                        } else {
                            position--;
                        }
                        break;

                    case OFF://顺序播放
                        if (position != 0) {
                            position--;
                        }
                        break;
                }
                break;
        }
        play();
    }

    /**
     * 点击下一首按钮
     */
    private void next() {
        switch (randomMode) {
            case ON://随机播放开启
                position = new Random().nextInt(audioList.size() - 1);
                break;

            case OFF://随机播放关闭
                switch (repeatMode) {
                    case REPEAT_TRACK://单曲循环
                    case REPEAT_LIST://列表循环
                        if (position == audioList.size() - 1) {
                            position = 0;
                        } else {
                            position++;
                        }
                        break;

                    case OFF://顺序播放
                        if (position != audioList.size() - 1) {
                            position++;
                        }
                        break;
                }
                break;
        }
        play();
    }

    /**
     * 点击重复播放按钮
     */
    private void repeat() {
        //遍历重复播放模式
        enums.RepeatMode[] modes = enums.RepeatMode.values();
        for (int i = 0; i < modes.length; i++) {
            enums.RepeatMode mode = modes[i];
            if (this.repeatMode == mode) {
                if (i == modes.length - 1) {//如果当前是枚举最后一个，则设置为第一个
                    this.repeatMode = modes[0];
                } else {//设置为枚举的下一个模式
                    this.repeatMode = modes[i + 1];
                }
                break;
            }
        }
        Log.i(TAG, "repeatMode--->" + repeatMode);
        ToastUtil.show("界面显示逻辑还没做");
    }

    /**
     * 点击随机播放按钮
     */
    private void random() {
        //遍历随机播放模式
        enums.RandomMode[] modes = enums.RandomMode.values();
        for (int i = 0; i < modes.length; i++) {
            enums.RandomMode mode = modes[i];
            if (this.randomMode == mode) {//如果当前是枚举最后一个，则设置为第一个
                if (i == modes.length - 1) {
                    this.randomMode = modes[0];
                } else {//设置为枚举的下一个模式
                    this.randomMode = modes[i + 1];
                }
                break;
            }
        }
        Log.i(TAG, "randomMode--->" + randomMode);
        ToastUtil.show("这个还没做");
    }

    /**
     * 音频绑定类，用于音频的播控
     */
    public class AudioBinder extends Binder {
        public void playAudio(List<Audio> audioList, int position) {
            AudioService.this.setAudioAndPlay(audioList, position);
        }

        public void startOrPause() {
            AudioService.this.startOrPause();
        }

        public boolean isPlaying() {
            return AudioService.this.isPlaying;
        }

        public void previous() {
            AudioService.this.previous();
        }

        public void next() {
            AudioService.this.next();
        }

        public void repeat() {
            AudioService.this.repeat();
        }

        public void random() {
            AudioService.this.random();
        }

        /**
         * 获取正在播放列表
         */
        public List<Audio> getAudioList() {
            return AudioService.this.audioList;
        }

        /**
         * 得到当前正在音频
         */
        public Audio getCurrentAudio() {
            if (AudioService.this.audioList != null && AudioService.this.audioList.size() > 0) {
                return AudioService.this.audioList.get(AudioService.this.position);
            }
            return null;
        }
    }
}