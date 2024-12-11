/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Blog;
import Models.BlogImage;
import Models.Blogs;
import Models.Category;
import Models.Customers;
import Models.FeedBacks;
import Models.PaymentHistory;
import Models.ProductColor;
import Models.ProductImage;
import Models.ProductSize;
import Models.Products;
import Models.Sliders;
import Models.Users;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hieu
 */
public class DAO extends DBContext {

    private static DAO INSTANCE = null;
    private Connection con;
    private String status;

    public DAO() {
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

    public static DAO getInstance() {
        if (INSTANCE == null) {
            synchronized (DAO.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DAO();
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

    public List<ProductSize> getProductSizesByProductId(int productId) {
        List<ProductSize> sizes = new ArrayList<>();
        String sql = "SELECT * FROM product_size WHERE product_id = ?";
        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sizes.add(new ProductSize(rs.getInt("product_size_id"), rs.getString("product_size_name"), rs.getInt("product_id"), rs.getInt("quantity"), rs.getString("product_color"), rs.getInt("import_price"), rs.getInt("prices"), rs.getInt("hold")));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return sizes;
    }

    public void insertProductSize(ProductSize productSize) throws SQLException {
        String sql = "INSERT INTO product_size (product_size_name, product_id, quantity, product_color, import_price, prices, hold) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try ( PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, productSize.getProductSizeName());
            pstmt.setInt(2, productSize.getProductId());
            pstmt.setInt(3, productSize.getQuantity());
            pstmt.setString(4, productSize.getProductColor());
            pstmt.setInt(5, productSize.getImportPrice());
            pstmt.setInt(6, productSize.getPrices());
            pstmt.setInt(7, productSize.getHold());

            pstmt.executeUpdate();
        }
    }

    public int getTotalFilteredProducts(String search, Integer categoryId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM products WHERE 1=1");

        if (categoryId != null) {
            sql.append(" AND category_id = ?");
        }

        if (search != null && !search.isEmpty()) {
            sql.append(" AND (product_name LIKE ?)");
        }

        try ( PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (categoryId != null) {
                statement.setInt(index++, categoryId);
            }
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search + "%";
                statement.setString(index++, searchPattern);
            }

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if no products found
    }

    public boolean hasUserFeedback(int orderId, int customerId) {
        boolean feedbackExists = false;
        String query = "SELECT COUNT(*) FROM Feedbacks WHERE order_d = ? AND customer_id = ?";

        try ( Connection conn = getConnection(); // Make sure to implement this method
                  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, orderId);
            ps.setInt(2, customerId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                feedbackExists = rs.getInt(1) > 0; // Feedback exists if count > 0
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
        return feedbackExists;
    }

    public List<String> getFeedbackImages(int customerId, int orderId, int productId) {
        List<String> imageFileNames = new ArrayList<>();
        String query = "SELECT images FROM Feedbacks WHERE customer_id = ? AND order_id = ? AND product_id = ?";

        // Ensure you have a method to get your DB connection
        try ( Connection conn = getConnection(); // Adjusted to use getConnection method
                  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, orderId);
            stmt.setInt(3, productId);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String images = rs.getString("images");
                    // Assuming images are stored as a comma-separated string
                    if (images != null && !images.isEmpty()) {
                        imageFileNames = Arrays.asList(images.split(",")); // Convert string to list
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions appropriately
        }

        return imageFileNames;
    }

    public List<ProductImage> getImagebyID(int productid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProductImage> image = new ArrayList<>();

        String sql = "SELECT image_url, product_id from product_image where product_id = ?";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            {
                ps.setInt(1, productid);
                rs = ps.executeQuery();
            }

            while (rs.next()) {
                image.add(new ProductImage(rs.getString(1), rs.getInt(2)));
            }

        } catch (Exception e) {
        }

        return image;
    }

    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String qr = "SELECT category_id,name FROM online_shop.category where status = 1";
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("category_id"),
                        rs.getString("name")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<FeedBacks> getAllFeedbackByProductId(int productId) {
        List<FeedBacks> list = new ArrayList<>();
        String qr = "SELECT f.feedback_id, f.product_id, f.customer_id, f.rated_star, f.content, f.create_date, "
                + "p.product_name, c.full_name AS customer_name, ps.product_size_name, ps.product_color, "
                + "f.size_id, f.images "
                + "FROM online_shop.feedbacks f "
                + "JOIN online_shop.products p ON f.product_id = p.product_id "
                + "JOIN online_shop.customers c ON f.customer_id = c.customer_id "
                + "JOIN online_shop.product_size ps ON f.size_id = ps.product_size_id " // Join with product_size
                + "WHERE f.status = 1 AND f.product_id = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(qr)) {
            stmt.setInt(1, productId);  // Set the product_id parameter
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int feedbackId = rs.getInt("feedback_id");
                    int prodId = rs.getInt("product_id");
                    int custId = rs.getInt("customer_id");
                    int ratedStar = rs.getInt("rated_star");
                    String content = rs.getString("content");
                    String product = rs.getString("product_name"); // Get product name from join
                    String customer = rs.getString("customer_name"); // Get customer name from join
                    String productSizeName = rs.getString("product_size_name"); // Get size name from product_size
                    String productColor = rs.getString("product_color"); // Get color from product_size
                    Integer sizeId = rs.getInt("size_id");

                    // Split images by commas or your chosen delimiter into a List<String>
                    String images = rs.getString("images");
                    List<String> imageFileNames = (images != null) ? Arrays.asList(images.split(",")) : new ArrayList<>();
                    String createDate = rs.getString("create_date");

                    // Create the FeedBacks object with the additional fields
                    FeedBacks feedback = new FeedBacks(
                            feedbackId,
                            prodId,
                            custId,
                            ratedStar,
                            content,
                            product,
                            customer,
                            sizeId,
                            imageFileNames,
                            createDate,
                            productSizeName,
                            productColor // Assuming you have these parameters in your FeedBacks constructor
                    );
                    list.add(feedback);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching feedbacks: " + e.getMessage());
        }
        return list;
    }

    public List<ProductImage> getAllByProId(int id) {
        List<ProductImage> list = new ArrayList<>();
        String sql = "SELECT * FROM `online_shop`.`product_image` WHERE product_id= ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            // loop until select the last object
            while (rs.next()) {
                ProductImage u = new ProductImage(rs.getInt(1), rs.getString(2), rs.getInt(3));
                list.add(u);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public boolean updateProduct(Products product) {
        String sql = "UPDATE `online_shop`.`products` SET "
                + "product_name = ?, "
                + "category_id = ?, "
                + "original_price = ?, "
                + "sale_price = ?, "
                + "number_left = ?, "
                + "image_url = ?, "
                + "description = ?, "
                + "status = ?, "
                + "featured = ?, "
                + "brief_information = ? "
                + "WHERE product_id = ?";

        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getCategory().getCategoryId());
            stmt.setInt(3, product.getOriginalPrice());
            stmt.setInt(4, product.getSalePrice());
            stmt.setInt(5, product.getNumberLeft());
            stmt.setString(6, product.getImageUrl());
            stmt.setString(7, product.getDescription());
            stmt.setInt(8, product.getStatus());
            stmt.setInt(9, product.getFeatured());
            stmt.setString(10, product.getBriefInformation());
            stmt.setInt(11, product.getProductId());

            // Execute the update and get the number of affected rows
            int affectedRows = stmt.executeUpdate();

            // Check if the update was successful
            return affectedRows > 0; // Return true if product was updated, false otherwise
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
            return false; // Indicate failure
        } finally {
            // Ensure the PreparedStatement is closed in the finally block
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Handle exceptions on closing resources
            }
        }
    }

    public boolean addProduct(Products product) {
        String sql = "INSERT INTO `online_shop`.`products` (product_name, category_id, original_price, sale_price, number_left, create_date, image_url, description, status, brief_information) VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?)";

        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getCategory().getCategoryId());
            stmt.setInt(3, product.getOriginalPrice());
            stmt.setInt(4, product.getSalePrice());
            stmt.setInt(5, product.getNumberLeft());
            stmt.setString(6, product.getImageUrl());
            stmt.setString(7, product.getDescription());
            stmt.setInt(8, product.getStatus());
            stmt.setString(9, product.getBriefInformation());

            // Execute the update and get the number of affected rows
            int affectedRows = stmt.executeUpdate();

            // Check if the insertion was successful
            return affectedRows > 0; // Return true if product was added, false otherwise
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
            return false; // Indicate failure
        } finally {
            // Ensure the PreparedStatement is closed in the finally block
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Handle exceptions on closing resources
            }
        }
    }

    public boolean addProductSizes(int productId, List<ProductSize> sizes) {
        String sql = "INSERT INTO online_shop.product_size (product_size_name, product_id, quantity, product_color) VALUES (?, ?, ?, ?)";

        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (ProductSize size : sizes) {
                stmt.setString(1, size.getProductSizeName());
                stmt.setInt(2, productId);
                stmt.setInt(3, size.getQuantity());
                stmt.setString(4, size.getProductColor());
                stmt.addBatch(); // Add to batch
            }
            int[] result = stmt.executeBatch(); // Execute batch insert
            return result.length > 0; // Return true if at least one insert succeeded
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addProductColors(int productId, List<ProductColor> colors) throws SQLException {
        String sql = "INSERT INTO `online_shop`.`product_color` (`product_color_name`, `product_id`) VALUES (?, ?)";
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (ProductColor color : colors) {
                stmt.setString(1, color.getProductColorName());
                stmt.setInt(2, productId);
                stmt.executeUpdate();
            }
        }
    }

    public void addProductImages(int productId, List<ProductImage> images) throws SQLException {
        String sql = "INSERT INTO `online_shop`.`product_image` (`image_url`, `product_id`) VALUES (?, ?)";
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (ProductImage image : images) {
                stmt.setString(1, image.getImageUrl());
                stmt.setInt(2, productId);
                stmt.executeUpdate();
            }
        }
    }

    public void updateProductColors(int productId, List<ProductColor> newColors) throws SQLException {
        // First, delete existing colors
        String deleteSql = "DELETE FROM `online_shop`.`product_color` WHERE `product_id` = ?";
        try ( PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, productId);
            deleteStmt.executeUpdate();
        }

        // Now, insert new colors
        String insertSql = "INSERT INTO `online_shop`.`product_color` (`product_color_name`, `product_id`) VALUES (?, ?)";
        try ( PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
            for (ProductColor color : newColors) {
                insertStmt.setString(1, color.getProductColorName());
                insertStmt.setInt(2, productId);
                insertStmt.executeUpdate();
            }
        }
    }

    public void updateProductSizes(int productId, List<ProductSize> newSizes) throws SQLException {
        // First, delete existing sizes
        String deleteSql = "DELETE FROM `online_shop`.`product_size` WHERE `product_id` = ?";
        try ( PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, productId);
            deleteStmt.executeUpdate();
        }

        // Now insert new sizes
        String insertSql = "INSERT INTO `online_shop`.`product_size` (`product_size_name`, `product_id`) VALUES (?, ?)";
        try ( PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
            for (ProductSize size : newSizes) {
                insertStmt.setString(1, size.getProductSizeName());
                insertStmt.setInt(2, productId);
                insertStmt.executeUpdate();
            }
        }
    }

    public void updateProductImage(int productImageId, String imageUrl) throws SQLException {
        String sql = "UPDATE `online_shop`.`product_image` SET `image_url` = ? WHERE `product_image_id` = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, imageUrl);
            stmt.setInt(2, productImageId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Image updated successfully!");
            } else {
                System.out.println("No image found with the given ID.");
            }
        }
    }

    public int getTotalProducts() {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM products";

        try ( PreparedStatement statement = connection.prepareStatement(sql);  ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void addPayHistory(PaymentHistory t) {
        String sql = "INSERT INTO `payment_history` (payment_date, payment_method, order_id, customer_id)"
                + " VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDate(1, t.getPayDate());
            st.setString(2, t.getPayMethod());
            st.setInt(3, t.getOrderId());
            st.setInt(4, t.getCustomerId());

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Method to filter products
    public List<Products> filterProducts(String category, String status, String title) {
        List<Products> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");

        if (category != null && !category.isEmpty()) {
            sql.append(" AND category_id = ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
        }
        if (title != null && !title.isEmpty()) {
            sql.append(" AND name LIKE ?");
        }

        try ( PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (category != null && !category.isEmpty()) {
                statement.setString(index++, category);
            }
            if (status != null && !status.isEmpty()) {
                statement.setString(index++, status);
            }
            if (title != null && !title.isEmpty()) {
                statement.setString(index++, "%" + title + "%");
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Products product = new Products();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setOriginalPrice(resultSet.getInt("original_price"));
                product.setSalePrice(resultSet.getInt("sale_price"));
                product.setNumberLeft(resultSet.getInt("number_left"));
                product.setImageUrl(resultSet.getString("image_url"));
                product.setStatus(resultSet.getInt("status"));
                Category c = getCategoryByID(resultSet.getInt("category_id"));
                product.setCategory(c);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Users getUserByUsernameAndPassword(String username, String password) {
        String qr = "Select * from users where username = '" + username + "' and password = '" + password + "'";
        try (
                 Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            if (rs.next()) {
                Users u = new Users(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("role")
                );
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
        return null;
    }

    public List<Users> getAllUsers() {
        List<Users> userList = new ArrayList<>();
        String query = "SELECT * FROM users";

        try ( Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String userName = rs.getString("username");
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phoneNumber");
                boolean gender = rs.getBoolean("gender");
                String avatar = rs.getString("avatar");
                String address = rs.getString("address");
                String password = rs.getString("password");
                int role = rs.getInt("role");

                Users user = new Users(userName, password, name, phoneNumber, gender, avatar, role, address);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }

        return userList;
    }

    public void changePasswordCus(String password, String email) {
        String sql = "UPDATE customers SET Password = ? WHERE email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, email);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void changePasswordEmp(String password, String email) {
        String sql = "UPDATE employees SET Password = ? WHERE email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, email);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Error fetching product by ID: " + e.getMessage());
        }
    }

    public List<Products> getProductsCurrent() {
        String sql = "SELECT * FROM online_shop.products where status = 1 and number_left > 0  ORDER BY create_date DESC LIMIT 8";
        List<Products> listP = new ArrayList<Products>();
        try ( Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Products p = new Products();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setOriginalPrice(rs.getInt("original_price"));
                p.setSalePrice(rs.getInt("sale_price"));
                p.setNumberLeft(rs.getInt("number_left"));
                p.setCreateDate(rs.getString("create_date"));
                p.setImageUrl(rs.getString("image_url"));
                p.setDescription(rs.getString("description"));
                p.setStatus(rs.getInt("status"));
                Category c = getCategoryByID(rs.getInt("category_id"));
                p.setCategory(c);
                listP.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }

        return listP;
    }

    public List<Products> getProductsFeatured() {
        String sql = "SELECT * FROM online_shop.products where status = 1 and featured = 1 and number_left > 0 LIMIT 10";
        List<Products> listP = new ArrayList<Products>();
        try ( Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Products p = new Products();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setOriginalPrice(rs.getInt("original_price"));
                p.setSalePrice(rs.getInt("sale_price"));
                p.setNumberLeft(rs.getInt("number_left"));
                p.setCreateDate(rs.getString("create_date"));
                p.setImageUrl(rs.getString("image_url"));
                p.setDescription(rs.getString("description"));
                p.setStatus(rs.getInt("status"));
                Category c = getCategoryByID(rs.getInt("category_id"));
                p.setCategory(c);
                listP.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }

        return listP;
    }

    public List<Sliders> getSlider() {
        String sql = "Select * from sliders where status = true";
        List<Sliders> listS = new ArrayList<Sliders>();
        try ( Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int sliderId = rs.getInt("slider_id");
                String imageUrl = rs.getString("image_url");
                String title = rs.getString("title");
                String backLink = rs.getString("back_link");

                Sliders s = new Sliders(sliderId, imageUrl, title, backLink);
                listS.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sliders: " + e.getMessage());
        }

        return listS;
    }

    public List<Blogs> getBlogsWithImages() {
        List<Blogs> blogs = new ArrayList<>();
        String query = "SELECT b.blog_id, b.title, b.detail, b.updateDate, bi.image_url "
                + "FROM blogs b "
                + "LEFT JOIN blog_image bi ON b.blog_id = bi.blog_id "
                + "ORDER BY RAND()DESC LIMIT 3";

        try (
                 PreparedStatement ps = con.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            // Map to track blogs and their images to avoid duplication
            Map<Integer, Blogs> blogMap = new HashMap<>();

            while (rs.next()) {
                int blogId = rs.getInt("blog_id");
                Blogs blog = blogMap.get(blogId); // Check if blog already exists

                if (blog == null) {
                    blog = new Blogs();
                    blog.setBlogId(blogId);
                    blog.setTitle(rs.getString("title"));
                    blog.setDetail(rs.getString("detail"));
                    blog.setUpdateDate(rs.getDate("updateDate"));

                    blogMap.put(blogId, blog); // Add blog to map
                    blogs.add(blog); // Add blog to the result list
                }

                // Add images to the blog
                String imageUrl = rs.getString("image_url");
                if (imageUrl != null) {
                    BlogImage image = new BlogImage(); // Assuming BlogImage has a field for the image URL
                    image.setImageUrl(imageUrl);
                    blog.addImage(image); // Add image to the blog
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blogs;
    }

    public List<Blogs> getBlogListRecent() {
        List<Blogs> blogs = new ArrayList<>();
        String query = "SELECT b.blog_id, b.title, b.detail, b.updateDate, bi.image_url "
                + "FROM blogs b "
                + "LEFT JOIN blog_image bi ON b.blog_id = bi.blog_id "
                + "ORDER BY b.updateDate DESC LIMIT 3";  // Fetch the latest 3 blogs

        try (
                 PreparedStatement ps = con.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            // Map to track blogs and their images to avoid duplication
            Map<Integer, Blogs> blogMap = new HashMap<>();

            while (rs.next()) {
                int blogId = rs.getInt("blog_id");
                Blogs blog = blogMap.get(blogId); // Check if blog already exists

                if (blog == null) {
                    blog = new Blogs();
                    blog.setBlogId(blogId);
                    blog.setTitle(rs.getString("title"));
                    blog.setDetail(rs.getString("detail"));
                    blog.setUpdateDate(rs.getDate("updateDate"));

                    blogMap.put(blogId, blog); // Add blog to map
                    blogs.add(blog); // Add blog to the result list
                }

                // Add images to the blog
                String imageUrl = rs.getString("image_url");
                if (imageUrl != null) {
                    BlogImage image = new BlogImage(); // Assuming BlogImage has a field for the image URL
                    image.setImageUrl(imageUrl);
                    blog.addImage(image); // Add image to the blog
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blogs;
    }

    public void changePassword(String password, String email) {
        String sql = "UPDATE customers SET Password = ? WHERE email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, email);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Products getProductById(int productId) {
        Products product = null; // Declare the product variable
        String sql = "SELECT * FROM products WHERE product_id = ? and status = 1";

        try ( PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            System.out.println("Executing SQL: " + ps.toString());

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Products(); // Initialize the product object
                    product.setProductId(rs.getInt("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setOriginalPrice(rs.getInt("original_price"));
                    product.setSalePrice(rs.getInt("sale_price"));
                    product.setNumberLeft(rs.getInt("number_left"));
                    product.setImageUrl(rs.getString("image_url"));
                    product.setStatus(rs.getInt("status"));
                    product.setDescription(rs.getString("description"));
                    product.setBriefInformation(rs.getString("brief_information"));
                    // Fetch the category for each product
                    Category category = getCategoryByID(rs.getInt("category_id"));
                    product.setCategory(category);
                } else {
                    System.out.println("No product found with ID: " + productId); // Debugging: check if product is found
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product by ID: " + e.getMessage());
        }

        return product; // Return the populated product object or null
    }

    public Products getProductCreateDateById(int productId) {
        Products product = null; // Declare the product variable
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try ( PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            System.out.println("Executing SQL: " + ps.toString());

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Products(); // Initialize the product object
                    product.setProductId(rs.getInt("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setOriginalPrice(rs.getInt("original_price"));
                    product.setSalePrice(rs.getInt("sale_price"));
                    product.setNumberLeft(rs.getInt("number_left"));
                    product.setCreateDate(rs.getString("create_date"));
                    product.setImageUrl(rs.getString("image_url"));
                    product.setStatus(rs.getInt("status"));
                    product.setDescription(rs.getString("description"));
                    product.setBriefInformation(rs.getString("brief_information"));
                    // Fetch the category for each product
                    Category category = getCategoryByID(rs.getInt("category_id"));
                    product.setCategory(category);
                } else {
                    System.out.println("No product found with ID: " + productId); // Debugging: check if product is found
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product by ID: " + e.getMessage());
        }

        return product; // Return the populated product object or null
    }

    public Products getProductByIdUpdate(int productId) {
        Products product = null; // Declare the product variable
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try ( PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            System.out.println("Executing SQL: " + ps.toString());

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Products(); // Initialize the product object
                    product.setProductId(rs.getInt("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setOriginalPrice(rs.getInt("original_price"));
                    product.setSalePrice(rs.getInt("sale_price"));
                    product.setNumberLeft(rs.getInt("number_left"));
                    product.setImageUrl(rs.getString("image_url"));
                    product.setStatus(rs.getInt("status")); // Set the status from the database
                    product.setDescription(rs.getString("description"));
                    product.setBriefInformation(rs.getString("brief_information"));
                    product.setFeatured(rs.getInt("featured"));

                    // Log the fetched status for debugging
                    System.out.println("Fetched Status: " + product.getStatus());

                    // Fetch the category for each product
                    Category category = getCategoryByID(rs.getInt("category_id"));
                    product.setCategory(category);
                } else {
                    System.out.println("No product found with ID: " + productId); // Debugging: check if product is found
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product by ID: " + e.getMessage());
        }

        return product; // Return the populated product object or null
    }

    public List<ProductColor> getProductColors(int productId) {
        String sql = "SELECT product_color_name FROM product_color WHERE product_id = ?";

        List<ProductColor> colors = new ArrayList<>();

        try ( PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String colorName = rs.getString("product_color_name");
                    ProductColor pc = new ProductColor(productId, colorName);
                    colors.add(pc);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product by ID: " + e.getMessage());
        }

        return colors;
    }

    public List<ProductColor> getColorByProductIdForUpdate(int productId) {
        String sql = "SELECT * FROM product_color WHERE product_id = ?";
        List<ProductColor> colorList = new ArrayList<>();

        try ( PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String colorName = rs.getString("product_color_name");
                    ProductColor pc = new ProductColor(productId, colorName);
                    colorList.add(pc);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching colors for product ID " + productId + ": " + e.getMessage());
        }

        return colorList;
    }

    public Category getTypebyID(int productId) {
        List<Category> category = new ArrayList<>();

        String query = "SELECT c.name\n"
                + "FROM products p\n"
                + "JOIN category c ON p.category_id = c.category_id\n"
                + "WHERE p.product_id = ?;";

        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Category c = new Category(
                        rs.getString("name")
                );
                category.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return (Category) category;
    }

    public List<ProductSize> getProductbySize(int productid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProductSize> size = new ArrayList();

        String sql = "SELECT product_size_id,product_size_name, product_id, (quantity - hold) as Avaliable , product_color, prices from product_size where product_id = ? and prices != 0 order by product_size_name asc";

        try {

            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            {
                ps.setInt(1, productid);
                rs = ps.executeQuery();
            }

            while (rs.next()) {
                size.add(new ProductSize(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getInt(6)
                ));
            }
        } catch (SQLException e) {
        }

        return size;
    }
//    public static void main(String[] args) {
//        DAO dao = new DAO();
//        int id = 41;
//        List<ProductSize> size = dao.getProductbySize(id);
//        for(ProductSize sizes : size){
//            System.out.println(sizes.getProductColor());
//            
//            
//        }
//        
//    }

    public List<ProductSize> getProductSizes(int productId) {
        String sql = "SELECT product_size_name FROM product_size WHERE product_id = ?";

        List<ProductSize> sizes = new ArrayList<>();

        try ( PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String sizeName = rs.getString("product_size_name");
                    ProductSize pds = new ProductSize(productId, sizeName);
                    sizes.add(pds);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product sizes: " + e.getMessage());
        }

        return sizes;
    }

    public List<ProductSize> getAllProductSizes(int productID) {
        List<ProductSize> productSizes = new ArrayList<>();
        String sql = "SELECT * FROM online_shop.product_size WHERE product_id = ?";

        try ( Connection conn = getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productID); // Set the productID parameter
            try ( ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("product_size_id");
                    String size = rs.getString("product_size_name");
                    int quantity = rs.getInt("quantity");
                    String color = rs.getString("product_color");
                    productSizes.add(new ProductSize(id, size, productID, quantity, color));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productSizes;
    }

    public List<ProductSize> getProductSizesById(int productId) {
        String sql = "SELECT * FROM product_size WHERE product_id = ?";
        List<ProductSize> sizes = new ArrayList<>();

        try ( PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String sizeName = rs.getString("product_size_name");
                    ProductSize pds = new ProductSize(productId, sizeName);
                    sizes.add(pds);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product sizes: " + e.getMessage());
        }

        return sizes;
    }

    public List<Products> getProducts() {
        String sql = "SELECT * FROM products";
        List<Products> listP = new ArrayList<Products>();
        try ( Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Products p = new Products();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setOriginalPrice(rs.getInt("original_price"));
                p.setSalePrice(rs.getInt("sale_price"));
                p.setNumberLeft(rs.getInt("number_left"));
                p.setCreateDate(rs.getString("create_date"));
                p.setImageUrl(rs.getString("image_url"));
                p.setDescription(rs.getString("description"));
                p.setStatus(rs.getInt("status"));
                Category c = getCategoryByID(rs.getInt("category_id"));
                p.setCategory(c);
                listP.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }

        return listP;
    }

    public List<Products> getProductsByPage(int pageIndex, int pageSize) {
        List<Products> productList = new ArrayList<>();
        String query = "SELECT * FROM products ORDER BY product_id LIMIT ? OFFSET ?";

        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, pageSize); // Set limit
            ps.setInt(2, pageIndex * pageSize); // Set offset

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products product = new Products();
                    product.setProductId(rs.getInt("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setOriginalPrice(rs.getInt("original_price"));
                    product.setSalePrice(rs.getInt("sale_price"));
                    product.setNumberLeft(rs.getInt("number_left"));
                    product.setCreateDate(rs.getString("create_date"));
                    product.setImageUrl(rs.getString("image_url"));
                    product.setDescription(rs.getString("description"));
                    product.setStatus(rs.getInt("status"));

                    // Fetch the category for each product
                    Category category = getCategoryByID(rs.getInt("category_id"));
                    product.setCategory(category);

                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
  public List<Products> getFilteredProductsWarehouser(int pageIndex, int pageSize, String search, Integer categoryId, Integer status, String sortBy, String sortDirection) {
        List<Products> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.product_id, p.product_name, p.original_price, "
                + "p.sale_price, p.number_left, p.image_url, p.status, p.brief_information, p.featured, "
                + "p.category_id, SUM(ps.hold) AS total_hold,  p.create_date "
                + "FROM products p "
                + "LEFT JOIN product_size ps ON p.product_id = ps.product_id "
                + "WHERE 1=1");

        // Add conditions based on the parameters
        if (categoryId != null) {
            sql.append(" AND p.category_id = ?");
        }
        if (status != null) {
            sql.append(" AND p.status = ?");
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (p.product_name LIKE ?)");
        }

        // GROUP BY clause with non-aggregated columns specified
        sql.append(" GROUP BY p.product_id, p.create_date ");

        // Sorting logic
        if (sortBy != null && !sortBy.isEmpty()) {
            sql.append(" ORDER BY ").append(sortBy).append(" ");
            sql.append("ASC".equalsIgnoreCase(sortDirection) ? "ASC" : "DESC");
        } else {
            sql.append(" ORDER BY p.create_date DESC"); // Default sorting
        }

        // Add LIMIT clause after ORDER BY
        sql.append(" LIMIT ? OFFSET ?");

        try ( PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (categoryId != null) {
                statement.setInt(index++, categoryId);
            }
            if (status != null) {
                statement.setInt(index++, status);
            }
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search + "%";
                statement.setString(index++, searchPattern); // for product_name
            }

            statement.setInt(index++, pageSize);
            statement.setInt(index, (pageIndex - 1) * pageSize); // Adjusted to pageIndex

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Products product = new Products();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setOriginalPrice(rs.getInt("original_price"));
                product.setSalePrice(rs.getInt("sale_price"));
                product.setNumberLeft(rs.getInt("number_left"));
                product.setImageUrl(rs.getString("image_url"));
                product.setStatus(rs.getInt("status"));
                product.setBriefInformation(rs.getString("brief_information"));
                product.setFeatured(rs.getInt("featured"));
                product.setHold(rs.getInt("total_hold")); // Set total_hold

                // Fetch the category for each product
                Category category = getCategoryByID(rs.getInt("category_id"));
                product.setCategory(category);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Products> getFilteredProducts(int pageIndex, int pageSize, String search, Integer categoryId, Integer status, String sortBy, String sortDirection) {
        List<Products> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.product_id, p.product_name, p.original_price, "
                + "p.sale_price, p.number_left, p.image_url, p.status, p.brief_information, p.featured, "
                + "p.category_id, SUM(ps.hold) AS total_hold,  p.create_date "
                + "FROM products p "
                + "LEFT JOIN product_size ps ON p.product_id = ps.product_id "
                + "WHERE 1=1");

        // Add conditions based on the parameters
        if (categoryId != null) {
            sql.append(" AND p.category_id = ?");
        }
        if (status != null) {
            sql.append(" AND p.status = ?");
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (p.product_name LIKE ? OR p.brief_information LIKE ? OR.p.product_id)");
        }

        // GROUP BY clause with non-aggregated columns specified
        sql.append(" GROUP BY p.product_id,p.create_date, p.product_name, p.original_price, p.sale_price, "
                + "p.number_left, p.image_url, p.status, p.brief_information, p.featured, p.category_id ");

        // Sorting logic
        if (sortBy != null && !sortBy.isEmpty()) {
            sql.append(" ORDER BY ").append(sortBy).append(" ");
            sql.append("ASC".equalsIgnoreCase(sortDirection) ? "ASC" : "DESC");
        } else {
            sql.append(" ORDER BY p.create_date DESC"); // Default sorting
        }

        // Add LIMIT clause after ORDER BY
        sql.append(" LIMIT ? OFFSET ?");

        try ( PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (categoryId != null) {
                statement.setInt(index++, categoryId);
            }
            if (status != null) {
                statement.setInt(index++, status);
            }
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search + "%";
                statement.setString(index++, searchPattern); // for product_name
                statement.setString(index++, searchPattern); // for brief_information
                statement.setString(index++, searchPattern); // for brief_information
            }

            statement.setInt(index++, pageSize);
            statement.setInt(index, (pageIndex - 1) * pageSize); // Adjusted to pageIndex

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Products product = new Products();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setOriginalPrice(rs.getInt("original_price"));
                product.setSalePrice(rs.getInt("sale_price"));
                product.setNumberLeft(rs.getInt("number_left"));
                product.setImageUrl(rs.getString("image_url"));
                product.setStatus(rs.getInt("status"));
                product.setBriefInformation(rs.getString("brief_information"));
                product.setFeatured(rs.getInt("featured"));
                product.setHold(rs.getInt("total_hold")); // Set total_hold

                // Fetch the category for each product
                Category category = getCategoryByID(rs.getInt("category_id"));
                product.setCategory(category);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean updateProductStatus(int productId, int status) {
        String updateSQL = "UPDATE products SET status = ? WHERE product_id = ?";

        try ( Connection conn = getConnection();  PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setInt(1, status);
            pstmt.setInt(2, productId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly in production
            return false;
        }
    }

    public int getTotalProducts(String search, Integer categoryId, Integer status) {
        int total = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM products WHERE 1=1");

        if (categoryId != null) {
            sql.append(" AND category_id = ?");
        }
        if (status != null) {
            sql.append(" AND status = ?");
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND product_name LIKE ?");
        }

        try ( PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (categoryId != null) {
                statement.setInt(index++, categoryId);
            }
            if (status != null) {
                statement.setInt(index++, status);
            }
            if (search != null && !search.isEmpty()) {
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

    public Map<LocalDate, Integer> getCustomerTrends(String endDate) {
        Map<LocalDate, Integer> customerTrends = new LinkedHashMap<>();

        // Parse endDate and calculate the start date (7 days back)
        LocalDate end = LocalDate.parse(endDate);
        LocalDate start = end.minusDays(6); // Get the date 6 days before the end date

        // Fill the map with dates and initial counts of zero for the 7 days
        for (int i = 0; i < 7; i++) {
            customerTrends.put(start.plusDays(i), 0); // Adding each date from start to end
        }

        String queryCustomers = "SELECT DATE(last_update) AS last_update_date, COUNT(*) AS customer_count "
                + "FROM customers WHERE last_update BETWEEN ? AND ? "
                + "GROUP BY DATE(last_update) ORDER BY DATE(last_update)";

        try ( PreparedStatement statement = connection.prepareStatement(queryCustomers)) {
            statement.setString(1, start.toString());
            statement.setString(2, end.toString());
            try ( ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    LocalDate lastUpdateDate = rs.getDate("last_update_date").toLocalDate();
                    int customerCount = rs.getInt("customer_count");

                    // Update the customer count for the corresponding date
                    if (customerTrends.containsKey(lastUpdateDate)) {
                        customerTrends.put(lastUpdateDate, customerCount);

                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return customerTrends;
    }

    public Map<String, Integer> getProductSales(LocalDate startDate, LocalDate endDate) {
        Map<String, Integer> productSales = new HashMap<>();

        // Updated SQL query to use o.order_date instead of od.order_date
        String query = "SELECT p.product_name, SUM(od.quantity) AS total_sold "
                + "FROM order_detail od "
                + "JOIN orders o ON od.order_id = o.order_id "
                + "JOIN products p ON od.product_id = p.product_id "
                + "WHERE o.order_date >= ? AND o.order_date <= ? " // Use o.order_date here
                + "GROUP BY p.product_name";

        try ( Connection connection = this.connection;  PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));

            try ( ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String productName = rs.getString("product_name");
                    int totalSold = rs.getInt("total_sold");
                    productSales.put(productName, totalSold);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }

        return productSales; // Return the map of product sales
    }

    public int getRoleByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Employees where Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) != 0) {
                    return 1;
                }
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
        sql = "SELECT COUNT(*) FROM Customers where Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) != 0) {
                    return 2;
                }
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
        return -1;
    }

    public boolean UpdatePasswordCustomer(String password, String email) {
        String sql = "UPDATE Customers SET Password = ? WHERE Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, email);
            int rowsUpdated = st.executeUpdate();
            st.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean UpdatePasswordEmployee(String password, String email) {
        String sql = "UPDATE Employees SET Password = ? WHERE Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, email);
            int rowsUpdated = st.executeUpdate();
            st.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean checkEmailExist(String email) {
        String sql = "SELECT COUNT(*) FROM Employees where Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) != 0) {
                    return true;
                }
            }
            st.close();
        } catch (SQLException e) {
            return false;
        }
        sql = "SELECT COUNT(*) FROM Customers where Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) != 0) {
                    return true;
                }
            }
            st.close();
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public int getInsertIdNewest() {
        int id = 0;
        String sql = "SELECT MAX(product_id) FROM products;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            // Loop until select the last object
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public int getPostCount(String startDate, String endDate) {
        String queryPosts = "SELECT COUNT(*) AS post_count FROM blogs WHERE updateDate BETWEEN ? AND ?";
        try ( PreparedStatement statement = connection.prepareStatement(queryPosts)) {
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            try ( ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("post_count");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return 0; // Return 0 if no posts are found
    }

    public int getProductCount(String startDate, String endDate) {
        String queryProducts = "SELECT COUNT(*) AS product_count FROM products WHERE create_date BETWEEN ? AND ?";
        try ( PreparedStatement statement = connection.prepareStatement(queryProducts)) {
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            try ( ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("product_count");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return 0; // Return 0 if no products are found
    }

    public int getCustomerCount(String startDate, String endDate) {
        String queryProducts = "SELECT COUNT(*) AS customer_count FROM customers WHERE last_update BETWEEN ? AND ?";
        try ( PreparedStatement statement = connection.prepareStatement(queryProducts)) {
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            try ( ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("customer_count");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getFeedbackCount(String startDate, String endDate) {
        String queryProducts = "SELECT COUNT(*) AS feedbacks_count FROM feedbacks WHERE create_date BETWEEN ? AND ?";
        try ( PreparedStatement statement = connection.prepareStatement(queryProducts)) {
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            try ( ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("feedbacks_count");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public boolean feedbackExists(int customerId, int orderId, int productId) {
        String sql = "SELECT COUNT(*) FROM feedbacks WHERE customer_id = ? AND order_id = ? AND product_id = ?";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, orderId);
            ps.setInt(3, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if feedback exists
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return false; // Default to false if there is an error
    }

    public void updateFeedback(FeedBacks feedback) {
        String sql = "UPDATE feedbacks SET rated_star = ?, content = ?, images = ?, size_id = ?, status = ? "
                + "WHERE order_id = ? AND product_id = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set the parameters in the prepared statement
            ps.setInt(1, feedback.getRated_star());
            ps.setString(2, feedback.getContent());

            // Convert images to a Base64 string
            String images = String.join(",", feedback.getImageFileNames());
            ps.setString(3, images);

            // Handle size_id which can be null
            if (feedback.getSize_id() != null && feedback.getSize_id() != 0) {
                ps.setInt(4, feedback.getSize_id());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.setInt(5, feedback.getStatus());
            ps.setInt(6, feedback.getOrderId());
            ps.setInt(7, feedback.getProduct_id());

            // Execute the update operation
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Feedback updated successfully.");
            } else {
                System.out.println("No feedback found for the given identifiers.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void insertFeedback(FeedBacks feedback) {
        String sql = "INSERT INTO feedbacks (product_id, customer_id, order_id, rated_star, status, content, create_date, images, size_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, feedback.getProduct_id());
            ps.setInt(2, feedback.getCustomer_id());
            ps.setInt(3, feedback.getOrderId());
            ps.setInt(4, feedback.getRated_star());
            ps.setInt(5, feedback.getStatus());
            ps.setString(6, feedback.getContent());
            ps.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));

            // Convert images to a Base64 string
            String images = String.join(",", feedback.getImageFileNames());
            ps.setString(8, images);

            // Handle size_id which can be null
            if (feedback.getSize_id() != null && feedback.getSize_id() != 0) {
                ps.setInt(9, feedback.getSize_id());
            } else {
                ps.setNull(9, java.sql.Types.INTEGER);
            }

            // Execute the insert operation
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void updatePriceInProductSize(ProductSize productSize) {
        String query = "UPDATE product_size SET prices = ? WHERE product_size_id = ?"; // Adjust query according to your schema
        try ( PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, productSize.getPrices());
            stmt.setInt(2, productSize.getProductSizeId()); // Assuming you have a unique ID for each size
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get product size by details
    public ProductSize getProductSizeByDetails(ProductSize productSize) {
        String query = "SELECT * FROM product_size WHERE product_size_name = ? AND product_color = ? AND product_id = ?";

        try ( PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, productSize.getProductSizeName());
            stmt.setString(2, productSize.getProductColor());
            stmt.setInt(3, productSize.getProductId());
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ProductSize size = new ProductSize();
                    size.setProductSizeId(rs.getInt("product_size_id"));
                    size.setProductSizeName(rs.getString("product_size_name"));
                    size.setQuantity(rs.getInt("quantity"));
                    size.setProductColor(rs.getString("product_color"));
                    size.setImportPrice(rs.getInt("import_price"));
                    size.setPrices(rs.getInt("prices"));
                    size.setHold(rs.getInt("hold"));
                    size.setProductId(rs.getInt("product_id"));
                    return size;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if not found
    }

    public int getLowestPriceByProductId(int productId) {
        String sql = "SELECT MIN(prices) FROM product_size WHERE product_id = ?";
        int lowestPrice = 0;

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                lowestPrice = rs.getInt(1); // Get the minimum price
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lowestPrice; // Return null if no prices found
    }

    public void updateProductQuantity(int productId, int totalQuantity) throws SQLException {
        String sql = "UPDATE `online_shop`.`products` SET `number_left` = ? WHERE `product_id` = ?";
        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, totalQuantity);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    // Update an existing product size
    public void updateProductSize(ProductSize productSize) {
        String query = "UPDATE product_size SET quantity = ?, import_price = ?, prices = ? WHERE product_size_id = ?";

        try ( PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, productSize.getQuantity());
            stmt.setInt(2, productSize.getImportPrice());
            stmt.setInt(3, productSize.getPrices());
            stmt.setInt(4, productSize.getProductSizeId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateProductSizeBool(ProductSize productSize) {
        String query = "UPDATE product_size SET quantity = ?, import_price = ?, prices = ? WHERE product_size_id = ?";
        boolean updated = false; // Track if the update was successful

        try ( PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, productSize.getQuantity());
            stmt.setInt(2, productSize.getImportPrice());
            stmt.setInt(3, productSize.getPrices());
            stmt.setInt(4, productSize.getProductSizeId());

            updated = stmt.executeUpdate() > 0; // Check if at least one row was updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated; // Return true if update was successful, false otherwise
    }

    public boolean updatePrices(int productId, int originalPrice, int salePrice) {
        String sql = "UPDATE online_shop.products SET original_price = ?, sale_price = ? WHERE product_id = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, originalPrice);
            stmt.setInt(2, salePrice);
            stmt.setInt(3, productId);

            return stmt.executeUpdate() > 0; // Returns true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Update failed
        }
    }

    public static void main(String[] args) {
        DAO dao = new DAO();
        List<FeedBacks> feedbacks = dao.getAllFeedbackByProductId(45);

        // Test data
        int productId = 47; // Replace with a valid product ID for testing
        Products product = dao.getProductByIdUpdate(productId);

        // Check if the product was found and display its details
        if (product != null) {
            System.out.println("Product found:");
            System.out.println("ID: " + product.getProductId());
            System.out.println("Name: " + product.getProductName());
            System.out.println("Original Price: " + product.getOriginalPrice());
            System.out.println("Sale Price: " + product.getSalePrice());
            System.out.println("Number Left: " + product.getNumberLeft());
            System.out.println("Image URL: " + product.getImageUrl());
            System.out.println("Status: " + product.getStatus());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Brief Information: " + product.getBriefInformation());
            System.out.println("Featured: " + product.getFeatured());
        } else {
            System.out.println("No product found with ID: " + productId);
        }
    }
}
