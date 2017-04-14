package com.karangop.desafioshuffle.models;

/**
 * Created by karan_000 on 12-04-2017.
 */

public class Song {

    private long id;
    private String title, artist, album;

    public Song(String id, String title, String artist, String album) {
        this.id = Long.parseLong(id);
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
