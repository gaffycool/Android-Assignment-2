package com.example.tae.assignment2.classic_music;

import com.example.tae.assignment2.ui.base.MvpPresenter;

/**
 * Created by TAE on 13-Feb-18.
 */

public interface IClassicMusicPresenter <V extends IClassicMusicMvpView> extends MvpPresenter<V>{

    void loadMusicList();
}
