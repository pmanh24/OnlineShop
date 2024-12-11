/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author T
 */
public class Blog {
    private int blogID;
    private String title;
    private String detail;
    private Date updateDate;
    private String image;
    private String short_content;
    private String category_name;
    public Blog() {
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Blog(int blogID, String title, String short_content,Date updateDate, String image) {
        this.blogID = blogID;
        this.title = title;
        this.short_content = short_content;
        this.updateDate = updateDate;
        this.image = image;
    }

    public Blog(int blogID, String title, String detail, Date updateDate, String image, String category_name) {
        this.blogID = blogID;
        this.title = title;
        this.detail = detail;
        this.updateDate = updateDate;
        this.image = image;
        this.category_name = category_name;
    }
    
    public Blog(int blogID, String title, Date updateDate, String image, String detail) {
        this.blogID = blogID;
        this.title = title;
        this.updateDate = updateDate;
        this.image = image;
        this.detail = detail;
    }
    
    public int getBlogID() {
        return blogID;
    }

    public String getShort_content() {
        return short_content;
    }

    public void setShort_content(String short_content) {
        this.short_content = short_content;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

   

    
    
    
    
}
