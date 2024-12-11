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
import Models.Products;
import Models.Role;
import Models.Status;
import Models.StatusCount;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class OrderListDAO extends DBContext {

    public Customers getCustomerByID(int id) {
        String qr = "SELECT * FROM online_shop.customers where customer_id='" + id + "'";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            if (rs.next()) {
                Customers c = new Customers(
                        rs.getInt("customer_id"),
                        rs.getString("full_name"),
                        rs.getBoolean("gender"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getInt("status"),
                        rs.getString("password")
                );
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public OrderDetail getFirstOrderDetailByOrderID(int orderID) {
        String qr = "SELECT * FROM online_shop.order_detail where order_id= " + orderID + " LIMIT 1";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                OrderDetail c = new OrderDetail(
                        rs.getInt("order_detail_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );
                return c;
            }
        } catch (SQLException e) {
            System.out.println("error in get orderdetal : " + e);
        }
        return null;
    }

    public Category getCategoryByID(int id) {
        String qr = "SELECT * FROM online_shop.category where category_id='" + id + "'";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            if (rs.next()) {
                Category c = new Category(
                        rs.getInt("category_id"),
                        rs.getString("name")
                );
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Products getProductByProductID(int productID) {
        String qr = "SELECT * FROM online_shop.products where product_id=" + productID;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Products p = new Products();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setOriginalPrice(rs.getInt("original_price"));
                p.setSalePrice(rs.getInt("sale_price"));
                p.setNumberLeft(rs.getInt("number_left"));
                p.setCreateDate(rs.getString("create_date"));
                p.setImageUrl(rs.getString("image_url"));
                Category c = getCategoryByID(rs.getInt("category_id"));
                p.setCategory(c);
                return p;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Employees getEmployeeByID(int id) {
        String qr = "SELECT * FROM online_shop.employees where employee_id='" + id + "'";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            if (rs.next()) {
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

    public Status getStatusByStatusId(int statusId) {
        String qr = "SELECT * FROM online_shop.status where status_id=" + statusId;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            if (rs.next()) {
                Status s = new Status();
                s.setStatusId(rs.getInt("status_id"));
                s.setStatusName(rs.getString("status_name"));
                s.setRoleId(rs.getString("role_id"));
                return s;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Order> getAllOrder() {
        List<Order> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.orders ";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setOrderDate(rs.getDate("order_date"));
                o.setTotalCost(rs.getLong("total_cost"));
                Status s = getStatusByStatusId(rs.getInt("status"));
                o.setStatusO(s);
                Employees e = getEmployeeByID(rs.getInt("employee_id"));
                o.setEmployee(e);
                Customers c = getCustomerByID(rs.getInt("customer_id"));
                o.setCustomer(c);
                OrderDetail od = getFirstOrderDetailByOrderID(rs.getInt("order_id"));
                o.setOrderDetail(od);
                String productName = getProductByProductID(od.getProductId()).getProductName();
                o.setProductName(productName);
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println("error in get all order: " + e);
        }
        return list;
    }

    public List<Order> getAllOrderByEmployeeID(int employeeID) {
        List<Order> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.orders where employee_id=" + employeeID;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setOrderDate(rs.getDate("order_date"));
                o.setTotalCost(rs.getLong("total_cost"));
                Status s = getStatusByStatusId(rs.getInt("status"));
                o.setStatusO(s);
                Employees e = getEmployeeByID(rs.getInt("employee_id"));
                o.setEmployee(e);
                Customers c = getCustomerByID(rs.getInt("customer_id"));
                o.setCustomer(c);
                OrderDetail od = getFirstOrderDetailByOrderID(rs.getInt("order_id"));
                o.setOrderDetail(od);
                String productName = getProductByProductID(od.getProductId()).getProductName();
                o.setProductName(productName);
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println("error in get all order: " + e);
        }
        return list;
    }

    public Customers getCustomerByCustomerName(String customerName) {
        String qr = "SELECT * FROM online_shop.customers where full_name like '%" + customerName + "%'";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Customers c = new Customers(
                        rs.getInt("customer_id"),
                        rs.getString("full_name"),
                        rs.getBoolean("gender"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getInt("status"),
                        rs.getString("password")
                );
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Employees getSaleBySaleName(String saleName) {
        String qr = "SELECT * FROM online_shop.employees where full_name like '%" + saleName + "%'";
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

    public List<Order> SearchAllOrderByKey(String orderId, String customerName, String startDate, String endDate, String status, String saleName) {
        List<Order> list = new ArrayList<>();

        String qr = "SELECT * FROM online_shop.orders where 1=1 ";
        int customerId = 0;
        if (customerName != null) {
            Customers c = getCustomerByCustomerName(customerName);
            if (c == null) {
                customerId = -1;
            } else {
                customerId = c.getCustomerId();
                qr += " and customer_id='" + customerId + "'";
            }
            
        }
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
        if (orderId != null) {
            if (!orderId.endsWith("null")) {
                qr += " and order_id ='" + orderId + "'";
            }
        }
        if (startDate != null) {
            if (!startDate.endsWith("null")) {
                qr += " and order_date>= '" + startDate + "'";
            }

        }
        if (endDate != null) {
            if (!endDate.endsWith("null")) {
                qr += " and order_date<= '" + endDate + "'";
            }
        }
        if (status != null) {
            if (!status.endsWith("null")) {
                qr += " and status ='" + status + "'";
            }

        }
        System.out.println(qr);
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setOrderDate(rs.getDate("order_date"));
                o.setTotalCost(rs.getLong("total_cost"));
                Status s = getStatusByStatusId(rs.getInt("status"));
                o.setStatusO(s);
                Employees e = getEmployeeByID(rs.getInt("employee_id"));
                o.setEmployee(e);
                Customers c = getCustomerByID(rs.getInt("customer_id"));
                o.setCustomer(c);
                OrderDetail od = getFirstOrderDetailByOrderID(rs.getInt("order_id"));
                o.setOrderDetail(od);
                String productName = getProductByProductID(od.getProductId()).getProductName();
                o.setProductName(productName);
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println("have error now here: " + e.getMessage());
        }
        return list;
    }

    public List<Order> getAllOrderByPage(List<Order> list, int start, int end) {
        ArrayList<Order> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public Role getRoleSale() {
        String qr = "SELECT * FROM online_shop.roles where role_name='Sale'";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            if (rs.next()) {
                Role c = new Role(
                        rs.getInt("role_id"),
                        rs.getString("role_name")
                );
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
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
            System.out.println("error in get all status: " + e);
        }
        return list;
    }

    public List<StatusCount> getAllStatusCount(int roleId) {
        List<StatusCount> list = new ArrayList<>();
        String qr = "SELECT s.status_id, s.status_name, COUNT(o.order_id) AS order_count FROM status s LEFT JOIN orders o ON s.status_id = o.status WHERE s.role_id LIKE '%" + roleId + "%' GROUP BY s.status_id ORDER BY s.status_id;";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                StatusCount s = new StatusCount();
                s.setStatusId(rs.getInt("status_id"));
                s.setStatusName(rs.getString("status_name"));
                s.setOrderCount(rs.getInt("order_count"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("error in get all status: " + e);
        }
        return list;
    }

    public List<StatusCount> getAllStatusCountWarehouse(int roleId) {
        List<StatusCount> list = new ArrayList<>();
        String qr = "SELECT s.status_id, s.status_name, COUNT(o.order_id) AS order_count, s.role_id FROM status s LEFT JOIN orders o ON s.status_id = o.status WHERE s.role_id LIKE '%"+roleId+"%' GROUP BY s.status_id ORDER BY s.status_id;";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                StatusCount s = new StatusCount();
                s.setStatusId(rs.getInt("status_id"));
                s.setStatusName(rs.getString("status_name"));
                s.setOrderCount(rs.getInt("order_count"));
                s.setRoleId(rs.getString("role_id"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("error in get all status: " + e);
        }
        return list;
    }
    
    public List<StatusCount> getAllStatusCountByEmployeeID(int roleId, int employeeID) {
        List<StatusCount> list = new ArrayList<>();
        String qr = "SELECT s.status_id, s.status_name, COUNT(o.order_id) AS order_count FROM status s LEFT JOIN orders o ON s.status_id = o.status AND o.employee_id = " + employeeID + " WHERE s.role_id LIKE '%" + roleId + "%' GROUP BY s.status_id ORDER BY s.status_id;";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                StatusCount s = new StatusCount();
                s.setStatusId(rs.getInt("status_id"));
                s.setStatusName(rs.getString("status_name"));
                s.setOrderCount(rs.getInt("order_count"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("error in get all status: " + e);
        }
        return list;
    }

    public static void main(String[] args) {

        OrderListDAO c = new OrderListDAO();
        // Fetch all statuses
        List<Status> statusList = c.getAllStatus();

        // Print the results
        for (Status status : statusList) {
            System.out.println("Status ID: " + status.getStatusId()
                    + ", Status Name: " + status.getStatusName()
                    + ", Role IDs: " + status.getRoleId());
        }
    }
}
