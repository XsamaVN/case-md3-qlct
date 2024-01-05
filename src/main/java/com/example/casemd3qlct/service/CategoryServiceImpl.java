package com.example.casemd3qlct.service;

import com.example.casemd3qlct.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService{
    List<Category> categoryList;
    public CategoryServiceImpl() {
        categoryList = new ArrayList<>();
    }

    @Override
    public List<Category> showAll(int id) {
        categoryList = new ArrayList<>();
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from category where id = ?")) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idGet = rs.getInt("id");
                String name = rs.getString("name");
                categoryList.add(new Category(idGet, name));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return categoryList;
    }

    @Override
    public void create(Category category) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into category(name) values (?)")) {
            preparedStatement.setString(1, category.getName());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void edit(int id, Category category) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE category SET name = ? WHERE id = ?")) {

            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, id);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM category where id = ? ")) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public Category findByid(int id) {
        Category category = new Category();
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from category where id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idCategory = rs.getInt("id");
                String name = rs.getString("name");
                category = new Category(idCategory, name);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return category;
    }


    public List<Category> getCategoryList() {
        categoryList = new ArrayList<>();
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from category")) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idGet = rs.getInt("id");
                String name = rs.getString("name");
                categoryList.add(new Category(idGet,name));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return categoryList;
    }
    public void delete(int id, int idUser) {
        try (Connection connection = CreateConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM transaction where idCategory = ? and idWalet = ?; DELETE FROM category WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, idUser);
            preparedStatement.setInt(3, id);
            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
