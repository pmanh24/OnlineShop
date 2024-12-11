/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Sliders;
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
public class SliderListDAO extends DBContext {
    
    public List<Sliders> getAllSlider() {
        List<Sliders> list = new ArrayList<>();

        String qr = "SELECT * FROM online_shop.sliders" ;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Sliders c = new Sliders(
                        rs.getInt("slider_id"),
                        rs.getString("image_url"),
                        rs.getString("title"),
                        rs.getString("back_link"),
                        rs.getInt("status"),
                        rs.getString("note"),
                        rs.getInt("employee_id")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public List<Sliders> searchSliderByKey(String key) {
        List<Sliders> list = new ArrayList<>();

        String qr = "SELECT * FROM online_shop.sliders where title like '%"+key+"%' or back_link  like '%"+key+"%'" ;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Sliders c = new Sliders(
                        rs.getInt("slider_id"),
                        rs.getString("image_url"),
                        rs.getString("title"),
                        rs.getString("back_link"),
                        rs.getInt("status"),
                        rs.getString("note"),
                        rs.getInt("employee_id")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public List<Sliders> getAllSliderByStatus(int status) {
        List<Sliders> list = new ArrayList<>();

        String qr = "SELECT * FROM online_shop.sliders where status="+status ;
        try (
                 Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(qr)) {
            while (rs.next()) {
                Sliders c = new Sliders(
                        rs.getInt("slider_id"),
                        rs.getString("image_url"),
                        rs.getString("title"),
                        rs.getString("back_link"),
                        rs.getInt("status"),
                        rs.getString("note"),
                        rs.getInt("employee_id")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    public void updateStatusOfSlider(int sliderId,int statusId) {
        
        String query = "UPDATE `online_shop`.`sliders` SET `status` ="+statusId+" , WHERE `slider_id` = "+sliderId;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    
    
    
}
