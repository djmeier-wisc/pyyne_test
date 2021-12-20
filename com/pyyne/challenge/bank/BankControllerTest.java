package com.pyyne.challenge.bank;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import com.adapter.BankAdapter;
import com.adapter.Transaction;
import com.adapter.Transaction.TRANSACTION_TYPES;
import com.bank1.integration.Bank1AccountSource;
import com.bank2.integration.Bank2AccountSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BankControllerTest {
    PrintStream prevConsole;
    ByteArrayOutputStream newConsole;
    BankAdapter bankAdapter;
    long BANK_1_ACC_NUM;
    long BANK_2_ACC_NUM;


    @Before
    public void setup() {
        // capture output stream
        prevConsole = System.out;
        newConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newConsole));

        BANK_1_ACC_NUM = 0L;
        BANK_2_ACC_NUM = 0L;

        bankAdapter = new BankAdapter(new Bank1AccountSource(), new Bank2AccountSource());
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
        BankController bank = new BankController(BANK_1_ACC_NUM, BANK_2_ACC_NUM);
        bank.printBalances();

        String outputString = newConsole.toString();
        double accBalances[] = bankAdapter.getBalances(BANK_1_ACC_NUM, BANK_2_ACC_NUM);

        assertTrue("Output string didn't contain bank1's balance!", outputString.contains(String.valueOf(accBalances[0])));
        assertTrue("Output string didn't contain bank2's balance!", outputString.contains(String.valueOf(accBalances[1])));
    }

    @Test
    public void testPrintTransactions() {
        BankController bank = new BankController(BANK_1_ACC_NUM, BANK_2_ACC_NUM);
        bank.printTransactions();

        String outputString = newConsole.toString();

        List<Transaction> transactions = bankAdapter.getTransactions(BANK_1_ACC_NUM, BANK_2_ACC_NUM, new Date(), new Date());

        for (Transaction t : transactions) {
            String tTypeText = t.getType() == TRANSACTION_TYPES.DEBIT ? "DEBIT" : "CREDIT";
            assertTrue("Missed transaction text: " + t.getText(), outputString.contains(t.getText()));
            assertTrue("Missed transaction amount: " + t.getAmount(), outputString.contains(String.valueOf(t.getAmount())));
            assertTrue("Missed transaction text: " + t.getText(), outputString.contains(tTypeText));
        }
    }
}
