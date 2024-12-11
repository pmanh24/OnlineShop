/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Category;
import Models.Customers;
import Models.Employees;
import Models.Order;
import Models.OrderDetail;
import Models.Status;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CategoryListDAO extends DBContext{
    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.category";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setName(rs.getString("name"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("error in get all order: "+e);
        }
        return list;
    }
    
    
    public void createCategory( String categoryName) {
        String query = "INSERT INTO `online_shop`.`category` (`name`,`status`) VALUES('"+categoryName+"',1);";
               
        try ( PreparedStatement stmt = connection.prepareStatement(query)) {          
            int rowsAffected = stmt.executeUpdate(); // Execute update and get affected rows

            if (rowsAffected > 0) {
            } else {
                System.err.println("Error creating Category. No rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating Category: " + e.getMessage());
        }       
    }
    
    public void updateCategory(int categoryID, int status, String categoryName) {
        String query = "UPDATE `online_shop`.`category`SET`name` = '"+categoryName+"', `status` = "+status+"  WHERE `category_id` = "+categoryID;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    
    public List<Category> SearchAllCategoryByKey(String categoryId, String categoryName, String status) {
        List<Category> list = new ArrayList<>();
       
        String qr = "SELECT * FROM online_shop.category where 1=1 ";
        
        if (categoryId!= null){
            qr+= " and category_id='"+categoryId+"'";
        }
        if (categoryName != null){
            qr+= " and name like '%"+categoryName+"%'";
        }       
        if (status != null){
            qr+= " and status ='"+status+"'";
        }
        System.out.println(qr);
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Category o = new Category();
                o.setCategoryId(rs.getInt("category_id"));
                o.setName(rs.getString("name"));
                o.setStatus(rs.getInt("status"));             
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println("have error now here: "+e.getMessage());
        }
        return list;
    }
    public List<Category> getAllCategoryByPage(List<Category> list, int start, int end){
        ArrayList<Category> arr = new ArrayList<>();
        for (int i = start; i< end ; i++){
            arr.add(list.get(i));
        }
        return arr;
    }
    
    public Category getCategoryByID(int id) {
        String qr = "SELECT * FROM online_shop.category where category_id='" + id + "'";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            if (rs.next()) {
                Category c = new Category(
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getInt("status")
                );
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
