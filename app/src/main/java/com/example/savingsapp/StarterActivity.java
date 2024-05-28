package com.example.savingsapp;

// Import necessary classes and packages
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StarterActivity extends BaseActivity implements View.OnClickListener {

    Button createAccountBtn; // Button to create an account
    TextView loginText; // TextView for login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter); // Set the content view to activity_starter layout
        initViews(); // Initialize views
        // Initialize database. This will create the database if it does not exist and will also create the tables
        initAppDb();
        // Redirect to login if the user exists
        redirectToLoginIfUserExists();
    }

    // Method to initialize views
    private void initViews() {
        // Initialize the create account button view
        createAccountBtn = findViewById(R.id.get_started);
        createAccountBtn.setOnClickListener(this); // Set OnClickListener for the create account button
        // Initialize the login text view
        loginText = findViewById(R.id.login);
        loginText.setOnClickListener(this); // Set OnClickListener for the login text view
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId(); // Get the ID of the clicked view
        if (viewId == R.id.get_started) { // Check if the create account button was clicked
            gotoActivity(this, RegisterActivity.class); // Navigate to RegisterActivity
        } else if (viewId == R.id.login) { // Check if the login text view was clicked
            gotoActivity(this, LoginActivity.class); // Navigate to LoginActivity
        }
    }

    // Method to redirect to login if the user exists
    void redirectToLoginIfUserExists() {
        // Check if the user exists
        runInBackground(() -> {
            if (appDatabase.userDao().getUserCount() > 0) { // If there is at least one user in the database
                gotoActivity(this, MainActivity.class); // Navigate to MainActivity
            }
        });
    }
}
