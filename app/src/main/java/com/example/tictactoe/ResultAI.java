package com.example.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ResultAI extends Dialog {

    private final String message;
    private final Activity activity;

    ImageButton backbutton;

    public ResultAI(@NonNull Context context, String message, Activity activity) {
        super(context);
        this.message = message;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dialog);

        TextView messageText = findViewById(R.id.messageText);
        Button startAgainButton = findViewById(R.id.startAgainButton);
        backbutton = findViewById(R.id.backbutton);

        messageText.setText(message);

        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AiTicTac) activity).restartMatch(); // Cast activity to MultiTicTac and call restartMatch()
                dismiss();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call onBackPressed when back button is clicked
                activity.onBackPressed();
                dismiss();
            }
        });


    }
}
