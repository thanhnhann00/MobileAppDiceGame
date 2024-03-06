package com.example.dicerollapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.dicerollapp.navigation_drawer.DataModel;
import com.example.dicerollapp.navigation_drawer.DrawerItemCustomAdapter;

import java.util.Random;

public class DiceActivity extends AppCompatActivity {
    // Store the Thread sleep time in an integer variable
    int delayTime = 20;
    // Store the number of Dice roll animations per execution
    int rollAnimations = 40;
    // Store the ids for Dice images in an integer array
    int[] diceImages = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};
    // Define a Random object
    Random random = new Random();
    // Declare View object references
    TextView tvHelp;
    ImageView die1;
    ImageView die2;
    LinearLayout diceContainer;
    // Declare a MediaPlayer object reference
    MediaPlayer mp;

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        
        // From the onCreate() method, find the Views
        tvHelp = findViewById(R.id.tvHelp);
        diceContainer = findViewById(R.id.diceContainer);
        die1 = findViewById(R.id.die1);
        die2 = findViewById(R.id.die2);
        // Instantiate the MediaPlayer object
        mp = MediaPlayer.create(this, R.raw.roll);
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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        setupToolbar();


        DataModel[] drawerItem = new DataModel[2];
        drawerItem[0] = new DataModel(R.drawable.history, "History");
        drawerItem[1] = new DataModel(R.drawable.select, "Select dice");


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(toggle);
        setupDrawerToggle();
    }


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
                fragment = new DiceHistoryFragment();
                break;
            case 1:
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
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        toggle.syncState();
    }

    private void rollDice() {
        // Define a Runnable object
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // In the run() method, use a for loop to iterate
                // some code to show rolling dice animation
                for (int i = 0; i < rollAnimations; i++) {
                    // Generate two random numbers between 1 and 6
                    // and store them in two integer variables
                    int dice1 = random.nextInt(6) + 1;
                    int dice2 = random.nextInt(6) + 1;
                    // Get the Image ids from diceImages array
                    // using the above random numbers as array-index.
                    // Then, set the ImageViews for die1 and die2 with them.
                    die1.setImageResource(diceImages[dice1-1]);
                    die2.setImageResource(diceImages[dice2-1]);
                    try {
                        // In a try block sleep the thread for a
                        // smooth animation
                        Thread.sleep(delayTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
