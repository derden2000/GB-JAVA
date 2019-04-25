package ru.geekbrains.Lesson7;

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

                        // TODO проверить является ли msg сообщением для пользователя
                        String[] msgParts = msg.split(" ");
                        // TODO если да, то переслать это сообщение пользователю
                        String userTo = msgParts[1];
                        String message = msgParts[2];
                        if (msgParts.length ==3 && userTo!=null)
                        sendMessage(userTo, message);
                        chatServer.sendMessage(userTo, login, message);
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

    public String getLogin() {
        return login;
    }

    public void sendMessage(String userTo, String msg) throws IOException {
        out.writeUTF(String.format(MESSAGE_SEND_PATTERN, userTo, msg));
    }
}
