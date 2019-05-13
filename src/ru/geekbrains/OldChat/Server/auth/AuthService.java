package ru.geekbrains.OldChat.Server.auth;

import ru.geekbrains.OldChat.Server.User;


public interface AuthService {

    boolean authUser(User user);
}
