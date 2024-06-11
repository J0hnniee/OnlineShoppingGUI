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
            rs = statement.executeQuery("select max(id) from purchase");
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
        
    
}
