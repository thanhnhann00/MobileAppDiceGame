package com.example.dicerollapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class DiceSelectFragment extends Fragment {

    private final int[] diceOptions = new int[]{R.drawable.dice4, R.drawable.d6, R.drawable.dice8, R.drawable.dice10, R.drawable.dice12, R.drawable.dice20};
    // Define a Random object
    private ImageView dice4;
    private ImageView d6;
    private ImageView dice8;
    private ImageView dice10;
    private ImageView dice12;
    private ImageView dice20;
    public DiceSelectFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dice_selection, container, false);

        dice4 = rootView.findViewById(R.id.dice4);
        d6 = rootView.findViewById(R.id.dice6);

        return rootView;
    }

}
