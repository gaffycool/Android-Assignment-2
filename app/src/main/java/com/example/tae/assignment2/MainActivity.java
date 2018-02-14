package com.example.tae.assignment2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.tae.assignment2.classic_music.ClassicMusicFragment;
import com.example.tae.assignment2.pop_music.PopMusicFragment;
import com.example.tae.assignment2.realm_database.controller.RealmHelper;
import com.example.tae.assignment2.realm_database.model.MusicModel;
import com.example.tae.assignment2.rock_music.RockMusicFragment;
import com.example.tae.assignment2.data.network.service.IRequestInterface;

import java.util.ArrayList;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private static Button button_sbm;


    private static FragmentManager fragmentManager;
    private IRequestInterface iRequestInterface;
    RecyclerView recyclerView;
    Bundle savedInstanceState;
    private Realm realm;
    private static RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        initRealm();
        //default fragment to load
        //onButtonClickListener();
        rockMusicFragment();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_rock:
                    rockMusicFragment();
                    return true;

                case R.id.navigation_classic:
                    classicMusicFragment();
                    return true;

                case R.id.navigation_pop:
                    popMusicFragment();
                    return true;
            }
            return false;
        }
    };

    private void initRealm(){
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
    }
    public static ArrayList<MusicModel> getRealmDatabase(){
        Log.i("realm db", String.valueOf(realmHelper.getMusicModel().size()));
        return realmHelper.getMusicModel();
    }

    //play rock music fragment that is being called from the RockMusicAdapter
    //passes the preview url from adapter to mainactivty
    public static void playMusic(String id, String name, String collection, String artWork) {

        PlayMusicFragment playMusicFragment = new PlayMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("preview_url", id);
        bundle.putString("name", name);
        bundle.putString("collection", collection);
        bundle.putString("artwork", artWork);

         playMusicFragment.setArguments(bundle);
         fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, playMusicFragment)
           .addToBackStack(null)
           .commit();
    }

    public static void deleteRealmDatabase(){
        realmHelper.deleteRealmData();
    }

    public static void saveRealm (String artistName, String collectionName, String artworkUrl60, String trackPrice){
        MusicModel classicMusicModel = new MusicModel(artistName, collectionName, artworkUrl60, trackPrice);
        realmHelper.storeData(classicMusicModel);
        Log.i("realm database", String.valueOf(realmHelper.getMusicModel().size()));
    }

    public void rockMusicFragment() {
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, new RockMusicFragment())
                    .disallowAddToBackStack()
                    .commit();
        }
    }

    public void classicMusicFragment() {
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, new ClassicMusicFragment())
                    .disallowAddToBackStack()
                    .commit();
        }
    }

    public void popMusicFragment() {
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, new PopMusicFragment())
                    .disallowAddToBackStack()
                    .commit();
        }
    }

}
