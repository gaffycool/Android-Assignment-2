package com.example.tae.assignment2.pop_music;


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

import com.example.tae.assignment2.R;
import com.example.tae.assignment2.pop_music.adapter.PopMusicAdapter;
import com.example.tae.assignment2.pop_music.model.PopMusic;
import com.example.tae.assignment2.service.IRequestInterface;
import com.example.tae.assignment2.service.ServiceConnection;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopMusicFragment extends Fragment {

    public IRequestInterface iRequestInterface;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private CompositeDisposable compositeDisposable;

    public PopMusicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pop_music, container, false);
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
                            displayPopMusic();
                        } else {
                            Toast.makeText(getActivity(), "no network avaialable", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    public void displayPopMusic()
    {
        //  compositeDisposable.add(
        iRequestInterface.getPopMusic()

                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PopMusic>() {
                               @Override
                               public void accept(PopMusic popMusic) throws Exception {


                                   recyclerView.setAdapter(new PopMusicAdapter(getActivity().getApplicationContext(), this, popMusic.getResults(), R.layout.row));
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
