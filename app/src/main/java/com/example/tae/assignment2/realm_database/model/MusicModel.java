package com.example.tae.assignment2.realm_database.model;

import io.realm.RealmObject;

public class MusicModel extends RealmObject {
    private String artworkUrl60;
    private String trackPrice;
    private String artistName;
    private String collectionName;


    public MusicModel() {
    }

    public MusicModel(String collectionName, String artistName, String artworkUrl60, String trackPrice) {
        this.artistName = artistName;
        this.collectionName = collectionName;
        this.artworkUrl60 = artworkUrl60;
        this.trackPrice = trackPrice;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(String trackPrice) {
        this.trackPrice = trackPrice;
    }
}
