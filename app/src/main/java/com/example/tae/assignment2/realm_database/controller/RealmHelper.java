package com.example.tae.assignment2.realm_database.controller;

import com.example.tae.assignment2.realm_database.model.MusicModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmHelper {

    private Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void storeData(final MusicModel musicModel){
        if (musicModel != null){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(musicModel);
                }
            });
        }
    }

    public void deleteRealmData(){
        RealmResults<MusicModel> realmResults = realm.where(MusicModel.class).findAll();
        realmResults.deleteAllFromRealm();
    }

    public ArrayList<MusicModel> getMusicModel(){
        ArrayList<MusicModel> musicModels = new ArrayList<>();

        RealmResults<MusicModel> realmResults = realm.where(MusicModel.class).findAll();
        for (MusicModel onlineMusic: realmResults) {
            musicModels.add(onlineMusic);
        }

        return musicModels;
    }


}
