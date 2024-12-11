/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Category;
import Models.FeedBacks;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author T
 */
public class FeedBackDAO extends DBContext {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

//
//        String query = "SELECT \n"
//                + "	f.feedback_id,\n"
//                + "    p.product_name ,\n"
//                + "    f.rated_star,\n"
//                + "    f.content ,\n"
//                + "    c.full_name ,\n"
//                + "    f.status \n"
//                + "FROM \n"
//                + "    feedbacks f\n"
//                + "JOIN \n"
//                + "    products p ON f.product_id = p.product_id\n"
//                + "JOIN \n"
//                + "    customers c ON f.customer_id = c.customer_id;";
//
//        try {
//
//            DBContext db = new DBContext();
//            conn = db.getConnection();
//            ps = conn.prepareStatement(query);
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                fbList.add(new FeedBacks(rs.getInt("rated_star"),
//                        rs.getInt("status"),
//                        rs.getString("content"),
//                        rs.getString("product_name"),
//                        rs.getString("full_name"),
//                        rs.getInt("feedback_id")
//                ));
//            }
//
//        } catch (SQLException e) {
//        }
//
//        return fbList;
//    }
    public FeedBacks getFeedbackByOrderIdAndProductId(int orderId, int productId) {
        FeedBacks feedback = null; // Initialize feedback to null

        // SQL query to fetch a single feedback based on orderId and productId
        String query = "SELECT \n"
                + "    f.feedback_id,\n"
                + "    p.product_name,\n"
                + "    f.rated_star,\n"
                + "    f.content,\n"
                + "    c.full_name,\n"
                + "    f.order_id,\n"
                + "    f.product_id,\n"
                + "    f.size_id,\n"
                + "    s.product_size_name,\n"
                + "    s.product_color,\n"
                + "    f.images,\n"
                + "    f.status \n"
                + "FROM \n"
                + "    feedbacks f\n"
                + "JOIN \n"
                + "    products p ON f.product_id = p.product_id\n"
                + "JOIN \n"
                + "    product_size s ON s.product_id = p.product_id \n" // Join for sizes
                + "JOIN \n"
                + "    customers c ON f.customer_id = c.customer_id\n"
                + "JOIN \n"
                + "    orders o ON f.order_id = o.order_id \n"
                + "JOIN \n"
                + "    order_detail od ON od.product_id = f.product_id \n"
                + "WHERE \n"
                + "    o.order_id = ? and f.product_id = ?"; // Filter by orderId

        // Use try-with-resources for proper resource management
        try ( Connection conn = this.getConnection(); // Get connection from DBContext
                  PreparedStatement ps = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            ps.setInt(1, orderId);
            ps.setInt(2, productId);

            // Execute the query and process the results
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Use if instead of while to get only the first feedback
                    // Fetch images and split them into a List<String>
                    String images = rs.getString("images");
                    List<String> imageFileNames = images != null ? Arrays.asList(images.split(",")) : new ArrayList<>();

                    feedback = new FeedBacks(
                            rs.getInt("feedback_id"),
                            rs.getInt("product_id"),
                            rs.getInt("rated_star"),
                            rs.getInt("status"),
                            rs.getString("content"),
                            rs.getInt("order_id"),
                            imageFileNames, // Pass the list of image file names
                            rs.getInt("size_id"),
                            rs.getString("product_size_name"),
                            rs.getString("product_color")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }

        return feedback; // Return the single feedback object or null if not found
    }

    public boolean existedFeedback(int orderId, int productId, int customerId) {
        boolean exists = false; // Initialize exists to false

        // SQL query to check existence of feedback for a given orderId and productId
        String query = "SELECT 1 FROM feedbacks WHERE order_id = ? AND product_id = ? AND customer_id = ?";

        // Use try-with-resources for proper resource management
        try ( Connection conn = this.getConnection(); // Get connection from DBContext
                  PreparedStatement ps = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, customerId);

            // Execute the query and check if a result is found
            try ( ResultSet rs = ps.executeQuery()) {
                exists = rs.next(); // If rs.next() returns true, then feedback exists
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }

        return exists; // Return true if feedback exists, false otherwise
    }

    public List<FeedBacks> getAllFeedbackByOrderIdAndProductId(int orderId, int productId) {
        List<FeedBacks> fbList = new ArrayList<>();

        // SQL query to fetch feedbacks based on orderId
        String query = "SELECT \n"
                + "    f.feedback_id,\n"
                + "    p.product_name,\n"
                + "    f.rated_star,\n"
                + "    f.content,\n"
                + "    c.full_name,\n"
                + "    s.product_size_name,\n"
                + "    s.product_color,\n"
                + "    f.images,\n"
                + "    f.status \n"
                + "FROM \n"
                + "    feedbacks f\n"
                + "JOIN \n"
                + "    products p ON f.product_id = p.product_id\n"
                + "JOIN \n"
                + "    product_size s ON s.product_id = p.product_id \n" // Join for sizes
                + "JOIN \n"
                + "    customers c ON f.customer_id = c.customer_id\n"
                + "JOIN \n"
                + "    orders o ON f.order_id = o.order_id \n"
                + "JOIN \n"
                + "    order_detail od ON od.product_id = f.product_id \n"
                + "WHERE \n"
                + "    o.order_id = ? and f.product_id = ?"; // Filter by orderId

        // Use try-with-resources for proper resource management
        try ( Connection conn = this.getConnection(); // Get connection from DBContext
                  PreparedStatement ps = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            ps.setInt(1, orderId);
            ps.setInt(2, productId);

            // Execute the query and process the results
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Fetch images and split them into a List<String>
                    String images = rs.getString("images");
                    List<String> imageFileNames = images != null ? Arrays.asList(images.split(",")) : new ArrayList<>();

                    fbList.add(new FeedBacks(
                            rs.getInt("feedback_id"),
                            rs.getInt("rated_star"),
                            rs.getString("content"),
                            rs.getString("product_name"),
                            rs.getString("full_name"),
                            rs.getString("product_size_name"), // Get product size
                            rs.getString("product_color"),
                            imageFileNames // Pass the list of image file names
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }

        return fbList; // Return the list of feedbacks
    }

    public static void main(String[] args) {
        // Create an instance of FeedBackDAO
        FeedBackDAO fbDAO = new FeedBackDAO();

        // Test cases
        int orderId1 = 89;
        int productId1 = 6;
        int customerId1 = 2;


        // Test case 1: Check if feedback exists for specific orderId, productId, and customerId
        boolean result1 = fbDAO.existedFeedback(orderId1, productId1, customerId1);
        System.out.println("Feedback exists for orderId=" + orderId1 + ", productId=" + productId1 + ", customerId=" + customerId1 + ": " + result1);
    }

    //lay count cua feedback
    public int getTotalFeedback() {
        int totalFeedback = 0;
        String query = "SELECT COUNT(*) AS total FROM feedbacks";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalFeedback = rs.getInt("total");
            }

        } catch (Exception e) {
        }

        return totalFeedback;
    }
//    public static void main(String[] args) {
//        FeedBackDAO dao = new FeedBackDAO();
//        int a = dao.getTotalFeedback();
//        System.out.println(a);
//    }

    // lay feedback phan trang 
    public List<FeedBacks> getFB(int page, int pageSize) {
        List<FeedBacks> fbList = new ArrayList<>();
        String query = "SELECT \n"
                + "	f.feedback_id,\n"
                + "    f.rated_star,\n"
                + "    f.status, \n"
                + "    f.content ,\n"
                + "    p.product_name ,\n"
                + "    c.full_name ,\n"
                + "    f.images \n"
                + "FROM \n"
                + "    feedbacks f\n"
                + "JOIN \n"
                + "    products p ON f.product_id = p.product_id\n"
                + "JOIN \n"
                + "    customers c ON f.customer_id = c.customer_id LIMIT ? OFFSET ?";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setInt(1, pageSize); // so luong fb hiee thi 
                ps.setInt(2, (page - 1) * pageSize);    //bo qua trang dau tien (offset)
                rs = ps.executeQuery();
            }
            while (rs.next()) {
                fbList.add(new FeedBacks(rs.getInt("feedback_id"),
                        rs.getInt("rated_star"),
                        rs.getInt("status"),
                        rs.getString("content"),
                        rs.getString("product_name"),
                        rs.getString("full_name"),
                        rs.getString("images")
                ));
            }

        } catch (Exception e) {
        }

        return fbList;
    }

    //sort by star
    public List<FeedBacks> sortFB(String ordersort, String orderby, int page, int pageSize) {
        List<FeedBacks> fbList = new ArrayList<>();

        String query = "SELECT \n"
                + "	f.feedback_id,\n"
                + "    p.product_name ,\n"
                + "    f.rated_star,\n"
                + "    f.content ,\n"
                + "    c.full_name ,\n"
                + "    f.status, \n"
                + "    f.images \n"
                + "FROM \n"
                + "    feedbacks f\n"
                + "JOIN \n"
                + "    products p ON f.product_id = p.product_id\n"
                + "JOIN \n"
                + "    customers c ON f.customer_id = c.customer_id order by " + orderby + " " + ordersort + " LIMIT ? OFFSET ?";

        try {

            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);
            rs = ps.executeQuery();

            while (rs.next()) {
                fbList.add(new FeedBacks(rs.getInt("rated_star"),
                        rs.getInt("status"),
                        rs.getString("content"),
                        rs.getString("product_name"),
                        rs.getString("full_name"),
                        rs.getInt("feedback_id"),
                        rs.getString("images")
                ));
            }

        } catch (SQLException e) {
        }

        return fbList;
    }

    //getBY status
    public List<FeedBacks> getFBbyStatus(int status) {
        List<FeedBacks> fbList = new ArrayList<>();

        String query = "SELECT \n"
                + "	f.feedback_id,\n"
                + "    p.product_name ,\n"
                + "    f.rated_star,\n"
                + "    f.content ,\n"
                + "    c.full_name ,\n"
                + "    f.status, \n"
                + "    f.images \n"
                + "FROM \n"
                + "    feedbacks f\n"
                + "JOIN \n"
                + "    products p ON f.product_id = p.product_id\n"
                + "JOIN \n"
                + "    customers c ON f.customer_id = c.customer_id where f.status = ?";

        try {

            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setInt(1, status);
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                fbList.add(new FeedBacks(rs.getInt("rated_star"),
                        rs.getInt("status"),
                        rs.getString("content"),
                        rs.getString("product_name"),
                        rs.getString("full_name"),
                        rs.getInt("feedback_id"),
                        rs.getString("images")
                ));
            }

        } catch (SQLException e) {
        }

        return fbList;
    }

    public List<FeedBacks> getStar() {
        List<FeedBacks> star = new ArrayList<>();

        String sql = "select feedback_id, rated_star from feedbacks    \n"
                + "ORDER BY rated_star ASC;";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                star.add(new FeedBacks(rs.getInt(1),
                        rs.getInt(2)));
            }

        } catch (SQLException e) {
        }

        return star;
    }
//   public static void main(String[] args) {
//        FeedBackDAO feedbackDAO = new FeedBackDAO();
//        List<FeedBacks> ratedStars = feedbackDAO.getStar();
//        
//        // Hiển thị kết quả
//        for (FeedBacks star : ratedStars) {
//            System.out.println("Rated Star: " + star);
//        }
//    }

    public List<FeedBacks> getFeedbackbyStar(String Star) {
        List<FeedBacks> fb = new ArrayList<>();
        String query = "SELECT f.rated_star, f.status , f.content ,  p.product_name , c.full_name , f.feedback_id,\n"
                + "    f.images \n"
                + "FROM \n"
                + "    feedbacks f \n"
                + "JOIN \n"
                + "    products p ON f.product_id = p.product_id\n"
                + "JOIN \n"
                + "    customers c ON f.customer_id = c.customer_id where rated_star = ?";

        try {

            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setString(1, Star);
            };

            rs = ps.executeQuery();

            while (rs.next()) {
                fb.add(new FeedBacks(rs.getInt("rated_star"),
                        rs.getInt("status"),
                        rs.getString("content"),
                        rs.getString("product_name"),
                        rs.getString("full_name"),
                        rs.getInt("feedback_id"),
                        rs.getString("images"))
                );
            }

        } catch (SQLException e) {
        }

        return fb;
    }

//    public static void main(String[] args) {
//        // Tạo đối tượng FeedbackDAO
//        FeedBackDAO feedbackDAO = new FeedBackDAO();
//
//        // Gọi phương thức getFeedbackbyStar với giá trị ngôi sao muốn kiểm tra
//        int starToCheck = 5; // Thay đổi giá trị ngôi sao theo nhu cầu
//        List<FeedBacks> feedbacks = feedbackDAO.getFeedbackbyStar(starToCheck);
//
//        // Kiểm tra và hiển thị kết quả
//        if (feedbacks != null && !feedbacks.isEmpty()) {
//            System.out.println("Danh sách phản hồi với rated star " + starToCheck + ":");
//            for (FeedBacks fb : feedbacks) {
//                System.out.println(fb);
//            }
//        } else {
//            System.out.println("Không tìm thấy phản hồi với rated star " + starToCheck);
//        }
//    }
    public List<FeedBacks> getFBbysearch(String search) {
        List<FeedBacks> fb = new ArrayList<>();

        String query = "SELECT \n"
                + "	f.feedback_id,\n"
                + "    p.product_name ,\n"
                + "    f.rated_star,\n"
                + "    f.content ,\n"
                + "    c.full_name ,\n"
                + "    f.status, \n"
                + "    f.images \n"
                + "FROM \n"
                + "    feedbacks f\n"
                + "JOIN \n"
                + "    products p ON f.product_id = p.product_id\n"
                + "JOIN \n"
                + "    customers c ON f.customer_id = c.customer_id where product_name LIKE ?";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setString(1, "%" + search + "%");
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                fb.add(new FeedBacks(rs.getInt("rated_star"),
                        rs.getInt("status"),
                        rs.getString("content"),
                        rs.getString("product_name"),
                        rs.getString("full_name"),
                        rs.getInt("feedback_id"),
                        rs.getString("images")
                ));
            }
        } catch (Exception e) {
        }

        return fb;

    }

    // feedback detail 
    public FeedBacks getFBbyID(int id) {
        FeedBacks feedback = null;

        String query = "SELECT \n"
                + "	f.feedback_id, f.rated_star, f.status, f.content,  p.product_name, c.full_name,  c.email, c.phone_number, f.images\n"
                + "\n"
                + "FROM \n"
                + "    customers c\n"
                + "JOIN \n"
                + "    feedbacks f ON c.customer_id = f.customer_id\n"
                + "JOIN \n"
                + "    products p ON f.product_id = p.product_id WHERE feedback_id = ?";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setInt(1, id);
                rs = ps.executeQuery();
            }
            while (rs.next()) {
                feedback = new FeedBacks(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9));
            }

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra log
        }

        return feedback;
    }

    public boolean updateFeedbackStatus(int feedbackId, int status) {
        String query = "UPDATE feedbacks SET status = ? WHERE feedback_id = ?";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, status);
            ps.setInt(2, feedbackId);
            int update = ps.executeUpdate();
            return update > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại lỗi
        }
        return false;
    }

}
