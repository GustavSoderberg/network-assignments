package Uppgift3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MulticastChatGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    private MulticastSocket socket;
    private InetAddress group;
    private int port = 12540;

    public MulticastChatGUI() {
        setTitle("Multicast Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        messageField = new JTextField(20);
        messageField.addActionListener(new SendMessageListener());

        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendMessageListener());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        try {
            socket = new MulticastSocket(port);
            group = InetAddress.getByName("234.235.236.237");
            socket.joinGroup(group);

            Thread receiverThread = new Thread(new MessageReceiver());
            receiverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SendMessageListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String message = messageField.getText();
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
                socket.send(packet);
                messageField.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class MessageReceiver implements Runnable {
        public void run() {
            while (true) {
                try {
                    byte[] buffer = new byte[1000];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    chatArea.append(received + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MulticastChatGUI chatGUI = new MulticastChatGUI();
            chatGUI.setVisible(true);
        });
    }
}
