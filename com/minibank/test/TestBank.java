package com.minibank.test;

import com.minibank.domain.*;

public class TestBank {
    public static void main(String[] args) {

        Bank bank = Bank.getBank();

        Customer customer1 = new Customer("Ivan", "Averin");
        Customer customer2 = new Customer("Oleg", "Germanov");

        SavingAccount costomer1SaveAcc = new SavingAccount(5);
        CheckingAccount costomer1CheckAcc = new CheckingAccount(5000);
        CheckingAccount costomer2CheckAcc = new CheckingAccount();


        customer1.addAccount(costomer1SaveAcc);
        customer1.addAccount(costomer1CheckAcc);
        customer2.addAccount(costomer2CheckAcc);

        bank.addCustomer(customer1);
        bank.addCustomer(customer2);

        System.out.println(bank.getCustomer(0));
        System.out.println(bank.getCustomer(1));
//        System.out.println(bank.getCustomer(9001));
//        System.out.println(bank.getCustomer(901));


        bank.getCustomer(0).getAccount(0).deposit(4000);
        System.out.println(bank.getCustomer(0).getAccount(0).getBalance());
        try {
            bank.getCustomer(0).getAccount(0).withDraw(720);
        } catch (OverdraftExeption ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(bank.getCustomer(0).getAccount(0).getBalance());


        try {
            bank.getCustomer(0).getAccount(1).withDraw(25000);
        } catch (OverdraftExeption ex) {
            System.out.println(ex.getMessage() + ": " + ex.getDeficit());
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(bank.getCustomer(0).getAccount(1).getBalance());

        ((SavingAccount) bank.getCustomer(0).getAccount(0)).addPercents();

        // если попробовать начислить проценты на чековый аккаунт , то будет ошибка
        //        ((SavingAccount) myBank.getCustomer(1).getAccount(0)).addPercents();

        System.out.println("\n\n");
        for (int i = 0; i < bank.getNumberOfClient(); i++) {
            System.out.println(bank.getCustomer(i));
        }

    }
}
