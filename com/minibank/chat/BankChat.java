package com.minibank.chat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankChat {
    private JPanel BankChatPanel;
    private JButton QuitButton;
    private JButton SendButton;
    private JTextArea ChatHistory;
    private JTextField messageField;

    public BankChat() {
        QuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hi!");
                JOptionPane.showMessageDialog(null,"Hi");
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bank client chat");
        frame.setContentPane(new BankChat().BankChatPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
