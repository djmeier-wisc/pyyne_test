package com.bank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bank.Transaction.TRANSACTION_TYPES;
import com.bank1.integration.Bank1AccountSource;
import com.bank1.integration.Bank1Transaction;

/**
 * An adaptor to convert Bank1AccountSource object into the type Bank.
 */
public class Bank1Adapter implements Bank {
    private Bank1AccountSource oldBank;

    /**
     * Takes in a Bank1Account source object, along with the account number, and
     * transaction start and end date, allowing for it to used as a Bank object.
     * 
     * @param oldBank
     */
    public Bank1Adapter(Bank1AccountSource oldBank) {
        this.oldBank = oldBank;
    }

    /**
     * Gets the balance of the source bank account object.
     * 
     * @param accountID The account ID to be used with the Bank1 object.
     * @return The account balance of accountID.
     */
    public double getBalance(long accountID) {
        return oldBank.getAccountBalance(accountID);
    }

    /**
     * Gets the currency of the source bank account object.
     * 
     * @param accountID The account ID to be used with the Bank1 object.
     * @return The currency of accountID.
     */
    public String getCurrency(long accountID) {
        return oldBank.getAccountCurrency(accountID);
    }

    /**
     * Gets the list of transactions in the form of an list. The list is not sorted.
     * 
     * @param accountID The account ID to be used with the Bank1 object.
     * @param startDate The start date to begin including transactions.
     * @param endDate   The end date to begin including transactions.
     * @return A list of transactions of type com.bank.Transaction.
     */
    public List<Transaction> getTransactions(long accountID, Date startDate, Date endDate) {
        List<Transaction> newTransactionsList = new ArrayList<Transaction>();
        List<Bank1Transaction> oldTransactions = oldBank.getTransactions(accountID, startDate, endDate);
        for (Bank1Transaction transaction : oldTransactions) {
            if (transaction.getType() == Bank1Transaction.TYPE_CREDIT) {
                newTransactionsList
                        .add(new Transaction(transaction.getAmount(), TRANSACTION_TYPES.DEBIT, transaction.getText()));
            } else if (transaction.getType() == Bank1Transaction.TYPE_CREDIT) {
                newTransactionsList
                        .add(new Transaction(transaction.getAmount(), TRANSACTION_TYPES.CREDIT, transaction.getText()));
            }

        }
        return newTransactionsList;
    }
}
