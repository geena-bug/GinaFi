package com.example.savingsapp;

// Import necessary classes and packages
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.savingsapp.db.entities.TransactionHistory;
import com.example.savingsapp.fragments.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaveFragment extends BaseFragment implements View.OnClickListener {
    Context context; // Context variable

    Button saveBtn; // Button for saving data
    TextInputEditText amountInput; // TextInputEditText for amount input
    TextInputEditText cardNumberInput; // TextInputEditText for card number input
    TextInputEditText nameInput; // TextInputEditText for name input
    TextInputEditText cvvInput; // TextInputEditText for CVV input
    TextInputEditText expiryInput; // TextInputEditText for expiry date input

    public SaveFragment() {
        // Required empty public constructor
    }

    // Static method to create a new instance of SaveFragment with context
    public static SaveFragment newInstance(Context context) {
        SaveFragment fragment = new SaveFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAppDb(context); // Initialize the app database
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save, container, false);
        initViews(view); // Initialize views
        return view;
    }

    // Method to initialize views
    void initViews(View view) {
        saveBtn = view.findViewById(R.id.save_btn); // Initialize save button
        amountInput = view.findViewById(R.id.amount_input); // Initialize amount input
        cardNumberInput = view.findViewById(R.id.card_number_input); // Initialize card number input
        nameInput = view.findViewById(R.id.card_name_input); // Initialize name input
        cvvInput = view.findViewById(R.id.card_cvv_input); // Initialize CVV input
        expiryInput = view.findViewById(R.id.card_exp_input); // Initialize expiry date input
        saveBtn.setOnClickListener(this); // Set OnClickListener for save button
    }

    // Method to save data
    void saveData() {
        String amount = amountInput.getText().toString(); // Get amount input
        String cardNumber = cardNumberInput.getText().toString(); // Get card number input
        String name = nameInput.getText().toString(); // Get name input
        String cvv = cvvInput.getText().toString(); // Get CVV input
        String expiry = expiryInput.getText().toString(); // Get expiry date input

        // Check if any input field is empty
        if (amount.isEmpty() || cardNumber.isEmpty() || name.isEmpty() || cvv.isEmpty() || expiry.isEmpty()) {
            return; // Return if any field is empty
        }

        // Get current date
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String description = "Deposit into savings account"; // Description for the transaction

        // Save data to the database in the background
        runInBackground(() -> {
            appDatabase.transactionsDao().insert(1, Double.parseDouble(amount), date, TransactionHistory.TYPE_DEPOSIT, description);
            appDatabase.userDao().updateUserBalanceByAmount(1, Double.parseDouble(amount));
            Log.d("SaveFragmentData", "Data saved"); // Log the save operation
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_btn) { // Check if save button is clicked
            saveData(); // Call saveData method
            showToast(context, "Savings was successful"); // Show toast message

            // Clear input fields
            amountInput.setText("");
            cardNumberInput.setText("");
            nameInput.setText("");
            cvvInput.setText("");
            expiryInput.setText("");
        }
    }
}
