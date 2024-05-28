package com.example.savingsapp;  // Define the package for the application

import android.os.Bundle;  // Import the Bundle class for passing data between activities
import android.util.Log;  // Import the Log class for logging debug messages
import android.view.Menu;  // Import the Menu class for handling menus
import android.view.MenuItem;  // Import the MenuItem class for handling menu items

import androidx.annotation.NonNull;  // Import the NonNull annotation
import androidx.appcompat.app.AppCompatActivity;  // Import the AppCompatActivity class for activity features
import androidx.fragment.app.Fragment;  // Import the Fragment class for using fragments

import com.example.savingsapp.fragments.HomeFragment;  // Import the HomeFragment class
import com.google.android.material.bottomnavigation.BottomNavigationView;  // Import the BottomNavigationView class for bottom navigation
import com.google.android.material.navigation.NavigationBarView;  // Import the NavigationBarView class for navigation bar

public class MainActivity extends BaseActivity implements NavigationBarView.OnItemSelectedListener {  // Define the MainActivity class which extends BaseActivity and implements OnItemSelectedListener

    BottomNavigationView bottomNavigationView;  // Declare a BottomNavigationView variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {  // Override the onCreate method to set up the activity
        super.onCreate(savedInstanceState);  // Call the superclass's onCreate method
        setContentView(R.layout.activity_main);  // Set the content view to the layout defined in activity_main.xml
        initViews();  // Initialize the views by calling the initViews method
        replaceFragment(HomeFragment.newInstance(this));  // Replace the fragment container with HomeFragment
    }

    void initViews() {  // Define the initViews method to initialize the views
        bottomNavigationView = findViewById(R.id.bottom_navigation);  // Find the BottomNavigationView by its ID and assign it to bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(this);  // Set the OnItemSelectedListener for the bottom navigation view to the current activity
    }

    private void replaceFragment(Fragment fragment) {  // Define the replaceFragment method to replace fragments
        getSupportFragmentManager().beginTransaction()  // Begin a fragment transaction
                .setReorderingAllowed(true)  // Allow reordering of fragment transactions
                .add(R.id.fragment_container, fragment, null)  // Add the fragment to the container
                .commit();  // Commit the transaction
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  // Override the onCreateOptionsMenu method to create options menu
        getMenuInflater().inflate(R.menu.nav_menu, menu);  // Inflate the menu from nav_menu.xml
        return true;  // Return true to display the menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  // Override the onOptionsItemSelected method to handle menu item selections
        if(item.getItemId() == R.id.save) {  // If the save item is selected
            Log.d("MainActivity", "Save clicked");  // Log a debug message
            replaceFragment(SaveFragment.newInstance(this));  // Replace the fragment container with SaveFragment
        }
        return true;  // Return true to indicate the menu item was handled
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {  // Override the onNavigationItemSelected method to handle navigation item selections
        int menuItemId = menuItem.getItemId();  // Get the ID of the selected menu item
        if(menuItemId == R.id.save) {  // If the save item is selected
            replaceFragment(SaveFragment.newInstance(this));  // Replace the fragment container with SaveFragment
        } else if(menuItemId == R.id.home) {  // If the home item is selected
            replaceFragment(HomeFragment.newInstance(this));  // Replace the fragment container with HomeFragment
        } else if(menuItemId == R.id.withdraw) {  // If the withdraw item is selected
            replaceFragment(WithdrawalFragment.newInstance(this));  // Replace the fragment container with WithdrawalFragment
        }
        return true;  // Return true to indicate the navigation item was handled
    }
}
