package com.example.dicerollapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class DiceHistoryFragment extends Fragment {
    private TableLayout historyTable;
    private DiceViewModel viewModel;

    public DiceHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dice_history, container, false);

        historyTable = view.findViewById(R.id.historyTable);

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(DiceViewModel.class);

        // Observe changes in the dice roll history and update the UI
        viewModel.getDiceRollHistory().observe(getViewLifecycleOwner(), diceRollHistory -> {
            // Clear the existing views
            historyTable.removeAllViews();

            // Iterate through the history and add rows to the table
            for (String roll : diceRollHistory) {
                TableRow row = new TableRow(requireContext());

                TextView rollNumberTextView = new TextView(requireContext());
                rollNumberTextView.setText(roll);
                row.addView(rollNumberTextView);

                // Add the row to the table
                historyTable.addView(row);
            }
        });

        return view;
    }
}