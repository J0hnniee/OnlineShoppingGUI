/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineshoppinggui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author johnn
 */
public class StoreDatabase {

    Connection connection = DatabaseConnection.getConnection();
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    //get purchase table max row
    public int getMaxRow() {
        int row = 0;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("select max(id) from store");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoreDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    public String[] getUserValue(String email) {
        String[] value = new String[5];
        String sql = "select userid, firstname, lastname, username, address, from users where useremail = '" + email + "'";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
                value[4] = rs.getString(5);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoreDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

    public void insert(int id, int userid, String username, int productid, String productName,
            int qty, double price, double total, String address) {
        String sql = "insert into purchase values(?,?,?,?,?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, userid);
            ps.setString(3, username);
            ps.setInt(4, productid);
            ps.setString(5, productName);
            ps.setInt(6, qty);
            ps.setDouble(7, price);
            ps.setDouble(8, total);
            ps.setString(8, address);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(StoreDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getProductsValue(JTable table, String search, int uid) {
        String sql = "select * from store where product_name like ? and userid = ? order by id desc";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, uid);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[10];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(5);
                row[2] = rs.getString(6);
                row[3] = rs.getInt(7);
                row[4] = rs.getDouble(8);
                row[5] = rs.getDouble(9);
                row[6] = rs.getString(10);
                row[7] = rs.getString(12);
                row[8] = rs.getString(13);
                row[9] = rs.getString(14);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoreDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
