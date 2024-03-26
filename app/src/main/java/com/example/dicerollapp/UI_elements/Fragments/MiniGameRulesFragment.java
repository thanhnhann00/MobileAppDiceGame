package com.example.dicerollapp.UI_elements.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dicerollapp.R;

public class MiniGameRulesFragment extends DialogFragment {

    public MiniGameRulesFragment() {
        // Required empty public constructor
    }

    public static MiniGameRulesFragment newInstance() {

        return new MiniGameRulesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_minigame_rules, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView rulesTextView = view.findViewById(R.id.rules_textview);
        rulesTextView.setText("Roll and try to get as close to 21 as possible before your opponent to win! But don't go over that number or you lose!");
    }
}