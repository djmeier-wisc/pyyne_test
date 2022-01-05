package com.bank;
import java.util.List;

public interface Bank {
    public double getBalance();
    public String getCurrency();
    public List<Transaction> getTransactions();
}
