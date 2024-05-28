package com.example.savingsapp.db.entities;

// Import necessary Room annotations
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction_history") // Annotation to define the table name in the database
public class TransactionHistory {
    // Constants to define transaction types
    public static final String TYPE_DEPOSIT = "deposit";
    public static final String TYPE_WITHDRAW = "withdraw";
    public static final String TYPE_INTEREST = "interest";

    @PrimaryKey(autoGenerate = true) // Annotation to define the primary key with auto-increment
    public int id;

    @ColumnInfo(name = "user_id") // Annotation to define the column name for user ID
    public int userId;

    @ColumnInfo(name = "amount") // Annotation to define the column name for amount
    public double amount;

    @ColumnInfo(name = "date") // Annotation to define the column name for date
    public String date;

    @ColumnInfo(name = "type") // Annotation to define the column name for transaction type
    public String type;

    @ColumnInfo(name = "description") // Annotation to define the column name for description
    public String description;
}
