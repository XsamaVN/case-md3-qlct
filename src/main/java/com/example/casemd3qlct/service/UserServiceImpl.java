package com.example.casemd3qlct.service;

import com.example.casemd3qlct.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    List<User> userList;

    public UserServiceImpl() {
        userList = new ArrayList<>();
    }

    @Override
    public List<User> showAll(int id) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from user where id = ?")) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idGet = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                userList.add(new User(idGet, username, password));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return userList;
    }

    @Override
    public void create(User user) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into user(username, password) values (?,?);")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void edit(int id, User user) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET username, password values (?,?) WHERE id = " + id)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void delete(int id) {
        int indexOf = findIndexById(id);
        userList.remove(indexOf);
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = " + id)) {
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public User findByid(int id) {
        User user = new User();
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from user where id =?")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idUser = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(idUser, username, password);
            }
        } catch (SQLException e) {
        }
        return user;
    }

    @Override
    public int findIndexById(int id) {
        int index = -1;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == id) {
                index = i;
            }
        }
        return index;
    }
    public List<User> getUserList() {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from user")) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idGet = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                userList.add(new User(idGet, username, password));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return userList;
    }
}



