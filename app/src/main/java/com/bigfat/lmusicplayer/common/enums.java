package com.bigfat.lmusicplayer.common;

/**
 * Created by yueban on 15/5/4.
 */
public class enums {
    /**
     * 音频列表类型
     */
    public enum AudioListType {
        All,//所有歌曲
        ALBUM,//专辑
        ARTIST,//歌手
        PLAYLIST,//播放列表
        SEARCH,//搜索
    }

    /**
     * 重复播放模式
     */
    public enum RepeatMode {
        OFF,//关闭循环,即顺序播放
        REPEAT_LIST,//列表循环
        REPEAT_TRACK,//单曲循环
    }

    /**
     * 随机播放模式
     */
    public enum RandomMode {
        OFF,//关闭
        ON,//开启
    }
}
