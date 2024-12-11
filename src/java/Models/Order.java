/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import DAL.OrderDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class Order {

    private int orderId;
    private Date orderDate;
    private long totalCost;
    private int status;
    private String status_name;
    private int customerId;
    private String address;
    private String phoneNumber;
    private int employeeId;
    private Customers customer;
    private OrderDetail orderDetail;
    private Employees employee;
    private String productName;
    private List<OrderDetail> orderDetails;
    private Map<Integer, Integer> quantities = new HashMap<>();
    private Map<Integer, String> productNames;
    private Status statusO;

    public Order() {
    }

    public Order(int orderId, Date orderDate, long totalCost, int status, int customerId, String address, String phoneNumber, int employeeId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;
        this.customerId = customerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.employeeId = employeeId;
    }

    public Order(Date orderDate, long totalCost, int status, int customerId, String address, String phoneNumber) {
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;
        this.customerId = customerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Order(int orderId, Date orderDate, long totalCost, int status, int customerId, String address, String phoneNumber) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;
        this.customerId = customerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Order(int orderId, Date orderDate, long totalCost, String status_name, int customerId, String address, String phoneNumber) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status_name = status_name;
        this.customerId = customerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

//    public Order(int orderId, Date orderDate, long totalCost, int status, String address, String phoneNumber, Customers customer) {
//        this.orderId = orderId;
//        this.orderDate = orderDate;
//        this.totalCost = totalCost;
//        this.status = status;
//        this.address = address;
//        this.phoneNumber = phoneNumber;
//        this.customer = customer;
//    }
    public Order(int orderId, Date orderDate, long totalCost, int status, String address, String phoneNumber, Customers customer) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.customer = customer;
        this.orderDetails = new ArrayList<>();
        this.productNames = new HashMap<>(); // Initialize the map
    }

    public void addOrderDetailList(OrderDetail orderDetail) {
        orderDetails.add(orderDetail); // Add OrderDetail to the list
    }

    public List<OrderDetail> getOrderDetailsList() {
        return orderDetails; // Return the list of order details
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public void addProductNameList(int productId, String productName) {
        this.productNames.put(productId, productName); // Use productId as key
    }

    public Map<Integer, String> getProductNamesList() {
        return productNames; // Return the map
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail); // Add OrderDetail to the list
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails; // Return the list of order details
    }

    public void addProductName(int productId, String productName) {
        this.productNames.put(productId, productName); // Use productId as key
    }

    public Map<Integer, String> getProductNames() {
        return productNames; // Return the map
    }

    public void addQuantity(int productId, int quantity) {
        quantities.merge(productId, quantity, Integer::sum); // Sum quantities for the same product ID
    }

    public int getTotalQuantityForProduct(int productId) {
        return quantities.getOrDefault(productId, 0);
    }

    public Map<Integer, Integer> getQuantities() {
        return quantities;
    }

    public Order(int orderId, Date orderDate, long totalCost, Status statusO, Employees employee, Customers customer, OrderDetail orderDetail, String productName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.statusO = statusO;
        this.employee = employee;
        this.customer = customer;
        this.orderDetail = orderDetail;
        this.productName = productName;
    }

    public Order(int orderId, Date orderDate, long totalCost, int status, String address, String phoneNumber, Customers customer, Employees employee) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.customer = customer;
        this.employee = employee;
    }

    public Status getStatusO() {
        return statusO;
    }

    public void setStatusO(Status statusO) {
        this.statusO = statusO;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Customers getCustomer() { // Getter for Customer
        return customer;
    }

    public void setCustomer(Customers customer) { // Setter for Customer
        this.customer = customer;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatusName() {
        OrderDAO orderDAO = new OrderDAO();
        return orderDAO.getStatusNameById(status);
    }

}
