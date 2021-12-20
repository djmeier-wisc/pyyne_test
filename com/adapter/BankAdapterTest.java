package com.adapter;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import com.adapter.Transaction.TRANSACTION_TYPES;
import com.bank1.integration.Bank1AccountSource;
import com.bank1.integration.Bank1Transaction;
import com.bank2.integration.Bank2AccountSource;
import com.bank2.integration.Bank2AccountTransaction;

import org.junit.Before;
import org.junit.Test;

public class BankAdapterTest {
    BankAdapter adapter;
    Bank1AccountSource bank1;
    Bank2AccountSource bank2;
    long BANK_1_ACC_NUM;
    long BANK_2_ACC_NUM;

    @Before
    public void setup() {
        bank1 = new Bank1AccountSource();
        bank2 = new Bank2AccountSource();
        adapter = new BankAdapter(bank1, bank2);

        BANK_1_ACC_NUM = 0L;
        BANK_2_ACC_NUM = 0L;
    }

    /**
     * This tests getting the balance, including what sum should be present and
     * making sure that no exceptions are thrown.
     * 
     * @throws DataFormatException - If the currencies don't match up, this doesn't
     *                             fit our predefinined banks.
     */
    @Test
    public void testGetBalance() throws DataFormatException {
        double[] calcSumBalances = adapter.getBalances(BANK_1_ACC_NUM, BANK_2_ACC_NUM);
        double[] actualBalances = new double[] { bank1.getAccountBalance(BANK_1_ACC_NUM),
                bank2.getBalance(BANK_2_ACC_NUM).getBalance() };
        assertTrue("bank1 has incorrect balance!", calcSumBalances[0] == actualBalances[0]);
        assertTrue("bank2 has incorrect balance!", calcSumBalances[1] == actualBalances[1]);
    }

    /**
     * 
     */
    @Test
    public void testGetSharedCurrency() throws DataFormatException {
        String bank1Currency = bank1.getAccountCurrency(BANK_1_ACC_NUM);
        String bank2Currency = bank2.getBalance(BANK_2_ACC_NUM).getCurrency();
        String sharedCurrency = adapter.getSharedCurrency(BANK_1_ACC_NUM, BANK_2_ACC_NUM);

        assertTrue("Bank1 and Bank2 must share a currency!", bank1Currency.equals(bank2Currency));
        assertTrue("getSharedCurrency does not match bank1!", sharedCurrency.equals(bank1Currency));
        assertTrue("getSharedCurrency does not match bank1!", sharedCurrency.equals(bank2Currency));
    }

    @Test
    public void testGetTransactions() {
        Date startDate = Date.from(LocalDate.of(2020, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Transaction> transactions = adapter.getTransactions(BANK_1_ACC_NUM, BANK_2_ACC_NUM, startDate, endDate);
        List<Bank1Transaction> bank1Transactions = bank1.getTransactions(BANK_1_ACC_NUM, startDate, endDate);
        List<Bank2AccountTransaction> bank2AccountTransactions = bank2.getTransactions(BANK_2_ACC_NUM, startDate,
                endDate);

        ArrayList<Transaction> realTransactions = new ArrayList<Transaction>();

        for (Bank1Transaction transaction : bank1Transactions) {
            realTransactions.add(new Transaction(transaction.getAmount(),
                    ((transaction.getType() == Bank1Transaction.TYPE_CREDIT) ? TRANSACTION_TYPES.CREDIT
                            : TRANSACTION_TYPES.DEBIT),
                    transaction.getText()));
        }
        for (Bank2AccountTransaction transaction : bank2AccountTransactions) {
            realTransactions.add(new Transaction(transaction.getAmount(),
                    ((transaction.getType() == com.bank2.integration.Bank2AccountTransaction.TRANSACTION_TYPES.CREDIT)
                            ? TRANSACTION_TYPES.CREDIT
                            : TRANSACTION_TYPES.DEBIT),
                    transaction.getText()));
        }

        for (Transaction transaction : realTransactions) {
            assertTrue("Error, found missed transaction.", transactions.remove(transaction));
        }
        assertTrue("Error, found extra transaction.", transactions.size() == 0);
    }
}
