package com.example.tae.assignment2;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tae.assignment2.data.network.service.IRequestInterface;
import com.example.tae.assignment2.data.network.service.ServiceConnection;
import com.example.tae.assignment2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayMusicFragment extends Fragment {


    public IRequestInterface iRequestInterface;
    private RecyclerView recyclerView;
    private TextView tPreviewUrl, tName, tCollection;
    private ImageView tImage, pausedButton;
    private String url;

    public PlayMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_music, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iRequestInterface = ServiceConnection.getConnection();
        //playMusic();
        tName = view.findViewById(R.id.mArtistName);
        tImage = view.findViewById(R.id.mArtwork);
        tCollection = view.findViewById(R.id.mCollectionName);
        pausedButton = view.findViewById(R.id.btnPause);

        if (NetworkUtils.isNetworkConnected((getActivity()))) {

            String name = getArguments().getString("name");
            String artwork = getArguments().getString("artwork");
            String collection = getArguments().getString("collection");
            url = getArguments().getString("preview_url");

            tName.setText(name.toString());
            tCollection.setText(collection.toString());

            Context context = tImage.getContext();
            Picasso.with(context)
                    .load(artwork)
                    .resize(100, 100)
                    .centerCrop()
                    .into(tImage);

            // PLAY MUSIC
            playMusic();
        }

        else {
            Toast.makeText(getActivity(), "no network avaialable", Toast.LENGTH_LONG).show();
        }

    }

    public void playMusic() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        //mediaPlayer.start();


        pausedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                //Toast.makeText(getActivity(), "Playing in background: " + tName.toString(), Toast.LENGTH_LONG).show();

            }

        });
        // mediaPlayer.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.stop();
    }
}
