package com.example.casemd3qlct.service;

import com.example.casemd3qlct.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.casemd3qlct.service.CreateConnector.getConnection;

public class UserServiceImpl implements UserService {
    List<User> userList;

    public UserServiceImpl() {
        userList = new ArrayList<>();
    }

    @Override
    public List<User> showAll(int id) {
        try (Connection connection = getConnection();
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into user(username, password) values (?,?);")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            userList.add(user);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void edit(int id, User user) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET username = ?, password = ? WHERE id = ?" )) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, id);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection();
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
        try (Connection connection = getConnection();
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


    public List<User> getUserList() {
        userList = new ArrayList<>();
        try (Connection connection = getConnection();
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

    @Override
    public void changeprofile(String username, String password) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from user where username =?")) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                int userId = rs.getInt("id");
                User userToUpdate = new User(username, password);
                edit(userId, userToUpdate);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void deleteTransactions(String username) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM transaction WHERE idwallet IN (SELECT id FROM wallet WHERE iduser IN (SELECT id FROM user WHERE username = ?))")) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteWallets(String username) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM wallet WHERE iduser IN (SELECT id FROM user WHERE username = ?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUserRecord(String username) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM user WHERE username = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean sigin(String username) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from user where username =?")) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}