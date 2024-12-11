/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Customerr;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author T
 */
public class CustomerrDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

//    public List<Customerr> getCus(){
//        List<Customerr> list = new ArrayList<>();
//        
//        String query = "SELECT customer_id, full_name, gender, phone_number, address FROM customers where customer_id = 1;";
//        
//        try {
//
//            DBContext db = new DBContext();
//            conn = db.getConnection();
//            ps = conn.prepareStatement(query);
//            rs = ps.executeQuery();
//        
//            while (rs.next()) {
//                list.add(new Customerr(rs.getInt(1),
//                        rs.getString(2)
//                        ,rs.getInt(3)
//                        , rs.getInt(4)
//                        , rs.getString(5)));
//            }
//        } catch (Exception e) {
//        }
//        
//        return list;
//    }
//    
//    public static void main(String[] args) {
//        CustomerrDAO dao = new CustomerrDAO();
//        List<Customerr> list = dao.getCus();
//
//        for (Customerr b : list) {
//            System.out.println(b);
//        }
//    }
    
    
    public Customerr getAllprofile(int customerID){
        Customerr customer = null;
        String query = "SELECT customer_id, full_name, gender, email, phone_number, address FROM customers WHERE customer_id = ?";
        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            
            while(rs.next()){
                customer = new Customerr(rs.getInt(1)
                                        , rs.getString(2)
                                        , rs.getInt(3)
                                        , rs.getString(4)
                                        , rs.getInt(5)
                                        , rs.getString(6));
            }
            
        } catch (SQLException e) {
        }
        
        
        
        return customer;
    }
//     public static void main(String[] args) {
//        CustomerrDAO dao = new CustomerrDAO();
//       int testCusID = 1; // Thay đổi thành ID thực tế của blog bạn muốn kiểm tra
//       Customerr customer = dao.getAllprofile(testCusID);
//
//       if (customer != null) {
//          System.out.println(customer.getFullname());
//            System.out.println (customer.getId());
//            System.out.println(customer.getAddress());
//            System.out.println(customer.getNumberphone());
//       } else {
//           System.out.println("No blog found with ID: " + testCusID);
//       }
//   }
    
     
    public void updateCustomerr(int customerId, String name, int gender, String phone, String address) {
        try  {
            String sql = "UPDATE customers SET full_name = ?, gender = ?, phone_number = ?, address = ? WHERE customer_id = ?";
            
            DBContext db = new DBContext();
            conn = db.getConnection();
           
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, name);
            ps.setInt(2, gender);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setInt(5, customerId);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
//    public static void main(String[] args) {
//        CustomerrDAO customer = new CustomerrDAO();
//
//        // Thay đổi thông tin khách hàng theo customerId
//        int customerId = 1; // Thay đổi customerId theo nhu cầu của bạn
//        String name = "Nguyen Van e";
//        int gender = 0;
//        String phone = "0123456789";
//        String address = "nha";
//
//        // Gọi phương thức updateCustomerr để cập nhật thông tin
//       customer.updateCustomerr(customerId, name, gender, phone, address);
//    }
     
     
}
