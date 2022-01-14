package com.pyyne.challenge.bank;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import com.bank.Bank;
import com.bank.Bank1Adapter;
import com.bank.Bank2Adaptor;
import com.bank.Transaction;
import com.bank.Transaction.TRANSACTION_TYPES;
import com.bank1.integration.Bank1AccountSource;
import com.bank2.integration.Bank2AccountSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BankControllerTest {
    PrintStream prevConsole;
    ByteArrayOutputStream newConsole;
    long BANK_1_ACC_NUM;
    long BANK_2_ACC_NUM;
    Bank bank1;
    Bank bank2;

    @Before
    public void setup() {
        // capture output stream
        prevConsole = System.out;
        newConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newConsole));

        BANK_1_ACC_NUM = 0L;
        BANK_2_ACC_NUM = 0L;

        bank1 = new Bank1Adapter(new Bank1AccountSource());
        bank2 = new Bank2Adaptor(new Bank2AccountSource());
    }

    @After
    public void cleanup() {
        System.setOut(prevConsole);
        try {
            newConsole.close();
        } catch (IOException e) {
            System.out.println("Failed to close newConsole.");
            e.printStackTrace();
        }
    }

    @Test
    public void testPrintBalances() throws DataFormatException {
        BankController bank = new BankController();
        bank.printBalances(BANK_1_ACC_NUM, BANK_2_ACC_NUM);

        String outputString = newConsole.toString();

        assertTrue("Output string didn't contain bank1's balance!", outputString.contains(String.valueOf(bank1.getBalance(BANK_1_ACC_NUM))));
        assertTrue("Output string didn't contain bank2's balance!", outputString.contains(String.valueOf(bank2.getBalance(BANK_2_ACC_NUM))));
    }

    @Test
    public void testPrintTransactions() {
        BankController bank = new BankController();
        Date startDate = new Date();
        Date endDate = new Date();
        bank.printTransactions(BANK_1_ACC_NUM, BANK_2_ACC_NUM, startDate, endDate);

        String outputString = newConsole.toString();

        List<Transaction> transactions = bank1.getTransactions(BANK_1_ACC_NUM, startDate, endDate);
        transactions.addAll(bank2.getTransactions(BANK_2_ACC_NUM, startDate, endDate));

        for (Transaction t : transactions) {
            String tTypeText = t.getType() == TRANSACTION_TYPES.DEBIT ? "DEBIT" : "CREDIT";
            assertTrue("Missed transaction text: " + t.getText(), outputString.contains(t.getText()));
            assertTrue("Missed transaction amount: " + t.getAmount(), outputString.contains(String.valueOf(t.getAmount())));
            assertTrue("Missed transaction text: " + t.getText(), outputString.contains(tTypeText));
        }
    }
}
