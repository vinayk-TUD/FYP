package com.app.footprintcalculator.models;

//Pojo model class for user data
public class UsersModel {
    private String userId;
    private String fullName;
    private String phone;
    private String picUrl;
    private String password;

    public UsersModel() { }

    public UsersModel(String userId, String fullName, String phone, String picUrl, String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.picUrl = picUrl;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getPassword() {
        return password;
    }
}
