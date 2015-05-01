package com.bigfat.lmusicplayer.model;

import android.provider.MediaStore;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;

/**
 * 音频
 * Created by yueban on 15/4/19.
 */
@Table("Audio")
public class Audio implements Serializable {
    @PrimaryKey(PrimaryKey.AssignType.BY_MYSELF)
    @Column(MediaStore.Audio.Media._ID)
    private String _id;
    @Column(MediaStore.Audio.Media.TITLE)
    private String title;
    @Column(MediaStore.Audio.Media.DATA)
    private String data;
    @Column(MediaStore.Audio.Media.ARTIST)
    private String artist;
    @Column(MediaStore.Audio.Media.ARTIST_ID)
    private String artist_id;
    @Column(MediaStore.Audio.Media.ALBUM)
    private String album;
    @Column(MediaStore.Audio.Media.ALBUM_ID)
    private String album_id;
    @Column(MediaStore.Audio.Albums.ALBUM_ART)
    private String album_art;

    public Audio() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", data='" + data + '\'' +
                ", artist='" + artist + '\'' +
                ", artist_id='" + artist_id + '\'' +
                ", album='" + album + '\'' +
                ", album_id='" + album_id + '\'' +
                ", album_art='" + album_art + '\'' +
                '}';
    }
}