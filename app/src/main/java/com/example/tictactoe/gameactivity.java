package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class gameactivity extends Fragment {


        ImageButton backbutton;
        ImageButton settingsbutton;

        ImageButton multibutton;
        ImageButton aibutton;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_game, container, false);
            backbutton = view.findViewById(R.id.backbutton);
            settingsbutton = view.findViewById(R.id.settingsbutton);
            multibutton = view.findViewById(R.id.multibutton);
            aibutton = view.findViewById(R.id.aibutton);

            aibutton.setOnClickListener(v -> {
                // Navigate to MultiTicTac activity
                Intent intent = new Intent(getActivity(), AddPlayersAI.class);
                startActivity(intent);
            });

            multibutton.setOnClickListener(v -> {
                // Navigate to MultiTicTac activity
                Intent intent = new Intent(getActivity(), AddPlayers.class);
                startActivity(intent);
            });


            backbutton.setOnClickListener(v -> {
                // Navigate back to menufrag
                navigateToMenuFragment();
            });

            settingsbutton.setOnClickListener(v -> {
                // Show custom popup
                showCustomPopup();
            });

            return view;
        }

        private void navigateToMenuFragment() {
            // Get the FragmentManager
            FragmentManager fragmentManager = getParentFragmentManager(); // or getActivity().getSupportFragmentManager() if you're using AppCompatActivity

            // Create an instance of menufrag
            menufrag menuFragment = new menufrag();

            // Replace the current fragment with menuFragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentHolder, menuFragment)
                    .commit();
        }

        private void showCustomPopup() {
            // Create and configure the custom popup dialog
            dialogfragment customDialog = new dialogfragment();
            customDialog.show(getChildFragmentManager(), "dialogfragment");
        }
    }
