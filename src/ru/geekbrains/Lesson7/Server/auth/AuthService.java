package ru.geekbrains.Lesson7.Server.auth;

import ru.geekbrains.Lesson7.Server.User;

public interface AuthService {

    boolean authUser(User user);
}
