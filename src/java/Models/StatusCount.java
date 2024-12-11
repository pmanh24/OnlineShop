/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class StatusCount {

    private int statusId;
    private String  statusName;
    private int orderCount;
    private String roleId;
    
    public StatusCount() {
    }

    public StatusCount(int statusId, String statusName, int orderCount) {
        this.statusId = statusId;
        this.statusName = statusName;
        this.orderCount = orderCount;
    }

    public StatusCount(int statusId, String statusName, int orderCount, String roleId) {
        this.statusId = statusId;
        this.statusName = statusName;
        this.orderCount = orderCount;
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
    
    
}
