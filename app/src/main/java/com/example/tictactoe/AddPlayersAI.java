package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class AddPlayersAI extends AppCompatActivity {

    private Intent musicServiceIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplayerai);
        musicServiceIntent = new Intent(this, BackgroundMusicManager.class);
        startService(musicServiceIntent);

        EditText playerOne = findViewById(R.id.playerOne);
        Button startGameButton = findViewById(R.id.startGameButton);
        ImageButton backButton = findViewById(R.id.backbutton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getPlayerOneName = playerOne.getText().toString();

                if (getPlayerOneName.isEmpty()) {
                    Toast.makeText(AddPlayersAI.this, "Please enter player name", Toast.LENGTH_SHORT).show();
                } else {
                    // Default name for player two is "AI"
                    String playerTwoName = "AI";

                    Intent intent = new Intent(AddPlayersAI.this, AiTicTac.class);
                    intent.putExtra("playerOne", getPlayerOneName);
                    intent.putExtra("playerTwo", playerTwoName);
                    startActivity(intent);
                }
            }
        });
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
