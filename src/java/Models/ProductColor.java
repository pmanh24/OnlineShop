/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hieu
 */
public class ProductColor {
    private int productColorId;
    private String productColorName;
    private int productId;

    public ProductColor(int productColorId, String productColorName, int productId) {
        this.productColorId = productColorId;
        this.productColorName = productColorName;
        this.productId = productId;
    }
    
    public ProductColor(int productId, String productColorName) {
        this.productId = productId;
        this.productColorName = productColorName;
    }

    public int getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(int productColorId) {
        this.productColorId = productColorId;
    }

    public String getProductColorName() {
        return productColorName;
    }

    public void setProductColorName(String productColorName) {
        this.productColorName = productColorName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    
}
