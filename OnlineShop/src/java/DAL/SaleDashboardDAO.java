/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Employees;
import Models.OrderCount;
import Models.Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SaleDashboardDAO extends DBContext {
    
    public Employees getSaleBySaleName(String saleName) {
        String qr = "SELECT * FROM online_shop.employees where full_name like '%"+saleName+"%'";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Employees c = new Employees(
                        rs.getInt("employee_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getInt("role_id"),
                        rs.getString("password")
                );
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public List<OrderCount> getDailyOrderCountsByKey(String startDate, String endDate, String status, String saleName) {
        List<OrderCount> list = new ArrayList<>();

        String qr = "SELECT DATE(order_date) AS order_date, COUNT(order_id) AS order_count FROM orders where 1=1 ";
        
        int saleId = 0;
        if (saleName != null) {
            Employees e = getSaleBySaleName(saleName);
            if (e == null) {
                saleId = -1;
            } else {
                saleId = e.getEmployeeId();
            }
            qr += " and employee_id='" + saleId + "'";
        }
        
        if (status != null){
            qr+= " and status ='"+status+"'";
        }
        
        if (startDate != null){
            qr+= " and order_date>= '"+startDate+"'";
        }
        if (endDate != null){
            qr+= " and order_date<= '"+endDate+"'";
        }

        qr += " GROUP BY DATE(order_date) ORDER BY DATE(order_date)";
        System.out.println(qr);
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                OrderCount c = new OrderCount(
                        rs.getString("order_date"),
                        rs.getInt("order_count")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public List<OrderCount> getDailyOrderCounts() {
        List<OrderCount> list = new ArrayList<>();

        String qr = "SELECT DATE(order_date) AS order_date, COUNT(order_id) AS order_count "
                + "FROM orders GROUP BY DATE(order_date) ORDER BY DATE(order_date)";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                OrderCount c = new OrderCount(
                        rs.getString("order_date"),
                        rs.getInt("order_count")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
     public List<Status> getAllStatus() {
        List<Status> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.status";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Status s = new Status();
                s.setStatusId(rs.getInt("status_id"));
                s.setStatusName(rs.getString("status_name"));
                s.setRoleId(rs.getString("role_id"));           
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("error in get all status: "+e);
        }
        return list;
    }
     
     public List<OrderCount> getDailyRevenueCountsByKey(String startDate, String endDate, String status, String saleName) {
        List<OrderCount> list = new ArrayList<>();

        String qr = "SELECT     DATE(o.order_date) AS order_date,     SUM(o.total_cost) - SUM(ps.import_price * od.quantity) AS total_cost FROM     `orders` o  JOIN     order_detail od ON o.order_id = od.order_id JOIN     product_size ps ON od.product_size_id = ps.product_size_id where 1=1 ";
        
        int saleId = 0;
        if (saleName != null) {
            Employees e = getSaleBySaleName(saleName);
            if (e == null) {
                saleId = -1;
            } else {
                saleId = e.getEmployeeId();
            }
            qr += " and o.employee_id='" + saleId + "'";
        }
        
        if (status != null){
            qr+= " and o.status ='"+status+"'";
        }
        
        if (startDate != null){
            qr+= " and o.order_date>= '"+startDate+"'";
        }
        if (endDate != null){
            qr+= " and o.order_date<= '"+endDate+"'";
        }

        qr += " GROUP BY     DATE(o.order_date) ORDER BY     DATE(o.order_date);";
        System.out.println(qr);
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                OrderCount c = new OrderCount(
                        rs.getString("order_date"),
                        rs.getLong("total_cost")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
}
