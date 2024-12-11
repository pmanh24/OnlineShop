/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Address;
import Models.Customers;
import Models.Item;
import Models.Order;
import Models.OrderDetail;
import Models.OrderNumbProduct;
import Models.ProductSize;
import Models.Products;
import Models.Status;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class OrderDAO {

    private static OrderDAO INSTANCE = null;
    private Connection con;
    private String status;

    public OrderDAO() {
        try {
            DBContext dbContext = new DBContext();
            con = dbContext.getConnection();
            if (con != null) {
                status = "Connection successful!";
            } else {
                status = "Failed to connect to the database.";
            }
        } catch (Exception e) {
            status = "Error at Connection: " + e.getMessage();
        }
    }

    public static OrderDAO getInstance() {
        if (INSTANCE == null) {
            synchronized (OrderDAO.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderDAO();
                }
            }
        }
        return INSTANCE;
    }

    public Connection getConnection() {
        return con;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalSpentByCustomer(int customerId) {
        int totalSpent = 0;
        String query = "SELECT SUM(total_cost) AS total_cost FROM online_shop.orders WHERE customer_id = ?";

        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                totalSpent = rs.getInt("total_cost");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalSpent;
    }

    public Order getOrderLatest() {
        String sql = "SELECT * FROM `order`\n"
                + "ORDER BY OrderId DESC LIMIT 1";
        try {
            PreparedStatement st = con.prepareCall(sql);
            ResultSet rs = st.executeQuery();
            //loop until select the last object
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setOrderDate(rs.getDate("order_date"));
                o.setTotalCost(rs.getLong("total_cost"));
                o.setStatus(rs.getInt("status"));
                o.setCustomerId(rs.getInt("customer_id"));
                o.setAddress(rs.getString("address"));
                o.setPhoneNumber(rs.getString("phone_number"));
                return o;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Products> getOrderedProductsByProductId(int orderId, int productId, int customerId) {
        List<Products> products = new ArrayList<>();
        String query = "SELECT p.product_id, p.product_name, p.image_url, p.sale_price, ps.product_size_id, ps.product_size_name, ps.product_color "
                + "FROM order_detail od "
                + "JOIN products p ON od.product_id = p.product_id "
                + "JOIN product_size ps ON od.product_size_id = ps.product_size_id "
                + "WHERE od.order_id = ? AND od.product_id = ? AND od.order_id IN (SELECT o.order_id FROM orders o WHERE o.customer_id = ?)";

        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, customerId);

            ResultSet rs = ps.executeQuery();

            // Use a map to aggregate sizes for each product
            Map<Integer, Products> productMap = new HashMap<>();

            while (rs.next()) {
                String productName = rs.getString("product_name");
                String imageUrl = rs.getString("image_url");
                int salePrice = rs.getInt("sale_price");
                String sizeName = rs.getString("product_size_name");
                String color = rs.getString("product_color");
                int productSizeId = rs.getInt("product_size_id");

                // If the product is not already in the map, create a new Products object
                Products product = productMap.get(productId);
                if (product == null) {
                    product = new Products(productId, productName, salePrice, imageUrl, new ArrayList<>());
                    productMap.put(productId, product);
                }

                // Add size to product's size list
                if (sizeName != null && !sizeName.isEmpty()) {
                    product.getSizes().add(new ProductSize(productSizeId, sizeName.trim(), 0, color)); // Add the size
                }
            }

            // Convert map values to list
            products.addAll(productMap.values());

//            // Format the sizes into a single string separated by ", "
//            for (Products product : products) {
//                List<ProductSize> sizes = product.getSizes();
//                if (sizes.size() > 1) {
//                    // Create a single size string with sizes separated by ", "
//                    StringBuilder sizeBuilder = new StringBuilder();
//                    for (ProductSize size : sizes) {
//                        sizeBuilder.append(size.getProductSizeName()).append(", ");
//                    }
//                    // Remove the last ", " and set it back to the sizes
//                    String allSizes = sizeBuilder.toString();
//                    allSizes = allSizes.length() > 2 ? allSizes.substring(0, allSizes.length() - 2) : allSizes; // Trim the last separator
//                    // Clear the list and add the formatted size
//                    sizes.clear();
//                    sizes.add(new ProductSize(0, allSizes, 0, null)); // Add all sizes as one entry
//                }
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Products getOrderedProductByProductId(int orderId, int productId, int customerId) {
        Products product = null; // Initialize a single product variable
        String query = "SELECT p.product_id, p.product_name, p.image_url, p.sale_price, ps.product_size_id, ps.product_size_name, ps.product_color "
                + "FROM order_detail od "
                + "JOIN products p ON od.product_id = p.product_id "
                + "JOIN product_size ps ON od.product_size_id = ps.product_size_id "
                + "WHERE od.order_id = ? AND od.product_id = ? AND od.order_id IN (SELECT o.order_id FROM orders o WHERE o.customer_id = ?)";

        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, customerId);

            ResultSet rs = ps.executeQuery();

            // Use a list to store sizes for the product
            List<ProductSize> sizeList = new ArrayList<>();

            if (rs.next()) { // Use if instead of while since we want a single product
                int productIdFromDB = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                String imageUrl = rs.getString("image_url");
                int salePrice = rs.getInt("sale_price");
                String sizeName = rs.getString("product_size_name");
                String color = rs.getString("product_color");
                int productSizeId = rs.getInt("product_size_id");

                // Create a new Products object
                product = new Products(productIdFromDB, productName, salePrice, imageUrl, sizeList);

                // Add size to product's size list
                if (sizeName != null && !sizeName.isEmpty()) {
                    sizeList.add(new ProductSize(productSizeId, sizeName.trim(), 0, color)); // Add the size
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product; // Return the single product object
    }

    public Status getStatusByStatusId(int statusId) {
        String qr = "SELECT * FROM online_shop.status where status_id=" + statusId;
        try (
                 Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
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

    public List<Order> getAllOrders(String search, Integer status, int index, int pageSize, String sort, String sortOrder, int roleId) {
        Map<Integer, Order> orderMap = new HashMap<>();

        String sql = "SELECT o.order_id, o.order_date, o.total_cost, o.status, o.address, o.phone_number, "
                + "c.customer_id AS customer_id, c.full_name AS customer_name, "
                + "od.product_id, od.quantity, p.product_name "
                + "FROM orders o "
                + "JOIN customers c ON o.customer_id = c.customer_id "
                + "JOIN order_detail od ON o.order_id = od.order_id "
                + "JOIN products p ON od.product_id = p.product_id "
                + "JOIN status s ON o.status = s.status_id "
                + "WHERE FIND_IN_SET(?, s.role_id) > 0 ";

        if (search != null && !search.trim().isEmpty()) {
            sql += " AND (o.order_id LIKE ? OR c.full_name LIKE ?)";
        }
        if (status != null) {
            sql += " AND o.status = ?";
        }

        sql += " ORDER BY o." + sort + " " + sortOrder + " LIMIT ? OFFSET ?";

        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            int parameterIndex = 1;
            statement.setInt(parameterIndex++, roleId);

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search + "%";
                statement.setString(parameterIndex++, searchPattern);
                statement.setString(parameterIndex++, searchPattern);
            }
            if (status != null) {
                statement.setInt(parameterIndex++, status);
            }
            statement.setInt(parameterIndex++, pageSize);
            statement.setInt(parameterIndex++, (index - 1) * pageSize);

            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");

                    Order order = orderMap.get(orderId);
                    if (order == null) {
                        Customers customer = new Customers();
                        customer.setCustomerId(resultSet.getInt("customer_id"));
                        customer.setFullName(resultSet.getString("customer_name"));

                        order = new Order(
                                orderId,
                                resultSet.getDate("order_date"),
                                resultSet.getLong("total_cost"),
                                resultSet.getInt("status"),
                                resultSet.getString("address"),
                                resultSet.getString("phone_number"),
                                customer
                        );
                        orderMap.put(orderId, order);
                    }

                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");
                    String productName = resultSet.getString("product_name");

                    // Sum the quantity for the same product ID
                    order.addProductName(productId, productName);
                    order.addQuantity(productId, quantity);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching orders: " + e.getMessage());
        }

        List<Order> orders = new ArrayList<>(orderMap.values());

        // Sort the orders after retrieving them from the database
        if ("order_date".equals(sort)) {
            orders.sort((o1, o2) -> {
                if ("asc".equalsIgnoreCase(sortOrder)) {
                    return o1.getOrderDate().compareTo(o2.getOrderDate());
                } else {
                    return o2.getOrderDate().compareTo(o1.getOrderDate());
                }
            });
        } else if ("order_id".equals(sort)) {
            orders.sort((o1, o2) -> {
                if ("asc".equalsIgnoreCase(sortOrder)) {
                    return Integer.compare(o1.getOrderId(), o2.getOrderId());
                } else {
                    return Integer.compare(o2.getOrderId(), o1.getOrderId());
                }
            });
        } else if ("total_cost".equals(sort)) {
            orders.sort((o1, o2) -> {
                if ("asc".equalsIgnoreCase(sortOrder)) {
                    return Long.compare(o1.getTotalCost(), o2.getTotalCost());
                } else {
                    return Long.compare(o2.getTotalCost(), o1.getTotalCost());
                }
            }
            );
        }
        return orders; // Return the sorted list of orders
    }

    public Order getOrderPrintById(int orderId) {
        String qr = "SELECT o.order_id, o.order_date, o.total_cost, o.status, "
                + "o.address, o.phone_number, c.customer_id AS customer_id, "
                + "c.full_name AS customer_name, c.email, od.product_id, "
                + "od.quantity, ps.product_size_id, ps.product_size_name, "
                + "ps.product_color, p.product_name "
                + "FROM orders o "
                + "JOIN customers c ON o.customer_id = c.customer_id "
                + "JOIN order_detail od ON o.order_id = od.order_id "
                + "JOIN products p ON od.product_id = p.product_id "
                + "JOIN product_size ps ON od.product_size_id = ps.product_size_id "
                + "WHERE o.order_id = ?;";

        try ( PreparedStatement ps = con.prepareStatement(qr)) {
            ps.setInt(1, orderId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Create Customer object
                    Customers customer = new Customers();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setFullName(rs.getString("customer_name"));
                    customer.setEmail(rs.getString("email"));

                    // Create Order object
                    Order o = new Order(
                            rs.getInt("order_id"),
                            rs.getDate("order_date"),
                            rs.getLong("total_cost"),
                            rs.getInt("status"),
                            rs.getString("address"),
                            rs.getString("phone_number"),
                            customer
                    );

                    // Create and add OrderDetail(s)
                    do {
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOrderId(o.getOrderId());
                        int productId = rs.getInt("product_id");
                        orderDetail.setProductId(productId);
                        orderDetail.setQuantity(rs.getInt("quantity"));

                        // Create and set ProductSize
                        ProductSize productSize = new ProductSize();
                        productSize.setProductSizeId(rs.getInt("product_size_id"));
                        productSize.setProductSizeName(rs.getString("product_size_name"));
                        productSize.setProductColor(rs.getString("product_color"));

                        orderDetail.setProductSize(productSize); // Set the product size
                        o.addOrderDetail(orderDetail); // Assuming Order has a method to add OrderDetails

                        // Add product name to the map (if you have such a method in Order class)
                        o.addProductName(productId, rs.getString("product_name"));
                    } while (rs.next());

                    return o; // Return the populated Order object
                } else {
                    System.out.println("No order found with ID: " + orderId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order: " + e.getMessage());
        }
        return null; // Return null if no order found or an error occurs
    }

    public int getTotalOrders(String search, Integer status, int roleId) {
        String sql = "SELECT COUNT(*) FROM orders o "
                + "JOIN status s ON o.status = s.status_id " // Join with the status table
                + "WHERE FIND_IN_SET(?, s.role_id) > 0 "; // Filter by role_id

        // Add search filter
        if (search != null && !search.trim().isEmpty()) {
            sql += " AND (o.order_id LIKE ? OR o.customer_id IN (SELECT c.customer_id FROM customers c WHERE c.full_name LIKE ?))";
        }

        // Add status filter if specified
        if (status != null) {
            sql += " AND o.status = ?";
        }

        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            int parameterIndex = 1;

            // Set role_id parameter
            statement.setInt(parameterIndex++, roleId);

            // Set search parameters
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search + "%";
                statement.setString(parameterIndex++, searchPattern);
                statement.setString(parameterIndex++, searchPattern);
            }

            // Set status parameter
            if (status != null) {
                statement.setInt(parameterIndex++, status);
            }

            try ( ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1); // Returns total number of orders
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total orders: " + e.getMessage());
        }
        return 0;
    }

    public boolean updateOrderStatus(int orderId, int status) {
        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // If one or more rows were updated, return true
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public Order addOrder(Order order) {
        String query = "INSERT INTO orders (order_date, total_cost, status, customer_id, address, phone_number) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try ( PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setLong(2, order.getTotalCost());
            stmt.setInt(3, order.getStatus());
            stmt.setInt(4, order.getCustomerId());
            stmt.setString(5, order.getAddress());
            stmt.setString(6, order.getPhoneNumber());

            int rowsAffected = stmt.executeUpdate(); // Execute update and get affected rows

            if (rowsAffected > 0) {
                // Lấy ID của Order vừa được thêm vào
                try ( ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1); // Lấy ID từ kết quả
                        order.setOrderId(generatedId); // Giả sử bạn có phương thức setId trong Order
                    }
                }
                return order; // Trả về đối tượng Order đã được cập nhật ID
            } else {
                System.err.println("Error creating order. No rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching order: " + e.getMessage());
        }
        return null;
    }

    public Order getOrderById(int orderId) {
        String qr = "SELECT * FROM orders WHERE order_id = ?";

        try ( PreparedStatement ps = con.prepareStatement(qr)) {
            ps.setInt(1, orderId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order o = new Order();
                    o.setOrderId(rs.getInt("order_id"));
                    o.setOrderDate(rs.getDate("order_date"));
                    o.setTotalCost(rs.getLong("total_cost"));
                    o.setStatus(rs.getInt("status"));
                    o.setCustomerId(rs.getInt("customer_id"));
                    o.setAddress(rs.getString("address"));
                    o.setPhoneNumber(rs.getString("phone_number"));
                    return o;
                } else {
                    System.out.println("No product found with ID: " + orderId); // Debugging: check if product is found
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return null;
    }

    public List<OrderDetail> getProductOrderInfo(int orderId) {
        List<OrderDetail> productOrderInfoList = new ArrayList<>();
        String query = "SELECT \n"
                + "	od.order_detail_id,\n"
                + "    p.product_name,\n"
                + "    c.name,\n"
                + "    od.product_id,\n"
                + "	\n"
                + "    od.quantity,\n"
                + "                     (ps.prices * od.quantity) as prices,\n"
                + "                     concat( ps.product_size_name ,' , ', ps.product_color )AS size\n"
                + "                 FROM \n"
                + "                     orders o\n"
                + "                 JOIN \n"
                + "                     order_detail od ON o.order_id = od.order_id\n"
                + "                 JOIN \n"
                + "                     products p ON od.product_id = p.product_id\n"
                + "                 JOIN \n"
                + "                     category c ON p.category_id = c.category_id\n"
                + "                 JOIN \n"
                + "                     product_size ps ON ps.product_id = p.product_id\n"
                + "                  AND ps.product_size_id = od.product_size_id  \n"
                + "                 WHERE \n"
                + "                     o.order_id = ?";
        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderDetailId = rs.getInt("order_detail_id");
                String product_name = rs.getString("product_name");
                String category_name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int productId = rs.getInt("product_id");
                int total_cost = rs.getInt("prices");
                String product_size_name = rs.getString("size");

                OrderDetail productOrderInfo = new OrderDetail(orderDetailId, productId, product_name, category_name, quantity, total_cost, product_size_name);
                productOrderInfoList.add(productOrderInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productOrderInfoList;
    }
//    public static void main(String[] args) {
//        OrderDAO o = new OrderDAO();
//        List<OrderDetail> l = o.getProductOrderInfo(85);
//        
//        
//            System.out.println(l);
//        
//    }

    public Order getOrderByID(int orderId) {
        String qr = "SELECT o.order_id, o.order_date, o.total_cost,o.status, s.status_name, o.customer_id, o.address, o.phone_number\n"
                + "FROM orders o\n"
                + "JOIN status s ON o.status = s.status_id\n"
                + "WHERE o.order_id = ?";

        try ( PreparedStatement ps = con.prepareStatement(qr)) {
            ps.setInt(1, orderId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order o = new Order();
                    o.setOrderId(rs.getInt("order_id"));
                    o.setOrderDate(rs.getDate("order_date"));
                    o.setTotalCost(rs.getLong("total_cost"));
                    o.setStatus_name(rs.getString("status_name"));
                    o.setStatus(rs.getInt("status"));
                    o.setCustomerId(rs.getInt("customer_id"));
                    o.setAddress(rs.getString("address"));
                    o.setPhoneNumber(rs.getString("phone_number"));
                    return o;
                } else {
                    System.out.println("No product found with ID: " + orderId); // Debugging: check if product is found
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return null;
    }

    public List<Order> getAllOrderByCustomerId(int customerId) {
        List<Order> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.orders WHERE customer_id = ? ORDER BY order_date desc";

        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setInt(1, customerId);

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setOrderId(rs.getInt("order_id"));
                    o.setOrderDate(rs.getDate("order_date"));
                    o.setTotalCost(rs.getLong("total_cost"));
                    o.setStatus(rs.getInt("status"));
                    o.setCustomerId(rs.getInt("customer_id"));
                    o.setAddress(rs.getString("address"));
                    o.setPhoneNumber(rs.getString("phone_number"));
                    list.add(o);
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }

        return list; // Trả về danh sách đơn hàng
    }

    public void addOrderDetail(List<Item> items, int orderId) {
        String query = "INSERT INTO order_detail (order_id, product_id, quantity, product_size_id) "
                + "VALUES(?, ?, ?, ?)";
        for (Item i : items) {
            try ( PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, i.getProductSize().getProduct().getProductId());
                stmt.setInt(3, i.getQuantity());
                stmt.setInt(4, i.getProductSize().getProductSizeId());

                stmt.executeUpdate(); // Execute update and get affected rows

            } catch (SQLException e) {
                System.err.println("Error fetching order: " + e.getMessage());
            }
        }

    }

    public int getNumberProductByOrder(int orderId) {
        int numberOfProducts = 0;
        String qr = "SELECT COUNT(*) FROM online_shop.order_detail WHERE order_id = ?";

        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setInt(1, orderId);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    numberOfProducts = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }

        return numberOfProducts;
    }

    public int getFirstProductIdByOrderId(int orderId) {

        String qr = "SELECT * FROM online_shop.order_detail WHERE order_id = ? LIMIT 1;";

        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setInt(1, orderId);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(3);
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return -1;
    }

    public List<OrderNumbProduct> getAllOrderByPage(List<OrderNumbProduct> list, int start, int end) {
        ArrayList<OrderNumbProduct> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public boolean checkRemainProductSize(int productSizeId, int quantity) {
        String qr = "SELECT * FROM online_shop.product_size where product_size_id = ?";

        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setInt(1, productSizeId);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int remain = rs.getInt("quantity");
                    if (quantity < remain) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return false;
    }

    public void updateProductRemain(List<Item> items) {
        String query = "UPDATE products set number_left = ? where product_id = ?";
        for (Item i : items) {
            try ( PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, i.getProductSize().getProduct().getNumberLeft() - i.getQuantity());
                stmt.setInt(2, i.getProductSize().getProduct().getProductId());

                stmt.executeUpdate(); // Execute update and get affected rows

            } catch (SQLException e) {
                System.err.println("Error fetching order: " + e.getMessage());
            }
        }

    }

    public List<Address> getAddressByCustomerId(int customerId) {

        String qr = "SELECT * FROM online_shop.address WHERE cus_id = ?";
        List<Address> addrs = new ArrayList<>();
        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setInt(1, customerId);

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    addrs.add(new Address(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getBoolean(4)));
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return addrs;
    }

    public Address getAddressById(int addressId) {
        String qr = "SELECT * FROM online_shop.address WHERE id = ?";

        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setInt(1, addressId);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Address(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getBoolean(4));
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return null;
    }

    public Address getAddressByName(String address) {
        String qr = "SELECT * FROM online_shop.address WHERE address = ?";

        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setString(1, address);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Address(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getBoolean(4));
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return null;
    }

    public boolean checkExistAddress(String address) {
        String qr = "SELECT * FROM online_shop.address WHERE address = ?";

        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setString(1, address);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return false;
    }

    public void addNewAddress(String a, int customerId, int mode) {
        String query = "INSERT INTO online_shop.address (cus_id, address, is_default) "
                + "VALUES(?, ?, ?)";

        try ( PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setString(2, a);
            if (mode == 1) {
                stmt.setInt(3, 1);
            } else {
                stmt.setInt(3, 0);
            }

            stmt.executeUpdate(); // Execute update and get affected rows

        } catch (SQLException e) {
            System.err.println("Error fetching order: " + e.getMessage());
        }
    }

    public void updateAddressDefault(int addressId, int customerId) {

        String query = "UPDATE online_shop.address set is_default = 0 where cus_id = ?";
        try ( PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate(); // Execute update and get affected rows
        } catch (SQLException e) {
            System.err.println("Error fetching order: " + e.getMessage());
        }

        query = "UPDATE online_shop.address set is_default = 1 where cus_id = ? AND id = ?";
        try ( PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, addressId);
            stmt.executeUpdate(); // Execute update and get affected rows
        } catch (SQLException e) {
            System.err.println("Error fetching order: " + e.getMessage());
        }

    }

    public Products getProductByProductSizeId(int productSizeId) {
        String qr = "SELECT product_id FROM online_shop.product_size WHERE product_size_id = ?";
        DAO dao = new DAO();
        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setInt(1, productSizeId);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return dao.getProductById(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return null;
    }

    public ProductSize getProductSizeByProductSizeId(int productSizeId) {
        String qr = "SELECT * FROM online_shop.product_size WHERE product_size_id = ?";

        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setInt(1, productSizeId);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ProductSize(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
                            rs.getInt(6), rs.getInt(7), rs.getInt(8));
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return null;
    }

    public String getStatusNameById(int statusId) {
        String qr = "SELECT status_name FROM online_shop.status WHERE status_id = ?";

        try ( PreparedStatement stmt = con.prepareStatement(qr)) {
            stmt.setInt(1, statusId);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }
        return null;
    }

//    public static void main(String[] args) {
//        OrderDAO orderDAO = new OrderDAO();
////        int customerId = 2;
////        int orderId = 67;
////        // Retrieve ordered products for the given customer ID
////        List<Products> orderedProducts = orderDAO.getOrderedProducts(orderId, customerId);
////
////        // Display the results
////        if (orderedProducts.isEmpty()) {
////            System.out.println("No ordered products found for customer ID: " + customerId);
////        } else {
////            System.out.println("Ordered Products for Customer ID " + customerId + ":");
////            for (Products product : orderedProducts) {
////                System.out.println("Product ID: " + product.getProductId());
////                System.out.println("Product Name: " + product.getProductName());
////                System.out.println("Image URL: " + product.getImageUrl());
////                System.out.println("Sale Price: $" + product.getSalePrice());
////                List<ProductSize> sizes = product.getSizes();
////                if (sizes != null && !sizes.isEmpty()) {
////                    System.out.print("Sizes: ");
////                    for (ProductSize size : sizes) {
////                        System.out.print(size.getProductSizeName() + " "); // Print each size name
////                    }
////                    System.out.println(); // New line after printing sizes
////                } else {
////                    System.out.println("Sizes: None available");
////                }
////
////                System.out.println("-------------------------------------");
////            }
////        }
//        List<Address> addrs = orderDAO.getAddressByCustomerId(2);
//        for (Address a : addrs) {
//            System.out.println(a.getAddress());
//        }
//    }
}
