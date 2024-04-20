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

public class AddPlayers extends AppCompatActivity {

    private Intent musicServiceIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);


        musicServiceIntent = new Intent(this, BackgroundMusicManager.class);
        startService(musicServiceIntent);

        EditText playerOne = findViewById(R.id.playerOne);
        EditText playerTwo = findViewById(R.id.playerTwo);
        Button startGameButton = findViewById(R.id.startGameButton);
        ImageButton backButton = findViewById(R.id.backbutton); // Add this line to find the back button

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Call onBackPressed when back button is clicked
            }
        });

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getPlayerOneName = playerOne.getText().toString();
                String getPlayerTwoName = playerTwo.getText().toString();

                if (getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()) {
                    Toast.makeText(AddPlayers.this, "Please enter player name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(AddPlayers.this, MultiTicTac.class);
                    intent.putExtra("playerOne", getPlayerOneName);
                    intent.putExtra("playerTwo", getPlayerTwoName);
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
        // Navigate back to the previous fragment using FragmentManager
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
