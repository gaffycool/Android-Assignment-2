package com.example.tae.assignment2.classic_music;

import com.example.tae.assignment2.data.network.DataManager;
import com.example.tae.assignment2.data.network.model.ClassicMusic;
import com.example.tae.assignment2.ui.base.BasePresenter;
import com.example.tae.assignment2.ui.base.MvpPresenter;
import com.example.tae.assignment2.ui.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by TAE on 13-Feb-18.
 */

public class ClassicMusicPresenterImpl <V extends IClassicMusicMvpView>
extends BasePresenter<V>
implements IClassicMusicPresenter<V>{


    public ClassicMusicPresenterImpl(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadMusicList() {

        getDataManager().getClassicMusic()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ClassicMusic>() {
                               @Override
                               public void accept(ClassicMusic classicMusic) throws Exception {

                                   getMvpView().onFetchDataSuccess(classicMusic);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {

                               }
                           }
                );

    }
}
