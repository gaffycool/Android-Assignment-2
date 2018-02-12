package com.example.tae.assignment2.classic_music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tae.assignment2.MainActivity;
import com.example.tae.assignment2.R;
import com.example.tae.assignment2.classic_music.controller.RealmBackupRestore;
import com.example.tae.assignment2.classic_music.controller.RealmHelper;
import com.example.tae.assignment2.classic_music.model.ClassicMusic;
import com.example.tae.assignment2.classic_music.model.RealmModel.ClassicMusicModel;
import com.example.tae.assignment2.classic_music.model.Result;
import com.example.tae.assignment2.rock_music.adapter.RockMusicAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by TAE on 11-Feb-18.
 */

public class ClassicMusicAdapter extends RecyclerView.Adapter<ClassicMusicAdapter.MyViewHolder> {

    private Context applicationContext;
    private int row;
    private List<Result> results;
    private Consumer<ClassicMusic> consumer;
    Realm realm;
    RealmHelper realmHelper;
    RealmBackupRestore realmBackupRestore;


    public ClassicMusicAdapter(Context applicationContext, Consumer<ClassicMusic> consumer, List<Result> results, int row) {
        this.applicationContext = applicationContext;
        this.row = row;
        this.results = results;
        this.consumer = consumer;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        initRealm();
        return new ClassicMusicAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(row, parent, false));
    }

    public void initRealm() {
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper((realm));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mArtistName.setText(results.get(position).getArtistName());
        holder.mTrackPrice.setText(results.get(position).getTrackPrice().toString() + " " + results.get(position).getCurrency());
        holder.mCollectionName.setText(results.get(position).getCollectionName());

        Context context = holder.mArtwork.getContext();
        Picasso.with(context)
                .load(results.get(position).getArtworkUrl60())
                .resize(100, 100)
                .centerCrop()
                .into(holder.mArtwork);

        //save this data to realm
        realm = Realm.getDefaultInstance();
        String mName = results.get(position).getTrackPrice().toString();
        String mTrackPrice = results.get(position).getArtistName();
        String mCollectionName = results.get(position).getCollectionName();
        String mPreviewURL = results.get(position).getPreviewUrl();
        String mCurrency = results.get(position).getCurrency();
        String mArtwork = results.get(position).getArtworkUrl60();


        ClassicMusicModel classicMusicModel = new ClassicMusicModel(
                mName, mCollectionName, mArtwork, mPreviewURL, mCurrency, mTrackPrice);

        realm.init(applicationContext);
        realmHelper.saveClassicMusic(classicMusicModel);
        //realmBackupRestore.backup();

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mCollectionName, mArtistName, mTrackPrice;
        private ImageView mArtwork;


        public MyViewHolder(View itemView) {
            super(itemView);
            mArtistName = itemView.findViewById(R.id.mArtistName);
            mCollectionName = itemView.findViewById(R.id.mCollectionName);
            mTrackPrice = itemView.findViewById(R.id.mTrackPrice);
            mArtwork = itemView.findViewById(R.id.mArtwork);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String id = results.get(pos).getPreviewUrl();
                    String name = results.get(pos).getArtistName();
                    String collection = results.get(pos).getCollectionName();
                    String artWork = results.get(pos).getArtworkUrl60();
                    //GET IN BUNDLE ETC

                    //check if item still exits
                    if(pos != RecyclerView.NO_POSITION)
                    {
                         Result clickedDataItem = results.get(pos);
                        //code to play music
                        MainActivity.playMusic(id, name, collection, artWork);
                    }
                }
            });
        }
    }
}