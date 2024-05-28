package com.example.savingsapp.data;

public class SavingsData {
    // Private fields for description, amount, date, and credit status
    private String description;
    private String amount;
    private String date;
    private boolean isCredit;

    // Constructor to initialize the fields
    public SavingsData(String description, String amount, String date, boolean isCredit) {
        this.description = description; // Initialize description
        this.amount = amount; // Initialize amount
        this.date = date; // Initialize date
        this.isCredit = isCredit; // Initialize credit status
    }

    // Getter method for description
    public String getDescription() {
        return description;
    }

    // Getter method for amount
    public String getAmount() {
        return amount;
    }

    // Getter method for date
    public String getDate() {
        return date;
    }

    // Getter method for credit status
    public boolean getIsCredit() {
        return isCredit;
    }
}
