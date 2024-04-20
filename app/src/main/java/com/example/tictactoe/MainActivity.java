//Tic Tac Toe
/*
Members:
1. Michelle O. Asis - Implemented the AI and Multiplayer Logic of Tic tac toe/ DESIGN
2. Rhealyn Borjal - Background Music Implementation/ DESIGN
3. Krizel Bonos - Result Dialog Implementation/ DESIGN
*/



package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private Intent musicServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicServiceIntent = new Intent(this, BackgroundMusicManager.class);
        startService(musicServiceIntent);

        FragmentManager fManager = getSupportFragmentManager();
        Fragment frag = fManager.findFragmentById(R.id.fragmentHolder);

        if (frag == null) {
            frag = new menufrag();
            fManager.beginTransaction()
                    .add(R.id.fragmentHolder, frag)
                    .commit();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        // Resume playing the music when the activity is resumed
        startService(musicServiceIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the music when the activity is paused
        stopService(musicServiceIntent);
    }




}
