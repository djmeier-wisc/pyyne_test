package com.pyyne.challenge.bank;

import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import com.adapter.BankAdapter;
import com.adapter.Transaction;
import com.adapter.Transaction.TRANSACTION_TYPES;
import com.bank1.integration.Bank1AccountSource;
import com.bank2.integration.Bank2AccountSource;

/**
 * Controller that pulls information form multiple bank integrations and prints
 * them to the console.
 *
 * Created by Par Renyard on 5/12/21.
 */
public class BankController {
    private long bank1Account;
    private long bank2Account;

    /**
     * Instantiates the BankController class to use bank1Account to gather info from
     * bank1, and bank2Account to gather info from bank2.
     * 
     * @param bank1Account - Used to access bank1's information.
     * @param bank2Account - Used to access bank2's information.
     */
    public BankController(long bank1Account, long bank2Account) {
        this.bank1Account = bank1Account;
        this.bank2Account = bank2Account;
    }

    /**
     * Prints the balances of both banks in the following format:
     * Bank1: XXX.XX
     * Bank2: XXX.XX
     */
    public void printBalances() {
        BankAdapter bankAdapter = new BankAdapter(new Bank1AccountSource(), new Bank2AccountSource());
        double balances[];
        try {
            balances = bankAdapter.getBalances(this.bank1Account, this.bank2Account);
        } catch (DataFormatException e) {
            System.out.println("Warning, bank accounts do not share a common currency.");
            return;
        }
        System.out.printf("Bank1: %.2f\n", balances[0]);
        System.out.printf("Bank2: %.2f\n", balances[1]);
    }

    /**
     * Prints a list of transactions made by both bank accounts in the following
     * format:
     * |CREDIT |XXXXXXXXXXXXX |100.0 |
     * |DEBIT |XXXXXXXXXXXXXXXXXXX |25.5 |
     * |DEBIT |XXXXXXXXXXXX |225.0 |
     * |DEBIT |XXXXXXXXXXXX |125.0 |
     * |DEBIT |XXX XXXXXXXXX |500.0 |
     * |CREDIT |XXXXXX |800.0 |
     */
    public void printTransactions() {
        BankAdapter bankAdapter = new BankAdapter(new Bank1AccountSource(), new Bank2AccountSource());
        List<Transaction> transactions = bankAdapter.getTransactions(this.bank2Account, this.bank2Account, new Date(),
                new Date());
        System.out.format("|%-10s|%-20s|%-10s|\n", "Type", "Text", "Amount");
        for (Transaction t : transactions) {
            String tTypeText = t.getType() == TRANSACTION_TYPES.DEBIT ? "DEBIT" : "CREDIT";
            try {
                System.out.format("|%-10s|%-20s|%-10s|\n", tTypeText, t.getText(), t.getAmount() + " " + bankAdapter.getSharedCurrency(bank1Account, bank2Account));
            } catch (DataFormatException e) {
                System.out.println("Bank1 and Bank2 do not share a common currency!");
            }
        }
    }
}
