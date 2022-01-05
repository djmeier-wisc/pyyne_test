package com.adapter;

/**
 * A class to capture information about transactions for bank1 and bank2.
 */
public class Transaction implements Comparable<Transaction> {
    /**
     * Enables comparison between transaction types
     */
    public static enum TRANSACTION_TYPES {
        DEBIT, CREDIT
    }

    private double amount; // stores the price of the transaction
    private TRANSACTION_TYPES type; // stores whether the transaction was debit or credit
    private String text; // stores details regarding the transaction

    /**
     * Constructor for an individual transaction, including the amount, type (debit
     * or credit) and text.
     * 
     * @param amount - A double containing the price of the transaction.
     * @param type   - Whether the transaction was credit or debit.
     * @param text   - Details regarding the transaction in the form of the a
     *               string.
     */
    public Transaction(double amount, TRANSACTION_TYPES type, String text) {
        this.amount = amount;
        this.type = type;
        this.text = text;
    }

    /**
     * Gets the amount.
     * 
     * @return - The amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the type (debit or credit).
     * 
     * @return The type (debit / credit).
     */
    public TRANSACTION_TYPES getType() {
        return type;
    }

    /**
     * Gets the text of the transaction, which is generally details of the seller.
     * 
     * @return - The text.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns equals if the two objects share amount, type, and text attributes.
     */
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

    /**
     * Compares different transactions, with regard to amount only.
     * 
     * @param o - The object upon which the transaction will be compared.
     * @return - The difference between the amounts of the two transactions.
     */
    @Override
    public int compareTo(Transaction o) {
        if (o == this)
            return 0;
        if (!(o instanceof Transaction))
            return -1;
        Transaction t2 = (Transaction) o;

        return (int) (this.getAmount() - t2.getAmount());
    }

    @Override
    public String toString() {
        return "Transaction [amount=" + amount + ", text=" + text + ", type=" + type + "]";
    }
}
