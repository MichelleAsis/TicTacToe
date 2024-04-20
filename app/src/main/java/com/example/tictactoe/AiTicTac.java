package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoe.databinding.FragmentticAiBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AiTicTac extends AppCompatActivity {

    FragmentticAiBinding binding; // View binding for the activity

    private static final int AI_DELAY_MS = 1000; // Set the delay time in milliseconds

    private final List<int[]> combinationList = new ArrayList<>(); // List to store winning combinations
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0}; //9 zero // Array to represent the state of each box
    private int humanPlayer = 1; // Human player's identifier
    private int aiPlayer = 2;// AI player's identifier
    private int totalSelectedBoxes = 0; //Counter
    private Intent musicServiceIntent; // Intent for background music service
    private boolean aiTurnInProgress = false; // Flag to indicate AI turn in progress

    ImageButton backbutton;
    ImageButton settingsbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentticAiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize views
        settingsbutton = findViewById(R.id.settingsbutton);


        backbutton = findViewById(R.id.backbutton);
        // Set click listener for back button
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Call onBackPressed when back button is clicked
            }
        });

        settingsbutton.setOnClickListener(v -> {
            // Show custom popup
            showCustomPopup();
        });

        // Initialize combinationList
        initializeCombinationList();

        musicServiceIntent = new Intent(this, BackgroundMusicManager.class);
        startService(musicServiceIntent);
        // Initialize background music


        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        binding.playerOneName.setText(getPlayerOneName);

        // Set onClickListeners for image views
        setOnClickListeners();

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




    // Method to initialize the list of winning combinations
    private void initializeCombinationList() {
        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{0, 4, 8});
    }

    private void setOnClickListeners() {
        // Set onClickListeners for image views
        binding.image1.setOnClickListener(getImageViewClickListener(0));
        binding.image2.setOnClickListener(getImageViewClickListener(1));
        binding.image3.setOnClickListener(getImageViewClickListener(2));
        binding.image4.setOnClickListener(getImageViewClickListener(3));
        binding.image5.setOnClickListener(getImageViewClickListener(4));
        binding.image6.setOnClickListener(getImageViewClickListener(5));
        binding.image7.setOnClickListener(getImageViewClickListener(6));
        binding.image8.setOnClickListener(getImageViewClickListener(7));
        binding.image9.setOnClickListener(getImageViewClickListener(8));
    }

    private View.OnClickListener getImageViewClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the box is selectable and perform actions accordingly
                if (isBoxSelectable(position)) {
                    performHumanAction((ImageView) view, position);
                    if (!checkResults()) {
                        performAIAction();
                    }
                }
            }
        };
    }


    // Method to perform human player's action
    private void performHumanAction(ImageView imageView, int selectedBoxPosition) {
        boxPositions[selectedBoxPosition] = humanPlayer;
        imageView.setImageResource(R.drawable.diamond);
        totalSelectedBoxes++;
    }


    // Method to perform AI player's action
    private void performAIAction() {
        aiTurnInProgress = true; // Set flag indicating AI turn is in progress
        disableAllClickListeners(); // Disable click listeners for all image views
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int aiMove = getAIMove();
                ImageView aiImageView = getImageViewByPosition(aiMove);
                aiImageView.setImageResource(R.drawable.heart);
                boxPositions[aiMove] = aiPlayer;
                totalSelectedBoxes++;
                checkResults();
                aiTurnInProgress = false; // Reset flag indicating AI turn is completed
                enableAllClickListeners(); // Re-enable click listeners for all image views
            }
        }, AI_DELAY_MS);
    }


    // Method to disable click listeners for all image views
    private void disableAllClickListeners() {
        binding.image1.setOnClickListener(null);
        binding.image2.setOnClickListener(null);
        binding.image3.setOnClickListener(null);
        binding.image4.setOnClickListener(null);
        binding.image5.setOnClickListener(null);
        binding.image6.setOnClickListener(null);
        binding.image7.setOnClickListener(null);
        binding.image8.setOnClickListener(null);
        binding.image9.setOnClickListener(null);
    }


    // Method to enable click listeners for all image views
    private void enableAllClickListeners() {
        binding.image1.setOnClickListener(getImageViewClickListener(0));
        binding.image2.setOnClickListener(getImageViewClickListener(1));
        binding.image3.setOnClickListener(getImageViewClickListener(2));
        binding.image4.setOnClickListener(getImageViewClickListener(3));
        binding.image5.setOnClickListener(getImageViewClickListener(4));
        binding.image6.setOnClickListener(getImageViewClickListener(5));
        binding.image7.setOnClickListener(getImageViewClickListener(6));
        binding.image8.setOnClickListener(getImageViewClickListener(7));
        binding.image9.setOnClickListener(getImageViewClickListener(8));
    }
    private int getAIMove() {
        // Implement AI logic to determine the best move
        Random random = new Random();
        int aiMove;
        do {
            aiMove = random.nextInt(9); // Generate a random move
        } while (!isBoxSelectable(aiMove));
        return aiMove;
    }

    private boolean checkResults() {
        // Check for a winner
        for (int[] combination : combinationList) {
            if (boxPositions[combination[0]] == humanPlayer &&
                    boxPositions[combination[1]] == humanPlayer &&
                    boxPositions[combination[2]] == humanPlayer) {
                showResultDialog(binding.playerOneName.getText().toString() + " is a Winner!");
                return true;
            } else if (boxPositions[combination[0]] == aiPlayer &&
                    boxPositions[combination[1]] == aiPlayer &&
                    boxPositions[combination[2]] == aiPlayer) {
                showResultDialog("AI is a Winner!");
                return true;
            }
        }

        // Check for draw
        if (totalSelectedBoxes == 9) {
            showResultDialog("Match Draw");
            return true;
        }

        return false;
    }

    private void showResultDialog(String message) {
        ResultAI dialog = new ResultAI(this, message, this);
        dialog.setCancelable(false);
        dialog.show();
    }

    private boolean isBoxSelectable(int boxPosition) {
        return boxPositions[boxPosition] == 0;
    }

    public void restartMatch() {
        for (int i = 0; i < boxPositions.length; i++) {
            boxPositions[i] = 0;
        }
        totalSelectedBoxes = 0;

        // Reset imageViews to default state
        binding.image1.setImageResource(R.drawable.transparent_bg);
        binding.image2.setImageResource(R.drawable.transparent_bg);
        binding.image3.setImageResource(R.drawable.transparent_bg);
        binding.image4.setImageResource(R.drawable.transparent_bg);
        binding.image5.setImageResource(R.drawable.transparent_bg);
        binding.image6.setImageResource(R.drawable.transparent_bg);
        binding.image7.setImageResource(R.drawable.transparent_bg);
        binding.image8.setImageResource(R.drawable.transparent_bg);
        binding.image9.setImageResource(R.drawable.transparent_bg);
    }


    // Method to get ImageView by position
    private ImageView getImageViewByPosition(int position) {
        switch (position) {
            case 0:
                return binding.image1;
            case 1:
                return binding.image2;
            case 2:
                return binding.image3;
            case 3:
                return binding.image4;
            case 4:
                return binding.image5;
            case 5:
                return binding.image6;
            case 6:
                return binding.image7;
            case 7:
                return binding.image8;
            case 8:
                return binding.image9;
            default:
                return null;
        }
    }

    // Method to show custom settings popup
    private void showCustomPopup() {
        // Create and configure the custom popup dialog
        dialogfragment customDialog = new dialogfragment();
        customDialog.show(getSupportFragmentManager(), "dialogfragment");
    }
}
