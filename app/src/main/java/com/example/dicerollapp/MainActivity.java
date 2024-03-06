package com.example.dicerollapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Button buttonLogin;
    private Button buttonQuickPlay;
    private Button buttonCreateAccount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setBackgroundColor(Color.parseColor("#010b13"));

        buttonQuickPlay = findViewById(R.id.buttonQuickPlay);
        buttonQuickPlay.setBackgroundColor(Color.parseColor("#010b13"));

        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        buttonCreateAccount.setBackgroundColor(Color.parseColor("#010b13"));

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                //Close the current activity
                finish();
            }
        });

        buttonQuickPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiceActivity.class);
                startActivity(intent);
                //Close the current activity
                finish();
            }
        });

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}