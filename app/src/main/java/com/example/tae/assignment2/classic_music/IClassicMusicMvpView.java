package com.example.tae.assignment2.classic_music;

import com.example.tae.assignment2.data.network.model.ClassicMusic;
import com.example.tae.assignment2.ui.base.MvpView;

/**
 * Created by TAE on 13-Feb-18.
 */

public interface IClassicMusicMvpView extends MvpView{

    void onFetchDataProgress();
    void onFetchDataSuccess(ClassicMusic classicMusic);
    void onFetchDataError(String error);
}
