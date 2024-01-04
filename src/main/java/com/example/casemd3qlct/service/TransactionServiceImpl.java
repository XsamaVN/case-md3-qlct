package com.example.casemd3qlct.service;

import com.example.casemd3qlct.model.Category;
import com.example.casemd3qlct.model.Transaction;
import com.example.casemd3qlct.model.Wallet;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService{
    WalletServiceImpl walletService = new WalletServiceImpl();
    CategoryServiceImpl categoryService = new CategoryServiceImpl();
    List<Transaction> transactionList;
    public TransactionServiceImpl() {
        transactionList = new ArrayList<>();
    }

    @Override
    public List<Transaction> showAll(int id) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction where id = ?")) {
                preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idGet = rs.getInt("id");
                Category category = categoryService.findByid(rs.getInt("idCategory"));
                Wallet wallet = walletService.findByid(rs.getInt("idWallet")) ;
                double amount = rs.getDouble("amount");
                LocalDateTime time = LocalDateTime.parse(rs.getString("time"));
                String type = rs.getString("type");
                String description = rs.getString("description");

                transactionList.add(new Transaction(idGet,category,wallet,amount,time,type,description));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return transactionList;
    }

    @Override
    public void create(Transaction transaction) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into Transaction(idCategory,idWallet,amount,time,type,description) values (?,?,?,?,?,?);")) {

            preparedStatement.setInt(1, transaction.getCategory().getId());
            preparedStatement.setInt(2, transaction.getWallet().getId());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getTime().toString());
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

    }

    @Override
    public void delete(int id) {
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
                int idTransaction = rs.getInt("id");
                Category category = categoryService.findByid(rs.getInt("idCategory"));
                Wallet wallet = walletService.findByid(rs.getInt("idWallet")) ;
                double amount = rs.getDouble("amount");
                LocalDateTime time = LocalDateTime.parse(rs.getString("time"));
                String type = rs.getString("type");
                String description = rs.getString("description");

                transaction = new Transaction(idTransaction,category,wallet,amount,time,type,description);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return transaction;
    }

    @Override
    public int findIndexById(int id) {
        int index = -1;
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getId() == id) {
                index = i;
            }
        }
        return index;
    }

}
