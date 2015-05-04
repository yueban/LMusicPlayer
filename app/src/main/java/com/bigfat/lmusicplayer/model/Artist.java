package com.bigfat.lmusicplayer.model;

import android.provider.MediaStore;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by yueban on 15/5/3.
 */
@Table("Artist")
public class Artist implements Serializable {
    @PrimaryKey(PrimaryKey.AssignType.BY_MYSELF)
    @Column(MediaStore.Audio.Artists._ID)
    private String _id;
    @Column(MediaStore.Audio.Artists.ARTIST)
    private String artist;
    @Column(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)
    private String number_of_albums;
    @Column(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)
    private String number_of_tracks;
    private String album_art;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getNumber_of_albums() {
        return number_of_albums;
    }

    public void setNumber_of_albums(String number_of_albums) {
        this.number_of_albums = number_of_albums;
    }

    public String getNumber_of_tracks() {
        return number_of_tracks;
    }

    public void setNumber_of_tracks(String number_of_tracks) {
        this.number_of_tracks = number_of_tracks;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "_id='" + _id + '\'' +
                ", artist='" + artist + '\'' +
                ", number_of_albums='" + number_of_albums + '\'' +
                ", number_of_tracks='" + number_of_tracks + '\'' +
                ", album_art='" + album_art + '\'' +
                '}';
    }
}
