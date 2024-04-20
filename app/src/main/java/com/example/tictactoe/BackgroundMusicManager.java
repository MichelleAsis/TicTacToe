package com.example.tictactoe;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusicManager extends Service {

    private MediaPlayer mediaPlayer; // MediaPlayer object for managing background music

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Create a MediaPlayer instance with the specified audio resource
        mediaPlayer = MediaPlayer.create(this, R.raw.dino);
        // Set looping to true to play the audio in a loop
        mediaPlayer.setLooping(true);
        // Set the volume for both left and right channels (range 0.0 to 1.0)
        mediaPlayer.setVolume(0.5f, 0.5f);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start playing the background music
        mediaPlayer.start();
        // Return START_STICKY to let the system know to restart the service if it gets terminated
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the playback and release the MediaPlayer resources when the service is destroyed
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // Pause the music when the app is removed from recent tasks
        mediaPlayer.pause();
        super.onTaskRemoved(rootIntent);
    }
}
