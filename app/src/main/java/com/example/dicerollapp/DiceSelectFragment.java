package com.example.dicerollapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    private Button selectButton;

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
        selectButton = rootView.findViewById(R.id.selectDiceButton);


       dice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {diceSize = 4;}
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

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checks which radioButton is selected upon confirmation
                int radioButtonID = diceRadioGroup.getCheckedRadioButtonId();
                View radioButton = diceRadioGroup.findViewById(radioButtonID);
                int numDice = diceRadioGroup.indexOfChild(radioButton)+1;
                Log.d("Dice Count:", "pain " + numDice);

                LinearLayout layout = rootView.findViewById(R.id.diceOptions);
                layout.removeAllViews();

                // Add DiceRollFragment to LinearLayout
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction fr = fm.beginTransaction();

                switch (diceSize) {

                    case 4:
                        d4RollFragment d4RollFragment = com.example.dicerollapp.d4RollFragment.newInstance(numDice);
                        fr.add(layout.getId(), d4RollFragment); // Add DiceRollFragment to LinearLayout
                        fr.addToBackStack(null);
                        fr.commit();
                        break;
                    case 6:
                        DiceRollFragment diceRollFragment = com.example.dicerollapp.DiceRollFragment.newInstance(numDice);
                        fr.add(layout.getId(), diceRollFragment); // Add DiceRollFragment to LinearLayout
                        fr.addToBackStack(null); //Adds the transaction to the back stack
                        fr.commit();
                        break;
                    case 8:
                        d8RollFragment d8RollFragment = com.example.dicerollapp.d8RollFragment.newInstance(numDice);
                        fr.add(layout.getId(), d8RollFragment); // Add DiceRollFragment to LinearLayout
                        fr.addToBackStack(null);
                        fr.commit();
                        break;
                    case 10:
                        d10RollFragment d10RollFragment = com.example.dicerollapp.d10RollFragment.newInstance(numDice);
                        fr.add(layout.getId(), d10RollFragment); // Add DiceRollFragment to LinearLayout
                        fr.addToBackStack(null);
                        fr.commit();
                        break;
                    case 12:
                        d12RollFragment d12RollFragment = com.example.dicerollapp.d12RollFragment.newInstance(numDice);
                        fr.add(layout.getId(), d12RollFragment); // Add DiceRollFragment to LinearLayout
                        fr.addToBackStack(null);
                        fr.commit();
                        break;
                    case 20:
                        d20RollFragment d20RollFragment = com.example.dicerollapp.d20RollFragment.newInstance(numDice);
                        fr.add(layout.getId(), d20RollFragment); // Add DiceRollFragment to LinearLayout
                        fr.addToBackStack(null);
                        fr.commit();
                        break;

                    default:
                        fr.commit();
                        break;
                }

            }
        });

        return rootView;
    }

}
