/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T
 */
public class CategoryDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
        
    
        public List<Category> getNameCategory(){
            List<Category> list = new ArrayList<>();
            String query = "select category_id, name from category ";
            
            try {
                DBContext db = new DBContext();
                conn = db.getConnection();
                ps = conn.prepareStatement(query);
                rs= ps.executeQuery();
             while(rs.next()){
                 list.add(new Category(rs.getInt(1),
                                       rs.getString(2)));
             }   
                
            } catch (SQLException e) {
            }
            
            return list ;
        }
        
        public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO();
        List<Category> list = dao.getNameCategory();

        for (Category b : list) {
            System.out.println(b);
        }
    }
}
