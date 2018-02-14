package com.example.tae.assignment2.classic_music;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tae.assignment2.MainActivity;
import com.example.tae.assignment2.R;
import com.example.tae.assignment2.classic_music.adapter.ClassicMusicAdapter;
import com.example.tae.assignment2.classic_music.adapter.ClassicMusicRealmAdapter;
import com.example.tae.assignment2.data.network.AppDataManager;
import com.example.tae.assignment2.data.network.model.ClassicMusic;
import com.example.tae.assignment2.data.network.model.Result;
import com.example.tae.assignment2.data.network.service.IRequestInterface;
import com.example.tae.assignment2.data.network.service.ServiceConnection;
import com.example.tae.assignment2.ui.base.BaseFragment;
import com.example.tae.assignment2.ui.base.BaseViewHolder;
import com.example.tae.assignment2.ui.utils.rx.AppSchedulerProvider;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassicMusicFragment extends BaseFragment implements IClassicMusicMvpView {

    private ClassicMusicPresenterImpl<ClassicMusicFragment> classicMusicClassicMusicPresenter;
   // public IRequestInterface iRequestInterface;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
   // private CompositeDisposable compositeDisposable;
    //private ClassicMusicRealmAdapter classicMusicRealmAdapter;

    public ClassicMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classic_music, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // iRequestInterface = ServiceConnection.getConnection();
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout = view.findViewById(R.id.swiperefresh);


        //initializing presenter class objects
        classicMusicClassicMusicPresenter = new ClassicMusicPresenterImpl<>
                (new AppDataManager(), new AppSchedulerProvider(),
                        new CompositeDisposable());
        classicMusicClassicMusicPresenter.onAttach(this);

        callService();

      //  compositeDisposable = new CompositeDisposable();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callService();
            }
        });
    }

    public void callService()
    {
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) throws Exception {
                        if (isConnectedToInternet)
                        {
                            //get data
                            //displayClassicMusic();
                            classicMusicClassicMusicPresenter.loadMusicList();
                            MainActivity.deleteRealmDatabase();
                            //DatabaseResults(classicMusic.getResults());
                        }
                        else
                        {
                            AlertNetwork();

                            recyclerView.setAdapter(new ClassicMusicRealmAdapter(getActivity(), MainActivity.getRealmDatabase(), R.layout.row));

                            //if there is no internet connection then it will get from the realm backup
                            //get data from realm backup
                            //displayClassicMusicBackup();

                        }
                    }
                });
    }

    public void AlertNetwork()
    {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(getActivity());
        a_builder.setMessage("There is no network connected.. Please make sure you are connected to the internet")
                .setCancelable(false)
                .setPositiveButton("Close the App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                }).setNegativeButton("Continue using the App", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                //displayClassicMusic();

            }
        });

        AlertDialog alert = a_builder.create();
        alert.setTitle("Connection status");
        alert.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void DatabaseResults(List<Result> classicMusicResult) {
        for (Result classicMusic : classicMusicResult) {
            MainActivity.saveRealm(
                    classicMusic.getCollectionName(),
                    classicMusic.getArtistName(),
                    classicMusic.getArtworkUrl60(),
                    classicMusic.getTrackPrice().toString()
            );
        }
    }


    @Override
    public void onFetchDataProgress() {

        showLoading();
    }

    @Override
    public void onFetchDataSuccess(ClassicMusic classicMusic) {

       recyclerView.setAdapter(new ClassicMusicAdapter(getActivity(), classicMusic.getResults(), R.layout.row));
        Toast.makeText(getActivity(), "MOVIE" + classicMusic.getResults().get(0).getArtistName(), Toast.LENGTH_LONG).show();

        refreshLayout.setRefreshing(false);
        hideLoading();
    }

    @Override
    public void onFetchDataError(String error) {

    }
}
