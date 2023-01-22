package com.minibank.data;

import com.minibank.domain.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DataSource {
    private File dataFile;

    public DataSource(String dataFilePath) {
        dataFile = new File(dataFilePath);
    }

    public void loadData() throws IOException {
        Scanner input = new Scanner(dataFile);
        Bank bank = Bank.getBank();

        Customer customer;
        int numberOfCustomer = input.nextInt();

        for (int i = 0; i < numberOfCustomer; i++) {
            String firstName = input.next();
            String lastName = input.next();
            bank.addCustomer(new Customer(firstName, lastName));
            customer = bank.getCustomer(i);

            int numberOfAccounts = input.nextInt();

            while (numberOfAccounts-- > 0) {
                char accountType = input.next().charAt(0);

                switch (accountType) {
                    case 'S' -> {
                        float setBalance = input.nextFloat();
                        float increaseRate = input.nextFloat();
                        customer.addAccount(new SavingAccount(increaseRate, setBalance));
                    }
                    case 'C' -> {
                        float setBalance = input.nextFloat();
                        float overdraftAmount = input.nextFloat();
                        customer.addAccount(new CheckingAccount(overdraftAmount, setBalance));
                    }
                }

//             СТАРАЯ ЗАПИСЬ ИСПОЛЬЗУЕТ ДВОЕТОЧИЕ , ВМЕСТО СТРЕЛКИ И ТРЕБУЕТ ОПЕРАТОР BREAK В КОНЦЕ БЛОКА CASE
//                switch (accountType) {
//                    case 'S': {
//                        float setBalance = input.nextFloat();
//                        float increaseRate = input.nextFloat();
//                        customer.addAccount(new SavingAccount(increaseRate, setBalance));
//                        break;
//                    }
//                    case 'C': {
//                        float setBalance = input.nextFloat();
//                        float overdraftAmount = input.nextFloat();
//                        customer.addAccount(new CheckingAccount(overdraftAmount, setBalance));
////                        customer.getAccount(numberOfAccounts).deposit(setBalance);
//                        break;
//                    }
//                }


            }

        }

    }


}
