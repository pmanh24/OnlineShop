/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hieu
 */
public class BlogImage {
    private int blogImageId;
    private String imageUrl;
    private int blogId;

    public BlogImage(int blogImageId, String imageUrl, int blogId) {
        this.blogImageId = blogImageId;
        this.imageUrl = imageUrl;
        this.blogId = blogId;
    }

    public BlogImage(int blogImageId, String imageUrl) {
        this.blogImageId = blogImageId;
        this.imageUrl = imageUrl;
    }

    public BlogImage() {
    }
    

    public int getBlogImageId() {
        return blogImageId;
    }

    public void setBlogImageId(int blogImageId) {
        this.blogImageId = blogImageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }
    
}
