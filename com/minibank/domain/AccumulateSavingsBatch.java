package com.minibank.domain;

import com.minibank.domain.*;

public class AccumulateSavingsBatch {

  public AccumulateSavingsBatch() {
  }

  public void doBatch() {

      Bank bank= Bank.getBank();
    // For each customer...
    for ( int cust_idx = 0;
          cust_idx < bank.getNumberOfClient();
          cust_idx++ ) {
      Customer customer = bank.getCustomer(cust_idx);

      // For each account for this customer...
      for ( int acct_idx = 0;
            acct_idx < customer.getNumberOfAccounts();
            acct_idx++ ) {
        Account account = customer.getAccount(acct_idx);
        String  account_type = "";

        // Determine the account type
        if (account instanceof SavingAccount savings) {
          savings.addPercents();

// СТАРЫЙ МЕТОД  , БОЛЕЕ ДЛИННЫЙ
// if ( account instanceof SavingAccount ) {
//   SavingAccount savings = (SavingAccount) account;
//   savings.addPercents();

        } else {
          // ignore all other account types
        }
      }
    }
  }
}
