package ru.geekbrains.Lesson7;

import ru.geekbrains.Lesson7.UI.TextMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static ru.geekbrains.Lesson7.UI.MessagePatterns.*;


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

                        String[] text = msg.split(" ");
                        if (text[0].equals(CONNECTED_USERS_REQUEST)) {
                            chatServer.serverSendConnectedUserList(login);
                        }
                        else if (text[0].equals(DISCONNECT)) {
                            //chatServer.serverSendDisconnectLogin(login);
                            chatServer.unsubscribe(login);
                        }
                        else {
                            TextMessage inText = StringHandler.parseMessage(msg);
                            if (inText.getUserTo() != null) {
                                chatServer.sendMessage(inText);
                            }
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

    public String getLogin() {
        return login;
    }

    public void sendConnectedMessage(String login) throws IOException {
        if (socket.isConnected()) {
            out.writeUTF(String.format(CONNECTED_SEND, login));
        }
    }

    public void sendConnectedUsersList(String toString) throws IOException {
        out.writeUTF(String.format(CONNECTED_USERS_LIST_SEND, toString));
    }

    public void sendDisconnectedLogin(String login) throws IOException {
        out.writeUTF(String.format(DISCONNECT_SEND, login));
    }
}
