package com.example.dicerollapp.UI_elements.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dicerollapp.MainMenuActivity;
import com.example.dicerollapp.R;
import com.example.dicerollapp.UI_elements.Fragments.MiniGameWinnerFragment;

import java.util.Random;

public class MiniGameActivity extends AppCompatActivity {
    private ImageView userDiceImageView;
    private ImageView aiDiceImageView;
    private TextView userScoreTextView;
    private TextView aiScoreTextView;

    private int userScore = 0;
    private int aiScore = 0;

    private final Random random = new Random();
    private final Handler handler = new Handler();
    private boolean aiTurn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame);

        // Initialize views
        userDiceImageView = findViewById(R.id.user_dice_imageview);
        aiDiceImageView = findViewById(R.id.ai_dice_imageview);
        userScoreTextView = findViewById(R.id.user_score_textview);
        aiScoreTextView = findViewById(R.id.ai_score_textview);

        //Previous button click listener
        ImageButton buttonPrevious = findViewById(R.id.button_previous);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiniGameActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();  // Close the current activity
            }
        });

        //Roll dice button click listener
        Button rollDiceButton = findViewById(R.id.roll_dice_button);
        rollDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollUserDice();
                aiTurn = true;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        aiTurn();
                        updateScores();
                        checkWinner();
                    }
                }, 1000);
            }
        });

        Button skipTurnButton = findViewById(R.id.skip_turn_button);
        skipTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userScore >= 19 && userScore <= 21) {
                    aiTurn = true;
                    rollAIDice();
                    updateScores();
                    checkWinner();
                } else if (aiScore < userScore && aiScore < 21) {
                    aiTurn = true;
                    rollAIDice();
                    updateScores();
                    checkWinner();
                } else {
                    Toast.makeText(MiniGameActivity.this, "You can only skip your turn when your score is between 19 and 21.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Method to simulate the AI's decision-making process
    private void aiTurn() {
        if (!aiTurn) {
            return;
        }
        if (userScore >= 21) {
            // User's score is 21 or above, no need for AI to roll
            checkWinner();
            return;
        } else if (aiScore >= 19 && aiScore <= 21) {
            // AI is close to 21, decide whether to skip or roll again
            if (random.nextBoolean()) {
                // AI skips turn
                Toast.makeText(MiniGameActivity.this, "AI skips its turn.", Toast.LENGTH_SHORT).show();
                aiTurn = false; // User's turn
            } else {
                // AI rolls again
                rollAIDice();
                updateScores();
                checkWinner();
            }
        } else if (aiScore < userScore || aiScore == userScore) {
            // AI's score is less than user's and less than 21, roll again
            rollAIDice();
            updateScores();
            checkWinner();
        } else {
            // AI's score is 21 or over, skip turn
            Toast.makeText(MiniGameActivity.this, "AI skips its turn.", Toast.LENGTH_SHORT).show();
            aiTurn = false; // User's turn
        }
    }

    private void rollUserDice() {
        int diceValue = random.nextInt(6) + 1;
        setUserDiceImage(diceValue);
        userScore += diceValue;
        userScoreTextView.setText("Your Score: " + userScore);
    }

    private void rollAIDice() {
        int diceValue = random.nextInt(6) + 1;
        setAIDiceImage(diceValue);
        aiScore += diceValue;
        aiScoreTextView.setText("AI Score: " + aiScore);
    }

    private void setUserDiceImage(int diceValue) {
        int drawableId = getResources().getIdentifier("d" + diceValue, "drawable", getPackageName());
        userDiceImageView.setImageResource(drawableId);
    }

    private void setAIDiceImage(int diceValue) {
        int drawableId = getResources().getIdentifier("d" + diceValue, "drawable", getPackageName());
        aiDiceImageView.setImageResource(drawableId);
    }

    private void updateScores() {
        userScoreTextView.setText("Your Score: " + userScore);
        aiScoreTextView.setText("AI Score: " + aiScore);
    }

    private void showWinnerFragment(String winnerName, int userScore, int aiScore) {
        MiniGameWinnerFragment winnerFragment = MiniGameWinnerFragment.newInstance(winnerName, userScore, aiScore);
        winnerFragment.show(getSupportFragmentManager(), "winner_fragment");

        // Delay dismissal of the fragment
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                winnerFragment.dismiss();
            }
        }, 10000);
    }

    private void checkWinner() {
        if (userScore > 21 && aiScore > 21) {
            showWinnerFragment("It's a draw!", userScore, aiScore);
        } else if (userScore > 21) {
            showWinnerFragment("AI", userScore, aiScore);
        } else if (aiScore > 21) {
            showWinnerFragment("You", userScore, aiScore);
        } else if (userScore == 21) {
            showWinnerFragment("You", userScore, aiScore);
        } else if (aiScore == 21) {
            showWinnerFragment("AI", userScore, aiScore);
        } else {
            return;
        }
        aiTurn = false;
        // Reset scores
        userScore = 0;
        aiScore = 0;
        userScoreTextView.setText("Your Score: " + userScore);
        aiScoreTextView.setText("AI Score: " + aiScore);
    }
}