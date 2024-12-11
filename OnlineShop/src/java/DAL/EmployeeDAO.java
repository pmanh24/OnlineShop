/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Customers;
import Models.Employees;
import Models.Employees;
import Models.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hieu
 */
public class EmployeeDAO extends DBContext {

    private static EmployeeDAO INSTANCE = null;
    private Connection con;
    private String status;

    public EmployeeDAO() {
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

    // Singleton getInstance method
    public static EmployeeDAO getInstance() {
        if (INSTANCE == null) {
            synchronized (EmployeeDAO.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EmployeeDAO();
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

    public Employees getEmployeeByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM employees WHERE email = ? AND password = ?";
        try ( PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Employees employee = new Employees(
                            rs.getInt("employee_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getInt("role_id"),
                            rs.getString("password")
                    );
                    return employee;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching employee: " + e.getMessage());
        }
        return null;
    }

    public boolean isEmailExist(String email) {
        String query = "SELECT COUNT(*) FROM employees WHERE email = ?";

        try ( PreparedStatement stmt = con.prepareStatement(query)) {
            // Set the email parameter in the query
            stmt.setString(1, email);

            // Execute the query
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Check if count is greater than 0
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking if email exists: " + e.getMessage());
        }
        return false; // Return false if no match or an error occurs
    }

    public int getTotalEmployees(String search, Boolean status, Boolean gender, Integer role) {
        int total = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM employees WHERE 1=1");

        if (status != null) {
            sql.append(" AND status = ?");
        }
        if (gender != null) {
            sql.append(" AND gender = ?");
        }
        if (role != null) {
            sql.append(" AND role_id = ?");
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (full_name LIKE ? OR email LIKE ? OR phone_number LIKE ?)");
        }

        try ( PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (status != null) {
                statement.setBoolean(index++, status);
            }

            if (gender != null) {
                statement.setBoolean(index++, gender);
            }
            if (role != null) {
                statement.setInt(index++, role);
            }
            if (search != null && !search.isEmpty()) {
                statement.setString(index++, "%" + search + "%");
                statement.setString(index++, "%" + search + "%");
                statement.setString(index++, "%" + search + "%");
            }

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<Employees> getAllEmployees(String search, Boolean status, Boolean gender, Integer role, int pageIndex, int pageSize, String sortBy, String sortDirection) {
        List<Employees> employees = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT employee_id, full_name, gender, email, phone_number, role_id, status  FROM employees WHERE 1=1");

        // Add conditions for status and search
        if (status != null) {
            query.append(" AND status = ?");
        }
        if (gender != null) {
            query.append(" AND gender = ?");
        }
        if (role != null) {
            query.append(" AND role_id = ?");
        }
        if (search != null && !search.isEmpty()) {
            query.append(" AND (full_name LIKE ? OR email LIKE ? OR phone_number LIKE ?)");
        }

        // Add sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            query.append(" ORDER BY ").append(sortBy);
            if ("desc".equalsIgnoreCase(sortDirection)) {
                query.append(" DESC");
            } else {
                query.append(" ASC");
            }
        }

        // Add limit and offset for pagination
        query.append(" LIMIT ? OFFSET ?");

        try {
            PreparedStatement ps = con.prepareStatement(query.toString());
            int index = 1;

            // Set status if it's provided
            if (status != null) {
                ps.setBoolean(index++, status);
            }

            if (gender != null) {
                ps.setBoolean(index++, gender);
            }
            if (role != null) {
                ps.setInt(index++, role);
            }

            // Set search terms if provided
            if (search != null && !search.isEmpty()) {
                ps.setString(index++, "%" + search + "%");
                ps.setString(index++, "%" + search + "%");
                ps.setString(index++, "%" + search + "%");
            }

            // Set limit (page size) and offset (page index)
            ps.setInt(index++, pageSize);
            ps.setInt(index++, (pageIndex - 1) * pageSize); // Calculate offset

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Process result set and populate the customer list
            while (rs.next()) {
                Employees employee = new Employees(
                        rs.getInt("employee_id"),
                        rs.getString("full_name"),
                        rs.getBoolean("gender"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getInt("role_id"),
                        rs.getBoolean("status")
                );

                // Automatically set status based on conditions
//                String email = rs.getString("email");
//                String phoneNumber = rs.getString("phone_number");
//                int newStatus;
//
//                if (email != null && !email.isEmpty() && phoneNumber != null && !phoneNumber.isEmpty()) {
//                    newStatus = 2; // 2 = Potential
//                } else if (email != null && !email.isEmpty() || phoneNumber != null && !phoneNumber.isEmpty()) {
//                    newStatus = 0; // 0 = Customer
//                } else {
//                    newStatus = 1; // 1 = Contact
//                }
//                if (employee.getStatus() != newStatus) {
//                    customer.setStatus(newStatus);
//                    updateCustomerStatus(customer);
//                }
                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return employees;
    }

    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.roles";

        try ( PreparedStatement stmt = con.prepareStatement(qr)) {

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Role r = new Role();
                    r.setRoleId(rs.getInt(1));
                    r.setRoleName(rs.getString(2));

                    list.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Have error now here: " + e.getMessage());
        }

        return list; // Trả về danh sách đơn hàng
    }

    public Employees getEmployeeById(int employeeId) {
        Employees employee = null;
        String query = "SELECT * from employees WHERE employee_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employee = new Employees(
                        rs.getInt("employee_id"),
                        rs.getString("full_name"),
                        rs.getBoolean("gender"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getInt("role_id"),
                        rs.getBoolean("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return employee;
    }

    public void updateEmployeeInfo(int employeeId, boolean status, int roleId) {
        String query = "UPDATE employees SET status = ?, role_id = ? WHERE employee_id = ?";

        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ps.setBoolean(1, status);
            ps.setInt(2, roleId);
            ps.setInt(3, employeeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employees createEmployee(Employees employee) {
        String query = "INSERT INTO `online_shop`.`employees`\n"
                + "(\n"
                + "`full_name`,\n"
                + "`gender`,\n"
                + "`email`,\n"
                + "`phone_number`,\n"
                + "`role_id`,\n"
                + "`status`,\n"
                + "`password`)\n"
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employee.getFullName());
            stmt.setBoolean(2, employee.isGender()); // Set boolean value directly
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPhoneNumber());
            stmt.setInt(5, employee.getRoleId());
            stmt.setBoolean(6, employee.isStatus());
            stmt.setString(7, employee.getPassword());

            int rowsAffected = stmt.executeUpdate(); // Execute update and get affected rows

            if (rowsAffected > 0) {
                // Insertion successful, potentially return newly created customer (optional)
                // You would need another query to retrieve the newly inserted customer details              
                return employee; // Modify this to return the new customer if needed
            } else {
                System.err.println("Error creating customer. No rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching customer: " + e.getMessage());
        }
        return null;
    }

    public int getOrderCount(String startDate, String endDate) {
        String queryPosts = "SELECT COUNT(*) AS order_count FROM orders WHERE order_date BETWEEN ? AND ?";
        try ( PreparedStatement statement = connection.prepareStatement(queryPosts)) {
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            try ( ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("order_count");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0; // Return 0 if no posts are found
    }

    public long getRevenueTotal(String startDate, String endDate) {
        String queryPosts = "SELECT SUM(total_cost) AS total_revenue FROM orders WHERE order_date BETWEEN ? AND ?";
        try ( PreparedStatement statement = connection.prepareStatement(queryPosts)) {
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            try ( ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("total_revenue");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0; // Return 0 if no posts are found
    }

    public int getNewCustomersCount(String startDate, String endDate) {
        String queryNewCustomers = "SELECT COUNT(*) AS new_customers_count FROM customers WHERE create_at BETWEEN ? AND ?";
        try ( PreparedStatement statement = connection.prepareStatement(queryNewCustomers)) {
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            try ( ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("new_customers_count");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0; // Return 0 if no new customers are found
    }

    public int getFeedbackCount(String startDate, String endDate) {
        String queryFeedback = "SELECT COUNT(*) AS feedback_count FROM feedbacks WHERE create_date BETWEEN ? AND ?";
        try ( PreparedStatement statement = connection.prepareStatement(queryFeedback)) {
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            try ( ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("feedback_count");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0; // Return 0 if no feedbacks are found
    }

    public Map<LocalDate, Integer> getOrderTrends(String endDate) {
        Map<LocalDate, Integer> orderTrends = new LinkedHashMap<>();

        // Parse endDate and calculate the start date (7 days back)
        LocalDate end = LocalDate.parse(endDate);
        LocalDate start = end.minusDays(6); // Get the date 6 days before the end date

        // Fill the map with dates and initial counts of zero for the 7 days
        for (int i = 0; i < 7; i++) {
            orderTrends.put(start.plusDays(i), 0); // Adding each date from start to end
        }

        // SQL query to get the order counts
        String queryOrders = "SELECT DATE(order_date) AS order_date, COUNT(*) AS order_count "
                + "FROM orders WHERE order_date BETWEEN ? AND ? "
                + "GROUP BY DATE(order_date) ORDER BY DATE(order_date)";

        try ( PreparedStatement statement = connection.prepareStatement(queryOrders)) {
            statement.setString(1, start.toString());
            statement.setString(2, end.toString());
            try ( ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    LocalDate orderDate = rs.getDate("order_date").toLocalDate();
                    int orderCount = rs.getInt("order_count");

                    // Update the order count for the corresponding date
                    if (orderTrends.containsKey(orderDate)) {
                        orderTrends.put(orderDate, orderCount);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orderTrends;
    }

    public Map<String, Integer> getOrderStatus(LocalDate startDate, LocalDate endDate) {
        Map<String, Integer> orderStatusCounts = new HashMap<>();

        // SQL query to get order status counts between the specified dates
        String query = "SELECT s.status_name, COUNT(*) AS status_count "
                + "FROM orders o join status s on o.status = s.status_id "
                + "WHERE ( o.status = 1 OR o.status = 6 OR o.status = 8 ) AND o.order_date >= ? AND o.order_date <= ? " // Filter by order date
                + "GROUP BY s.status_name"; // Group by order status

        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));

            try ( ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String status = rs.getString("status_name");
                    int count = rs.getInt("status_count");
                    orderStatusCounts.put(status, count); // Map status to its count
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }

        return orderStatusCounts; // Return the map of order status counts
    }

    public Map<String, Long> getRevenues(LocalDate startDate, LocalDate endDate) {
        Map<String, Long> revenueCounts = new HashMap<>();

        // SQL query to get total revenue and revenue by product categories between the specified dates
        String query = "SELECT c.name, SUM(p.sale_price * od.quantity) AS total_revenue "
                + "FROM order_detail od "
                + "JOIN orders o ON od.order_id = o.order_id "
                + "JOIN products p ON od.product_id = p.product_id "
                + "JOIN category c ON p.category_id = c.category_id "
                + "WHERE o.order_date >= ? AND o.order_date <= ? " // Filter by order date
                + "GROUP BY c.name"; // Group by product category

        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));

            try ( ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String category = rs.getString("name");
                    long revenue = rs.getLong("total_revenue");
                    revenueCounts.put(category, revenue); // Map category to its revenue
                }

            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }

        return revenueCounts; // Return the map of revenue counts
    }

    public Map<String, Integer> getCustomerPurchaseStatistics(LocalDate startDate, LocalDate endDate) {
        Map<String, Integer> customerCounts = new HashMap<>();

        // SQL query to get the counts of bought and not bought customers between the specified dates
        String boughtQuery = "SELECT COUNT(DISTINCT o.customer_id) AS bought_count "
                + "FROM orders o "
                + "WHERE o.order_date >= ? AND o.order_date <= ?";

        String notBoughtQuery = "SELECT COUNT(DISTINCT c.customer_id) AS not_bought_count "
                + "FROM customers c "
                + "WHERE c.customer_id NOT IN (SELECT DISTINCT o.customer_id FROM orders o "
                + "WHERE o.order_date >= ? AND o.order_date <= ?)";

        try ( PreparedStatement boughtStatement = connection.prepareStatement(boughtQuery)) {
            // Set parameters for the bought query
            boughtStatement.setDate(1, java.sql.Date.valueOf(startDate));
            boughtStatement.setDate(2, java.sql.Date.valueOf(endDate));

            try ( ResultSet rs = boughtStatement.executeQuery()) {
                if (rs.next()) {
                    int boughtCount = rs.getInt("bought_count");
                    customerCounts.put("Bought", boughtCount); // Add bought count
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }

        try ( PreparedStatement notBoughtStatement = connection.prepareStatement(notBoughtQuery)) {
            // Set parameters for the not bought query
            notBoughtStatement.setDate(1, java.sql.Date.valueOf(startDate));
            notBoughtStatement.setDate(2, java.sql.Date.valueOf(endDate));

            try ( ResultSet rs = notBoughtStatement.executeQuery()) {
                if (rs.next()) {
                    int notBoughtCount = rs.getInt("not_bought_count");
                    customerCounts.put("Not Bought", notBoughtCount); // Add not bought count
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }

        return customerCounts; // Return the map of customer counts
    }

    public Map<String, Double> getAverageStarsByCategory(LocalDate startDate, LocalDate endDate) {
        Map<String, Double> averageStarsCounts = new HashMap<>();

        // SQL query to get average stars by product categories between the specified dates
        String query = "SELECT c.name, AVG(f.rated_star) AS average_star "
                + "FROM feedbacks f "
                + "JOIN products p ON f.product_id = p.product_id "
                + "JOIN category c ON p.category_id = c.category_id "
                + "WHERE f.create_date >= ? AND f.create_date <= ? " // Filter by feedback date
                + "GROUP BY c.name"; // Group by product category

        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));

            try ( ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String category = rs.getString("name");
                    double averageStar = rs.getDouble("average_star");
                    averageStarsCounts.put(category, averageStar); // Map category to its average star rating
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }

        return averageStarsCounts; // Return the map of average star ratings
    }

    public Map<String, double[]> getAverageStarsAndVotesByCategory(LocalDate startDate, LocalDate endDate) {
        Map<String, double[]> averageStarsCounts = new HashMap<>();

        // SQL query to get average stars and vote count by product categories between the specified dates
        String query = "SELECT c.name, AVG(f.rated_star) AS average_star, COUNT(f.feedback_id) AS vote_count "
                + "FROM feedbacks f "
                + "JOIN products p ON f.product_id = p.product_id "
                + "JOIN category c ON p.category_id = c.category_id "
                + "WHERE f.create_date >= ? AND f.create_date <= ? " // Filter by feedback date
                + "GROUP BY c.name"; // Group by product category

        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));

            try ( ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String category = rs.getString("name");
                    double averageStar = rs.getDouble("average_star");
                    int voteCount = rs.getInt("vote_count");

                    averageStarsCounts.put(category, new double[]{averageStar, voteCount}); // Map category to its average star rating and vote count
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }

        return averageStarsCounts; // Return the map of average star ratings and vote counts
    }

    public static void main(String[] args) {
        String email = "sale@gmail.com"; // Replace with the test email
        String password = "vWWFGwlqsOS/bEeeZHdhi0Rj1mA="; // Replace with the test password
        EmployeeDAO dao = new EmployeeDAO();
        List<Employees> employees = dao.getAllEmployees("", true, true, 1, 1, 10, null, null);
        for (Employees emp : employees) {
            System.out.println(emp.getFullName());
        }

        LocalDate startDate = LocalDate.of(2024, 10, 1); // Ngày bắt đầu
        LocalDate endDate = LocalDate.of(2024, 10, 20); // Ngày kết thúc

        // Gọi hàm getOrderStatus
        Map<String, Integer> orderStatusCounts = dao.getOrderStatus(startDate, endDate);

        // Hiển thị dữ liệu của map
        if (orderStatusCounts.isEmpty()) {
            System.out.println("Không có dữ liệu nào trong khoảng thời gian này.");
        } else {
            for (Map.Entry<String, Integer> entry : orderStatusCounts.entrySet()) {
                System.out.println("Trạng thái: " + entry.getKey() + " - Số lượng: " + entry.getValue());
            }
        }

        Map<String, Long> revenues = dao.getRevenues(startDate, endDate);

        // In ra kết quả
        for (Map.Entry<String, Long> entry : revenues.entrySet()) {
            System.out.println("Category: " + entry.getKey() + ", Revenue: " + entry.getValue());
        }

        Map<String, Integer> customerPurchaseStats = dao.getCustomerPurchaseStatistics(startDate, endDate);

        System.out.println("Customer Purchase Statistics from " + startDate + " to " + endDate + ":");
        System.out.println("Bought: " + customerPurchaseStats.get("Bought"));
        System.out.println("Not Bought: " + customerPurchaseStats.get("Not Bought"));

        Map<String, Double> averageStars = dao.getAverageStarsByCategory(startDate, endDate);

        // In ra kết quả
        System.out.println("Average Stars by Category:");
        for (Map.Entry<String, Double> entry : averageStars.entrySet()) {
            System.out.printf("Category: %s, Average Star: %.2f%n", entry.getKey(), entry.getValue());
        }

        Map<String, double[]> averageStarsAndVotes = dao.getAverageStarsAndVotesByCategory(startDate, endDate);

        // In kết quả ra console
        for (Map.Entry<String, double[]> entry : averageStarsAndVotes.entrySet()) {
            String category = entry.getKey();
            double averageStar = entry.getValue()[0];
            int voteCount = (int) entry.getValue()[1]; // Lấy số lượng vote
            System.out.printf("Category: %s, Average Star: %.2f, Vote Count: %d%n", category, averageStar, voteCount);
        }

        Map<String, double[]> rateCate = dao.getAverageStarsAndVotesByCategory(startDate, endDate);

// Create a new map to hold modified category names and their average star ratings and vote counts
        Map<String, Double> modifiedRate = new HashMap<>();
        Map<String, Integer> voteCounts = new HashMap<>();

        for (Map.Entry<String, double[]> entry : rateCate.entrySet()) {
            String modifiedRateName = entry.getKey()
                    .replace("'", "_"); // Replace apostrophes with underscores

            double averageStar = entry.getValue()[0]; // Lấy số sao trung bình
            int voteCount = (int) entry.getValue()[1]; // Lấy số lượng vote

            modifiedRate.put(modifiedRateName, averageStar); // Lưu số sao trung bình
            voteCounts.put(modifiedRateName, voteCount); // Lưu số lượng vote
        }

        for (Map.Entry<String, Double> entry : modifiedRate.entrySet()) {
            String category = entry.getKey();
            Double averageStar = entry.getValue();
            Integer voteCount = voteCounts.get(category);

            System.out.println("Category: " + category + ", Average Star: " + averageStar + ", Vote Count: " + voteCount);
            // Tính tổng số sao trung bình
            int totalRate = voteCounts.values().stream().mapToInt(Integer::intValue).sum();
            Map<String, Double> ratePercent = new HashMap<>(); // Map để lưu tỷ lệ phần trăm

            for (Map.Entry<String, Integer> entry1 : voteCounts.entrySet()) {
                String category1 = entry1.getKey(); // Lấy tên category
                int voteCount1 = entry1.getValue(); // Lấy số lượng vote cho category

                // Tính tỷ lệ phần trăm và làm tròn đến 2 chữ số thập phân
                double percent = ((double) voteCount / totalRate) * 100;
                ratePercent.put(category, Math.round(percent * 100.0) / 100.0); // Lưu kết quả vào map với key là category
                System.out.println(category1 +""+ voteCount1);
            }
        }

        
//        if (employee != null) {
//            System.out.println("Employee found: " + employee.getFullName());
//            System.out.println("Employee role: " + employee.getRoleId());
//        } else {
//            System.out.println("No employee found with the provided email and password.");
//        }
    }
}
