package com.example.dicerollapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class d8RollFragment extends Fragment implements SensorEventListener{
    // Store the Thread sleep time in an integer variable
    private final int delayTime = 20;
    // Store the number of Dice roll animations per execution
    private final int rollAnimations = 40;
    // Store the ids for Dice images in an integer array
    private final int[] diceImages = new int[]{R.drawable.d81, R.drawable.d82, R.drawable.d83, R.drawable.d84, R.drawable.d85, R.drawable.d86, R.drawable.d87, R.drawable.dice8};
    // Define a Random object
    private final Random random = new Random();
    // Declare View object references
    private TextView tvHelp;
    private ImageView die1;
    private ImageView die2;
    private ImageView die3;
    private LinearLayout diceContainer;
    //Declare a MediaPlayer object reference
    private MediaPlayer mp;

    //Declare a ViewModel object reference
    private DiceViewModel viewModel;

    // Declare Sensor variables
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float SHAKE_THRESHOLD = 5.0f;
    private long lastShakeTime;

    //constructor to pass in the number of dice
    public static d8RollFragment newInstance(int NumDice) {
        d8RollFragment fragment = new d8RollFragment();
        Bundle args = new Bundle();
        args.putInt("numDice", NumDice); // Note: using the passed argument
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_d8_roll, container, false);

        // From the onCreateView() method, find the Views
        tvHelp = view.findViewById(R.id.tvHelp);
        diceContainer = view.findViewById(R.id.diceContainer);
        die1 = view.findViewById(R.id.die81);
        die2 = view.findViewById(R.id.die82);
        die3 = view.findViewById(R.id.die83);

        //If args were passed to the fragment, then run this code to get right amount of dice
        Bundle args = getArguments();
        if (args != null) {
            int numDice = args.getInt("numDice");
            switch (numDice) {
                case(1): {
                    die2.setVisibility(View.GONE);
                    die3.setVisibility(View.GONE);
                    break;
                }
                case(2): {
                    die3.setVisibility(View.GONE);
                    break;
                }
                default: {
                    break;
                }
            }

        }
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

        // Initialize sensor variables
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastShakeTime = System.currentTimeMillis();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Register the accelerometer sensor
        sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        //Unregister the sensor to avoid battery drain
        sensorManager.unregisterListener((SensorEventListener) this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastShakeTime) > delayTime) {
                lastShakeTime = currentTime;
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
                if (acceleration > SHAKE_THRESHOLD) {
                    rollDice();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example
    }

    private void rollDice() {
        // Define a Runnable object
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                Bundle args = getArguments();
                assert args != null;
                int numDice = args.getInt("numDice");
                    switch (numDice) {
                        case (1): {
                            // In the run() method, use a for loop to iterate
                            // some code to show rolling dice animation
                            int dice1 = 0;
                            for (int i = 0; i < rollAnimations; i++) {
                                // Generate a random number between 1 and 8
                                // and store in integer variable
                                dice1 = random.nextInt(8) + 1;
                                // Get the Image ids from diceImages array
                                // using the above random numbers as array-index.
                                // Then, set the ImageViews for die1 and die2 with them.
                                die1.setImageResource(diceImages[dice1 - 1]);

                                try {
                                    // In a try block sleep the thread for a
                                    // smooth animation
                                    Thread.sleep(delayTime);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            // Add the total of both dice to the history using ViewModel
                            final String rollResult = "Roll " + ": " + (dice1);
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    viewModel.addToDiceRollHistory(rollResult);
                                }
                            });
                        }
                        case (2): {
                            // In the run() method, use a for loop to iterate
                            // some code to show rolling dice animation
                            int dice1 = 0;
                            int dice2 = 0;
                            for (int i = 0; i < rollAnimations; i++) {
                                // Generate two random numbers between 1 and 8
                                // and store them in two integer variables
                                dice1 = random.nextInt(8) + 1;
                                dice2 = random.nextInt(8) + 1;
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
                        case (3): {
                            // In the run() method, use a for loop to iterate
                            // some code to show rolling dice animation
                            int dice1 = 0;
                            int dice2 = 0;
                            int dice3 = 0;
                            for (int i = 0; i < rollAnimations; i++) {
                                // Generate random numbers between 1 and 8
                                // and store them in integer variables
                                dice1 = random.nextInt(8) + 1;
                                dice2 = random.nextInt(8) + 1;
                                dice3 = random.nextInt(8) + 1;
                                // Get the Image ids from diceImages array
                                // using the above random numbers as array-index.
                                // Then, set the ImageViews for die1 and die2 with them.
                                die1.setImageResource(diceImages[dice1 - 1]);
                                die2.setImageResource(diceImages[dice2 - 1]);
                                die3.setImageResource(diceImages[dice3 - 1]);

                                try {
                                    // In a try block sleep the thread for a
                                    // smooth animation
                                    Thread.sleep(delayTime);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            // Add the total of both dice to the history using ViewModel
                            final String rollResult = "Roll " + ": " + (dice1 + dice2 + dice3);
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    viewModel.addToDiceRollHistory(rollResult);
                                }
                            });
                        }
                    }
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