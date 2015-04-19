package com.bigfat.lmusicplayer.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.bigfat.lmusicplayer.fragment.AudioListFragment;
import com.bigfat.lmusicplayer.model.Audio;

import java.util.ArrayList;
import java.util.List;

/**
 * 音频工具类
 * Created by yueban on 15/4/8.
 */
public class AudioUtil {
    private static final String TAG = AudioUtil.class.getSimpleName();

    //获取音频文件的URI
    private static final String[] AUDIO_COLUMNS = new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ARTIST_KEY,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM_KEY,
    };

    /**
     * 获取音频数据
     *
     * @param type 音频数据类型
     * @param key  音频数据类型对应值
     */
    public static List<Audio> getAudioData(Context context, AudioListFragment.AudioListType type, String key) {
        List<Audio> list = new ArrayList<>();
        if (type == null ||
                (type != AudioListFragment.AudioListType.All && TextUtils.isEmpty(key))) {
            return list;
        }

        ContentResolver cr = context.getContentResolver();
        //ContentProvider只能由ContentResolver发送请求
        Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = null;
        switch (type) {
            case All:
                cursor = cr.query(AUDIO_URI, AUDIO_COLUMNS, MediaStore.Audio.Media.DURATION + ">?", new String[]{"10000"}, null);
                break;

            case ALBUM:
                cursor = cr.query(AUDIO_URI, AUDIO_COLUMNS, MediaStore.Audio.Media.DURATION + ">?" + " and " + MediaStore.Audio.Media.ALBUM + "=?", new String[]{"10000", key}, null);
                break;

            case ARTIST:
                cursor = cr.query(AUDIO_URI, AUDIO_COLUMNS, MediaStore.Audio.Media.DURATION + ">?" + " and " + MediaStore.Audio.Media.ARTIST + "=?", new String[]{"10000", key}, null);
                break;

            case PLAYLIST:
                cursor = cr.query(AUDIO_URI, AUDIO_COLUMNS, MediaStore.Audio.Media.DURATION + ">?" + " and " + "playlist=?", new String[]{"10000", key}, null);
                break;

            case SEARCH:
//                cursor = cr.query(AUDIO_URI, AUDIO_COLUMNS, MediaStore.Audio.Media.DURATION + ">?" + " and " + MediaStore.Audio.Media.ALBUM + "=?", new String[]{"10000", key}, null);
                break;
        }
        if (cursor == null) {
            return list;
        }
        while (cursor.moveToNext()) {
            Audio audio = new Audio(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_KEY)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_KEY)));
            list.add(audio);
        }
        cursor.close();
        return list;
    }
}
