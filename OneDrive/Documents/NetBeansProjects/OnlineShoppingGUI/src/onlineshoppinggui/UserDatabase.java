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
import java.sql.DriverManager;

/**
 *
 * @author johnn
 */
public class UserDatabase {

    Connection connection = DatabaseConnection.getConnection();
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    // get user table row
    public int getMaxRow() {
        int row = 0;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("select max(userid) from users");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    // check if email already exists
    public boolean isEmailExists(String email) {
        try {
            ps = connection.prepareStatement("select * from users where useremail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    // insert data into user table
    /*public void insert(int id, String firstName, String lastName, String email, String username, String password, String address){
        String sql = "insert into users values(?,?,?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(5, email);
            ps.setString(4, username);
            ps.setString(6, password);
            ps.setString(7, address);
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "User added successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    public boolean insert(String firstName, String lastName, String username, String userPassword, String userEmail, String address) {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean isSuccess = false;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO USERS (FIRSTNAME, LASTNAME, USERNAME, USERPASSWORD, USEREMAIL, ADDRESS) VALUES (?, ?, ?, ?, ?, ?)";

            ps = connection.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, username);
            ps.setString(4, userPassword);
            ps.setString(5, userEmail);
            ps.setString(6, address);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
            System.out.println("User inserted successfully.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }
}
