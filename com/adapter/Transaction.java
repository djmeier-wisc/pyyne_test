package com.adapter;
/**
 * A class to capture information about transactions for bank1 and bank2.
 */
public class Transaction {
    public static enum TRANSACTION_TYPES {
        DEBIT, CREDIT
    }
    private double amount; //stores the price of the transaction
    private TRANSACTION_TYPES type;
    private String text;
    public Transaction(double amount, TRANSACTION_TYPES type, String text) {
        this.amount = amount;
        this.type = type;
        this.text = text;
    }
    public double getAmount() {
        return amount;
    }
    public TRANSACTION_TYPES getType() {
        return type;
    }
    public String getText() {
        return text;
    }
}
