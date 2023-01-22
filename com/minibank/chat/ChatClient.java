package com.minibank.chat;

import java.awt.*;
import javax.swing.*;

//todo ЗАКОНЧИЛ ТУТ ! УРОК 10 минута 71
public class ChatClient {

    private TextArea output;
    private TextField input;
    private JButton quitButton;
    private JButton sendButton;

    public ChatClient() {
        this.output = new TextArea(10, 50);
        this.input = new TextField("Message...", 50);
        this.quitButton = new JButton("Quit");
        this.sendButton = new JButton("Send");
    }

    public void launchFrame() {
        JFrame frame = new JFrame("Bank chat");
        frame.setLayout(new BorderLayout());
        frame.add(output, BorderLayout.CENTER);
        frame.add(input, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(quitButton, BorderLayout.NORTH);
        buttonPanel.add(sendButton, BorderLayout.SOUTH);

        frame.add(buttonPanel, BorderLayout.EAST);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.launchFrame();

    }

}
