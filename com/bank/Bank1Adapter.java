package com.bank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bank.Transaction.TRANSACTION_TYPES;
import com.bank1.integration.Bank1AccountSource;
import com.bank1.integration.Bank1Transaction;

public class Bank1Adapter extends NewBank {
    /**
     * Takes in a Bank1Account source object, along with the account number, and transaction start and end date, allowing for it to be accessed via 
     * @param oldBank
     * @param accountID
     * @param startDate
     * @param endDate
     */
    public Bank1Adapter(Bank1AccountSource oldBank, long accountID, Date startDate, Date endDate) {
        super(oldBank.getAccountCurrency(accountID), oldBank.getAccountBalance(accountID));
        List<Bank1Transaction> oldTransactions = oldBank.getTransactions(accountID, startDate, endDate);
        for (Bank1Transaction transaction : oldTransactions) {
            if (transaction.getType() == Bank1Transaction.TYPE_CREDIT) {
                super.addTransaction(new Transaction(transaction.getAmount(), TRANSACTION_TYPES.DEBIT, transaction.getText()));
            } else if (transaction.getType() == Bank1Transaction.TYPE_CREDIT) {
                super.addTransaction(new Transaction(transaction.getAmount(), TRANSACTION_TYPES.CREDIT, transaction.getText()));
            }
            
        }
    }

}
