package ru.geekbrains.Lesson7.Server.auth;

import ru.geekbrains.Lesson7.Server.User;
import ru.geekbrains.Lesson7.Server.persistance.UserRepository;

import java.sql.SQLException;

public class AuthServiceJdbcImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceJdbcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authUser(User user) {
        String pass = null;
        try {
            User find = userRepository.findByLogin(user.getLogin());
            pass = find.getPassword();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (pass != null) {
            return pass.equals(user.getPassword());
        } else {
            return false;
        }
    }
}
