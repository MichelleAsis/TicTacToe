
//This class is for the Multiplayer of Tic Tac Toe

package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.tictactoe.databinding.FragmentTicBinding;

import java.util.ArrayList;
import java.util.List;

public class MultiTicTac extends AppCompatActivity {

    FragmentTicBinding binding;

    private Intent musicServiceIntent;

    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0,0,0,0,0,0,0,0,0}; //9 zero
    private int playerTurn = 1;
    private int totalSelectedBoxes = 1;

    ImageButton backbutton;
    ImageButton settingsbutton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentTicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        musicServiceIntent = new Intent(this, BackgroundMusicManager.class);
        startService(musicServiceIntent);

        settingsbutton = findViewById(R.id.settingsbutton);


        backbutton = findViewById(R.id.backbutton);

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


        // Initialize winning combinations

        combinationList.add(new int[] {0,1,2});
        combinationList.add(new int[] {3,4,5});
        combinationList.add(new int[] {6,7,8});
        combinationList.add(new int[] {0,3,6});
        combinationList.add(new int[] {1,4,7});
        combinationList.add(new int[] {2,5,8});
        combinationList.add(new int[] {2,4,6});
        combinationList.add(new int[] {0,4,8});

        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");

        binding.playerOneName.setText(getPlayerOneName);
        binding.playerTwoName.setText(getPlayerTwoName);


        // Similar onClickListeners for other image views (image2 to image9)

        binding.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(0)){
                    performAction((ImageView) view, 0);
                }
            }
        });

        binding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(1)){
                    performAction((ImageView) view, 1);
                }
            }
        });
        binding.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(2)){
                    performAction((ImageView) view, 2);
                }
            }
        });
        binding.image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(3)){
                    performAction((ImageView) view, 3);
                }
            }
        });
        binding.image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(4)){
                    performAction((ImageView) view, 4);
                }
            }
        });
        binding.image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(5)){
                    performAction((ImageView) view, 5);
                }
            }
        });
        binding.image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(6)){
                    performAction((ImageView) view, 6);
                }
            }
        });
        binding.image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(7)){
                    performAction((ImageView) view, 7);
                }
            }
        });
        binding.image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSelectable(8)){
                    performAction((ImageView) view, 8);
                }
            }
        });

    }


    // Method to perform action when a box is clicked

    private void performAction(ImageView  imageView, int selectedBoxPosition) {
        boxPositions[selectedBoxPosition] = playerTurn;

        // Set image based on current player's turn
        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.diamond);
            if (checkResults()) {
                // Show result dialog indicating Player 1 is the winner
                ResultDialog resultDialog = new ResultDialog(MultiTicTac.this, binding.playerOneName.getText().toString()
                        + " is a Winner!", MultiTicTac.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else if(totalSelectedBoxes == 9) {
                // Show result dialog indicating a draw
                ResultDialog resultDialog = new ResultDialog(MultiTicTac.this, "Match Draw", MultiTicTac.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(2); // Change the turn to Player 2
                totalSelectedBoxes++; // Increment the count of selected boxes
            }
        } else {
            imageView.setImageResource(R.drawable.heart);
            if (checkResults()) { // Check if there is a winner after Player 2's move
                // Show result dialog indicating Player 2 is the winner
                ResultDialog resultDialog = new ResultDialog(MultiTicTac.this, binding.playerTwoName.getText().toString()
                        + " is a Winner!", MultiTicTac.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else if(totalSelectedBoxes == 9) {
                ResultDialog resultDialog = new ResultDialog(MultiTicTac.this, "Match Draw", MultiTicTac.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(1);
                totalSelectedBoxes++;
            }
        }

    }


    // Method to change player turn
    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            binding.playerOneLayout.setBackgroundResource(R.drawable.whiteborder);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.boxes);
        } else {
            binding.playerTwoLayout.setBackgroundResource(R.drawable.whiteborder);
            binding.playerOneLayout.setBackgroundResource(R.drawable.boxes);
        }

    }



    // Method to check game results (win/draw)
    private boolean checkResults(){
        boolean response = false;
        for (int i = 0; i < combinationList.size(); i++){
            final int[] combination = combinationList.get(i);

            if (boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {
                response = true;
            }
        }
        return response;
    }


    // Method to check if a box is selectable
    private boolean isBoxSelectable(int boxPosition) {
        boolean response = boxPositions[boxPosition] == 0;
        return response;
    }


    // Method to restart the match

    public void restartMatch(){

        // Reset box positions, player turn, and total selected boxes count
        boxPositions = new int[] {0,0,0,0,0,0,0,0,0}; //9 zero
        playerTurn = 1;
        totalSelectedBoxes = 1;


        // Reset image views to default state

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

    private void showCustomPopup() {
        // Create and configure the custom popup dialog
        dialogfragment customDialog = new dialogfragment();
        customDialog.show(getSupportFragmentManager(), "dialogfragment");
    }

}