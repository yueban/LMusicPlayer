package com.bigfat.lmusicplayer.model;

/**
 * 音频
 * Created by yueban on 15/4/19.
 */
public class Audio {
    private String _id;
    private String title;
    private String data;
    private String artist;
    private String artist_id;
    private String artist_key;
    private String album;
    private String album_id;
    private String album_key;

    public Audio(String _id, String title, String data, String artist, String artist_id, String artist_key, String album, String album_id, String album_key) {
        this._id = _id;
        this.title = title;
        this.data = data;
        this.artist = artist;
        this.artist_id = artist_id;
        this.artist_key = artist_key;
        this.album = album;
        this.album_id = album_id;
        this.album_key = album_key;
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

    public String getArtist_key() {
        return artist_key;
    }

    public void setArtist_key(String artist_key) {
        this.artist_key = artist_key;
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

    public String getAlbum_key() {
        return album_key;
    }

    public void setAlbum_key(String album_key) {
        this.album_key = album_key;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", data='" + data + '\'' +
                ", artist='" + artist + '\'' +
                ", artist_id='" + artist_id + '\'' +
                ", artist_key='" + artist_key + '\'' +
                ", album='" + album + '\'' +
                ", album_id='" + album_id + '\'' +
                ", album_key='" + album_key + '\'' +
                '}';
    }
}