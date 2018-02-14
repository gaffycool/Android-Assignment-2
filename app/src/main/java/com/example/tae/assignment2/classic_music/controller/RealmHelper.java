package com.example.tae.assignment2.classic_music.controller;


import com.example.tae.assignment2.realm_database.model.MusicModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by TAE on 02-Feb-18.
 */

public class RealmHelper {

    Realm realm;


    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void saveClassicMusic(final MusicModel classicMusicModel)

            //Async
    {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(classicMusicModel);
                }
            });
    }

    public ArrayList<MusicModel> getClassicMusic()
    {
        ArrayList<MusicModel> classicMusicModelArrayList = new ArrayList<>();
        RealmResults<MusicModel> realmResults = realm.where(MusicModel.class).findAll();

        for(MusicModel classicMusicModel: realmResults){
            classicMusicModelArrayList.add(classicMusicModel);
        }
        return classicMusicModelArrayList;
    }

}
