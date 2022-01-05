package com.bank;

import com.bank2.integration.Bank2AccountTransaction;
import java.util.Date;
import java.util.List;

import com.bank2.integration.Bank2AccountBalance;
import com.bank2.integration.Bank2AccountSource;

/**
 * Allows for the adaption of a Bank2Account source into a common NewBank object.
 */
public class Bank2Adaptor extends NewBank {

    /**
     * Takes in a Bank2AccountSource object, along with an accountID and start and
     * end date, allowing for its access via Bank methods.
     * 
     * @param oldBank The Bank2AccountSource object to be converted
     * @param accountID The account number, in the form of a long int.
     * @param fromDate The start date to begin including transactions.
     * @param endDate The end date to stop inculding transacitons.
     */
    public Bank2Adaptor(Bank2AccountSource oldBank, long accountID, Date fromDate, Date endDate) {
        super(oldBank.getBalance(accountID).getCurrency(), oldBank.getBalance(accountID).getBalance());
        List<Bank2AccountTransaction> transactions = oldBank.getTransactions(accountID, fromDate, endDate);
        for (Bank2AccountTransaction currTransaction : transactions) {
            if (currTransaction.getType() == Bank2AccountTransaction.TRANSACTION_TYPES.CREDIT)
                super.addTransaction(new Transaction(currTransaction.getAmount(),
                        Transaction.TRANSACTION_TYPES.CREDIT, currTransaction.getText()));
            else if (currTransaction.getType() == Bank2AccountTransaction.TRANSACTION_TYPES.DEBIT)
                super.addTransaction(new Transaction(currTransaction.getAmount(),
                        Transaction.TRANSACTION_TYPES.DEBIT, currTransaction.getText()));
        }
    }

}
