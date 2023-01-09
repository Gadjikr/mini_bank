package com.minibank.domain;

public class OverdraftExeption extends Exception {

    private double deficit;

    public OverdraftExeption(String message, double deficit) {
        super(message);
        this.deficit = deficit;
    }

    public double getDeficit() {
//    public double getDeficit(double withdrawAmount, double balance, double overdraftAmount) {
//        deficit = withdrawAmount - (balance + overdraftAmount);
        return deficit;
    }
}
