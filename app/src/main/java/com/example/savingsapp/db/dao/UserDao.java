package com.example.savingsapp.db.dao;

// Import necessary Room annotations and classes
import androidx.room.Dao;
import androidx.room.Query;

import com.example.savingsapp.db.entities.User;

import java.util.List;

@Dao // Annotation to indicate this is a DAO (Data Access Object) for Room database
public interface UserDao {

    // Method to retrieve all users from the users table
    @Query("SELECT * FROM users")
    List<User> getAll();

    // Method to retrieve a user by their ID from the users table
    @Query("SELECT * FROM users WHERE id = :id")
    User getById(int id);

    // Method to insert a new user into the users table
    @Query("INSERT INTO users (first_name, last_name, email, password, photo_url) VALUES (:firstName, :lastName, :email, :password, :photoUrl)")
    long insert(String firstName, String lastName, String email, String password, String photoUrl);

    // Method to retrieve a user by their email from the users table
    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    // Method to retrieve a user by their email and password from the users table
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User getUserByEmailAndPassword(String email, String password);

    // Method to get the count of users in the users table
    @Query("SELECT COUNT(*) FROM users")
    int getUserCount();

    // Method to update a user's photo URL in the users table
    @Query("UPDATE users SET photo_url = :photoUrl WHERE id = :id")
    void updateUser(int id, String photoUrl);

    // Method to update a user's account balance in the users table
    @Query("UPDATE users SET account_balance = :balance WHERE id = :id")
    void updateUserBalance(int id, double balance);

    // Method to update a user's account balance by adding a specified amount in the users table
    @Query("UPDATE users SET account_balance = account_balance + :amount WHERE id = :id")
    void updateUserBalanceByAmount(int id, double amount);
}
