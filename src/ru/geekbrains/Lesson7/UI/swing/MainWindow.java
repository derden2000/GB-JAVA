package ru.geekbrains.Lesson7.UI.swing;

import ru.geekbrains.Lesson7.UI.MessageReciever;
import ru.geekbrains.Lesson7.UI.Network;
import ru.geekbrains.Lesson7.UI.TextMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainWindow extends JFrame implements MessageReciever {

    private final JList<TextMessage> messageList;

    private final DefaultListModel<TextMessage> messageListModel;

    private final TextMessageCellRenderer messageCellRenderer;

    private final JScrollPane scroll;

    private final JPanel sendMessagePanel;

    private final JButton sendButton;

    private final JComboBox userChoice;

    private final JTextField messageField;

    private final Network network;


    public MainWindow() {
        setTitle(String.format("GeekBrains"));
        setBounds(200,200, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        messageList = new JList<>();
        messageListModel = new DefaultListModel<>();
        messageCellRenderer = new TextMessageCellRenderer();
        messageList.setModel(messageListModel);
        messageList.setCellRenderer(messageCellRenderer);

        scroll = new JScrollPane(messageList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        sendMessagePanel = new JPanel();
        sendMessagePanel.setLayout(new BorderLayout());
        sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = messageField.getText();
                if (text != null && !text.trim().isEmpty()) {
                    TextMessage msg = new TextMessage(network.getLogin(), userChoice.getSelectedItem().toString(), text);
                    messageListModel.add(messageListModel.size(), msg);
                    messageField.setText(null);
                    network.sendTextMessage(msg);
                } else {
                    JOptionPane.showMessageDialog(MainWindow.this,
                            "Сообщение пустое. Введите новое сообщение",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //users = AuthServiceImpl.users;
        sendMessagePanel.add(sendButton, BorderLayout.EAST);
        messageField = new JTextField();
        sendMessagePanel.add(messageField, BorderLayout.CENTER);
        userChoice = new JComboBox<>();
        userChoice.addItem("ivan");
        userChoice.addItem("petr");
        userChoice.addItem("julia");
        userChoice.addItem("All");
        sendMessagePanel.add(userChoice, BorderLayout.WEST);

        add(sendMessagePanel, BorderLayout.SOUTH);
        setVisible(true);

        this.network = new Network("localhost", 7777, this);

        LoginDialog loginDialog = new LoginDialog(this, network);
        loginDialog.setVisible(true);

        if (!loginDialog.isConnected()) {
            System.exit(0);
        }
    }

    @Override
    public void submitMessage(TextMessage message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                messageListModel.add(messageListModel.size(), message);
                messageList.ensureIndexIsVisible(messageListModel.size() - 1);
            }
        });
    }
}
