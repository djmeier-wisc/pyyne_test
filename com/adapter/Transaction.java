package com.adapter;

/**
 * A class to capture information about transactions for bank1 and bank2.
 */
public class Transaction implements Comparable<Transaction> {
    public static enum TRANSACTION_TYPES {
        DEBIT, CREDIT
    }

    private double amount; // stores the price of the transaction
    private TRANSACTION_TYPES type;
    private String text;

    public Transaction(double amount, TRANSACTION_TYPES type, String text) {
        this.amount = amount;
        this.type = type;
        this.text = text;
    }

    public double getAmount() {
        return amount;
    }

    public TRANSACTION_TYPES getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Transaction))
            return false;
        Transaction t2 = (Transaction) o;

        return Double.compare(this.getAmount(), t2.getAmount()) == 0 && this.getType() == t2.getType()
                && this.getText().equals(t2.getText());
    }

    @Override
    public int compareTo(Transaction o) {
        if (o == this)
            return 0;
        if (!(o instanceof Transaction))
            return -1;
        Transaction t2 = (Transaction) o;

        return (int) (this.getAmount() - t2.getAmount()) + this.getText().compareTo(t2.getText())
                + (this.getType() == t2.getType() ? 0 : 1);
    }
}
