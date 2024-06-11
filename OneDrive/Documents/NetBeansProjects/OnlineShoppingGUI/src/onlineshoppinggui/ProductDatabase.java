/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineshoppinggui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author johnn
 */
public class ProductDatabase {

    Connection connection = DatabaseConnection.getConnection();
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    public int getMaxRow() {
        int row = 0;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("select max(productid) from product");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    public int countCategories() {
        int total = 0;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) AS total FROM category");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public String[] getCategory() {
        String[] categories = new String[countCategories()];
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from category");
            int i = 0;
            while (rs.next()) {
                categories[i] = rs.getString(2);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public boolean ProductIDExist(int id) {
        try {
            ps = connection.prepareStatement("select * from product where productid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isProCatExist(String pro, String cat) {
        try {
            ps = connection.prepareStatement("select * from product where productname = ? and categoryname = ?");
            ps.setString(1, pro);
            ps.setString(2, cat);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insertProduct(String pname, String cname, int qty, double price) {
        String sql = "INSERT INTO product (PRODUCTNAME, CATEGORYNAME, PRODUCTQUANTITY, PRODUCTPRICE) VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, pname);
            ps.setString(2, cname);
            ps.setInt(3, qty);
            ps.setDouble(4, price);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Product added successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getProductsValue(JTable table, String search) {
        String sql = "SELECT * FROM product WHERE productname || categoryname LIKE ? ORDER BY productid asc";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getInt(4);
                row[4] = rs.getDouble(5);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
