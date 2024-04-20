package com.example.tictactoe;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class dialogfragment extends DialogFragment {

    private SeekBar volumeSeekBar;
    private AudioManager audioManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.popup, container, false);

        // Initialize AudioManager
        audioManager = (AudioManager) requireActivity().getSystemService(requireActivity().AUDIO_SERVICE);

        // Find views
        volumeSeekBar = view.findViewById(R.id.volumeSeekBar);
        Button closeButton = view.findViewById(R.id.closeButton);

        // Set progress change listener for volume SeekBar
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Adjust volume based on SeekBar progress
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });

        // Close button click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Dismiss the dialog fragment
            }
        });

        return view;
    }
}
