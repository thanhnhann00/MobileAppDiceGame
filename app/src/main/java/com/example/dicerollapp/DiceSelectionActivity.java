package com.example.dicerollapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class DiceSelectionActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_selection);

        // Previous button click listener
        ImageButton buttonPrevious = findViewById(R.id.button_previous);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiceSelectionActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();  // Close the current activity
            }
        });
    }
}
