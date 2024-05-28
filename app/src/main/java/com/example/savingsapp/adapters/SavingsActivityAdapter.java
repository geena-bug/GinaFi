package com.example.savingsapp.adapters;

// Import necessary classes and packages
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.savingsapp.R;
import com.example.savingsapp.db.entities.TransactionHistory;
import java.util.ArrayList;

public class SavingsActivityAdapter extends RecyclerView.Adapter<SavingsActivityAdapter.SavingsViewHolder> {
    // List of savings data
    ArrayList<TransactionHistory> transactionHistoryList;
    // Context
    Context context;

    /**
     * Constructor
     * @param transactionHistoryList List of savings data
     */
    public SavingsActivityAdapter(ArrayList<TransactionHistory> transactionHistoryList) {
        this.transactionHistoryList = transactionHistoryList; // Initialize the list of transaction history
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new SavingsViewHolder that holds a View of the given view type.
     */
    @Override
    public SavingsActivityAdapter.SavingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activities_layout, parent, false);
        context = parent.getContext(); // Get the context from the parent ViewGroup
        return new SavingsViewHolder(view); // Return a new instance of SavingsViewHolder
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(SavingsActivityAdapter.SavingsViewHolder holder, int position) {
        // Get the transaction history for the current position
        TransactionHistory transactionHistory = transactionHistoryList.get(position);
        // Set the description text
        holder.description.setText(transactionHistory.description);
        // Set the amount text with a formatted string
        holder.amount.setText(context.getString(R.string.amount, transactionHistory.amount));
        // Check the type of transaction and set the appropriate dot image and text colors
        if (TransactionHistory.TYPE_DEPOSIT.equals(transactionHistory.type) || TransactionHistory.TYPE_INTEREST.equals(transactionHistory.type)) {
            holder.dotImage.setImageResource(R.drawable.circle_green); // Set green dot for deposit or interest
            holder.description.setTextColor(ContextCompat.getColor(context, R.color.black)); // Set text color to black
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.black)); // Set amount text color to black
        } else {
            holder.dotImage.setImageResource(R.drawable.circle_red); // Set red dot for other types
            holder.description.setTextColor(ContextCompat.getColor(context, R.color.red)); // Set text color to red
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red)); // Set amount text color to red
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return transactionHistoryList.size(); // Return the size of the transaction history list
    }

    /**
     * ViewHolder class for the RecyclerView items
     */
    public static class SavingsViewHolder extends RecyclerView.ViewHolder {
        ImageView dotImage; // ImageView for the dot indicator
        TextView description; // TextView for the description
        TextView amount; // TextView for the amount

        /**
         * Constructor
         * @param itemView The view that will be used to display the data at the specified position.
         */
        public SavingsViewHolder(View itemView) {
            super(itemView);
            // Initialize views
            dotImage = itemView.findViewById(R.id.dot_image); // Find the dot image view by ID
            description = itemView.findViewById(R.id.description); // Find the description text view by ID
            amount = itemView.findViewById(R.id.amount); // Find the amount text view by ID
        }
    }
}
