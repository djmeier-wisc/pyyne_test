package com.bank;

import java.util.ArrayList;
import java.util.List;
/**
 * A bank class to be used by the BankController.
 */
public class NewBank implements Bank {
    private String currency;
    private double balance;
    private List<Transaction> transactions;

    /**
     * Creates a NewBank object, setting the currency to the parameter. Balance is
     * set to 0 and the transaction list will be empty.
     * 
     * @param currency - The currency of the new account, I.E. "USD".
     */
    public NewBank(String currency) {
        this.currency = currency;
        this.balance = 0d;
        this.transactions = new ArrayList<Transaction>();
    }
    /**
     * Creates a NewBank object, setting currency and balance to their respective parameters. The transaction list will be empty to start.
     * @param currency - The currency of the new account, I.E. "USD".
     * @param balance - The balance of the new account, I.E 100d.
     */
    public NewBank(String currency, double balance) {
        this.currency = currency;
        this.balance = balance;
        this.transactions = new ArrayList<Transaction>();
    }
    /**
     * Adds a new transaction to the transaction list.
     * @param t - The transaction to be added.
     * @return True if the transaction can be covered by the current balance, false otherwise.
     */
    public boolean addTransaction(Transaction t) {
        this.transactions.add(t);
        if (t.getAmount() > this.balance)
            return false;
        else
            return true;
    }
    /**
     * Gets the currency of this account, I.E. "USD".
     * @return The currency of the account.
     */
    public String getCurrency() {
        return this.currency;
    }
    /**
     * Gets the balance of this account, I.E. 100d.
     * @return The balance of the account, as a double.
     */
    public double getBalance() {
        return this.getBalance();
    }
    /**
     * Gets a list of all transactions for the account.
     * @return A list of all transactions.
     */
    public List<Transaction> getTransactions() {
        return this.transactions;
    }
}
