package com.example.dicerollapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dicerollapp.UI_elements.Activities.DiceSelectionActivity;
import com.example.dicerollapp.UI_elements.Activities.MiniGameActivity;
import com.example.dicerollapp.UI_elements.Activities.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenuActivity extends AppCompatActivity {
    private Button buttonDiceSelect;
    private Button buttonMiniGame;
    private Button buttonSettings;
    private Button buttonDefaultDices;
    private Button logout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buttonDiceSelect = findViewById(R.id.buttonSelect);
        buttonDiceSelect.setBackgroundColor(Color.parseColor("#010b13"));

        buttonMiniGame = findViewById(R.id.buttonMiniGame);
        buttonMiniGame.setBackgroundColor(Color.parseColor("#010b13"));

        buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setBackgroundColor(Color.parseColor("#010b13"));

        buttonDiceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, DiceSelectionActivity.class);
                startActivity(intent);
                //Close the current activity
                finish();
            }
        });

        buttonMiniGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, MiniGameActivity.class);
                startActivity(intent);
                //Close the current activity
                finish();
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainMenuActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logout = findViewById(R.id.button_logout1);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainMenuActivity.this, "Logged out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainMenuActivity.this, MainActivity.class));


            }
        });
buttonDefaultDices = findViewById(R.id.buttonDefaultDices);
        buttonDefaultDices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, DiceActivity.class);
                startActivity(intent);
                //Close the current activity
                finish();
            }
        });
    }
}
