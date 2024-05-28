package com.example.savingsapp.fragments;

// Import necessary classes and packages
import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.savingsapp.db.AppDatabase;

public class BaseFragment extends Fragment {
    protected AppDatabase appDatabase; // Protected variable for the app database

    /**
     * Initialize the app database
     * @param context The context
     * @return The app database instance
     */
    protected AppDatabase initAppDb(Context context) {
        return appDatabase = AppDatabase.getInstance(context); // Get and assign the app database instance
    }

    /**
     * Run a task in the background thread
     * @param runnable The task to run
     */
    protected void runInBackground(Runnable runnable) {
        new Thread(runnable).start(); // Start a new thread to run the task
    }

    /**
     * Show a toast message
     * @param context The context
     * @param message The message to show
     */
    protected void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show(); // Show a toast message with the given context and message
    }
}
