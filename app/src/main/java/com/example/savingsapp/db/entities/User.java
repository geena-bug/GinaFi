package com.example.savingsapp.db.entities;

// Import necessary Room annotations
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users") // Annotation to define the table name in the database
public class User {
    /**
     * The user id with auto increment
     */
    @PrimaryKey(autoGenerate = true) // Annotation to define the primary key with auto-increment
    public int id;

    /**
     * The user first name. Column name is first_name
     */
    @ColumnInfo(name = "first_name") // Annotation to define the column name for first name
    public String firstName;

    /**
     * The user last name. Column name is last_name
     */
    @ColumnInfo(name = "last_name") // Annotation to define the column name for last name
    public String lastName;

    /**
     * The user email. Column name is email
     */
    @ColumnInfo(name = "email") // Annotation to define the column name for email
    public String email;

    /**
     * The user password. Column name is password
     */
    @ColumnInfo(name = "password") // Annotation to define the column name for password
    public String password;

    /**
     * The user photo url. Column name is photo_url
     */
    @ColumnInfo(name = "photo_url") // Annotation to define the column name for photo URL
    public String photoUrl;

    @ColumnInfo(name = "account_balance", defaultValue = "0.00") // Annotation to define the column name for account balance with a default value
    public double accountBalance;
}
