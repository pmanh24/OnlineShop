/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hieu
 */
public class Blogs {

    private int blogId;
    private int categoryId;
    private int employeeId;
    private String title;
    private Date updateDate;
    private String detail;
    private String short_content;
    private int status;
    private String image;
    private String categoryName;
    private List<BlogImage> images = new ArrayList<>();
    
    public Blogs(int blogId, int categoryId, int employeeId, String title, Date updateDate, String detail, String short_content, int status) {
        this.blogId = blogId;
        this.categoryId = categoryId;
        this.employeeId = employeeId;
        this.title = title;
        this.updateDate = updateDate;
        this.detail = detail;
        this.short_content = short_content;
        this.status = status;
    }

    public Blogs(int blogId, String title, Date updateDate, String detail, String short_content, int status, String image, String categoryName) {
        this.blogId = blogId;
        this.title = title;
        this.updateDate = updateDate;
        this.detail = detail;
        this.short_content = short_content;
        this.status = status;
        this.image = image;
        this.categoryName = categoryName;
    }

    public Blogs(String title, Date updateDate, String detail, String short_content, int status) {
        this.title = title;
        this.updateDate = updateDate;
        this.detail = detail;
        this.short_content = short_content;
        this.status = status;
    }

    public Blogs(int blogId, int categoryId, String title, Date updateDate, String detail, String short_content, int status) {
        this.blogId = blogId;
        this.categoryId = categoryId;
        this.title = title;
        this.updateDate = updateDate;
        this.detail = detail;
        this.short_content = short_content;
        this.status = status;
    }

  
    
    
    
    
    
    public Blogs(int blogId, int categoryId, int employeeId, String title, Date updateDate, String detail) {
        this.blogId = blogId;
        this.categoryId = categoryId;
        this.employeeId = employeeId;
        this.title = title;
        this.updateDate = updateDate;
        this.detail = detail;
         this.images = new ArrayList<>();
    }

    public Blogs(int blogId, int categoryId, String title, Date updateDate, String detail) {
        this.blogId = blogId;
        this.categoryId = categoryId;
        this.title = title;
        this.updateDate = updateDate;
        this.detail = detail;
         this.images = new ArrayList<>();
    }

    public Blogs() {
        this.images = new ArrayList<>();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public void addImage(BlogImage image) {
        this.images.add(image);
    }

    public List<BlogImage> getImages() {
        return images;
    }

    public void setImages(List<BlogImage> images) {
        this.images = images;
    }

    public String getShort_content() {
        return short_content;
    }

    public void setShort_content(String short_content) {
        this.short_content = short_content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
