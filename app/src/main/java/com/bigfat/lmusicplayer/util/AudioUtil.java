package com.bigfat.lmusicplayer.util;

import android.provider.MediaStore;
import android.text.TextUtils;

import com.bigfat.lmusicplayer.common.enums;
import com.bigfat.lmusicplayer.model.Artist;
import com.bigfat.lmusicplayer.model.Audio;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 音频工具类
 * Created by yueban on 15/4/8.
 */
public class AudioUtil {
    private static final String TAG = AudioUtil.class.getSimpleName();

    /**
     * 获取音频数据
     *
     * @param type 音频数据类型
     * @param key  音频数据类型对应值
     */
    public static List<Audio> getAudioData(enums.AudioListType type, String key) {
        List<Audio> list = new ArrayList<>();
        if (type == null || (type != enums.AudioListType.All && TextUtils.isEmpty(key))) {
            return list;
        }

        switch (type) {
            case All:
                list = DBUtil.queryAll(Audio.class);
                break;

            case ALBUM:
                list = DBUtil.query(QueryBuilder.create(Audio.class).where(MediaStore.Audio.Media.ALBUM_ID + "=?", new String[]{key}));
                break;

            case ARTIST:
                list = DBUtil.query(QueryBuilder.create(Audio.class).where(MediaStore.Audio.Media.ARTIST_ID + "=?", new String[]{key}));
                break;

            case PLAYLIST:
//               list = DBUtil.query(QueryBuilder.create(Audio.class).where(MediaStore.Audio.Media.ALBUM_ID + "=?", new String[]{key}));
                break;

            case SEARCH:
//               list = cursor = cr.query(AUDIO_URI, AUDIO_COLUMNS, MediaStore.Audio.Media.DURATION + ">?" + " and " + MediaStore.Audio.Media.ALBUM + "=?", new String[]{"10000", key}, null);
                break;
        }
        return list;
    }

    /**
     * 获取所有专辑数据
     */
    public static List<Audio> getAlbumData() {
        return DBUtil.query(QueryBuilder.create(Audio.class).distinct(true).columns(new String[]{MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Albums.ALBUM_ART}));
    }

    /**
     * 获取所有歌手数据
     */
    public static List<Artist> getArtistData() {
        return DBUtil.queryAll(Artist.class);
    }
}