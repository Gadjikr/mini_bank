package com.minibank.domain;

import java.util.ArrayList;

public class Customer {
    private final ArrayList<Account> accounts;

    private int numberOfAccounts;

    private final String firstName;
    private final String lastName;

    private final int customerID;

    private static int customerNumberInBase = 1000;


    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        accounts = new ArrayList<>();
        this.customerID = customerNumberInBase++;
        numberOfAccounts = 0;
    }

    public Account getAccount(int accountNumber) {
        if (accountNumber < accounts.size() && accounts.get(accountNumber) != null && numberOfAccounts != 0) {
            return accounts.get(accountNumber);
        } else {
            System.out.println("wrong account number !");
            return null;
        }
    }

    public void addAccount(Account account) {
        if (numberOfAccounts < 10) {
            accounts.add(account);
            numberOfAccounts++;
        } else {
            System.out.println("You can't use more than 10 accounts");
        }
    }

    @Override
    public String toString() {
        String s = "\nCustomer{" +
                "customer ID=" + customerID +
                ", First Name='" + firstName + '\'' +", Last Name='" + lastName + '\'' +
                ", Number of active Accounts=" + numberOfAccounts +
                '}';
        for (int i = 0; i < numberOfAccounts; i++) {
            if (getAccount(i) instanceof SavingAccount) {
                s = s + "\nсчет номер " + (i + 1) + ", тип счёта - сберегательный, " +
                        " процент годовых = " + ((SavingAccount) getAccount(i)).increaseRate +
                        ", баланс " + accounts.get(i).balance;
            } else {
                s = s + "\nсчет номер " + (i + 1) + ", тип счёта - расчётный," +
                        "кредитная линия = " + ((CheckingAccount) getAccount(i)).getOverdraftAmount() +
                        " монет, " + " баланс = " + accounts.get(i).balance;
            }

        }
        return s;
    }
}
