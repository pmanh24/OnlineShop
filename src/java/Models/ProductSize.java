/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import DAL.OrderDAO;

/**
 *
 * @author Hieu
 */
public class ProductSize {

    private int productSizeId;
    private String productSizeName;
    private int productId;
    private int quantity;
    private String productColor;
    private int importPrice;     // Converted to int, if representing a whole number (e.g., in cents)
    private int prices;          // Converted to int, if representing a whole number (e.g., in cents)
    private int hold;

    public ProductSize() {
    }

    public ProductSize(int productSizeId, String productSizeName, int productId, int quantity, String productColor) {
        this.productSizeId = productSizeId;
        this.productSizeName = productSizeName;
        this.productId = productId;
        this.quantity = quantity;
        this.productColor = productColor;
    }
    public ProductSize(int productSizeId, String productSizeName, int productId, int quantity, String productColor, int prices) {
        this.productSizeId = productSizeId;
        this.productSizeName = productSizeName;
        this.productId = productId;
        this.quantity = quantity;
        this.productColor = productColor;
        this.prices = prices;
    }

    public ProductSize(int productSizeId, String productSizeName, int quantity, String productColor) {
        this.productSizeId = productSizeId;
        this.productSizeName = productSizeName;
        this.quantity = quantity;
        this.productColor = productColor;
    }

    public ProductSize(int productSizeId, String productSizeName, int productId, int quantity, String productColor, int importPrice, int prices, int hold) {
        this.productSizeId = productSizeId;
        this.productSizeName = productSizeName;
        this.productId = productId;
        this.quantity = quantity;
        this.productColor = productColor;
        this.importPrice = importPrice;
        this.prices = prices;
        this.hold = hold;
    }


    public ProductSize(int productSizeId, String productSizeName, int productId, int quantity, String productColor, int importPrice, int prices) {
        this.productSizeId = productSizeId;
        this.productSizeName = productSizeName;
        this.productId = productId;
        this.quantity = quantity;
        this.productColor = productColor;
        this.importPrice = importPrice;
        this.prices = prices;
    }

    public Products getProduct() {
        OrderDAO dao = new OrderDAO();
        return dao.getProductByProductSizeId(productSizeId);
    }

    public int getPrices() {
        return prices;
    }

    public void  setPrices(int prices) {
        this.prices = prices;
    }
    
    
    
    
    public ProductSize(int productSizeId, String productSizeName, int productId) {
        this.productSizeId = productSizeId;
        this.productSizeName = productSizeName;
        this.productId = productId;
    }

    public int getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(int importPrice) {
        this.importPrice = importPrice;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public ProductSize(int productId, String productSizeName) {
        this.productId = productId;
        this.productSizeName = productSizeName;
    }

    public int getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(int productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getProductSizeName() {
        return productSizeName;
    }

    public void setProductSizeName(String productSizeName) {
        this.productSizeName = productSizeName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    
    
    

}
