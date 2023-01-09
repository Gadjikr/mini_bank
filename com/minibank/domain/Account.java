package com.minibank.domain;

import com.minibank.effect.Effects;

import java.util.concurrent.TimeUnit;

/**
 * bank accounts class
 * default constructor make balance = "0"
 *
 * @author Gadjik
 */
public class Account {
    protected double balance = 0;

    /**
     * only default constructor , makes balance "0"
     */
    public Account() {
        this.balance = 0;
    }

    final String RESET = "\u001B[0m";
    final String RED = "\u001B[31m";

    /**
     * method to see balance
     *
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * add coins to account (only if amount positive)
     *
     * @param amount only positive
     */
    public void deposit(double amount) {
        if (amount > 0) {
            System.out.println("\ncurrent balance : " + balance + ", \ndeposit amount : " + amount);
            this.balance = balance + amount;

            Effects.pause(3);

            System.out.println("new balance : " + balance);
        } else {
            System.out.println("\ndeposit amount '" + amount + "' is incorrect" + "\ncurrent balance : " + balance);
        }
    }

    /**
     * withdraw coins
     *
     * @param amount must be less than balance
     */
    public void withDraw(double amount) throws Exception {
        System.out.println("\ncurrent balance : " + balance);
        System.out.println("amount to withdraw : " + amount);
        double newBalance = balance - amount;

        if (newBalance < 0) {
            System.out.println(RED + "You have not enough money to withdraw " + amount + " coins" + RESET);
        } else if (amount <= 0) {
            System.out.println("incorrect amount !");
        } else {
            balance = newBalance;
            System.out.print("withdraw");

            Effects.pause(3);

            System.out.println("success ! ");
        }

    }

//        System.out.println("Your balance is - " + balance);

}

