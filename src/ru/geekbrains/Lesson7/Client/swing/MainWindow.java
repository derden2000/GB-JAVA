package ru.geekbrains.Lesson7.Client.swing;

import ru.geekbrains.Lesson7.Client.MessageReciever;
import ru.geekbrains.Lesson7.Client.Network;
import ru.geekbrains.Lesson7.Client.TextMessage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;



public class MainWindow extends JFrame implements MessageReciever {

    private final JList<TextMessage> messageList;

    private final DefaultListModel<TextMessage> messageListModel;

    private final TextMessageCellRenderer messageCellRenderer;

    private final JScrollPane scroll;

    private final JPanel sendMessagePanel;

    private final JButton sendButton;

    private final JTextField messageField;

    private final Network network;

    private final JList<String> userList;

    private final DefaultListModel<String> userListModel;


    public MainWindow() {
        setTitle(String.format("Сетевой чат"));
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
                    TextMessage msg = new TextMessage(network.getLogin(), userList.getSelectedValue(), text);
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

        sendMessagePanel.add(sendButton, BorderLayout.EAST);
        messageField = new JTextField();
        sendMessagePanel.add(messageField, BorderLayout.CENTER);

        userList = new JList<>();
        userListModel = new DefaultListModel<>();
        userList.setModel(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object element = userList.getSelectedValue();
            }
        });
        userList.setPreferredSize(new Dimension(100, 0));
        add(userList, BorderLayout.WEST);

        add(sendMessagePanel, BorderLayout.SOUTH);
        setVisible(true);

        this.network = new Network("localhost", 7777, this);

        ChooseDialog choose = new ChooseDialog(this);
        choose.setVisible(true);

        if (!choose.getIsRegistered()) {
            RegisterDialog regDialog = new RegisterDialog(this, network);
            regDialog.setVisible(true);

            if (!regDialog.isRegistered()) {
                System.exit(0);
            }
        } else {
            LoginDialog loginDialog = new LoginDialog(this, network);
            loginDialog.setVisible(true);

            if (!loginDialog.isConnected()) {
                System.exit(0);
            }
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (network != null) {
                    network.close();
                }
                super.windowClosing(e);
            }
        });

        setTitle("Сетевой чат. Пользователь " + network.getLogin());


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

    @Override
    public void userConnected(String login) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int ix = userListModel.indexOf(login);
                if (ix == -1) {
                    userListModel.add(userListModel.size(), login.toLowerCase());
                }
            }
        });
    }

    @Override
    public void userDisconnected(String login) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int ix = userListModel.indexOf(login);
                if (ix >= 0) {
                    userListModel.remove(ix);
                }
            }
        });
    }

    @Override
    public void initClientsOnline(List<String> list) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (list.size()>0) {
                    for (String user : list) {
                        userListModel.addElement(user.toLowerCase());
                    }
                }
            }
        });
    }
}