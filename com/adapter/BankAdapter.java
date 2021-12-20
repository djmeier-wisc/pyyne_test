package com.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import com.adapter.Transaction.TRANSACTION_TYPES;
import com.bank1.integration.Bank1AccountSource;
import com.bank1.integration.Bank1Transaction;
import com.bank2.integration.Bank2AccountSource;
import com.bank2.integration.Bank2AccountTransaction;

/**
 * @author Douglas Meier
 * @see pyyne.challenge.bank.BankController
 */
public class BankAdapter {
    private Bank1AccountSource bank1;
    private Bank2AccountSource bank2;
    /**
     * Creates an adapter between two bank classes, Bank1AccountSource and Bank2AccountSource.
     * @param bank1 - The instantiated Bank1AccountSource.
     * @param bank2 - The instantiated Bank2AccountSource.
     */
    public BankAdapter(Bank1AccountSource bank1, Bank2AccountSource bank2) {
        this.bank1 = bank1;
        this.bank2 = bank2;
    }
    /**
     * Gets the summation of the balances of both bank accounts.
     * 
     * @param bank1AccNum - The account number for bank1.
     * @param bank2AccNum = The account number for bank2.
     * @return - The summation of both balances.
     * @exception DataFormatException - Indicates the bank account currencies do not
     *                                line up, so they cannot be added.
     */
    public double getBalance(long bank1AccNum, long bank2AccNum) throws DataFormatException {
        double bank1Balance = bank1.getAccountBalance(bank1AccNum);
        double bank2Balance = bank2.getBalance(bank2AccNum).getBalance();

        // confirms that currencies match before adding them
        if (!currencyCheck(bank1AccNum, bank2AccNum)) {
            throw new DataFormatException("Bank1 and Bank2 do not share a common currency.");
        }
        return bank1Balance + bank2Balance;
    }

    /**
     * Gets the shared currency of both bank accounts, throwing a
     * DataFormatException if the currency for the bank accounts do not match.
     * 
     * @param bank1AccNum - The account number for bank1.
     * @param bank2AccNum - The account number for bank2.
     * @return - The shared currency of both bank accounts, in the form of a string.
     *         IE "USD"
     * @exception DataFormatException - Indicates the bank account currencies do not
     *                                line up, so we cannot return a currency.
     */
    public String getSharedCurrency(long bank1AccNum, long bank2AccNum) throws DataFormatException {
        if (!currencyCheck(bank1AccNum, bank2AccNum)) {
            throw new DataFormatException("Bank1 and Bank2 do not share a common currency.");
        }
        return bank1.getAccountCurrency(bank1AccNum);
    }

    /**
     * Takes in two account numbers, one for bank1 and the other for bank2. It
     * checks that they are using the same currency.
     * 
     * @param bank1AccNum - The account number used for bank1.
     * @param bank2AccNum - The account number used for bank2.
     * @return true if both banks share the same currency for the accounts provided.
     */
    private boolean currencyCheck(long bank1AccNum, long bank2AccNum) {
        return bank1.getAccountCurrency(bank1AccNum).equals(bank2.getBalance(bank2AccNum).getCurrency());
    }

    /**
     * Gets the list of transactions, returning them as a list of type Transaction.
     * 
     * @param bank1AccNum - The account information for bank1.
     * @param bank2AccNum - The account information for bank2.
     * @param startDate   - The first inclusive day that transactions will be
     *                    searched.
     * @param endDate     - The last inclusive day that transactions will be
     *                    searched.
     * @return An ArrayList of transactions.
     */
    public List<Transaction> getTransactions(long bank1AccNum, long bank2AccNum, Date startDate, Date endDate) {
        ArrayList<Transaction> transactions = new ArrayList<>(); // This will be used to build up our transactions.

        List<Bank1Transaction> bank1Transactions = bank1.getTransactions(bank1AccNum, startDate, endDate);
        List<Bank2AccountTransaction> bank2Transactions = bank2.getTransactions(bank2AccNum, startDate, endDate);

        for (int i = 0; i < bank1Transactions.size(); i++) {
            Bank1Transaction currTransaction = bank1Transactions.get(i);

            double currAmount = currTransaction.getAmount();
            String currText = currTransaction.getText();
            // map transaction type to custom object
            TRANSACTION_TYPES currType = ((currTransaction.getType() == 1)
                    ? Transaction.TRANSACTION_TYPES.CREDIT
                    : Transaction.TRANSACTION_TYPES.DEBIT);

            transactions.add(new Transaction(currAmount, currType, currText));
        }

        for (int i = 0; i < bank2Transactions.size(); i++) {
            Bank2AccountTransaction currTransaction = bank2Transactions.get(i);

            double currAmount = currTransaction.getAmount();
            String currText = currTransaction.getText();
            TRANSACTION_TYPES currType = ((currTransaction
                    .getType() == Bank2AccountTransaction.TRANSACTION_TYPES.CREDIT) ? TRANSACTION_TYPES.CREDIT
                            : TRANSACTION_TYPES.DEBIT);

            transactions.add(new Transaction(currAmount, currType, currText));
        }
        return transactions;
    }
}
