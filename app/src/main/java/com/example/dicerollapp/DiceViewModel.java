package com.example.dicerollapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DiceViewModel extends ViewModel {
    private final MutableLiveData<List<String>> diceRollHistory = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<List<String>> getDiceRollHistory() {
        return diceRollHistory;
    }

    public void addToDiceRollHistory(String rollResult) {
        List<String> currentHistory = diceRollHistory.getValue();
        if (currentHistory != null) {
            currentHistory.add(rollResult);
            diceRollHistory.setValue(currentHistory);
        }
    }
}