package com.example.casemd3qlct.service;

import com.example.casemd3qlct.model.Category;
import com.example.casemd3qlct.model.Transaction;
import com.example.casemd3qlct.model.Wallet;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    WalletServiceImpl walletService = new WalletServiceImpl();
    CategoryServiceImpl categoryService = new CategoryServiceImpl();
    List<Transaction> transactionList;

    public TransactionServiceImpl() {
        transactionList = new ArrayList<>();
    }

    @Override
    public List<Transaction> showAll(int id) {
        transactionList = new ArrayList<>();
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction join wallet on idWallet = wallet.id join user on idUser = user.id where user.id = ?")) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idGet = rs.getInt("id");
                Category category = categoryService.findByid(rs.getInt("idCategory"));
                Wallet wallet = walletService.findByid(rs.getInt("idWallet"));
                double amount = rs.getDouble("amount");
                String time = rs.getString("time");
                String type = rs.getString("type");
                String description = rs.getString("description");

                transactionList.add(new Transaction(idGet, category, wallet, amount, time, type, description));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return transactionList;
    }

    @Override
    public void create(Transaction transaction) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into Transaction(idCategory,idWallet,amount,transaction_time,transaction_type,description) values (?,?,?,?,?,?)")) {

            preparedStatement.setInt(1, transaction.getCategory().getId());
            preparedStatement.setInt(2, transaction.getWallet().getId());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getTime());
            preparedStatement.setString(5, transaction.getType());
            preparedStatement.setString(6, transaction.getDescription());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void edit(int id, Transaction transaction) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE transaction SET idCategory = ?, idWallet = ?, amount = ?, transaction_time = ?, transaction_type = ?, description = ? WHERE id = ?;")) {
            preparedStatement.setInt(1, transaction.getCategory().getId());
            preparedStatement.setInt(2, transaction.getWallet().getId());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getTime());
            preparedStatement.setString(5, transaction.getType());
            preparedStatement.setString(6, transaction.getDescription());
            preparedStatement.setInt(7, id);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public  void delete(int id) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM transaction WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public Transaction findByid(int id) {
        Transaction transaction = new Transaction();
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idGet = rs.getInt("id");
                Category category = categoryService.findByid(rs.getInt("idCategory"));
                Wallet wallet = walletService.findByid(rs.getInt("idWallet"));
                double amount = rs.getDouble("amount");

                String time = rs.getString("transaction_time");
                String type = rs.getString("transaction_type");
                String description = rs.getString("description");

                transaction = new Transaction(idGet, category, wallet, amount, time, type, description);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return transaction;
    }



    public List<Transaction> findTransactionListByWalletId(int idWallet, String type_transaction) {
        transactionList = new ArrayList<>();
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction join wallet on idWallet = wallet.id join user on idUser = user.id where idWallet = ? and transaction_type like ?")) {
            preparedStatement.setInt(1, idWallet);
            preparedStatement.setString(2, type_transaction);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idGet = rs.getInt("id");
                Category category = categoryService.findByid(rs.getInt("idCategory"));
                Wallet wallet = walletService.findByid(rs.getInt("idWallet"));
                double amount = rs.getDouble("amount");
                String time = rs.getString("transaction_time");
                String type = rs.getString("transaction_type");
                String description = rs.getString("description");
                transactionList.add(new Transaction(idGet, category, wallet, amount, time, type, description));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return transactionList;
    }

}
