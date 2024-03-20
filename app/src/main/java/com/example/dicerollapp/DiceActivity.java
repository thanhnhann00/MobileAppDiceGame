package com.example.dicerollapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.dicerollapp.navigation_drawer.DataModel;
import com.example.dicerollapp.navigation_drawer.DrawerItemCustomAdapter;
import java.util.Objects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DiceActivity extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ActionBarDrawerToggle toggle;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        // Previous button click listener
        ImageButton buttonPrevious = findViewById(R.id.button_previous);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the main page (MainActivity)
                Intent intent = new Intent(DiceActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Close the current activity
            }
        });

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);
        setupToolbar();


        DataModel[] drawerItem = new DataModel[3];
        drawerItem[0] = new DataModel(R.drawable.dice, "Dice Roll");
        drawerItem[1] = new DataModel(R.drawable.history, "History");
        drawerItem[2] = new DataModel(R.drawable.select, "Select dice");



        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(toggle);
        setupDrawerToggle();
        selectItem(0);

        logout = findViewById(R.id.button_logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DiceActivity.this, "Logged out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DiceActivity.this, MainActivity.class));


            }
        });


        //------------Dummy----------
        HashMap<String, Object> player = new HashMap<>();
        player.put("Name","Jason");
        player.put("Email","Jason@gmai.com");
        player.put("ID","1");
        FirebaseDatabase.getInstance().getReference().child("Players").child("Player 1").updateChildren(player);
    }
    private void insertPlayerData(String name, String email,int score){

    }
    //----------------DUmmy----------------

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new DiceRollFragment();
                break;
            case 1:
                fragment = new DiceHistoryFragment();
                break;
            case 2:
                fragment = new DiceSelectFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("DiceActivity", "Error in creating fragment");
        }
    }

//    @Override
//    public void onBackPressed() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);
//
//        // Check if the current fragment is not the DiceHistoryFragment
//        if (!(currentFragment instanceof DiceHistoryFragment)) {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    void setupToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        toggle.syncState();
    }
}
