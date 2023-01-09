package com.minibank.domain;

import com.minibank.effect.Effects;

/**
 * if money in this account more then some period, they increase with some percent
 */
public class SavingAccount extends Account {
    int increaseRate;

    public SavingAccount(int increaseRate) {
        this.increaseRate = increaseRate;
    }


    @Override
    public void withDraw(double amount) {

        System.out.println("\ncurrent balance (with percents): " + (balance + (balance * increaseRate / 100)));
        System.out.println("amount to withdraw : " + amount);
        double newBalance = balance + (balance * increaseRate / 100) - amount;

        if (newBalance < 0) {
            System.out.println(RED + "You have not enough money to withdraw " + amount + " coins" + RESET);
        } else if (amount <= 0) {
            System.out.println("incorrect amount !");
            Effects.pauseSilent(3);
        } else {
            balance = newBalance;
            System.out.print("withdraw");
            Effects.pause(3);
            System.out.println("success ! ");
        }
    }

    public void addPercents() {
        System.out.println("It's time to add percents !");
        System.out.println("Old balance = " + balance);
        balance += balance * increaseRate / 100;
        System.out.println("New balance = " + balance);
        Effects.pauseSilent(3);
    }

}
