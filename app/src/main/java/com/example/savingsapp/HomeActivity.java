package com.example.savingsapp; // Defines the package name

import android.os.Bundle; // Imports the Bundle class
import android.widget.TextView; // Imports the TextView class

public class HomeActivity extends BaseActivity { // Declares the HomeActivity class, which extends BaseActivity
    TextView helloMessage; // Declares a TextView variable named helloMessage

    @Override // Indicates that the following method overrides a method in the superclass
    protected void onCreate(Bundle savedInstanceState) { // The onCreate method, called when the activity is created
        super.onCreate(savedInstanceState); // Calls the superclass's onCreate method
        setContentView(R.layout.activity_home); // Sets the layout for this activity to activity_home.xml
        initViews(); // Calls the initViews method to initialize views

        // Placeholder
        helloMessage.setText(getString(R.string.hello_message, "John Doe.")); // Sets the text of helloMessage TextView with a formatted string
    }

    void initViews() { // Method to initialize views
        helloMessage = findViewById(R.id.hello_message); // Finds the TextView with the ID hello_message and assigns it to helloMessage
    }
}
