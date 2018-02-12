package com.example.tae.assignment2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tae.assignment2.classic_music.ClassicMusicFragment;
import com.example.tae.assignment2.pop_music.PopMusicFragment;
import com.example.tae.assignment2.rock_music.RockMusicFragment;
import com.example.tae.assignment2.service.IRequestInterface;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private static FragmentManager fragmentManager;
    private IRequestInterface iRequestInterface;
    RecyclerView recyclerView;
    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        //default fragment to load
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
