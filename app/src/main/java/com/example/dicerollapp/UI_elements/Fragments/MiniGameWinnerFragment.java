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

import java.util.Objects;

public class MiniGameWinnerFragment extends DialogFragment {
    private String winnerName;
    private int userScore;
    private int aiScore;

    public MiniGameWinnerFragment() {
        // Required empty public constructor
    }

    public static MiniGameWinnerFragment newInstance(String winnerName, int userScore, int aiScore) {
        MiniGameWinnerFragment fragment = new MiniGameWinnerFragment();
        Bundle args = new Bundle();
        args.putString("winnerName", winnerName);
        args.putInt("userScore", userScore);
        args.putInt("aiScore", aiScore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            winnerName = getArguments().getString("winnerName");
            userScore = getArguments().getInt("userScore");
            aiScore = getArguments().getInt("aiScore");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_minigame_winner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView winnerTextView = view.findViewById(R.id.winner_textview);
        TextView scoresTextView = view.findViewById(R.id.scores_textview);

        if(Objects.equals(winnerName, "You")) {
            winnerTextView.setText(winnerName + " won!");
        } else{
            winnerTextView.setText(winnerName + " wins!");
        }
        scoresTextView.setText("Final scores:\nYou: " + userScore + "\nAI: " + aiScore);
    }
}