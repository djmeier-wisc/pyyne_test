package com.pyyne.challenge.bank;

import com.bank.Bank;
import com.bank.Bank1Adapter;
import com.bank.Bank2Adaptor;

import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import com.bank.Transaction;
import com.bank.Transaction.TRANSACTION_TYPES;
import com.bank1.integration.Bank1AccountSource;
import com.bank2.integration.Bank2AccountSource;

/**
 * Controller that pulls information form multiple bank integrations and prints
 * them to the console.
 *
 * Created by Par Renyard on 5/12/21.
 */
public class BankController {
    private Bank bank1Account;
    private Bank bank2Account;

    /**
     * Sets up bankController, with Bank1AccountSource and Bank2AccountSource.
     */
    public BankController() {
        this.bank1Account = new Bank1Adapter(new Bank1AccountSource());
        this.bank2Account = new Bank2Adaptor(new Bank2AccountSource());
    }

    /**
     * Prints the balances of both banks in the following format:
     * Bank1: XXX.XX CURR
     * Bank2: XXX.XX CURR
     */
    public void printBalances(long bank1ID, long bank2ID) {
        System.out.printf("Bank1: %.2f %s\n", bank1Account.getBalance(bank1ID), bank2Account.getCurrency(bank1ID));
        System.out.printf("Bank2: %.2f %s\n", bank2Account.getBalance(bank2ID), bank2Account.getCurrency(bank2ID));
    }

    /**
     * Prints a list of transactions made by both bank accounts in the following
     * format:
     * |CREDIT |XXXXXXXXXXXXX |100.0 |
     * |DEBIT |XXX XXX XXXXX |25.5 |
     * |DEBIT |XXXXXXXXXXXX |225.0 |
     * |DEBIT |XXXXXXXXXXXX |125.0 |
     * |DEBIT |XXX XXXXXXXXX |500.0 |
     * |CREDIT |XXXXXX |800.0 |
     */
    public void printTransactions(long bank1ID, long bank2ID, Date startDate, Date endDate) {
        List<Transaction> transactions = bank1Account.getTransactions(bank1ID, startDate, endDate);
        transactions.addAll(bank2Account.getTransactions(bank2ID, startDate, endDate));
        System.out.format("|%-10s|%-20s|%-10s|\n", "Type", "Text", "Amount");
        for (Transaction t : transactions) {
            String tTypeText = t.getType() == TRANSACTION_TYPES.DEBIT ? "DEBIT" : "CREDIT";
            System.out.format("|%-10s|%-20s|%-10s|\n", tTypeText, t.getText(),
                    t.getAmount());
        }
    }
}
