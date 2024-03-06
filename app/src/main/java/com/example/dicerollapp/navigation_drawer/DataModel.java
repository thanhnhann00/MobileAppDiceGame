package com.example.dicerollapp.navigation_drawer;

public class DataModel {

    public int icon;
    public String name;

    // Constructor.
    public DataModel(int icon, String name) {

        this.icon = icon;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }
}