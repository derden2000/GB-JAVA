package ru.geekbrains.Lesson7.Server.persistance;

import ru.geekbrains.Lesson7.Server.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static Connection conn = null;

    public UserRepository(Connection conn) {
        this.conn = conn;
        PreparedStatement prepareStatement = null;
        try {
            prepareStatement = conn.prepareStatement("create table if not exists users (" +
                    "id int auto_increment primary key," +
                    " login varchar(25)," +
                    " password varchar(25)," +
                    " unique index uq_login(login));");
            prepareStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert(User user) throws SQLException {
        PreparedStatement prepareStatement = conn.prepareStatement("insert into users(login, password) values (?, ?)");
        prepareStatement.setString(1, user.getLogin());
        prepareStatement.setString(2, user.getPassword());
        prepareStatement.execute();
    }

    public static User findByLogin(String Login) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(String.format("select * from users where login = '%s';", Login));
            ) {
                String name = null, pass = null;
                while (resultSet.next()) {
                    name = resultSet.getString(2);
                    pass = resultSet.getString(3);
                }
                return new User(name, pass);
            }
        }

    public List<User> getAllUsers() throws SQLException {
        ArrayList<User> result = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery("select * from users")
        )
        {
            while (resultSet.next()) {
            result.add(new User(resultSet.getString(2),resultSet.getString(3)));
            }
        }

        return result;
    }
}
