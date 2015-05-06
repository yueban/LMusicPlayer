package com.bigfat.lmusicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.bigfat.lmusicplayer.common.enums;
import com.bigfat.lmusicplayer.model.Audio;
import com.bigfat.lmusicplayer.util.ToastUtil;

import java.io.IOException;
import java.util.List;

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
    private enums.RepeatMode repeatMode;//循环播放模式
    private enums.RandomMode randomMode;//随机播放模式

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
                if (mp.getDuration() - mp.getCurrentPosition() < 1000) {
                    mediaPlayer.stop();
                }
            }
        });
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
    }

    /**
     * 播放音频
     *
     * @param audioList 播放列表
     * @param position  播放歌曲在列表中的位置
     */
    private void playAudio(List<Audio> audioList, int position) {
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
     * 上一首
     */
    private void previous() {
        position--;
        play();
    }

    /**
     * 下一首
     */
    private void next() {
        position++;
        play();
    }

    /**
     * 重复播放模式
     */
    private void repeat() {
        ToastUtil.show("这个还没做");
    }

    /**
     * 随机播放模式
     */
    private void random() {
        ToastUtil.show("这个还没做");
    }

    /**
     * 音频绑定类，用于音频的播控
     */
    public class AudioBinder extends Binder {
        public void playAudio(List<Audio> audioList, int position) {
            AudioService.this.playAudio(audioList, position);
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