package ru.geekbrains.Lesson7;

import ru.geekbrains.Lesson7.UI.TextMessage;

import java.io.IOException;

public class StringHandler {
    public static TextMessage strHandler(String msg) throws IOException {
        if (msg != null) {
            String[] msgParts = msg.split(" ");
            String userFrom = msgParts[1];
            String userTo = msgParts[2];
            StringBuilder builder = new StringBuilder();
            for (int i = 3; i < msgParts.length; i++) {
                builder.append(msgParts[i] + " ");
            }
            String message = builder.toString();
            TextMessage outTextMessage = new TextMessage(userFrom, userTo, message);
            return outTextMessage;
        } else
            return null;
    }
}
