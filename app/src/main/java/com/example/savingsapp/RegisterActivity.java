package com.example.savingsapp;  // Define the package for the application

import android.content.Intent;  // Import the Intent class for handling intents
import android.os.Bundle;  // Import the Bundle class for passing data between activities
import android.view.View;  // Import the View class for UI components
import android.widget.Button;  // Import the Button class for buttons
import android.widget.EditText;  // Import the EditText class for text input fields
import android.widget.TextView;  // Import the TextView class for displaying text
import android.widget.Toast;  // Import the Toast class for displaying toast messages

import java.util.regex.Matcher;  // Import the Matcher class for regex operations
import java.util.regex.Pattern;  // Import the Pattern class for regex operations

public class RegisterActivity extends BaseActivity implements View.OnClickListener {  // Define the RegisterActivity class which extends BaseActivity and implements OnClickListener

    Button signupButton;  // Declare a Button variable for the signup button
    TextView loginTextView;  // Declare a TextView variable for the login text
    EditText firstNameEditText;  // Declare an EditText variable for the first name input
    EditText lastNameEditText;  // Declare an EditText variable for the last name input
    EditText passwordEditText;  // Declare an EditText variable for the password input
    EditText emailEditText;  // Declare an EditText variable for the email input

    @Override
    protected void onCreate(Bundle savedInstanceState) {  // Override the onCreate method to set up the activity
        super.onCreate(savedInstanceState);  // Call the superclass's onCreate method
        setContentView(R.layout.activity_register);  // Set the content view to the layout defined in activity_register.xml
        initViews();  // Initialize the views by calling the initViews method
        this.initAppDb();  // Initialize the application database
    }

    private void initViews() {  // Define the initViews method to initialize the views
        signupButton = findViewById(R.id.login_btn);  // Find the signup Button by its ID
        signupButton.setOnClickListener(this);  // Set the OnClickListener for the signup button

        loginTextView = findViewById(R.id.signup_text);  // Find the login TextView by its ID
        loginTextView.setOnClickListener(this);  // Set the OnClickListener for the login text view

        firstNameEditText = findViewById(R.id.first_name_input);  // Find the first name EditText by its ID
        lastNameEditText = findViewById(R.id.last_name_input);  // Find the last name EditText by its ID
        emailEditText = findViewById(R.id.email_input);  // Find the email EditText by its ID
        passwordEditText = findViewById(R.id.password_input);  // Find the password EditText by its ID
    }

    private void handleSignup() {  // Define the handleSignup method to handle the signup process
        String firstName = firstNameEditText.getText().toString();  // Get the first name input as a string
        String lastName = lastNameEditText.getText().toString();  // Get the last name input as a string
        String email = emailEditText.getText().toString();  // Get the email input as a string
        String password = passwordEditText.getText().toString();  // Get the password input as a string

        // Validate the user input
        if (firstName.isEmpty()) {  // Check if the first name is empty
            showToast("First name is required");  // Show a toast message indicating the first name is required
            return;  // Return from the method
        }
        if (lastName.isEmpty()) {  // Check if the last name is empty
            showToast("Last name is required");  // Show a toast message indicating the last name is required
            return;  // Return from the method
        }
        if (email.isEmpty()) {  // Check if the email is empty
            showToast("Email is required");  // Show a toast message indicating the email is required
            return;  // Return from the method
        }
        if (!isValidEmail(email)) {  // Check if the email is not valid
            showToast("Invalid email");  // Show a toast message indicating the email is invalid
            return;  // Return from the method
        }
        if (password.isEmpty()) {  // Check if the password is empty
            showToast("Password is required");  // Show a toast message indicating the password is required
            return;  // Return from the method
        }
        if (!isValidPassword(password)) {  // Check if the password is not valid
            return;  // Return from the method
        }

        // Run the database operation in the background
        runInBackground(() -> {
            long id = appDatabase.userDao().insert(firstName, lastName, email, password, "");  // Insert the user into the database
            runOnUiThread(() -> showToast("User created successfully"));  // Show a toast message on the UI thread indicating the user was created successfully
            Intent intent = new Intent(RegisterActivity.this, PhotoActivity.class);  // Create an intent to start the PhotoActivity
            intent.putExtra("userId", id);  // Put the user ID in the intent
            startActivity(intent);  // Start the PhotoActivity
        });
    }

    private boolean isValidPassword(String password) {  // Define the isValidPassword method to validate the password
        if (password.length() < 8) {  // Check if the password length is less than 8 characters
            showToast("Password should not be less than 8 characters");  // Show a toast message indicating the password is too short
            return false;  // Return false indicating the password is not valid
        }
        if (password.length() > 10) {  // Check if the password length is greater than 10 characters
            showToast("Password should not be greater than 10 characters");  // Show a toast message indicating the password is too long
            return false;  // Return false indicating the password is not valid
        }

        // Define patterns for uppercase letter, number, and symbol
        Pattern upperCase = Pattern.compile("[A-Z]");  // Pattern for uppercase letters
        Pattern number = Pattern.compile("[0-9]");  // Pattern for numbers
        Pattern symbol = Pattern.compile("[^a-zA-Z0-9]");  // Pattern for symbols

        // Match the password against the patterns
        Matcher hasUpperCase = upperCase.matcher(password);  // Matcher for uppercase letters
        Matcher hasNumber = number.matcher(password);  // Matcher for numbers
        Matcher hasSymbol = symbol.matcher(password);  // Matcher for symbols

        if (!hasUpperCase.find()) {  // Check if the password does not contain an uppercase letter
            showToast("Password must contain at least one uppercase letter");  // Show a toast message indicating the password needs an uppercase letter
            return false;  // Return false indicating the password is not valid
        }
        if (!hasNumber.find()) {  // Check if the password does not contain a number
            showToast("Password must contain at least one number");  // Show a toast message indicating the password needs a number
            return false;  // Return false indicating the password is not valid
        }
        if (!hasSymbol.find()) {  // Check if the password does not contain a symbol
            showToast("Password must contain at least one symbol");  // Show a toast message indicating the password needs a symbol
            return false;  // Return false indicating the password is not valid
        }

        return true;  // Return true indicating the password is valid
    }

    private boolean isValidEmail(String email) {  // Define the isValidEmail method to validate the email
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");  // Define the pattern for a valid email address
        Matcher emailMatcher = emailPattern.matcher(email);  // Match the email against the pattern
        return emailMatcher.find();  // Return true if the email matches the pattern
    }

    public void showToast(String message) {  // Define the showToast method to show a toast message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();  // Show a toast message with the given text
    }

    @Override
    public void onClick(View v) {  // Override the onClick method to handle click events
        int id = v.getId();  // Get the ID of the clicked view
        if (id == R.id.login_btn) {  // Check if the signup button was clicked
            handleSignup();  // Call the handleSignup method
        } else if (id == R.id.signup_text) {  // Check if the login text view was clicked
            gotoActivity(this, LoginActivity.class);  // Navigate to the LoginActivity
        }
    }
}
