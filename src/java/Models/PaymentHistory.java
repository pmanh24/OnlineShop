/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author Hieu
 */
public class PaymentHistory {

    private int payId;
    private Date payDate;
    private String payMethod;
    private int orderId;
    private int customerId;

    public PaymentHistory() {
    }

    public PaymentHistory(int payId, Date payDate, String payMethod, int orderId, int customerId) {
        this.payId = payId;
        this.payDate = payDate;
        this.payMethod = payMethod;
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    
}
