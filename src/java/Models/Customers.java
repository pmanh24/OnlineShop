/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;

/**
 *
 * @author Hieu
 */
public class Customers {

    private int customerId;
    private String fullName;
    private boolean gender;
    private String email;
    private String phoneNumber;
    private String address;
    private int status;
    private String password;
    private Date lastUpdate;
    private int updateBy;
    private String note;

    public Customers() {
    }

    public Customers(int customerId, String fullName, boolean gender, String email, String phoneNumber, String address, int status, String password) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.password = password;
    }

    public Customers(int customerId, String fullName, boolean gender, String email, String phoneNumber, String address, int status, String password, Date lastUpdate, int updateBy, String note) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.password = password;
        this.lastUpdate = lastUpdate;
        this.updateBy = updateBy;
        this.note = note;
    }
    public Customers(int customerId, String fullName, boolean gender, String email, String phoneNumber, String address, int status, String password, Date lastUpdate, int updateBy) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.password = password;
        this.lastUpdate = lastUpdate;
    }
    public Customers(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Customers(int customerId, String fullName, boolean gender, String email, String phoneNumber, String address, int status) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
    }
    
    

    public Customers(int customerId, String fullName, String email, String password) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
