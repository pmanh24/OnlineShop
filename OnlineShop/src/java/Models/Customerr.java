/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author T
 */
public class Customerr {
    private int id;
    private String fullname;
    private int gender;
    private String email;
    private int numberphone;
    private String address;

    public Customerr() {
    }

    public Customerr(int id, String fullname, int gender, String email, int numberphone, String address) {
        this.id = id;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.numberphone = numberphone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(int numberphone) {
        this.numberphone = numberphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    @Override
//    public String toString() {
//        return "Customerr{" + "id=" + id + ", fullname=" + fullname + ", gender=" + gender + ", numberphone=" + numberphone + ", address=" + address + '}';
//    }
    
    
    
}
