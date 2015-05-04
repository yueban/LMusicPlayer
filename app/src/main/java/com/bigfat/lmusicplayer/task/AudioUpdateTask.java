package com.bigfat.lmusicplayer.task;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.bigfat.lmusicplayer.common.enums;
import com.bigfat.lmusicplayer.model.Artist;
import com.bigfat.lmusicplayer.model.Audio;
import com.bigfat.lmusicplayer.util.AudioUtil;
import com.bigfat.lmusicplayer.util.DBUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 更新本地音频数据
 * Created by yueban on 15/4/20.
 */
public abstract class AudioUpdateTask extends AsyncTask<String, Integer, String> {
    private static final String TAG = AudioUpdateTask.class.getSimpleName();

    private Context context;
    private ProgressDialog progressDialog;

    public AudioUpdateTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("update...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        initAudioData();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (values[0] == -1) {
            progressDialog.setMax(values[1]);
        } else {
            progressDialog.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        doInUIThread();
    }

    protected abstract void doInUIThread();

    private void initAudioData() {
        final ContentResolver contentResolver = context.getContentResolver();
        final Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final Uri ALBUMS_URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        final Uri ARTISTS_URI = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        final Cursor cursor = contentResolver.query(AUDIO_URI, null, null, null, null);
        final List<Audio> list = new ArrayList<>();
        final List<Artist> artists = new ArrayList<>();

        //更新歌曲总数
        onProgressUpdate(-1, cursor.getCount());

        while (cursor.moveToNext()) {
            //更新当前检索Index
            onProgressUpdate(cursor.getPosition());

            Audio audio = new Audio();
            audio.set_id(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            audio.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            audio.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            audio.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            audio.setArtist_id(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)));
            audio.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            audio.setAlbum_id(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            //获取专辑封面
            Cursor cursor_album = contentResolver.query(ALBUMS_URI, null, MediaStore.Audio.Albums._ID + "=?", new String[]{audio.getAlbum_id()}, null);
            if (cursor_album.getCount() > 0) {
                cursor_album.moveToFirst();
                audio.setAlbum_art(cursor_album.getString(cursor_album.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
            }
            cursor_album.close();

            list.add(audio);
        }
        cursor.close();
        //存储数据
        DBUtil.deleteAll(Audio.class);
        DBUtil.save(list);

        //获取歌手信息
        Cursor cursor_artist = contentResolver.query(ARTISTS_URI, null, null, null, null);
        //该歌手的所有歌曲，用于遍历获取歌手封面图片
        List<Audio> artistAudioList;
        while (cursor_artist.moveToNext()) {
            Artist artist = new Artist();
            artist.set_id(cursor_artist.getString(cursor_artist.getColumnIndex(MediaStore.Audio.Artists._ID)));
            artist.setArtist(cursor_artist.getString(cursor_artist.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
            artist.setNumber_of_albums(cursor_artist.getString(cursor_artist.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)));
            artist.setNumber_of_tracks(cursor_artist.getString(cursor_artist.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)));
            //获取该歌手所有歌曲，遍历取得一张专辑封面图作为歌手封面图片
            artistAudioList = AudioUtil.getAudioData(enums.AudioListType.ARTIST, artist.get_id());
            for (Audio audio : artistAudioList) {
                if (!TextUtils.isEmpty(audio.getAlbum_art())) {
                    artist.setAlbum_art(audio.getAlbum_art());
                    break;
                }
            }
            artists.add(artist);
        }
        cursor_artist.close();
        //存储数据
        DBUtil.delete(Artist.class);
        DBUtil.save(artists);
    }
}