package com.bigfat.lmusicplayer.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.Random;

/**
 * Created by yueban on 15/4/8.
 */
public class AudioUtil {
    public static String getRandomAudioUrl(Context context) {
        ContentResolver cr = context.getContentResolver();
        //ContentProvider只能由ContentResolver发送请求
        Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //获取音频文件的URI
        String[] columns = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.ARTIST,
        };
        //要读的列名,这些常量可以查GOOGLE官方开发文档,TITLE是标题 DATA是路径
        Cursor cursor = cr.query(AUDIO_URI, columns, MediaStore.Audio.Media.DURATION + ">?", new String[]{"10000"}, null);
        //跟查询SQL一样了,除了第一个参数不同外.后面根据时长过滤小于10秒的文件
        cursor.moveToPosition(new Random().nextInt(cursor.getCount()));
        //循环读取第一列,即文件路径,0列是标题
        String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        cursor.close();
        return url;
    }
}
