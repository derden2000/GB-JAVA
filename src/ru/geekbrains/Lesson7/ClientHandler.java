package ru.geekbrains.Lesson7;

import ru.geekbrains.Lesson7.UI.TextMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static ru.geekbrains.Lesson7.UI.MessagePatterns.MESSAGE_SEND_PATTERN;

public class ClientHandler {

    private final String login;
    private final Socket socket;
    private final DataInputStream inp;
    private final DataOutputStream out;
    private final Thread handleThread;
    private ChatServer chatServer;

    public ClientHandler(String login, Socket socket, ChatServer chatServer) throws IOException {
        this.login = login;
        this.socket = socket;
        this.inp = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.chatServer = chatServer;

        this.handleThread = new Thread(new Runnable() { //входящий поток сервера
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        String msg = inp.readUTF();
                        System.out.printf("Message from user %s: %s%n", login, msg);
                        TextMessage inText = StringHandler.strHandler(msg);
                        if (inText.getUserTo()!=null) {
                            chatServer.sendMessage(inText);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        this.chatServer = chatServer;
        this.handleThread.start();
    }

    public void sendMessage(TextMessage message) throws IOException {
        out.writeUTF(String.format(MESSAGE_SEND_PATTERN, message.getUserFrom(), message.getUserTo(), message.getText()));
    }
}
