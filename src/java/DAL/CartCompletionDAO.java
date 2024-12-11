/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Category;
import Models.Customers;
import Models.Employees;
import Models.Item;
import Models.Order;
import Models.OrderDetail;
import Models.ProductSize;
import Models.Products;
import Models.Role;
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
public class CartCompletionDAO extends DBContext {

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

    public List<Employees> getAllSaleEmployees() {
        CartCompletionDAO dao = new CartCompletionDAO();
        List<Employees> list = new ArrayList<>();
        Role r = dao.getRoleSale();
        String qr = "SELECT * FROM online_shop.employees where status =1 and role_id=" + r.getRoleId();
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                System.out.println("sale employee: " + rs.getInt("employee_id"));
                Employees c = new Employees(
                        rs.getInt("employee_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getInt("role_id"),
                        rs.getString("password")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Order> getAllOrderByEmployeeID(int employeeId) {
        List<Order> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.orders where status = 1 and employee_id=" + employeeId;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setOrderDate(rs.getDate("order_date"));
                o.setTotalCost(rs.getLong("total_cost"));
                o.setStatus(rs.getInt("status"));
                o.setCustomerId(rs.getInt("customer_id"));
                o.setAddress(rs.getString("address"));
                o.setPhoneNumber(rs.getString("phone_number"));
                o.setEmployeeId(rs.getInt("employee_id"));
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public int getEmployeeIDHasTheLeastOrder() {
        int employeeID = 0;
        int count = -1;
        CartCompletionDAO c = new CartCompletionDAO();
        List<Employees> listEmployee = c.getAllSaleEmployees();
        for (int i = 0; i < listEmployee.size(); i++) {
            List<Order> listOrder = c.getAllOrderByEmployeeID(listEmployee.get(i).getEmployeeId());
            if (listOrder.size() == 0) {
                employeeID = listEmployee.get(i).getEmployeeId();
                break;
            }
            if (count == -1) {
                count = listOrder.size();
                employeeID = listEmployee.get(i).getEmployeeId();
                continue;
            }
            if (count > listOrder.size()) {
                count = listOrder.size();
                employeeID = listEmployee.get(i).getEmployeeId();
            }

        }

        return employeeID;
    }

    public void updateStatusAndEmployeeIDForOrder(int orderID) {
        CartCompletionDAO c = new CartCompletionDAO();
        int employeeID = c.getEmployeeIDHasTheLeastOrder();
        String query = "UPDATE `online_shop`.`orders` SET `status` = 1, `employee_id` = " + employeeID + " WHERE `order_id` = " + orderID;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public List<OrderDetail> getOrderDetailByOrderID(int orderID) {
        List<OrderDetail> list = new ArrayList<>();

        String qr = "SELECT * FROM online_shop.order_detail where order_id =" + orderID;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                OrderDetail c = new OrderDetail(
                        rs.getInt("order_detail_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getInt("product_size_id")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
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
        String query = "UPDATE `online_shop`.`product_size` SET `quantity` =" + quantityUpdate + " WHERE `product_size_id` = " + productDetailID;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void updateHoldOfProductDetailByProductDetailID(int productDetailID, int quantityUpdate) {
        String query = "UPDATE `online_shop`.`product_size` SET `hold` =" + quantityUpdate + " WHERE `product_size_id` = " + productDetailID;
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
                p.setPrices(rs.getInt("prices"));
                p.setImportPrice(rs.getInt("import_price"));
                p.setHold(rs.getInt("hold"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void UpdateQuantity(int orderID) {
        CartCompletionDAO c = new CartCompletionDAO();
        List<OrderDetail> listOrderDetail = c.getOrderDetailByOrderID(orderID);
        int updateProductSize = 0;
        for (int i = 0; i < listOrderDetail.size(); i++) {          
            ProductSize ps = c.getProductSizeByProductSizeId(listOrderDetail.get(i).getProductSizeId());
            updateProductSize = ps.getHold() + listOrderDetail.get(i).getQuantity();
            c.updateHoldOfProductDetailByProductDetailID(listOrderDetail.get(i).getProductSizeId(), updateProductSize);
        }

    }

    public Order getOrderByOrderID(int orderID) {
        String qr = "SELECT * FROM online_shop.orders where order_id =" + orderID;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setOrderDate(rs.getDate("order_date"));
                o.setTotalCost(rs.getLong("total_cost"));
                o.setStatus(rs.getInt("status"));
                o.setCustomerId(rs.getInt("customer_id"));
                o.setAddress(rs.getString("address"));
                o.setPhoneNumber(rs.getString("phone_number"));
                o.setEmployeeId(rs.getInt("employee_id"));

                return o;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Customers getCustomerByCustomerID(int customerID) {
        String qr = "SELECT * FROM online_shop.customers where customer_id=" + customerID;
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

    public String GetEmailByOrderID(int orderID) {
        CartCompletionDAO c = new CartCompletionDAO();
        Order o = c.getOrderByOrderID(orderID);
        int customerID = o.getCustomerId();
        Customers cus = c.getCustomerByCustomerID(customerID);
        return cus.getEmail();

    }

    public List<OrderDetail> getOrderDetailWithProductByOrderID(int orderID) {
        List<OrderDetail> list = new ArrayList<>();

        String qr = "SELECT * FROM online_shop.order_detail where order_id =" + orderID;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                OrderDetail c = new OrderDetail();
                c.setOrderDetailId(rs.getInt("order_detail_id"));
                c.setOrderId(rs.getInt("order_id"));
                c.setQuantity(rs.getInt("quantity"));
                Products p = getProductByProductID(rs.getInt("product_id"));
                c.setProduct(p);
                ProductSize od = getProductSizeByProductSizeId(rs.getInt("product_size_id"));
                c.setProductSize(od);
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public static void main(String[] args) {

        CartCompletionDAO c = new CartCompletionDAO();
        c.UpdateQuantity(129);

    }

}
