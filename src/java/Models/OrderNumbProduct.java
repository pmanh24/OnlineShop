/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class OrderNumbProduct {

    private Order order;
    private int number;
    private Products firstProduct;

    public OrderNumbProduct() {
    }

    public OrderNumbProduct(Order order, int number, Products firstProduct) {
        this.order = order;
        this.number = number;
        this.firstProduct = firstProduct;
    }

    public Products getFirstProduct() {
        return firstProduct;
    }

    public void setFirstProduct(Products firstProduct) {
        this.firstProduct = firstProduct;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
