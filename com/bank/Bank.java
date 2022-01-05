package com.bank;

import java.util.Date;
import java.util.List;

/**
 * A new bank object. Please use Bank1Adapter or Bank2Adapter to instantiate the
 * object.
 */
public interface Bank {
    /**
     * Gets the balance of accountID.
     * 
     * @param accountID The accountID to be accessed.
     * @return The account balance.
     */
    public double getBalance(long accountID);

    /**
     * Gets the currency type, say "USD".
     * 
     * @param accountID The accountID to be accessed.
     * @return The account currency, of type String.
     */
    public String getCurrency(long accountID);

    /**
     * Gets a list of transactions. The list may be unsorted.
     * 
     * @param accountID The account ID to access the transactions for.
     * @param startDate The start date to begin accessing transactions, inlcusive.
     * @param endDate   The end date to stop accessing transactions, inclusive.
     * @return
     */
    public List<Transaction> getTransactions(long accountID, Date startDate, Date endDate);
}
