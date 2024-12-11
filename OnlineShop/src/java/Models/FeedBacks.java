/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T
 */

public class FeedBacks {

    private int feedback_id;
    private int product_id;
    private int customer_id;
    private int orderId;
    private int rated_star;
    private int status;
    private String content;
    private String product;
    private String customer;
    private String email;
    private String phone_number;
    private Integer size_id;
    private String image;

    private List<String> imageFileNames;
    private String createDate;
    private String productSizeName; // New field
    private String productColor;     // New field
    public FeedBacks() {
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public FeedBacks(int feedback_id, int product_id, int customer_id, int rated_star, int status, String content) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.customer_id = customer_id;
        this.rated_star = rated_star;
        this.status = status;
        this.content = content;
    }

    public FeedBacks(int rated_star, int status, String content, String product, String customer, int feedback_id, String image) {
        this.rated_star = rated_star;
        this.status = status;
        this.content = content;
        this.product = product;
        this.customer = customer;
        this.feedback_id = feedback_id;
        this.image = image;
    }

    public FeedBacks(int feedback_id, int rated_star, int status, String content, String product, String customer, String image) {
        this.feedback_id = feedback_id;
        this.rated_star = rated_star;
        this.status = status;
        this.content = content;
        this.product = product;
        this.customer = customer;
        this.image = image;
    }

    public FeedBacks(int feedback_id, int rated_star, String content, String product, String customer, String productSizeName, String productColor,List<String> imageFileNames) {
        this.feedback_id = feedback_id;
        this.rated_star = rated_star;
        this.content = content;
        this.product = product;
        this.customer = customer;
        this.productSizeName = productSizeName;
        this.productColor = productColor;
        this.imageFileNames = imageFileNames;
    }
    
    
    
    
    
    public FeedBacks(int feedback_id, int rated_star, int status, String content, String product, String customer, String email, String phone_number, String image) {
        this.feedback_id = feedback_id;
        this.rated_star = rated_star;
        this.status = status;
        this.content = content;
        this.product = product;
        this.customer = customer;
        this.email = email;
        this.phone_number = phone_number;
        this.image = image;
    }

    public FeedBacks(int feedback_id, int product_id, int customer_id, int rated_star, int status, String content, int size_id, List<String> imageFileNames) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.customer_id = customer_id;
        this.rated_star = rated_star;
        this.status = status;
        this.content = content;
        this.size_id = size_id;
        this.imageFileNames = imageFileNames;
    }
    public FeedBacks(int feedback_id, int product_id, int rated_star, int status, String content, int orderId, List<String> imageFileNames) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.rated_star = rated_star;
        this.status = status;
        this.content = content;
        this.orderId = orderId;
        this.imageFileNames = imageFileNames;
    }
    public FeedBacks(int feedback_id, int product_id, int rated_star, int status, String content, int orderId, List<String> imageFileNames,int size_id, String productSizeName, String productColor) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.rated_star = rated_star;
        this.status = status;
        this.content = content;
        this.orderId = orderId;
        this.imageFileNames = imageFileNames;
        this.size_id = size_id;
        this.productSizeName = productSizeName;
        this.productColor = productColor;
    }
    public FeedBacks(int feedback_id, int product_id, int customer_id, int orderId, int rated_star, int status, String content, String product, String customer, String email, String phone_number, int size_id, List<String> imageFileNames) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.customer_id = customer_id;
        this.orderId = orderId;
        this.rated_star = rated_star;
        this.status = status;
        this.content = content;
        this.product = product;
        this.customer = customer;
        this.email = email;
        this.phone_number = phone_number;
        this.size_id = size_id;
        this.imageFileNames = imageFileNames;
    }

    public FeedBacks(int feedback_id, int product_id, int customer_id, int rated_star, String content,
            String product, String customer, Integer size_id, List<String> imageFileNames,
            String createDate, String productSizeName, String productColor) {
        this.feedback_id = feedback_id;
        this.product_id = product_id;
        this.customer_id = customer_id;
        this.rated_star = rated_star;
        this.content = content;
        this.product = product;
        this.customer = customer;
        this.size_id = size_id;
        this.imageFileNames = imageFileNames;
        this.createDate = createDate;
        this.productSizeName = productSizeName; // Initialize new field
        this.productColor = productColor;         // Initialize new field
    }

    public String getProductSizeName() {
        return productSizeName;
    }

    public void setProductSizeName(String productSizeName) {
        this.productSizeName = productSizeName;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<String> getImageFileNames() {
        return imageFileNames;
    }

    public void setImageFileNames(List<String> imageFileNames) {
        this.imageFileNames = imageFileNames;
    }

    public FeedBacks(int feedback_id, int rated_star) {
        this.feedback_id = feedback_id;
        this.rated_star = rated_star;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Integer getSize_id() {
        return size_id;
    }

    public void setSize_id(Integer size_id) {
        this.size_id = size_id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getRated_star() {
        return rated_star;
    }

    public void setRated_star(int rated_star) {
        this.rated_star = rated_star;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
