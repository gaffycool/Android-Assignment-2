package com.example.tae.assignment2.data.network;

import com.example.tae.assignment2.data.network.model.ClassicMusic;

import io.reactivex.Observable;

/**
 * Created by TAE on 13-Feb-18.
 */

public class AppDataManager implements DataManager{

    private IApiHelper iApiHelper;


    public AppDataManager() {
        iApiHelper = new AppApiHelper();
    }

    @Override
    public Observable<ClassicMusic> getClassicMusic() {
        return iApiHelper.getClassicMusic();
    }
}
