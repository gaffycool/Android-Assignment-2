package com.example.tae.assignment2.classic_music.controller;

import com.example.tae.assignment2.classic_music.model.RealmModel.ClassicMusicModel;

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

    public void saveClassicMusic(final ClassicMusicModel classicMusicModel)

            //Async
    {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(classicMusicModel);
                }
            });
    }

    public ArrayList<ClassicMusicModel> getClassicMusic()
    {
        ArrayList<ClassicMusicModel> classicMusicModelArrayList = new ArrayList<>();
        RealmResults<ClassicMusicModel> realmResults = realm.where(ClassicMusicModel.class).findAll();

        for(ClassicMusicModel classicMusicModel: realmResults){
            classicMusicModelArrayList.add(classicMusicModel);
        }
        return classicMusicModelArrayList;
    }

}
