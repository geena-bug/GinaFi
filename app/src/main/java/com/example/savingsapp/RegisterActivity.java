package com.example.savingsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    Button signupButton;
    TextView loginTextView;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText passwordEditText;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize views
        initViews();
        // Initialize database
        this.initAppDb();
    }

    private void initViews() {
        // Initialize button view and set click listener
        signupButton = findViewById(R.id.login_btn);
        signupButton.setOnClickListener(this);
        // Initialize login text view and set click listener
        loginTextView = findViewById(R.id.signup_text);
        loginTextView.setOnClickListener(this);

        // Initialize first name edit text
        firstNameEditText = findViewById(R.id.first_name_input);
        // Initialize last name edit text
        lastNameEditText = findViewById(R.id.last_name_input);
        // Initialize email edit text
        emailEditText = findViewById(R.id.email_input);
        // Initialize password edit text
        passwordEditText = findViewById(R.id.password_input);
    }

    private void handleSignup() {
        // Get the user input from edit texts
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Validate the user input
        if (firstName.isEmpty()) {
            showToast("First name is required");
            return;
        }
        if (lastName.isEmpty()) {
            showToast("Last name is required");
            return;
        }
        if (email.isEmpty()) {
            showToast("Email is required");
            return;
        }
        if (!isValidEmail(email)) {
            showToast("Invalid email");
            return;
        }
        if (password.isEmpty()) {
            showToast("Password is required");
            return;
        }
        if (!isValidPassword(password)) {
            return;
        }

        // Run the database operation in the background
        runInBackground(() -> {
            // Insert the user into the database
            long id = appDatabase.userDao().insert(firstName, lastName, email, password, "");
            // Show toast on UI thread
            runOnUiThread(() -> showToast("User created successfully"));
            // Navigate to the photo activity
            Intent intent = new Intent(RegisterActivity.this, PhotoActivity.class);
            intent.putExtra("userId", id);
            startActivity(intent);
        });
    }

    private boolean isValidPassword(String password) {
        // Check if the password length is less than 8 characters
        if (password.length() < 8) {
            showToast("Password should not be less than 8 characters");
            return false;
        }
        // Check if the password length is greater than 10 characters
        if (password.length() > 10) {
            showToast("Password should not be greater than 10 characters");
            return false;
        }

        // Define patterns for uppercase letter, number, and symbol
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern number = Pattern.compile("[0-9]");
        Pattern symbol = Pattern.compile("[^a-zA-Z0-9]");

        // Match the password against the patterns
        Matcher hasUpperCase = upperCase.matcher(password);
        Matcher hasNumber = number.matcher(password);
        Matcher hasSymbol = symbol.matcher(password);

        // Check if the password contains at least one uppercase letter
        if (!hasUpperCase.find()) {
            showToast("Password must contain at least one uppercase letter");
            return false;
        }
        // Check if the password contains at least one number
        if (!hasNumber.find()) {
            showToast("Password must contain at least one number");
            return false;
        }
        // Check if the password contains at least one symbol
        if (!hasSymbol.find()) {
            showToast("Password must contain at least one symbol");
            return false;
        }

        // All validations passed
        return true;
    }

    private boolean isValidEmail(String email) {
        // Define pattern for a valid email address
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher emailMatcher = emailPattern.matcher(email);
        // Return true if the email matches the pattern
        return emailMatcher.find();
    }

    public void showToast(String message) {
        // Show a toast message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        // Handle signup button click
        if (id == R.id.login_btn) {
            handleSignup();
            // Handle login text view click
        } else if (id == R.id.signup_text) {
            gotoActivity(this, LoginActivity.class);
        }
    }
}
