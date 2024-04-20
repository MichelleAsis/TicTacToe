package com.example.tictactoe;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class menufrag extends Fragment {

    ImageButton playButton;
    MediaPlayer mediaPlayer;
    ImageButton settingsbutton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        settingsbutton = view.findViewById(R.id.settingsbutt);


        settingsbutton.setOnClickListener(v -> {
            // Show custom popup
            showCustomPopup();
        });


        playButton = view.findViewById(R.id.playbutton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                Fragment gameFragment = new gameactivity();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentHolder, gameFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });



        return view;




    }
    private void showCustomPopup() {
        // Create and configure the custom popup dialog
        dialogfragment customDialog = new dialogfragment();
        customDialog.show(getChildFragmentManager(), "dialogfragment");
    }




}
