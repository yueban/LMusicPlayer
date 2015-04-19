package com.bigfat.lmusicplayer.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

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
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ARTIST,
    };

    /**
     * 获取音频数据
     */
    public static List<Audio> getAudioData(Context context) {
        List<Audio> list = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        //ContentProvider只能由ContentResolver发送请求
        Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //要读的列名,这些常量可以查GOOGLE官方开发文档,TITLE是标题 DATA是路径
        Cursor cursor = cr.query(AUDIO_URI, AUDIO_COLUMNS, MediaStore.Audio.Media.DURATION + ">?", new String[]{"10000"}, null);
        while (cursor.moveToNext()) {
            Audio audio = new Audio(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            list.add(audio);
        }
        cursor.close();
        return list;
    }
}
