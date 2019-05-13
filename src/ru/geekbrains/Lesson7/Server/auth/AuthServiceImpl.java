package ru.geekbrains.Lesson7.Server.auth;

import ru.geekbrains.Lesson7.Server.User;

import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements AuthService {

    public static Map<String, String> users = new HashMap<>();

    public AuthServiceImpl() {
        users.put("ivan", "123");
        users.put("petr", "345");
        users.put("julia", "789");
    }

    @Override
    public boolean authUser(User user) {
        String pwd = users.get(user.getLogin());
        return pwd != null && pwd.equals(user.getPassword());
    }
}
