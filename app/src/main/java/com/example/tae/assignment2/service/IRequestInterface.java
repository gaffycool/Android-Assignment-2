package com.example.tae.assignment2.service;

import com.example.tae.assignment2.classic_music.model.ClassicMusic;
import com.example.tae.assignment2.pop_music.model.PopMusic;
import com.example.tae.assignment2.rock_music.model.RockMusic;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by TAE on 09-Feb-18.
 */

public interface IRequestInterface {

    @GET(ApiList.ROCK_API)
    Observable<RockMusic> getRockMusic();

    @GET(ApiList.CLASSIC_API)
    Observable<ClassicMusic> getClassicMusic();

    @GET(ApiList.POP_API)
    Observable<PopMusic> getPopMusic();


}
