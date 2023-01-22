package com.minibank.domain;

import com.minibank.effect.Effects;

/**
 * allow to use overdraft
 */

public class CheckingAccount extends Account {

    private double overdraftAmount;

    public CheckingAccount() {
        super();
        overdraftAmount = 0;
    }

    public CheckingAccount(double overdraftAmount) {
        super();
        this.overdraftAmount = overdraftAmount;
    }

    public CheckingAccount(double overdraftAmount, double balance) {
        this.balance = balance;
        this.overdraftAmount = overdraftAmount;
    }


    /**
     * @param amount must be less than (balance and overdraft sum)
     */
    @Override
    public void withDraw(double amount) throws OverdraftExeption {
        System.out.println("\ncurrent balance : " + balance);
        System.out.println("amount to withdraw : " + amount);
        double newBalance = balance - amount;

        if (newBalance < -overdraftAmount) {
//            System.out.println(RED + "You have not enough money to withdraw," +
//                    RESET + " try  " + (balance + overdraftAmount)  + " coins" );
            throw new OverdraftExeption(RED + "You have not enough money to withdraw !," +
                    " deficit of funds is " + RESET, -(newBalance + overdraftAmount));

//            Effects.pauseSilent(3);
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

    public double getOverdraftAmount() {
        return overdraftAmount;
    }
}



