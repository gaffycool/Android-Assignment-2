package com.example.tae.assignment2.data.network;

import com.example.tae.assignment2.data.network.model.ClassicMusic;
import com.example.tae.assignment2.data.network.service.IRequestInterface;
import com.example.tae.assignment2.data.network.service.ServiceConnection;

import io.reactivex.Observable;

/**
 * Created by TAE on 13-Feb-18.
 */

public class AppApiHelper implements IApiHelper{

    private IRequestInterface iRequestInterface;


    public AppApiHelper() {
        iRequestInterface = ServiceConnection.getConnection();
    }


    @Override
    public Observable<ClassicMusic> getClassicMusic() {
        return iRequestInterface.getClassicMusic();
    }
}
