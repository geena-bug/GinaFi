package com.example.savingsapp;  // Define the package for the application

import android.content.Intent;  // Import the Intent class for handling intents
import android.os.Bundle;  // Import the Bundle class for passing data between activities
import android.view.View;  // Import the View class for UI components
import android.widget.Button;  // Import the Button class for buttons
import android.widget.EditText;  // Import the EditText class for text input fields
import android.widget.TextView;  // Import the TextView class for displaying text

import com.example.savingsapp.db.entities.User;  // Import the User entity from the database package

public class LoginActivity extends BaseActivity implements View.OnClickListener {  // Define the LoginActivity class which extends BaseActivity and implements OnClickListener
    EditText emailEditText;  // Declare an EditText variable for the email input
    EditText passwordEditText;  // Declare an EditText variable for the password input
    Button loginButton;  // Declare a Button variable for the login button
    TextView signupTextView;  // Declare a TextView variable for the signup text

    User user;  // Declare a User variable to hold the user data

    @Override
    protected void onCreate(Bundle savedInstanceState) {  // Override the onCreate method to set up the activity
        super.onCreate(savedInstanceState);  // Call the superclass's onCreate method
        setContentView(R.layout.activity_login);  // Set the content view to the layout defined in activity_login.xml
        initAppDb();  // Initialize the application database
        initViews();  // Initialize the views by calling the initViews method
    }

    private void initViews() {  // Define the initViews method to initialize the views
        emailEditText = findViewById(R.id.email_input);  // Find the email EditText by its ID and assign it to emailEditText
        passwordEditText = findViewById(R.id.password_input);  // Find the password EditText by its ID and assign it to passwordEditText
        loginButton = findViewById(R.id.login_btn);  // Find the login Button by its ID and assign it to loginButton
        loginButton.setOnClickListener(this);  // Set the onClickListener for the login button to the current activity
        signupTextView = findViewById(R.id.signup_text);  // Find the signup TextView by its ID and assign it to signupTextView
        signupTextView.setOnClickListener(this);  // Set the onClickListener for the signup text view to the current activity
    }

    @Override
    public void onClick(View v) {  // Override the onClick method to handle click events
        int viewId = v.getId();  // Get the ID of the clicked view
        if (viewId == R.id.login_btn) {  // If the login button is clicked
            handleLogin();  // Call the handleLogin method
        } else if (viewId == R.id.signup_text) {  // If the signup text view is clicked
            gotoActivity(this, RegisterActivity.class);  // Go to the RegisterActivity
        }
    }

    private void handleLogin() {  // Define the handleLogin method to handle the login process
        String email = emailEditText.getText().toString();  // Get the email input as a string
        String password = passwordEditText.getText().toString();  // Get the password input as a string

        // Validate the user input
        if (email.isEmpty()) {  // Check if the email is empty
            showToast("Email is required");  // Show a toast message indicating the email is required
            return;  // Return from the method
        }
        if (password.isEmpty()) {  // Check if the password is empty
            showToast("Password is required");  // Show a toast message indicating the password is required
            return;  // Return from the method
        }

        // Run the login process in a background thread
        runInBackground(() -> {
            user = appDatabase.userDao().getUserByEmailAndPassword(email, password);  // Retrieve the user from the database by email and password
            if (user == null) {  // If the user is not found
                runOnUiThread(() -> showToast("Login invalid"));  // Show a toast message indicating the login is invalid on the UI thread
            } else {  // If the user is found
                runOnUiThread(() -> showToast("Login successful"));  // Show a toast message indicating the login is successful on the UI thread
                gotoActivity(LoginActivity.this, MainActivity.class);  // Go to the MainActivity
            }
        });
    }
}
