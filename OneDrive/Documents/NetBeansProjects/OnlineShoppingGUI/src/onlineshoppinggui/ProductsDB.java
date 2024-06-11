/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineshoppinggui;

/**
 *
 * @author johnn
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsDB {

    // Method to create the items table if it does not exist
    public void createProductsTable() {
        try {
            Connection connection = OnlineShoppingDatabase.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "ITEMS", null);

            // Check if the table already exists
            if (!resultSet.next()) {
                String createTableSQL = "CREATE TABLE items ("
                        + "id VARCHAR(255) PRIMARY KEY, "
                        + "name VARCHAR(255), "
                        + "price DOUBLE, "
                        + "productInfo VARCHAR(255))";
                try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                    preparedStatement.executeUpdate();
                }
                System.out.println("Products table created successfully.");
            } else {
                System.out.println("Products table already exists.");
            }

            resultSet.close();
        } catch (SQLException e) {
            System.err.println("Error creating or checking items table: " + e.getMessage());
        }
    }

    // Method to insert an item into the items table
    public void insertProduct(Products item) {
        String insertProductSQL = "INSERT INTO items (id, name, price, productInfo) VALUES (?, ?, ?, ?)";
        try (Connection connection = OnlineShoppingDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertProductSQL)) {
            preparedStatement.setString(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setDouble(3, item.getPrice());
            preparedStatement.setString(4, item.getProductInfo());
            preparedStatement.executeUpdate();
            System.out.println("Product inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting item: " + e.getMessage());
        }
    }

    // Method to retrieve all items from the items table
    public List<Products> getProducts() {
        List<Products> items = new ArrayList<>();
        String selectProductsSQL = "SELECT * FROM items";
        try (Connection connection = OnlineShoppingDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectProductsSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String productInfo = resultSet.getString("productInfo");
                Products item = new Products(id, name, price, productInfo); 
                items.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving items: " + e.getMessage());
        }
        return items;
    }
}

