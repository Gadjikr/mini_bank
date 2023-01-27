package com.minibank.chat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ChatClient {

    private TextArea output;
    private TextField input;
    private JButton quitButton;
    private JButton sendButton;

    public ChatClient() {
        this.output = new TextArea(10, 50);
        this.input = new TextField("", 50);
        this.quitButton = new JButton("Quit");
        this.sendButton = new JButton("Send");
    }

    public void launchFrame() {
        JFrame frame = new JFrame("Bank chat");
        frame.setLayout(new BorderLayout());
        frame.add(output, BorderLayout.CENTER);
        output.setEditable(false);
        frame.add(input, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(quitButton, BorderLayout.NORTH);
        buttonPanel.add(sendButton, BorderLayout.SOUTH);

        frame.add(buttonPanel, BorderLayout.EAST);

        input.addActionListener(new SendHandler());
        sendButton.addActionListener(new SendHandler());

        quitButton.addActionListener( (ActionEvent e) ->  {
                System.exit(0);
        });
        // old expression
//        quitButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.launchFrame();

    }

    private class SendHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = input.getText();
            output.append(message + "\n");
            input.setText("");
        }
    }

}
