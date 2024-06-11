/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineshoppinggui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static Connection connection;
    private static final String url = "jdbc:derby://localhost:1527/OnlineShopping;create=true"; // Derby URL
    private static final String username = "admin1"; // Derby username
    private static final String password = "admin"; // Derby password
    
    // Make DB connection
    public static Connection getConnection(){
        if (connection == null) {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver"); // Derby driver class
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }
}