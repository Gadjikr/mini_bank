package com.minibank.domain;

import java.util.ArrayList;

public class Bank {

    private static final Bank myBank = new Bank();
    private final ArrayList<Customer> customers = new ArrayList<>();
    private int numberOfClient = 0;

    private Bank() {
    }

    public static Bank getBank() {
        return myBank;
    }

    public Customer getCustomer(int numberOfCustomer) {


        if (numberOfCustomer < customers.size() && customers.get(numberOfCustomer) != null) {
            return customers.get(numberOfCustomer);
        }

        System.out.println("false in getCustomer");
        return null;
    }

    //разобраться почему делаем так, а не  customers[numberOfClients] = new Customer("FIO")
    //клиент создаётся в отдельной строке, в точке входа , а в этой только добавляется
    public void addCustomer(Customer newCustomer) {
        customers.add(newCustomer);
        numberOfClient++;
    }

    public int getNumberOfClient() {
        return numberOfClient;
    }


}
