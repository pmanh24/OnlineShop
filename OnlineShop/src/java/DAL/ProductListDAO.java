/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Category;
import Models.Products;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProductListDAO extends DBContext {

    
    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.category where status=1";
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

    public Category getCategoryByID(int id) {
        String qr = "SELECT * FROM online_shop.category where category_id='"+id+"'";
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
    
    public List<Products> getAllProducts() {
        List<Products> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.products where status=1";
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
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("have error now here: "+e.getMessage());
        }
        return list;
    }
    
    public List<Products> getAllProductsOrderByDate() {
        List<Products> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.products where status=1 order by create_date desc";
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
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public List<Products> getAllProductsOrderByPrice() {
        List<Products> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.products where status=1 order by sale_price ";
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
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    public List<Products> getAllProductsSearchByKey(String key) {
        List<Products> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.products where status=1 and product_name like '%"+key+"%'";
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
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public List<Products> getAllProductsSearchByCateID(int cid) {
        List<Products> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.products where status=1";
        if(cid!=0){
            qr+=" and category_id='"+cid+";'";
        }
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
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public List<Products> getAllProductsSearchByArrayCateID(int[] cid) {
        List<Products> list = new ArrayList<>();
        String qr = "SELECT * FROM online_shop.products where status=1";
        if(cid!=null && cid[0]!=0){
            qr+=" and category_id in(";
            for (int i=0;i<cid.length;i++){
                qr+="'"+cid[i]+"',";
            }
            if (qr.endsWith(",")){
                qr=qr.substring(0,qr.length()-1);
            }
            qr+=")";
        }
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
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    public List<Products> getAllProductByPage(List<Products> list, int start, int end){
        ArrayList<Products> arr = new ArrayList<>();
        for (int i = start; i< end ; i++){
            arr.add(list.get(i));
        }
        return arr;
    }
    
   public static void main(String[] args) {
       int[] cidd = {1,2};
        ProductListDAO c = new ProductListDAO();
       List<Products> list = c.getAllProductsOrderByPrice();
      for(int i=0; i<list.size();i++){
            System.out.println(list.get(i).getProductName());
        }
    }
}
