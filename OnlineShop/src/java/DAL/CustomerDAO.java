package DAL;

import Models.Customers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DBContext {

    private static CustomerDAO INSTANCE = null;
    private Connection con;
    private String status;

    // Private constructor for Singleton
    public CustomerDAO() {
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
    public static CustomerDAO getInstance() {
        if (INSTANCE == null) {
            synchronized (CustomerDAO.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CustomerDAO();
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

    public Customers getCustomerByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM customers WHERE email = ? AND password = ?";
        try ( PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Customers customer = new Customers(
                            rs.getInt("customer_id"),
                            rs.getString("full_name"),
                            rs.getBoolean("gender"),
                            rs.getString("email"),
                            rs.getString("phone_number"),
                            rs.getString("address"),
                            rs.getInt("status"),
                            rs.getString("password"),
                            rs.getDate("last_update"),
                            rs.getInt("update_by")
                    );
                    return customer;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customer: " + e.getMessage());
        }
        return null;
    }

    public boolean isEmailExist(String email) {
        String query = "SELECT COUNT(*) FROM customers WHERE email = ?";

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

    public Customers createCustomer(Customers customer) {
        String query = "INSERT INTO `online_shop`.`customers`\n"
                + "(\n"
                + "`full_name`,\n"
                + "`gender`,\n"
                + "`email`,\n"
                + "`phone_number`,\n"
                + "`address`,\n"
                + "`status`,\n"
                + "`password`)\n"
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customer.getFullName());
            stmt.setBoolean(2, customer.isGender()); // Set boolean value directly
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getAddress());
            stmt.setInt(6, customer.getStatus());
            stmt.setString(7, customer.getPassword());

            int rowsAffected = stmt.executeUpdate(); // Execute update and get affected rows

            if (rowsAffected > 0) {
                // Insertion successful, potentially return newly created customer (optional)
                // You would need another query to retrieve the newly inserted customer details              
                return customer; // Modify this to return the new customer if needed
            } else {
                System.err.println("Error creating customer. No rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching customer: " + e.getMessage());
        }
        return null;
    }

    public void updateCustomerInfo(String note,int updateBy, int customerId) {
        String query = "UPDATE customers SET  note = ?, last_update = ?, update_by =? WHERE customer_id = ?";

        try ( PreparedStatement ps = con.prepareStatement(query)) {
            // Set the note parameter (assuming it's a String)
            ps.setString(1, note);

            // Set the update_date parameter (assuming you're using java.sql.Timestamp for the date)
            ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));

            // Set the customer_id parameter
            ps.setInt(3, updateBy);
            ps.setInt(4, customerId);

            // Execute the update
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerStatus(Customers customer) {
        String query = "UPDATE customers SET status = ? WHERE customer_id = ?";

        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customer.getStatus());
            ps.setInt(2, customer.getCustomerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customers> getAllCustomers(String search, Integer status, int pageIndex, int pageSize, String sortBy, String sortDirection) {
        List<Customers> customers = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT customer_id, full_name, gender, email, phone_number, address, status, password, last_update, update_by FROM customers WHERE 1=1");

        // Add conditions for status and search
        if (status != null) {
            query.append(" AND status = ?");
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
                ps.setInt(index++, status);
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
                Customers customer = new Customers(
                        rs.getInt("customer_id"),
                        rs.getString("full_name"),
                        rs.getBoolean("gender"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getInt("status"),
                        rs.getString("password"),
                        rs.getDate("last_update"),
                        rs.getInt("update_by")
                );

                // Automatically set status based on conditions
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");

                int newStatus;

                if (email != null && !email.isEmpty() && phoneNumber != null && !phoneNumber.isEmpty()) {
                    newStatus = 2; // 2 = Potential
                } else if (email != null && !email.isEmpty() || phoneNumber != null && !phoneNumber.isEmpty()) {
                    newStatus = 0; // 0 = Customer
                } else {
                    newStatus = 1; // 1 = Contact
                }

                if (customer.getStatus() != newStatus) {
                    customer.setStatus(newStatus);
                    updateCustomerStatus(customer);
                }

                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return customers;
    }

    public int getTotalCustomers(String search, Integer status) {
        int total = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM customers WHERE 1=1");

        if (status != null) {
            sql.append(" AND status = ?");
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (full_name LIKE ? OR email LIKE ? OR phone_number LIKE ?)");
        }

        try ( PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (status != null) {
                statement.setInt(index++, status);
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

    public Customers getCustomerById(int customerId) {
        Customers customer = null;
        String query = "SELECT * from customers WHERE customer_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = new Customers(
                        rs.getInt("customer_id"),
                        rs.getString("full_name"),
                        rs.getBoolean("gender"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getInt("status"),
                        rs.getString("password"),
                        rs.getDate("last_update"),
                        rs.getInt("update_by"), 
                        rs.getString("note")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return customer;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Use getInstance() for Singleton pattern
        CustomerDAO dao = CustomerDAO.getInstance();
        Customers c = dao.getCustomerById(3);
        System.out.println("c: " + c.getAddress());

    }
}
