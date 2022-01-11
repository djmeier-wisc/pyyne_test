package com.bank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bank2.integration.Bank2AccountBalance;
import com.bank2.integration.Bank2AccountSource;
import com.bank2.integration.Bank2AccountTransaction;



/**
 * An adaptor to convert Bank1AccountSource object into the type Bank.
 */
public class Bank2Adaptor implements Bank {
    private Bank2AccountSource oldBank;

    /**
     * Takes in a Bank2AccountSource object, along with an accountID and start and
     * end date, allowing for its access via Bank methods.
     * 
     * @param oldBank   The Bank2AccountSource object to be converted
     * @param accountID The account number, in the form of a long int.
     * @param fromDate  The start date to begin including transactions.
     * @param endDate   The end date to stop inculding transacitons.
     */
    public Bank2Adaptor(Bank2AccountSource oldBank) {
        this.oldBank = oldBank;
    }

    /**
     * Gets the currency of the source bank account object.
     * 
     * @param accountID The account ID to be used with the Bank2 object.
     * @return The currency of accountID.
     */
    public String getCurrency(long accountID) {
        Bank2AccountBalance balance =  oldBank.getBalance(accountID);
        return balance.getCurrency();
    }

    /**
     * Gets the balance of the source bank account object.
     * 
     * @param accountID The account ID to be used with the Bank2 object.
     * @return The account balance of accountID.
     */
    public double getBalance(long accountID) {
        return oldBank.getBalance(accountID).getBalance();
    }

    /**
     * Gets the list of transactions in the form of an list. The list is not sorted.
     * 
     * @param accountID The account ID to be used with the Bank2 object.
     * @param startDate The start date to begin including transactions.
     * @param endDate   The end date to begin including transactions.
     * @return A list of transactions of type com.bank.Transaction.
     */
    public List<Transaction> getTransactions(long accountID, Date fromDate, Date endDate) {
        List<Transaction> newTransactionList = new ArrayList<Transaction>();
        List<Bank2AccountTransaction> transactions = oldBank.getTransactions(accountID, fromDate, endDate);
        for (Bank2AccountTransaction currTransaction : transactions) {
            if (currTransaction.getType() == Bank2AccountTransaction.TRANSACTION_TYPES.CREDIT)
                newTransactionList.add(new Transaction(currTransaction.getAmount(),
                        Transaction.TRANSACTION_TYPES.CREDIT, currTransaction.getText()));
            else if (currTransaction.getType() == Bank2AccountTransaction.TRANSACTION_TYPES.DEBIT)
                newTransactionList.add(new Transaction(currTransaction.getAmount(),
                        Transaction.TRANSACTION_TYPES.DEBIT, currTransaction.getText()));
        }
        return newTransactionList;
    }

}
