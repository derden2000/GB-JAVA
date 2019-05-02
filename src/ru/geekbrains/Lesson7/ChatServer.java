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


import static ru.geekbrains.Lesson7.UI.MessagePatterns.AUTH_FAIL_RESPONSE;
import static ru.geekbrains.Lesson7.UI.MessagePatterns.AUTH_SUCCESS_RESPONSE;



public class ChatServer {

    private AuthService authService = new AuthServiceImpl();
    public static Map<String, ClientHandler> clientHandlerMap = Collections.synchronizedMap(new HashMap<>());

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
                    out.writeUTF(AUTH_FAIL_RESPONSE);
                    out.flush();
                    socket.close();
                }
                if (user != null && authService.authUser(user)) {
                    System.out.printf("User %s authorized successful!%n", user.getLogin());
                    //clientHandlerMap.put(user.getLogin(), new ClientHandler(user.getLogin(), socket, this));
                    subcribe(user.getLogin(), socket);
                    //sendUserConnectedMessage(user.getLogin());
                    out.writeUTF(AUTH_SUCCESS_RESPONSE);

                    out.flush();
                } else {
                    if (user != null) {
                        System.out.printf("Wrong authorization for user %s%n", user.getLogin());
                    }
                    out.writeUTF(AUTH_FAIL_RESPONSE);
                    out.flush();
                    socket.close();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void subcribe(String login, Socket socket) throws IOException {
        if (!clientHandlerMap.containsKey(login)) {
            clientHandlerMap.put(login, new ClientHandler(login, socket, this));
            sendUserConnectedMessage(login);
        }
        else {
            sendMessage(new TextMessage("Server", login, "Пользователь уже зарегистрирован"));
        }
    }

    public void unsubscribe(String login) throws IOException {
        serverSendDisconnectLogin(login);
        clientHandlerMap.remove(login);
    }

    private void sendUserConnectedMessage(String login) throws IOException {
        for (ClientHandler clientHandler : clientHandlerMap.values()) {
            if (!clientHandler.getLogin().equals(login)) {
                System.out.printf("Sending connect notification to %s about %s%n", clientHandler.getLogin(), login);
                clientHandler.sendConnectedMessage(login);
            }
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

    public void sendMessage(TextMessage message) throws IOException {
        if (message.getUserTo().equals("All")){
            for (Map.Entry<String, ClientHandler> pair: clientHandlerMap.entrySet()) {
                if (!message.getUserFrom().equals(pair.getKey())) {
                    ClientHandler userToClientHandler = pair.getValue();
                    userToClientHandler.sendMessage(message);
                }
            }
        }
        else if (clientHandlerMap.containsKey(message.getUserTo())) {
            ClientHandler userToClientHandler = clientHandlerMap.get(message.getUserTo());
            userToClientHandler.sendMessage(message);
        }
        else {
            ClientHandler userToClientHandler = clientHandlerMap.get(message.getUserFrom());
            String error = String.format("Пользователя %s нет в сети", message.getUserTo());
            TextMessage messageError = new TextMessage("Server", message.getUserFrom(), error);
            userToClientHandler.sendMessage(messageError);
        }
    }

    public void serverSendConnectedUserList(String login) throws IOException {
        ClientHandler userToClientHandler = clientHandlerMap.get(login);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, ClientHandler> pair: clientHandlerMap.entrySet()) {
            if (!login.equals(pair.getKey())) {
                String name = pair.getKey().toLowerCase();
                builder.append(name + " ");
            }
        }
        String sendList = builder.toString();
        userToClientHandler.sendConnectedUsersList(sendList);
    }

    public void serverSendDisconnectLogin(String login) throws IOException {
        for (Map.Entry<String, ClientHandler> pair: clientHandlerMap.entrySet()) {
            if (!login.equals(pair.getKey())) {
                ClientHandler userToClientHandler = pair.getValue();
                userToClientHandler.sendDisconnectedLogin(login);
            }
        }
    }
}
