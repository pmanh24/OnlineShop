/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import DAL.EmployeeDAO;

/**
 *
 * @author Hieu
 */
public class Employees {

    private int employeeId;
    private String fullName;
    private boolean gender;
    private String email;
    private String phoneNumber;
    private int roleId;
    private boolean status;
    private String password;

    public Employees(int employeeId, String fullName, String email, int roleId, String password) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.roleId = roleId;
        this.password = password;
    }

    public Employees(int employeeId, String fullName, boolean gender, String email, String phoneNumber, int roleId, boolean status) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.status = status;
    }

    public Employees() {
    }

    
    
    

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole(){
        EmployeeDAO dao = EmployeeDAO.getInstance();
        for(Role r : dao.getAllRoles()){
            if(r.getRoleId() == roleId){
                return r.getRoleName();
            }
        }
        return null;
    }

}
