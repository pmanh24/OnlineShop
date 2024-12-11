/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hieu
 */
public class Sliders {
    private int sliderId;
    private String imageUrl;
    private String title;
    private String backLink;
    private boolean status;
    private int statusNum;
    private String note;
    private int employeeId;
    
    public Sliders() {
    }

    
    public Sliders(int sliderId, String imageUrl, String title, String backLink) {
        this.sliderId = sliderId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.backLink = backLink;
    }

    public Sliders(int sliderId, String imageUrl, String title, String backLink, boolean status, String note, int employeeId) {
        this.sliderId = sliderId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.backLink = backLink;
        this.status = status;
        this.note = note;
        this.employeeId = employeeId;
    }
        public Sliders(int sliderId, String imageUrl, String title, String backLink, int statusNum, String note) {
        this.sliderId = sliderId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.backLink = backLink;
        this.statusNum = statusNum;
        this.note = note;
    }
    
    


    public int getStatusNum() {
        return statusNum;
    }

    public void setStatusNum(int statusNum) {
        this.statusNum = statusNum;
    }

    public Sliders(int sliderId, String imageUrl, String title, String backLink, int statusNum, String note, int employeeId) {
        this.sliderId = sliderId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.backLink = backLink;
        this.statusNum = statusNum;
        this.note = note;
        this.employeeId = employeeId;
    }

    public int getSliderId() {
        return sliderId;
    }

    public void setSliderId(int sliderId) {
        this.sliderId = sliderId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackLink() {
        return backLink;
    }

    public void setBackLink(String backLink) {
        this.backLink = backLink;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    
}
