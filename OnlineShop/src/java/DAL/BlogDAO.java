/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Blog;
import Models.Blogs;
import Models.Sliders;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T
 */
public class BlogDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Blog> getAllBlog() {
        List<Blog> list = new ArrayList<>();

        String query = "SELECT b.blog_id ,"
                + "b.title, \n"
                + "       SUBSTRING(b.detail, 1, 100) AS detail, \n"
                + "       b.updateDate, \n"
                + "       bi.image_url\n"
                + "FROM blogs b\n"
                + "JOIN blog_image bi ON b.blog_id = bi.blog_id;";
        try {

            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Blog(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5)));
            }
        } catch (SQLException e) {
        }

        return list;
    }

//    public static void main(String[] args) {
//        BlogDAO dao = new BlogDAO();
//        List<Blog> list = dao.getAllBlog();
//
//        for (Blog b : list) {
//            System.out.println(b);
//        }
//    }
    public Blog getBlogbySearch(String title){
        Blog blog = null;
        String query = "SELECT \n"
                + "    b.blog_id, \n"
                + "    b.title, \n"
                + "    b.detail, \n"
                + "    b.updateDate, \n"
                + "    i.image_url, \n"
                + "    \n"
                + "    c.name AS category_name \n"
                + "FROM \n"
                + "    blogs b \n"
                + "LEFT JOIN \n"
                + "    blog_image i ON b.blog_id = i.blog_id \n"
                + "LEFT JOIN \n"
                + "    category c ON b.category_id = c.category_id \n"
                + "WHERE \n"
                + "    b.title LIKE ?";
        
        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setString(1, title);
                rs = ps.executeQuery();

                while (rs.next()) {
                    blog = new Blog(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDate(4),
                            rs.getString(5),
                            rs.getString(6));
                }
            }

        } catch (Exception e) {
        }
        return blog;
        
        
    }
    public Blog getBlogbyID(String blogID) {
        Blog blog = null;
        String query = "SELECT \n"
                + "    b.blog_id, \n"
                + "    b.title, \n"
                + "    b.detail, \n"
                + "    b.updateDate, \n"
                + "    i.image_url, \n"
                + "    \n"
                + "    c.name AS category_name \n"
                + "FROM \n"
                + "    blogs b \n"
                + "LEFT JOIN \n"
                + "    blog_image i ON b.blog_id = i.blog_id \n"
                + "LEFT JOIN \n"
                + "    category c ON b.category_id = c.category_id \n"
                + "WHERE \n"
                + "    b.blog_id = ?";
        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setString(1, blogID);
                rs = ps.executeQuery();

                while (rs.next()) {
                    blog = new Blog(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDate(4),
                            rs.getString(5),
                            rs.getString(6));
                }
            }

        } catch (Exception e) {
        }
        return blog;
    }

    public static void main(String[] args) {
        BlogDAO dao = new BlogDAO();
        List<Blog> blogs = dao.getAllBlog();

        // Print the retrieved blogs
        for (Blog blog : blogs) {
            System.out.println("Blog ID: " + blog.getBlogID());
            System.out.println("Title: " + blog.getTitle());
            System.out.println("Detail: " + blog.getDetail());
            System.out.println("Update Date: " + blog.getUpdateDate());
            System.out.println("Image URL: " + blog.getImage());
            System.out.println("-----------------------------------");
        }
    }
    
    public int getTotalBlog() {
        int totalBlog = 0;
        String query = "SELECT COUNT(*) AS total FROM blogs where status = 1";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalBlog = rs.getInt("total");
            }

        } catch (Exception e) {
        }

        return totalBlog;
    }
    
      public int getTotalPost() {
        int totalBlog = 0;
        String query = "SELECT COUNT(*) AS total FROM blogs";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalBlog = rs.getInt("total");
            }

        } catch (Exception e) {
        }

        return totalBlog;
    }
    

    public List<Blog> getAllBlogss(int page, int pageSize) {
        List<Blog> list = new ArrayList<>();
        String image = null;
        String query = "SELECT b.blog_id ,\n"
                + "                b.title, \n"
                + "                      b.short_content, \n"
                + "                     b.updateDate, \n"
                + "                  bi.image_url\n"
                + "                FROM blogs b\n"
                + "                JOIN blog_image bi ON b.blog_id = bi.blog_id where status = 1 LIMIT ? OFFSET ?";
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
                list.add(new Blog(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5)));
            }
        } catch (SQLException e) {
        }

        return list;
    }

    //    public static void main(String[] args) {
    //        BlogDAO dao = new BlogDAO();
    //        List<Blog> list = dao.getAllBlog();
    //
    //        for (Blog b : list) {
    //            System.out.println(b);
    //        }
    //    }
    //Search by title
    public List<Blog> searchBytitle(String title) {
        List<Blog> blogs = new ArrayList();

        String query = "SELECT b.blog_id ,\n"
                + "                b.title, \n"
                + "                      b.short_content, \n"
                + "                     b.updateDate, \n"
                + "                  bi.image_url\n"
                + "                FROM blogs b\n"
                + "                JOIN blog_image bi ON b.blog_id = bi.blog_id where status = 1 and  title LIKE ?";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setString(1, "%" + title + "%");
                rs = ps.executeQuery();
            }
            while (rs.next()) {
                blogs.add(new Blog(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5)));
            }
        } catch (Exception e) {
        }

        return blogs;
    }

    public List<Blog> sortBydate() {
        List<Blog> blog = new ArrayList();
        String query = "SELECT b.blog_id ,\n"
                + "                b.title, \n"
                + "                      b.short_content, \n"
                + "                     b.updateDate, \n"
                + "                  bi.image_url\n"
                + "                FROM blogs b\n"
                + "                JOIN blog_image bi ON b.blog_id = bi.blog_id where status = 1 ORDER BY updateDate DESC";

        try {

            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                blog.add(new Blog(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5)));
            }
        } catch (SQLException e) {
        }

        return blog;
    }

//    public static void main(String[] args) {
//        BlogDAO dao = new BlogDAO();
//        List<Blog> blogs = dao.getAllBlog();
//
//        // Print the retrieved blogs
//        for (Blog blog : blogs) {
//            System.out.println("Blog ID: " + blog.getBlogID());
//            System.out.println("Title: " + blog.getTitle());
//            System.out.println("Detail: " + blog.getDetail());
//            System.out.println("Update Date: " + blog.getUpdateDate());
//            System.out.println("Image URL: " + blog.getImage());
//            System.out.println("-----------------------------------");
//        }
//    }
    public List<Blogs> searchBlogsByTitle(String title) {
        List<Blogs> blog = new ArrayList<>();
        String query = "SELECT b.blog_id, b.title, b.updateDate, b.detail, i.image_url, b.short_content, b.status, c.name as categoryName \n"
                + "                 FROM blogs b \n"
                + "                 LEFT JOIN blog_image i ON b.blog_id = i.blog_id \n"
                + "                 LEFT JOIN category c ON b.category_id = c.category_id\n"
                + "                 WHERE b.title LIKE ?";
        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setString(1, "%" + title + "%");
                rs = ps.executeQuery();

                while (rs.next()) {
                    Blogs blogs = new Blogs(
                            rs.getInt("blog_id"),
                            rs.getString("title"),
                            rs.getDate("updateDate"),
                            rs.getString("detail"),
                            rs.getString("short_content"),
                            rs.getInt("status"),
                            rs.getString("image_url"),
                            rs.getString("categoryName")
                    );
                    blog.add(blogs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Should log the exception in real-world use case
        }
        return blog;
    }
//    public static void main(String[] args) {
//        String searchTitle = "Today"; // Thay thế với tiêu đề bạn muốn tìm kiếm
//        
//        // Tạo một đối tượng của lớp DAO
//        BlogDAO blogDAO = new BlogDAO();
//        
//        // Gọi phương thức tìm kiếm
//        List<Blogs> foundBlogs = blogDAO.searchBlogsByTitle(searchTitle);
//        
//        // Kiểm tra và in ra kết quả tìm kiếm
//        if (foundBlogs.isEmpty()) {
//            System.out.println("No blogs found with the title: " + searchTitle);
//        } else {
//            System.out.println("Blogs found:");
//            for (Blogs blogs : foundBlogs) {
//                System.out.println("ID: " + blogs.getBlogId()+ ", Title: " + blogs.getTitle() + ", Detail: " + blogs.getDetail() + ", Update Date: " + blogs.getUpdateDate() + ", Image URL: " + blogs.getImage()+ ", Category: " + blogs.getCategoryName());
//            }
//        }
//    }
    
    public List<Blogs> getAllBlogsSort(String ordersort, String orderby, int page, int pageSize) {
        List<Blogs> blog = new ArrayList<>();
        String query = "SELECT b.blog_id, b.title, SUBSTRING(b.detail, 1, 100) AS detail, b.updateDate, i.image_url, b.short_content, c.name AS categoryName, b.status \n"
                + "                     FROM blogs b \n"
                + "                     LEFT JOIN blog_image i ON b.blog_id = i.blog_id \n"
                + "                     LEFT JOIN category c ON b.category_id = c.category_id order by " + orderby + " " + ordersort + " LIMIT ? OFFSET ?  ";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
             ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);
            rs = ps.executeQuery();

            while (rs.next()) {
                Blogs blogs = new Blogs(
                        rs.getInt("blog_id"),
                        rs.getString("title"),
                        rs.getDate("updateDate"),
                        rs.getString("detail"),
                        rs.getString("short_content"),
                        rs.getInt("status"),
                        rs.getString("image_url"),
                        rs.getString("categoryName")
                );
                blog.add(blogs);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi thực tế
        }
        return blog;
    }
    
    
    
    
    
    public List<Blogs> getAllBlogs(int page, int pageSize) {
        List<Blogs> blog = new ArrayList<>();
        String query = "SELECT b.blog_id, b.title, SUBSTRING(b.detail, 1, 100) AS detail, b.updateDate, i.image_url, b.short_content, c.name AS categoryName, b.status \n"
                + "                     FROM blogs b \n"
                + "                     LEFT JOIN blog_image i ON b.blog_id = i.blog_id \n"
                + "                     LEFT JOIN category c ON b.category_id = c.category_id  LIMIT ? OFFSET ?  ";

        try {
            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
             ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);
            rs = ps.executeQuery();

            while (rs.next()) {
                Blogs blogs = new Blogs(
                        rs.getInt("blog_id"),
                        rs.getString("title"),
                        rs.getDate("updateDate"),
                        rs.getString("detail"),
                        rs.getString("short_content"),
                        rs.getInt("status"),
                        rs.getString("image_url"),
                        rs.getString("categoryName")
                );
                blog.add(blogs);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi thực tế
        }
        return blog;
    }
    
//
//    public static void main(String[] args) {
//        BlogDAO dao = new BlogDAO();
//        List<Blogs> list = dao.getAllBlogs();
//
//        for (Blogs b : list) {
//            System.out.println(b);
//        }
//    }

    public List<Blogs> getBlogsbyCategory(String categoryID) {
        List<Blogs> list = new ArrayList<>();
        String query = "SELECT b.blog_id, b.title, b.detail, b.updateDate, i.image_url, b.short_content, c.name AS categoryName, b.status \n"
                + "                     FROM blogs b \n"
                + "                     LEFT JOIN blog_image i ON b.blog_id = i.blog_id \n"
                + "                     LEFT JOIN category c ON b.category_id = c.category_id   where c.category_id = ? and b.status = 1";
        try {

            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setString(1, categoryID);
            };

            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Blogs(rs.getInt("blog_id"),
                        rs.getString("title"),
                        rs.getDate("updateDate"),
                        rs.getString("detail"),
                        rs.getString("short_content"),
                        rs.getInt("status"),
                        rs.getString("image_url"),
                        rs.getString("categoryName")));
            }
        } catch (SQLException e) {
        }

        return list;
    }
        public List<Blogs> getBlogsbyCategoryMar(String categoryID) {
        List<Blogs> list = new ArrayList<>();
        String query = "SELECT b.blog_id, b.title, b.detail, b.updateDate, i.image_url, b.short_content, c.name AS categoryName, b.status \n"
                + "                     FROM blogs b \n"
                + "                     LEFT JOIN blog_image i ON b.blog_id = i.blog_id \n"
                + "                     LEFT JOIN category c ON b.category_id = c.category_id   where c.category_id = ? ";
        try {

            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {
                ps.setString(1, categoryID);
            };

            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Blogs(rs.getInt("blog_id"),
                        rs.getString("title"),
                        rs.getDate("updateDate"),
                        rs.getString("detail"),
                        rs.getString("short_content"),
                        rs.getInt("status"),
                        rs.getString("image_url"),
                        rs.getString("categoryName")));
            }
        } catch (SQLException e) {
        }

        return list;
    }
    
    
//        public static void main(String[] args) {
//        BlogDAO dao = new BlogDAO();
//        List<Blogs> list = dao.getBlogsbyCategory("1");
//
//        for (Blogs b : list) {
//            System.out.println(b);
//        }
//    }

    //ADD blog
    public void saveBlogWithImageAndCategory(Blogs blog, String imageUrl, String categoryId) throws SQLException {
        String insertBlogQuery = "INSERT INTO blogs (title, detail, updateDate, short_content, status, category_id, employee_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, 2)";
        String insertImageQuery = "INSERT INTO blog_image (blog_id, image_url) VALUES (?, ?)";
        DBContext db = new DBContext();
        conn = db.getConnection();
        conn.setAutoCommit(false);
        try {
            // Chèn vào bảng blogs
            ps = conn.prepareStatement(insertBlogQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, blog.getTitle());
            ps.setString(2, blog.getDetail());
            ps.setDate(3, (Date) blog.getUpdateDate());
            ps.setString(4, blog.getShort_content());
            ps.setInt(5, blog.getStatus());
            ps.setInt(6, Integer.parseInt(categoryId)); // Sử dụng categoryId từ JSP
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int blogId = 0;
            if (generatedKeys.next()) {
                blogId = generatedKeys.getInt(1);
            }

            // Chèn vào bảng blog_image với blog_id vừa lấy
            ps = conn.prepareStatement(insertImageQuery);
            ps.setInt(1, blogId);
            ps.setString(2, "imageBlog\\" + imageUrl);
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException("Error saving blog and image: " + e.getMessage());
        }
    }

    public Blogs getPostByID(int blogId) {
        Blogs blog = null;
        String sql = "SELECT b.blog_id, b.title,  b.updateDate, b.detail, b.short_content, b.status,  i.image_url,  \n"
                + "                      c.name as category \n"
                + "                     FROM blogs b \n"
                + "                     JOIN blog_image i ON b.blog_id = i.blog_id \n"
                + "                     JOIN category c ON b.category_id = c.category_id \n"
                + "                     WHERE b.blog_id = ?";
        DBContext db = new DBContext();
        conn = db.getConnection();
        try (
                 PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                blog = new Blogs();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setTitle(rs.getString("title"));
                blog.setUpdateDate(rs.getDate("updateDate"));
                blog.setDetail(rs.getString("detail"));
                blog.setShort_content("short_content");
                blog.setStatus(rs.getInt("status"));
                blog.setImage(rs.getString("image_url"));
                blog.setCategoryName(rs.getString("category"));
            }

        } catch (SQLException ex) {

        }
        return blog;
    }
//      public static void main(String[] args) {
//        // Tạo một đối tượng của DAO
//        BlogDAO blogDAO = new BlogDAO();
//
//        // Gọi phương thức getPostByID với ID blog bạn muốn kiểm tra
//        int blogId = 5; // ID của bài blog cần kiểm tra
//        Blogs blog = blogDAO.getPostByID(blogId);
//
//        // Kiểm tra kết quả và in ra thông tin blog
//        if (blog != null) {
//            System.out.println("Blog ID: " + blog.getBlogId());
//            System.out.println("Title: " + blog.getTitle());
//            System.out.println("Update Date: " + blog.getUpdateDate());
//            System.out.println("Detail: " + blog.getDetail());
//            System.out.println("Short Content: " + blog.getShort_content());
//            System.out.println("Status: " + blog.getStatus());
//            System.out.println("Image: " + blog.getImage());
//            System.out.println("Category: " + blog.getCategoryName());
//        } else {
//            System.out.println("No blog found with ID: " + blogId);
//        }
//    }

    public boolean updateBlog(int blogID, String title, String detail, Date updateDate, int status, int categoryId, String imageFileName) {
        String sql = "UPDATE blogs SET title = ?, updateDate = ?, detail = ?, status = ?, category_id = ? WHERE blog_id = ?";
        String updateImageSql = "UPDATE blog_image SET image_url = ? WHERE blog_id = ?";
        DBContext db = new DBContext();
        Connection conn = db.getConnection();

        try ( PreparedStatement ps = conn.prepareStatement(sql);  PreparedStatement psImage = conn.prepareStatement(updateImageSql)) {

            // Cập nhật thông tin blog
            ps.setString(1, title);
            ps.setDate(2, updateDate);
            ps.setString(3, detail);
            ps.setInt(4, status);
            ps.setInt(5, categoryId);
            ps.setInt(6, blogID);
            int blogUpdated = ps.executeUpdate();

            // Cập nhật hình ảnh nếu có
            if (imageFileName != null) {
                psImage.setString(1, "imageBlog\\" + imageFileName);
                psImage.setInt(2, blogID);
                psImage.executeUpdate();
            }

            return blogUpdated > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Sliders getSliderByID(int sliderID) {
        String sql = "SELECT slider_id, image_url, title, back_link, status,note FROM sliders WHERE slider_id = ?";
        DBContext db = new DBContext();
        Connection conn = db.getConnection();

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sliderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Sliders slider = new Sliders();
                slider.setSliderId(rs.getInt("slider_id"));
                slider.setImageUrl(rs.getString("image_url"));
                slider.setTitle(rs.getString("title"));
                slider.setBackLink(rs.getString("back_link"));
                slider.setStatusNum(rs.getInt("status"));
                slider.setNote(rs.getString("note"));

                return slider;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updateSlider(int sliderID, String title, String backLink, String note, int status, String imageFileName) {
        String sql = "UPDATE sliders SET title = ?, back_link = ?, status = ?, note = ? WHERE slider_id = ?";
        String updateImageSql = "UPDATE sliders SET image_url = ? WHERE slider_id = ?";
        DBContext db = new DBContext();
        Connection conn = db.getConnection();

        try ( PreparedStatement ps = conn.prepareStatement(sql);  PreparedStatement psImage = conn.prepareStatement(updateImageSql)) {

            // Cập nhật thông tin slider
            ps.setString(1, title);
            ps.setString(2, backLink);

            ps.setInt(3, status);
            ps.setString(4, note);
            ps.setInt(5, sliderID);
            int sliderUpdated = ps.executeUpdate();

            // Cập nhật hình ảnh nếu có
            if (imageFileName != null && !imageFileName.isEmpty()) {
                psImage.setString(1, "sliderImage\\" + imageFileName);
                psImage.setInt(2, sliderID);
                psImage.executeUpdate();
            }

            return sliderUpdated > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
