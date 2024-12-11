/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Blog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T
 */
public class Filter {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Blog> getBlogbyCategory(String categoryID) {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT b.blog_id , b.title, b.short_content, b.updateDate, i.image_url\n"
                + "FROM blogs b\n"
                + "LEFT JOIN blog_image i ON b.blog_id = i.blog_id\n"
                + "WHERE b.category_id = ? and status = 1;";
        try {

            DBContext db = new DBContext();
            conn = db.getConnection();
            ps = conn.prepareStatement(query);
            {   
                ps.setString(1, categoryID);
            };

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
//        Filter dao = new Filter();
//        List<Blog> list = dao.getBlogbyCategory("1");
//
//        for (Blog b : list) {
//            System.out.println(b);
//        }
//    }
}
