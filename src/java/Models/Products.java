/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.List;

/**
 *
 * @author Admin
 */
public class Products {

    private int productId;
    private String productName;
    private int originalPrice;
    private int salePrice;
    private int numberLeft;
    private String createDate;
    private String imageUrl;
    private String description;
    private String briefInformation;
    private int status;
    private Category category;
    private List<ProductImage> images; // Multiple images
    private List<ProductColor> colors; // Multiple colors
    private List<ProductSize> sizes;   // Multiple sizes
    private int featured;
    private int hold;

    public Products() {
    }

    public Products(int productId, String productName, 
            int originalPrice, int salePrice, 
            int numberLeft,String imageUrl, 
            int status,String description, String briefInformation,  List<ProductImage> images,
            int featured,Category category) {
        this.productId = productId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.numberLeft = numberLeft;
        this.imageUrl = imageUrl;
        this.status = status;
        this.description = description;
        this.briefInformation = briefInformation;
        this.featured = featured;
        this.category = category;
    }

    public Products(int productId, String productName, String createDate, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.createDate = createDate;
        this.category = category;
    }

    public Products(int productId, String productName, int originalPrice, int salePrice, int numberLeft, String createDate, String imageUrl, String description, int status, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.numberLeft = numberLeft;
        this.createDate = createDate;
        this.imageUrl = imageUrl;
        this.description = description;
        this.status = status;
        this.category = category;
    }

    public Products(int productId, String productName, int originalPrice, int salePrice, int numberLeft, String createDate, String imageUrl, String description, String briefInformation, int status, Category category, int featured) {
        this.productId = productId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.numberLeft = numberLeft;
        this.createDate = createDate;
        this.imageUrl = imageUrl;
        this.description = description;
        this.briefInformation = briefInformation;
        this.status = status;
        this.category = category;
        this.featured = featured;
    }

    public Products(int productId, String productName, int originalPrice, int salePrice, int numberLeft, String createDate, String imageUrl, String description, String briefInformation, int status, Category category, List<ProductImage> images, List<ProductColor> colors, List<ProductSize> sizes) {
        this.productId = productId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.numberLeft = numberLeft;
        this.createDate = createDate;
        this.imageUrl = imageUrl;
        this.description = description;
        this.briefInformation = briefInformation;
        this.status = status;
        this.category = category;
        this.images = images;
        this.colors = colors;
        this.sizes = sizes;
    }

    public Products(int productId, String productName, String imageUrl, int salePrice) {
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.salePrice = salePrice;
    }

    public Products(int productId, String productName, int salePrice, String imageUrl, List<ProductSize> sizes) {
        this.productId = productId;
        this.productName = productName;
        this.salePrice = salePrice;
        this.imageUrl = imageUrl;
        this.sizes = sizes;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    
    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured ? 1 : 0;
    }

    public int getCategoryId() {
        return category != null ? category.getCategoryId() : 0; // Assuming Category has getCategoryId()
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public List<ProductColor> getColors() {
        return colors;
    }

    public void setColors(List<ProductColor> colors) {
        this.colors = colors;
    }

    public List<ProductSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<ProductSize> sizes) {
        this.sizes = sizes;
    }

    public String getBriefInformation() {
        return briefInformation;
    }

    public void setBriefInformation(String briefInformation) {
        this.briefInformation = briefInformation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getNumberLeft() {
        return numberLeft;
    }

    public void setNumberLeft(int numberLeft) {
        this.numberLeft = numberLeft;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
