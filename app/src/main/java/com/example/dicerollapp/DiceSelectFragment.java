package com.example.dicerollapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import java.util.Objects;

public class DiceSelectFragment extends Fragment {

    private final int[] diceOptions = new int[]{R.drawable.dice4, R.drawable.d6, R.drawable.dice8, R.drawable.dice10, R.drawable.dice12, R.drawable.dice20};
    // Define a Random object

    private ImageButton dice4;
    private ImageButton d6;
    private ImageButton dice8;
    private ImageButton dice10;
    private ImageButton dice12;
    private ImageButton dice20;
    private int diceSize = 4;

    private RadioGroup diceRadioGroup;
    private int diceCount = 1;
    public DiceSelectFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dice_selection, container, false);

        dice4 = rootView.findViewById(R.id.dice4);
        d6 = rootView.findViewById(R.id.dice6);
        dice8 = rootView.findViewById(R.id.dice8);
        dice10 = rootView.findViewById(R.id.dice10);
        dice12 = rootView.findViewById(R.id.dice12);
        dice20 = rootView.findViewById(R.id.dice20);
        diceRadioGroup = rootView.findViewById(R.id.diceRadioGroup);

       dice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diceSize = 4;
                /*
                // Navigate back to the main page (MainActivity)
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();  // Close the current activity
                */
            }
        });
        d6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diceSize = 6;
            }
        });
        dice8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diceSize = 8;
            }
        });
        dice10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diceSize = 10;
            }
        });
        dice12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diceSize = 12;
            }
        });
        dice20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diceSize = 20;
            }
        });

        //change dice count based on radioGroup
        diceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //gets the index of the child radioButton
            int radioButtonID = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(radioButtonID);
            int idx = group.indexOfChild(radioButton);
            //Log.d("index", "Selected index is now " + idx);

            //assigns the amount of dice depending on the selected option
            switch (idx) {
                case (0):
                    diceCount = 1;
                    //Log.d("DiceCount" , "Dice is now " + diceCount);
                    break;
                case (1):
                    diceCount = 2;
                    break;
                case(2):
                    diceCount = 3;
                    break;

                default:
                    break;
                }
            }
        });

        return rootView;
    }

}
