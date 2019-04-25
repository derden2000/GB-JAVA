package ru.geekbrains.Lesson7;

import ru.geekbrains.Lesson7.UI.AuthException;
import ru.geekbrains.Lesson7.UI.TextMessage;
import ru.geekbrains.Lesson7.auth.AuthService;
import ru.geekbrains.Lesson7.auth.AuthServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static ru.geekbrains.Lesson7.UI.MessagePatterns.MESSAGE_SEND_PATTERN;

public class ChatServer {

    private AuthService authService = new AuthServiceImpl();
    private Map<String, ClientHandler> clientHandlerMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start(7777);
    }

    private void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream inp = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("New client connected!");

                User user = null;
                try {
                    String authMessage = inp.readUTF();
                    user = checkAuthentication(authMessage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (AuthException ex) {
                    out.writeUTF("/auth fails");
                    out.flush();
                    socket.close();
                }
                if (user != null && authService.authUser(user)) {
                    System.out.printf("User %s authorized successful!%n", user.getLogin());
                    clientHandlerMap.put(user.getLogin(), new ClientHandler(user.getLogin(), socket, this));
                    //clientHandlerMap.put("Pasha", new ClientHandler("Pasha", socket, this));
                    out.writeUTF("/auth successful");
                    out.flush();
                } else {
                    if (user != null) {
                        System.out.printf("Wrong authorization for user %s%n", user.getLogin());
                    }
                    out.writeUTF("/auth fails");
                    out.flush();
                    socket.close();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private User checkAuthentication(String authMessage) throws AuthException {
        String[] authParts = authMessage.split(" ");
        if (authParts.length != 3 || !authParts[0].equals("/auth")) {
            System.out.printf("Incorrect authorization message %s%n", authMessage);
            throw new AuthException();
        }
        return new User(authParts[1], authParts[2]);
    }

    public void sendMessage(String userTo, String userFrom, String msg) throws IOException {
        ClientHandler userToClientHandler = clientHandlerMap.get(userTo);
        if (userToClientHandler != null) {
            //TextMessage chatText = new TextMessage(userFrom, userTo, msg);
            //String persText = String.format(MESSAGE_SEND_PATTERN, userFrom, msg);
            userToClientHandler.sendMessage(userFrom, msg);
        }
        // TODO убедиться, что userToClientHandler существует и отправить сообщение
        // TODO для отправки сообщения нужно вызвать метод userToClientHandler.sendMessage()
    }
}
