package com.example.tae.assignment2.rock_music;


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

import com.example.tae.assignment2.R;
import com.example.tae.assignment2.rock_music.adapter.RockMusicAdapter;
import com.example.tae.assignment2.rock_music.model.RockMusic;
import com.example.tae.assignment2.data.network.service.IRequestInterface;
import com.example.tae.assignment2.data.network.service.ServiceConnection;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RockMusicFragment extends Fragment {

    public IRequestInterface iRequestInterface;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private CompositeDisposable compositeDisposable;

    public RockMusicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rock_music, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iRequestInterface = ServiceConnection.getConnection();
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = view.findViewById(R.id.swiperefresh);

        callService();
        compositeDisposable = new CompositeDisposable();


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callService();
            }
        });

    }




    public void callService() {
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) throws Exception {
                        if (isConnectedToInternet) {
                            displayRockMusic();
                        } else {
                            AlertNetwork();
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
                displayRockMusic();

            }
        });

        AlertDialog alert = a_builder.create();
        alert.setTitle("Connection status");
        alert.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    public void displayRockMusic()
    {
      //  compositeDisposable.add(
        iRequestInterface.getRockMusic()

                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RockMusic>() {
                               @Override
                               public void accept(RockMusic rockMusic) throws Exception {


                                   recyclerView.setAdapter(new RockMusicAdapter(getActivity().getApplicationContext(), this, rockMusic.getResults(), R.layout.row));
                                    refreshLayout.setRefreshing(false);
                                   //int position = getArguments().getInt("position", -1);



                                //   Toast.makeText(getActivity(), "movie" + topRatedMovies.getResults().get(0).getTitle(), Toast.LENGTH_LONG).show();

                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                                refreshLayout.setRefreshing(false);
                            }
                        });
    }

}
