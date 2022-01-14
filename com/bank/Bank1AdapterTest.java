package com.bank;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import com.bank1.integration.Bank1AccountSource;

import org.junit.Before;
import org.junit.Test;

public class Bank1AdapterTest {
    private Bank bank;
    private long accountID;
    private Date startDate;
    private Date endDate;

    @Before
    public void setup() {
        bank = new Bank1Adapter(new Bank1AccountSource());
        accountID = 0L;
        startDate = new Date();
        endDate = new Date();
    }

    @Test
    public void testGetBalance() {
        double currBalance = bank.getBalance(accountID);
        assertTrue("Warning, balance is negative!", currBalance > 0);
    }

    @Test
    public void testGetCurrency() {
        String currency = bank.getCurrency(accountID);
        assertTrue("Nothing returned for currency!", currency != null);
        assertTrue("Currency is empty!", !currency.isBlank());
    }

    @Test
    public void testGetTransactions() {
        List<Transaction> transactions = bank.getTransactions(accountID, startDate, endDate);
        assertTrue("Transactions list is null!", transactions != null);
        for (Transaction transaction : transactions) {
            assertTrue("Transaction is null!", transaction != null);
            assertTrue("Transaction is blank", !transaction.getText().isBlank());
            assertTrue("Transaction is empty", !transaction.getText().isEmpty());
            assertTrue("Transaction type is null (should be debit or credit).", !(transaction.getType() == null));
        }
    }
}
