/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Category;
import Models.Customers;
import Models.EmployeeCount;
import Models.Employees;
import Models.Order;
import Models.OrderDetail;
import Models.ProductSize;
import Models.Products;
import Models.Role;
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
public class OrderDetailDAO extends DBContext {

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

    public Order getOrderByOrderID(int orderId) {
        String qr = "SELECT * FROM online_shop.orders where order_id=" + orderId;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setOrderDate(rs.getDate("order_date"));
                o.setTotalCost(rs.getLong("total_cost"));
                o.setAddress(rs.getString("address"));
                o.setPhoneNumber(rs.getString("phone_number"));
                o.setStatus(rs.getInt("status"));
                Employees e = getEmployeeByID(rs.getInt("employee_id"));
                o.setEmployee(e);
                Customers c = getCustomerByID(rs.getInt("customer_id"));
                o.setCustomer(c);              
                return o;
            }
        } catch (SQLException e) {
            System.out.println("error in get all order: " + e);
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

    public List<OrderDetail> getOrderDetailByOrderID(int orderID) {
        List<OrderDetail> list = new ArrayList<>();

        String qr = "SELECT * FROM online_shop.order_detail where order_id =" + orderID;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                OrderDetail c = new OrderDetail();
                c.setOrderDetailId(rs.getInt("order_detail_id"));
                c.setOrderId(rs.getInt("order_id"));
                c.setQuantity(rs.getInt("quantity"));
                c.setProductId(rs.getInt("product_id"));
                Products p = getProductByProductID(rs.getInt("product_id"));               
                c.setProduct(p);
                c.setProductSizeId(rs.getInt("product_size_id"));
                ProductSize ps = getProductSizeByProductSizeId(rs.getInt("product_size_id"));
                c.setProductSize(ps);
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
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
    
    public List<EmployeeCount> getAllSaleEmployees() {
        CartCompletionDAO dao = new CartCompletionDAO();
        List<EmployeeCount> list = new ArrayList<>();
        Role r = dao.getRoleSale();
        String qr = "SELECT  e.employee_id, e.full_name, COUNT(o.order_id) AS order_count FROM  employees e LEFT JOIN orders o ON e.employee_id = o.employee_id AND o.status = 1 WHERE  e.role_id="+r.getRoleId()+" GROUP BY  e.employee_id";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                EmployeeCount c = new EmployeeCount(
                        rs.getInt("employee_id"),
                        rs.getString("full_name"),
                        rs.getInt("order_count")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public void updateStatusForOrder(int orderID, int status) {
        CartCompletionDAO c = new CartCompletionDAO();
        String query = "UPDATE `online_shop`.`orders` SET `status` = "+status+" WHERE `order_id` = " + orderID;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    
    public void updateSaleEmployeeForOrder(int orderID, int employeeID) {
        CartCompletionDAO c = new CartCompletionDAO();
        String query = "UPDATE `online_shop`.`orders` SET  `employee_id` = " + employeeID + " WHERE `order_id` = " + orderID;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    
    public List<Status> getAllStatusForSale() {
        Role r = getRoleSale();
        List<Status> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.status where role_id like '%"+r.getRoleId()+"%'";
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
    
    public Status getStatusByStatusId(int statusId){
        String qr = "SELECT * FROM online_shop.status where status_id="+statusId;
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
    
    public void updateQuantityOfProductByProductID(int productID, int quantityUpdate) {
        String query = "UPDATE `online_shop`.`products` SET `number_left` =" + quantityUpdate + " WHERE `product_id` = " + productID;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    public void updateQuantityOfProductDetailByProductDetailID(int productDetailID, int quantityUpdate) {
        String query = "UPDATE `online_shop`.`product_size` SET `quantity` ="+quantityUpdate+" WHERE `product_size_id` = " + productDetailID;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    
    public void updateHoldOfProductDetailByProductDetailID(int productDetailID, int quantityUpdate) {
        String query = "UPDATE `online_shop`.`product_size` SET `hold` ="+quantityUpdate+" WHERE `product_size_id` = " + productDetailID;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    public ProductSize getProductSizeByProductSizeId(int productSizeID) {
        String qr = "SELECT * FROM online_shop.product_size where product_size_id=" + productSizeID;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                ProductSize p = new ProductSize();
                p.setProductSizeId(rs.getInt("product_size_id"));
                p.setProductSizeName(rs.getString("product_size_name"));
                p.setProductId(rs.getInt("product_id"));
                p.setQuantity(rs.getInt("quantity"));
                p.setProductColor(rs.getString("product_color"));
                p.setImportPrice(rs.getInt("import_price"));
                p.setPrices(rs.getInt("prices"));
                p.setHold(rs.getInt("hold"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public void UpdateQuantityCancel(int orderID) {
        OrderDetailDAO c = new OrderDetailDAO();
        List<OrderDetail> listOrderDetail = c.getOrderDetailByOrderID(orderID);
        int updateProduct = 0;
        int updateProductSize = 0;
        int updateHold=0;
        for (int i = 0; i < listOrderDetail.size(); i++) {
            Products p = c.getProductByProductID(listOrderDetail.get(i).getProductId());
            updateProduct = p.getNumberLeft() + listOrderDetail.get(i).getQuantity();
            ProductSize ps = c.getProductSizeByProductSizeId(listOrderDetail.get(i).getProductSizeId());
            updateProductSize = ps.getQuantity()+listOrderDetail.get(i).getQuantity();
            updateHold = ps.getHold()-listOrderDetail.get(i).getQuantity();
            c.updateQuantityOfProductByProductID(listOrderDetail.get(i).getProductId(), updateProduct);
            c.updateQuantityOfProductDetailByProductDetailID(listOrderDetail.get(i).getProductSizeId(), updateProductSize);
            c.updateHoldOfProductDetailByProductDetailID(listOrderDetail.get(i).getProductSizeId(), updateHold);
        }

    }
    
    public void UpdateQuantityReturn(int orderID) {
        OrderDetailDAO c = new OrderDetailDAO();
        List<OrderDetail> listOrderDetail = c.getOrderDetailByOrderID(orderID);
        int updateProduct = 0;
        int updateProductSize = 0;
        for (int i = 0; i < listOrderDetail.size(); i++) {
            Products p = c.getProductByProductID(listOrderDetail.get(i).getProductId());
            updateProduct = p.getNumberLeft() + listOrderDetail.get(i).getQuantity();
            ProductSize ps = c.getProductSizeByProductSizeId(listOrderDetail.get(i).getProductSizeId());
            updateProductSize = ps.getQuantity()+listOrderDetail.get(i).getQuantity();           
            c.updateQuantityOfProductByProductID(listOrderDetail.get(i).getProductId(), updateProduct);
            c.updateQuantityOfProductDetailByProductDetailID(listOrderDetail.get(i).getProductSizeId(), updateProductSize);
        }

    }
    
    public void UpdateQuantityDelivering(int orderID) {
        OrderDetailDAO c = new OrderDetailDAO();
        List<OrderDetail> listOrderDetail = c.getOrderDetailByOrderID(orderID);
        int updateProduct = 0;
        int updateProductSize = 0;
        int updateHold=0;
        for (int i = 0; i < listOrderDetail.size(); i++) {
            Products p = c.getProductByProductID(listOrderDetail.get(i).getProductId());
            updateProduct = p.getNumberLeft() - listOrderDetail.get(i).getQuantity();
            ProductSize ps = c.getProductSizeByProductSizeId(listOrderDetail.get(i).getProductSizeId());
            updateProductSize = ps.getQuantity()-listOrderDetail.get(i).getQuantity();
            updateHold = ps.getHold()-listOrderDetail.get(i).getQuantity();
            c.updateQuantityOfProductByProductID(listOrderDetail.get(i).getProductId(), updateProduct);
            c.updateQuantityOfProductDetailByProductDetailID(listOrderDetail.get(i).getProductSizeId(), updateProductSize);
            c.updateHoldOfProductDetailByProductDetailID(listOrderDetail.get(i).getProductSizeId(), updateHold);
        }
    }
    
    
    public static void main(String[] args) {

        OrderDetailDAO c = new OrderDetailDAO();
       

    }
}
