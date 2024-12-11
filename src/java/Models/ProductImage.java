/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hieu
 */
public class ProductImage {
    private int productImageId;
    private String imageUrl;
    private int productId;

    public ProductImage(int productImageId, String imageUrl, int productId) {
        this.productImageId = productImageId;
        this.imageUrl = imageUrl;
        this.productId = productId;
    }

    public ProductImage(String imageUrl, int productId) {
        this.imageUrl = imageUrl;
        this.productId = productId;
    }

    public ProductImage() {
    }
    

    public int getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(int productImageId) {
        this.productImageId = productImageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    
}
