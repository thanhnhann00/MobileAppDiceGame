package com.example.dicerollapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Random;

public class DiceRollFragment extends Fragment {
    // Store the Thread sleep time in an integer variable
    private final int delayTime = 20;
    // Store the number of Dice roll animations per execution
    private final int rollAnimations = 40;
    // Store the ids for Dice images in an integer array
    private final int[] diceImages = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};
    // Define a Random object
    private final Random random = new Random();
    // Declare View object references
    private TextView tvHelp;
    private ImageView die1;
    private ImageView die2;
    private LinearLayout diceContainer;
    // Declare a MediaPlayer object reference
    private MediaPlayer mp;

    // Declare a ViewModel object reference
    private DiceViewModel viewModel;

    public DiceRollFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dice_roll, container, false);

        // From the onCreateView() method, find the Views
        tvHelp = view.findViewById(R.id.tvHelp);
        diceContainer = view.findViewById(R.id.diceContainer);
        die1 = view.findViewById(R.id.die1);
        die2 = view.findViewById(R.id.die2);
        // Instantiate the MediaPlayer object
        mp = MediaPlayer.create(requireContext(), R.raw.roll);
        // Attach OnClickListener with diceContainer
        diceContainer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    // In a try block call rollDice() method to show the
                    // dice roll animation. We'll define this method below.
                    rollDice();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        viewModel = new ViewModelProvider(requireActivity()).get(DiceViewModel.class);
        return view;
    }

    private void rollDice() {
        // Define a Runnable object
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // In the run() method, use a for loop to iterate
                // some code to show rolling dice animation
                int dice1 = 0;
                int dice2 = 0;
                for (int i = 0; i < rollAnimations; i++) {
                    // Generate two random numbers between 1 and 6
                    // and store them in two integer variables
                    dice1 = random.nextInt(6) + 1;
                    dice2 = random.nextInt(6) + 1;
                    // Get the Image ids from diceImages array
                    // using the above random numbers as array-index.
                    // Then, set the ImageViews for die1 and die2 with them.
                    die1.setImageResource(diceImages[dice1 - 1]);
                    die2.setImageResource(diceImages[dice2 - 1]);

                    try {
                        // In a try block sleep the thread for a
                        // smooth animation
                        Thread.sleep(delayTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Add the total of both dice to the history using ViewModel
                final String rollResult = "Roll " + ": " + (dice1 + dice2);
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.addToDiceRollHistory(rollResult);
                    }
                });
            }
        };

        // Define a Thread object and pass the runnable object
        // in the constructor.
        Thread thread = new Thread(runnable);
        // Start the thread. This will cause the run() method to be called
        // where all the dice rolling animation happens.
        thread.start();
        // If the MediaPlayer object is not null then start playing
        // the music.
        if (mp != null) {
            mp.start();
        }
    }
}
