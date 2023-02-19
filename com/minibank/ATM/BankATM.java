package com.minibank.ATM;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.minibank.domain.Bank;
import com.minibank.domain.CheckingAccount;
import com.minibank.domain.Customer;
import com.minibank.domain.SavingAccount;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;


public class BankATM extends JFrame {

    Bank bank;
    Customer currentCustomer;
    int currentAccount = -1;
    private double sum = 0;
    private boolean severalAccountDeposit = false;
    private double withdrawAmount;
    boolean inputAccountNumToWithdraw = false;


    private JPanel mainPanel;
    private JPanel leftPanel = new JPanel();
    private JButton checkBalanceButton = new JButton();
    private JButton makeDepositButton = new JButton();
    private JButton makeWithdrawalButton = new JButton();
    private JButton n1Button = new JButton();
    private JButton n2Button = new JButton();
    private JButton n3Button = new JButton();
    private JButton n4Button = new JButton();
    private JButton n5Button = new JButton();
    private JButton n6Button = new JButton();
    private JTextField amountField = new JTextField();
    private JButton n7Button = new JButton();
    private JButton n8Button = new JButton();
    private JButton n9Button = new JButton();
    private JTextField statusField = new JTextField();
    private JButton n0Button = new JButton();
    private JButton ENTERButton = new JButton();
    private JTextArea historyField = new JTextArea();
    private JButton pointButton = new JButton();
    private JButton clearButton;
    private JPanel numButtonPanel;

    public BankATM() {
        $$$setupUI$$$();

        bank = Bank.getBank();

        Customer customer1 = new Customer("Ivan", "Averin");
        Customer customer2 = new Customer("Oleg", "Germanov");
        Customer customer3 = new Customer("Dmitry", "Malikov");
        Customer customer4 = new Customer("Igor", "Debrov");

        SavingAccount costomer1SaveAcc = new SavingAccount(5, 7500);
        CheckingAccount costomer1CheckAcc = new CheckingAccount(3000, 5000);
        SavingAccount costomer2SaveAcc = new SavingAccount(3, 7290);
        CheckingAccount costomer2CheckAcc = new CheckingAccount(5000, 7838);
        SavingAccount costomer3SaveAcc = new SavingAccount(2, 3350);


        customer1.addAccount(costomer1SaveAcc);
        customer1.addAccount(costomer1CheckAcc);
        customer2.addAccount(costomer2SaveAcc);
        customer2.addAccount(costomer2CheckAcc);
        customer3.addAccount(costomer3SaveAcc);


        bank.addCustomer(customer1);
        bank.addCustomer(customer2);
        bank.addCustomer(customer3);
        bank.addCustomer(customer4);

        for (Component component : numButtonPanel.getComponents()) {
            if (component.getClass() == JButton.class && !((JButton) component).getText().equalsIgnoreCase("ENTER")) {
                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        addButtonDigit(e);
                    }
                });
            }
        }

        checkBalanceButton.addActionListener(e -> {

            if (currentCustomer != null) {
                showBalance();
            }

        });
        makeDepositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentCustomer.getNumberOfAccounts() < 1 /*&& !severalAccountDeposit*/ && amountField.getText().length() != 0) {
                    historyField.append("\tYou don't have any accounts to deposit ! \n\tPlease contact the bank office\n");
                } else if (currentCustomer.getNumberOfAccounts() > 1 && !severalAccountDeposit && amountField.getText().length() != 0) {

                    sum = (Double.parseDouble(amountField.getText()));
                    severalAccountDeposit = true;
                    amountField.setText("");

                    historyField.append("""
                            \tYou have more than one account !
                            \tPlease input the number of account
                            \tClick 'make deposit' button
                            """);

                    historyField.append("Amount to deposit - " + Double.toString(sum) + "\nInput number of account...\n");

                } else {
                    try {
                        if (!severalAccountDeposit) {

                            System.out.println("several account - false");
                            sum = Double.parseDouble(amountField.getText());
                            currentCustomer.getAccount(0).deposit(sum);
                            amountField.setText("");
                            historyField.append("Done !(deposit customer with 1 account)\n");

                            showBalance();
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }

            }
        });

        makeDepositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (severalAccountDeposit && amountField.getText().length() != 0 && Integer.parseInt(amountField.getText()) < currentCustomer.getNumberOfAccounts()) {
                    try {
                        currentAccount = Integer.parseInt(amountField.getText());
                        currentCustomer.getAccount(currentAccount).deposit(sum);
                        amountField.setText("");
                        historyField.append("Done !\n");
                        showBalance();
                        severalAccountDeposit = false;
                        System.out.println(" done depositListener 2");
                        currentAccount = -1;

                    } catch (NumberFormatException ex) {
                        historyField.append("format ERROR2");
                    }

                } else if (severalAccountDeposit) {
                    historyField.append("wrong number of account");
                }
            }

        });

        makeWithdrawalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (amountField.getText().length() != 0) {
// if customer don't have any accounts
                    if (currentCustomer.getNumberOfAccounts() < 1) {
                        historyField.append("\tYou don't have any accounts to withdraw\n\tCall the bank office please\n");
// if customer have one account
                    } else if (currentCustomer.getNumberOfAccounts() == 1) {
                        withdrawAmount = Double.parseDouble(amountField.getText());
                        amountField.setText("");

                        try {
                            currentAccount = 0;
                            doWithdraw();
                        } catch (Exception ex) {
                            System.out.println("exception in doWithdraw , with one account");
                        }
                        amountField.setText("");
// withdraw if customer have several accounts
                    } else {
                        System.out.println("currentAccount =" + currentAccount);
                        multiAccountWithdraw:
                        {
                            if (currentAccount == -1 && currentCustomer.getNumberOfAccounts() > 1) {
                                if (!inputAccountNumToWithdraw) {
                                    withdrawAmount = Double.parseDouble(amountField.getText());
                                    historyField.append("""
                                            \tYou have several account
                                            \tInput number of account to withdraw
                                            \tClick 'make withdraw' button again...
                                            """);
                                    System.out.println(withdrawAmount);
                                }

                                if (inputAccountNumToWithdraw) {
                                    System.out.println("assign inputAccountNumToWithdraw");
                                    currentAccount = Integer.parseInt(amountField.getText());
                                    amountField.setText("");
                                    try {
                                        doWithdraw();
                                    } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    currentAccount = -1;
                                    inputAccountNumToWithdraw = false;
                                    System.out.println("end of withdraw, inputAccountNumToWithdraw " + inputAccountNumToWithdraw);
                                    break multiAccountWithdraw;
                                }
                                inputAccountNumToWithdraw = true;
                                System.out.println("inputAccountNumToWithdraw = " + inputAccountNumToWithdraw);
                                amountField.setText("");

                            }
                        }
                    }
                }
            }
        });

        ENTERButton.addActionListener(e ->

        {
            if (amountField.getText().length() > 0) {
                try {
                    int customerID = Integer.parseInt(amountField.getText());
                    currentCustomer = bank.getCustomer(customerID);

                    if (currentCustomer == null) {
                        historyField.setText("");
                        historyField.append("customer ID not found\n");
                        checkBalanceButton.setEnabled(false);
                        makeDepositButton.setEnabled(false);
                        makeWithdrawalButton.setEnabled(false);
                    } else {
                        checkBalanceButton.setEnabled(true);
                        makeDepositButton.setEnabled(true);
                        makeWithdrawalButton.setEnabled(true);

                        historyField.setText("");
                        historyField.append("Hi, " + currentCustomer.getLastName() + " " + currentCustomer.getFirstName() + " !!!\n");
                        ENTERButton.setEnabled(false);
                    }
                } catch (Exception exception) {
                    historyField.append("Wrong format of ID !\n");
                }
                amountField.setText("");
            }
        });


        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                amountField.setText("");
            }
        });


    }

    private void doWithdraw() throws Exception {
        if (currentCustomer.getAccount(currentAccount) instanceof SavingAccount) {

            if (currentCustomer.getAccount(currentAccount).getBalance() < withdrawAmount) {
                historyField.append("withdraw amount more than your balance\n");
            } else {
                currentCustomer.getAccount(currentAccount).withDraw(withdrawAmount);
                historyField.append("Done !\n");
            }

        } else if (currentCustomer.getAccount(currentAccount) instanceof CheckingAccount) {
            if (currentCustomer.getAccount(currentAccount).getBalance()
                    + ((CheckingAccount) currentCustomer.getAccount(currentAccount)).getOverdraftAmount() < withdrawAmount) {
                historyField.append("\tAmount to withdraw is to much !\n\tTry another amount");
            } else {
                currentCustomer.getAccount(currentAccount).withDraw(withdrawAmount);
                historyField.append("Done !\n");
            }

        }
    }

    private void showBalance() {
        if (currentCustomer.getNumberOfAccounts() > 0) {

            for (int i = 0; i < currentCustomer.getNumberOfAccounts(); i++) {
                if (currentCustomer.getAccount(i) instanceof SavingAccount) {
                    historyField.append("Account number " + (i) + ", Saving account\n");
                    historyField.append("Balance - $" + Double.toString(currentCustomer.getAccount(i).getBalance()) + "\n");
                } else {
                    historyField.append("Account number " + (i) + ", checking account\n");
                    historyField.append("Balance - $" + currentCustomer.getAccount(i).getBalance() + "\n");
                }
            }

        } else {
            historyField.append("\tYou don't have any accounts !\n\tPlease contact the bank office\n");
        }
    }

    private void addButtonDigit(MouseEvent e) {
        amountField.setText(amountField.getText() + ((JButton) e.getSource()).getText());
    }

    private void createUIComponents() {
    }

    public static void main(String[] args) {
        BankATM bankATM = new BankATM();
        bankATM.setContentPane(bankATM.mainPanel);
        bankATM.setLocation(500, 200);
        bankATM.setSize(600, 305);
        bankATM.setVisible(true);
        bankATM.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setEnabled(true);
        Font mainPanelFont = this.$$$getFont$$$(null, Font.BOLD, -1, mainPanel.getFont());
        if (mainPanelFont != null) mainPanel.setFont(mainPanelFont);
        mainPanel.setMinimumSize(new Dimension(600, 305));
        mainPanel.setName("Mini Bank ATM");
        mainPanel.setPreferredSize(new Dimension(600, 305));
        mainPanel.setRequestFocusEnabled(true);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        leftPanel.setLayout(new GridLayoutManager(9, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanel.setEnabled(true);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        checkBalanceButton = new JButton();
        checkBalanceButton.setEnabled(false);
        checkBalanceButton.setText("Check balance");
        checkBalanceButton.setVisible(true);
        leftPanel.add(checkBalanceButton, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        makeDepositButton = new JButton();
        makeDepositButton.setEnabled(false);
        makeDepositButton.setText("Make Deposit");
        leftPanel.add(makeDepositButton, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        makeWithdrawalButton = new JButton();
        makeWithdrawalButton.setEnabled(false);
        makeWithdrawalButton.setText("Make Withdrawal");
        leftPanel.add(makeWithdrawalButton, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numButtonPanel = new JPanel();
        numButtonPanel.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanel.add(numButtonPanel, new GridConstraints(4, 0, 5, 3, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        n1Button = new JButton();
        n1Button.setText("1");
        numButtonPanel.add(n1Button, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        n2Button = new JButton();
        n2Button.setText("2");
        numButtonPanel.add(n2Button, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        n3Button = new JButton();
        n3Button.setText("3");
        numButtonPanel.add(n3Button, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        n4Button = new JButton();
        n4Button.setText("4");
        numButtonPanel.add(n4Button, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        n5Button = new JButton();
        n5Button.setText("5");
        numButtonPanel.add(n5Button, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        n6Button = new JButton();
        n6Button.setText("6");
        numButtonPanel.add(n6Button, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        n7Button = new JButton();
        n7Button.setText("7");
        numButtonPanel.add(n7Button, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        n8Button = new JButton();
        n8Button.setText("8");
        numButtonPanel.add(n8Button, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        n9Button = new JButton();
        n9Button.setText("9");
        numButtonPanel.add(n9Button, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        n0Button = new JButton();
        n0Button.setText("0");
        numButtonPanel.add(n0Button, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ENTERButton = new JButton();
        ENTERButton.setEnabled(true);
        ENTERButton.setText("ENTER");
        numButtonPanel.add(ENTERButton, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        numButtonPanel.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 8, false));
        final Spacer spacer2 = new Spacer();
        numButtonPanel.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 8, false));
        final Spacer spacer3 = new Spacer();
        numButtonPanel.add(spacer3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 8, false));
        pointButton = new JButton();
        pointButton.setText(".");
        numButtonPanel.add(pointButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPanel.add(panel1, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        amountField = new JTextField();
        amountField.setHorizontalAlignment(2);
        panel1.add(amountField, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(170, 25), null, null, 0, false));
        clearButton = new JButton();
        clearButton.setText("Clear");
        panel1.add(clearButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statusField = new JTextField();
        statusField.setEditable(false);
        statusField.setText("Welcome to miniBank !!! Enter client ID and press ENTER ...");
        mainPanel.add(statusField, BorderLayout.SOUTH);
        historyField = new JTextArea();
        historyField.setBackground(new Color(-1));
        historyField.setCaretPosition(0);
        historyField.setEditable(false);
        Font historyFieldFont = this.$$$getFont$$$(null, Font.BOLD, -1, historyField.getFont());
        if (historyFieldFont != null) historyField.setFont(historyFieldFont);
        historyField.setForeground(new Color(-14803426));
        historyField.setMargin(new Insets(0, 10, 0, 0));
        historyField.setName("");
        historyField.setText("");
        historyField.setVisible(true);
        historyField.putClientProperty("caretWidth", new Integer(285));
        mainPanel.add(historyField, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
