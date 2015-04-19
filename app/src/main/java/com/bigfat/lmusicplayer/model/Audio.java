package com.bigfat.lmusicplayer.model;

/**
 * 音频
 * Created by yueban on 15/4/19.
 */
public class Audio {
    private String _id;
    private String title;
    private String data;
    private String artist_id;
    private String artist;

    public Audio(String _id, String title, String data, String artist_id, String artist) {
        this._id = _id;
        this.title = title;
        this.data = data;
        this.artist_id = artist_id;
        this.artist = artist;
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

    public String getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
