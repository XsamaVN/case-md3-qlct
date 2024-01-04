package com.example.casemd3qlct.service;

import com.example.casemd3qlct.model.Wallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletServiceImpl implements WalletService {
    List<Wallet> walletList;
    UserServiceImpl userService = new UserServiceImpl();

    public WalletServiceImpl() {
        walletList = new ArrayList<>();
    }

    @Override
    public List<Wallet> showAll(int id) {
        walletList = new ArrayList<>();
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from wallet join user on idUser = user.id where user.id = ?")) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idGet = rs.getInt("id");
                int idUser = rs.getInt("idUser");
                double totalIncome = getTotalIncomeById(idGet);
                double totalExpense = getTotalExpenseById(idGet);
                double initialBalance = rs.getDouble("initial_balance");
                double currentBalance = initialBalance + totalIncome - totalExpense;

                walletList.add(new Wallet(idGet, userService.findByid(idUser), currentBalance, totalIncome, totalExpense, initialBalance));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return walletList;
    }

    @Override
    public void create(Wallet wallet) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into wallet(idUser, currentBalance, totalIncome, totalExpense,initialBalance) values (?,?,?,?,?,?);")) {

            preparedStatement.setInt(1, wallet.getUser().getId());
            preparedStatement.setDouble(2, wallet.getCurrentBalance());
            preparedStatement.setDouble(3, wallet.getTotalIncome());
            preparedStatement.setDouble(4, wallet.getTotalExpense());
            preparedStatement.setDouble(5, wallet.getInitialBalance());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void edit(int id, Wallet wallet) {

    }

    @Override
    public void delete(int id) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM wallet WHERE id = " + id)) {
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public Wallet findByid(int id) {
        Wallet wallet = new Wallet();
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from wallet where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idWallet = rs.getInt("id");
                int idUser = rs.getInt("idUser");
                double currentBalance = rs.getDouble("current_balance");
                double totalIncome = rs.getDouble("total_income");
                double totalExpense = rs.getDouble("total_expense");
                double initialBalance = rs.getDouble("initial_balance");

                wallet = new Wallet(idWallet, userService.findByid(idUser), currentBalance, totalIncome, totalExpense, initialBalance);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return wallet;
    }


    public Double getTotalIncomeById(int id) {
        Double totalIncome = null;
        try (Connection connection = CreateConnector.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement("select sum(amount) as `totalIncome` from wallet " +
                     "join transaction on idwallet = wallet.id  " +
                     "where wallet.id = ? and transaction_type like \"thu\"")) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                totalIncome = rs.getDouble("totalIncome");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return totalIncome;
    }

    public Double getTotalExpenseById(int id) {
        Double totalExpense = null;
        try (Connection connection = CreateConnector.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement("select sum(amount) as `totalExpense` from wallet  \n" +
                     "join transaction on idwallet = wallet.id  \n" +
                     "where wallet.id = ? and transaction_type like \"chi\"")) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                totalExpense = rs.getDouble("totalExpense");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return totalExpense;
    }

}
