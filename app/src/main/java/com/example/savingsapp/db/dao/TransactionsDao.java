package com.example.savingsapp.db.dao;

// Import necessary Room annotations and classes
import androidx.room.Dao;
import androidx.room.Query;

import com.example.savingsapp.db.entities.TransactionHistory;

import java.util.List;

@Dao // Annotation to indicate this is a DAO (Data Access Object) for Room database
public interface TransactionsDao {

    // Method to insert a transaction into the transaction_history table
    @Query("INSERT INTO transaction_history (user_id, amount, date, type, description) VALUES (:userId, :amount, :date, :type, :description)")
    long insert(int userId, double amount, String date, String type, String description);

    // Method to retrieve all transactions from the transaction_history table
    @Query("SELECT * FROM transaction_history")
    List<TransactionHistory> getAll();

    // Method to count the number of transactions of a specific type in the transaction_history table
    @Query("SELECT COUNT(id) FROM transaction_history WHERE type = :type")
    int getTotalNoOfTransactionsByType(String type);
}
