/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class OrderDetail {

    private int orderDetailId;
    private int orderId;
    private int productId;
    private String product_name;
    private String category_name;
    private String product_size_name;
    private int quantity;
    private int productSizeId;
    private int total_cost;
    private Products product;
    private ProductSize productSize;

    public OrderDetail() {
    }

    public OrderDetail(int orderDetailId, int orderId, int productId, int quantity) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderDetail(int orderDetailId, int orderId, int quantity, Products product) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.product = product;
    }

    public OrderDetail(int orderDetailId, int orderId, int quantity, Products product, ProductSize productSize) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.product = product;
        this.productSize = productSize;
    }

    public OrderDetail(int orderDetailId, int orderId, int productId, int quantity, int productSizeId, Products product) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.productSizeId = productSizeId;
        this.product = product;
    }

    public OrderDetail(int orderDetailId, int orderId, int productId, int quantity, int productSizeId) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.productSizeId = productSizeId;
    }

    public OrderDetail(int orderDetailId, int orderId, int productId, int quantity, int productSizeId, Products product, ProductSize productSize) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.productSizeId = productSizeId;
        this.product = product;
        this.productSize = productSize;
    }

    public OrderDetail(int orderDetailId, int productId, String product_name, String category_name, int quantity, int total_cost, String product_size_name) {
        this.orderDetailId = orderDetailId;
        this.productId = productId;
        this.product_name = product_name;
        this.category_name = category_name;
        this.quantity = quantity;
        this.total_cost = total_cost;
        this.product_size_name = product_size_name;
    }

    public OrderDetail(int orderDetailId, String product_name, String category_name, int quantity, int total_cost, String product_size_name) {
        this.orderDetailId = orderDetailId;
        this.product_name = product_name;
        this.category_name = category_name;
        this.quantity = quantity;
        this.total_cost = total_cost;
        this.product_size_name = product_size_name;
    }

    public String getProduct_size_name() {
        return product_size_name;
    }

    public void setProduct_size_name(String product_size_name) {
        this.product_size_name = product_size_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public ProductSize getProductSize() {
        return productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public int getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(int productSizeId) {
        this.productSizeId = productSizeId;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
