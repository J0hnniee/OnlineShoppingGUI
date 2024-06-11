/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineshoppinggui;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author johnn
 */
public class CategoryDatabase {

    Connection connection = DatabaseConnection.getConnection();
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    public int getMaxRow() {
        int row = 0;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("select max(categoryid) from category");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    public boolean CategoryIDExist(int id) {
        try {
            ps = connection.prepareStatement("select * from category where categoryid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isCategoryNameExist(String categoryName) {
        try {
            ps = connection.prepareStatement("select * from category where categoryname = ?");
            ps.setString(1, categoryName);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insertCategory(int id, String cname, String desc) {
        String sql = "insert into category values(?,?,?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, cname);
            ps.setString(3, desc);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Category added successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getCategoryValues(JTable table, String search) {
        String categorySQL = "select * from category where categoryname like ? order by categoryid asc";
        try {
            ps = connection.prepareStatement(categorySQL);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[3];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateCategories(int id, String categoryName, String description) {
        String sql = "update category set categoryname = ?, categorydesc = ? where categoryid = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, categoryName);
            ps.setString(2, description);
            ps.setInt(3, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Category updated successfully!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteCategory(int id) {
        int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this?", "Delete Category", JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.OK_OPTION) {
            try {
                ps = connection.prepareStatement("delete from category where categoryid = ?");
                ps.setInt(1, i);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Category deleted");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
