package com.example.savingsapp.fragments;

// Import necessary classes and packages
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.savingsapp.R;
import com.example.savingsapp.adapters.SavingsActivityAdapter;
import com.example.savingsapp.db.entities.TransactionHistory;
import com.example.savingsapp.db.entities.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends BaseFragment {

    // Declare UI components
    TextView helloMessage;
    RecyclerView activitiesRecyclerView;
    View view;

    Context context; // Context variable

    User user; // User variable

    CircleImageView profileImage; // Profile image view
    TextView textViewTotalWithdrawals; // TextView for total withdrawals
    TextView totalSavings; // TextView for total savings

    int totalNoWithdrawals = 0; // Variable to store total number of withdrawals

    ArrayList<TransactionHistory> activitiesList = new ArrayList<>(); // List to store transaction history

    public HomeFragment() {
        // Required empty public constructor
    }

    // Static method to create a new instance of HomeFragment with context
    public static HomeFragment newInstance(Context context) {
        HomeFragment fragment = new HomeFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAppDb(context); // Initialize the app database
        getDataFromDB(); // Retrieve data from the database
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(); // Initialize views
        loadProfileImage(); // Load the profile image
        initRecyclerView(); // Initialize the RecyclerView
        return view;
    }

    // Method to initialize views
    void initViews() {
        helloMessage = view.findViewById(R.id.hello_message); // Initialize hello message TextView
        textViewTotalWithdrawals = view.findViewById(R.id.total_withdrawals); // Initialize total withdrawals TextView
        totalSavings = view.findViewById(R.id.total_savings); // Initialize total savings TextView

        // Set the user name in hello message
        helloMessage.setText(getString(R.string.hello_message, user.firstName));
        // Set the total savings amount
        totalSavings.setText(getString(R.string.amount, user.accountBalance));
        // Set the total number of withdrawals
        textViewTotalWithdrawals.setText(getString(R.string.total_withdrawal, totalNoWithdrawals));
    }

    // Method to initialize the RecyclerView
    void initRecyclerView() {
        activitiesRecyclerView = view.findViewById(R.id.activities); // Initialize RecyclerView
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Set layout manager
        activitiesRecyclerView.setAdapter(new SavingsActivityAdapter(activitiesList)); // Set adapter
    }

    // Method to retrieve data from the database
    void getDataFromDB() {
        runInBackground(() -> {
            activitiesList = (ArrayList<TransactionHistory>) appDatabase.transactionsDao().getAll(); // Get all transaction history
            user = appDatabase.userDao().getById(1); // Get user by ID
            totalNoWithdrawals = appDatabase.transactionsDao().getTotalNoOfTransactionsByType(TransactionHistory.TYPE_WITHDRAW); // Get total number of withdrawals
        });
    }

    // Method to load the profile image
    void loadProfileImage() {
        if (user.photoUrl == null || user.photoUrl.isEmpty()) {
            return; // Return if photo URL is null or empty
        }
        profileImage = view.findViewById(R.id.photo); // Initialize profile image view
        Log.d("userPhotoPath", user.photoUrl); // Log the photo URL
        Picasso.get().load(user.photoUrl).into(profileImage); // Load the photo URL into the profile image view using Picasso
    }
}
