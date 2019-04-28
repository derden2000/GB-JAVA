package ru.geekbrains.Lesson7.UI;

import ru.geekbrains.Lesson7.StringHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static ru.geekbrains.Lesson7.UI.MessagePatterns.AUTH_PATTERN;
import static ru.geekbrains.Lesson7.UI.MessagePatterns.MESSAGE_SEND_PATTERN;

public class Network {

    public Socket socket;
    public DataInputStream in;
    public DataOutputStream out;

    private String hostName;
    private int port;
    private MessageReciever messageReciever;

    private String login;

    private Thread receiverThread;

    public Network(String hostName, int port, MessageReciever messageReciever) {
        this.hostName = hostName;
        this.port = port;
        this.messageReciever = messageReciever;

        this.receiverThread = new Thread(new Runnable() { //входящий поток клиента
            @Override
            public void run() {
                while (true) {
                    try {
                        String text = in.readUTF();
                        System.out.println(text);
                        TextMessage textMessage = StringHandler.strHandler(text);
                        if (textMessage!=null){ messageReciever.submitMessage(textMessage); }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (socket.isClosed()) {
                            break;
                        }
                    }
                }
            }
        });
    }

    public void authorize(String login, String password) throws IOException, AuthException {
        socket = new Socket(hostName, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        sendMessage(String.format(AUTH_PATTERN, login, password));
        String response = in.readUTF();
        if (response.equals("/auth successful")) {
            this.login = login;
            receiverThread.start();
        } else {
            throw new AuthException();
        }
    }

    public void sendTextMessage(TextMessage message) {
        sendMessage(String.format(MESSAGE_SEND_PATTERN, message.getUserFrom(), message.getUserTo(), message.getText()));
    }

    private void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }
}
