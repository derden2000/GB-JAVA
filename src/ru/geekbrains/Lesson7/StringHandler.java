package ru.geekbrains.Lesson7;

import ru.geekbrains.Lesson7.UI.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static ru.geekbrains.Lesson7.UI.MessagePatterns.*;

public class StringHandler {
    public static TextMessage parseMessage(String msg) throws IOException {
        Matcher matcher = MESSAGE_REC_PATTERN.matcher(msg);
        if (matcher.matches()) {
            return new TextMessage(matcher.group(1), matcher.group(2), matcher.group(3));
        } else {
            System.out.println("Unknown message pattern: " + msg);
            return null;
        }
    }

    public static String parseConnectedMessage(String text) {
        String[] parts = text.split(" ");
        if (parts.length == 2 && parts[0].equals(CONNECTED)) {
            return parts[1];
        } else {
            System.out.println("Unknown message pattern: " + text);
            return null;
        }
    }

    public static List<String> parseConnectedUsers(String text) {
        List<String> connectedUsers = new ArrayList<>();
        String[] parts = text.split(" ");
        if (parts[0].equals(CONNECTED_USERS_LIST))
            for (int i = 1; i < parts.length; i++) {
                connectedUsers.add(parts[i]);
            }
        return connectedUsers;
    }

    public static String parseDisconnectedMessage(String text) {
        String[] parts = text.split(" ");
        if (parts.length == 2 && parts[0].equals(DISCONNECT)) {
            return parts[1];
        } else {
            System.out.println("Unknown message pattern: " + text);
            return null;
        }
    }
}
